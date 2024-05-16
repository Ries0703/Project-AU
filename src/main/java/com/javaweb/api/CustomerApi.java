package com.javaweb.api;

import com.javaweb.model.dto.*;
import com.javaweb.model.request.OrderRequestDto;
import com.javaweb.model.response.CustomerLoginResponse;
import com.javaweb.model.response.MessageResponse;
import com.javaweb.service.CustomerService;
import com.javaweb.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;



@RestController
@RequestMapping(value = "/customers")
// url: localhost:8081/customers/[the String variable in the @Mapping],
// http method : @PostMapping -> POST method, and so on
// with @RequestParam:  http://localhost:8081/customers/profile?id=10 will make id = 10, use for Updates
//                      http://localhost:8081/customers/profile?id=   will make id = null, use for Inserts
public class CustomerApi {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RestaurantService restaurantService;

    /*
    * Request body example
    *
    * {
        "email": "example@example.com",
        "password": "securePassword123"
      }
      *
      *
      * response body example
      *
      * {
            "message": "Customer logged in",
            "customerId": 57 //use this to do other operations, like placing orders or see customer profile
        }
    * */
    @PostMapping(value = "/login")
    public ResponseEntity<CustomerLoginResponse> login(@RequestBody CustomerLoginDto customerLoginDto) {
        if (!customerService.existCustomer(customerLoginDto.getEmail(), customerLoginDto.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new CustomerLoginResponse("Incorrect username or password", null));
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new CustomerLoginResponse("Customer logged in", customerService.getCustomerId(customerLoginDto.getEmail())));
    }


    /*
    {
        "email": "example@example.com",
        "password": "securePassword123",
        "firstName": "John",
        "lastName": "Doe",
        "phoneNumber": "123-456-7890"
    }
*/
    @PutMapping(value = "/signup")
    public ResponseEntity<MessageResponse> signUp(@RequestBody CustomerSignUpDto customerSignUpDto) {
        if (customerService.existEmail(customerSignUpDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new MessageResponse("Email already registered"));
        }
        customerService.createCustomer(customerSignUpDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new MessageResponse("Customer account registered"));
    }


    /*
    request body example
    {
        "customerId": 2, //use customerId gotten from login() here
        "vipType": "MONTHLY"
    }
    remember to use all uppercase for vipType
    * */
    @PostMapping(value = "/membership")
    public ResponseEntity<MessageResponse> registerMembership(@RequestBody MembershipDto membershipDto) {
        if (customerService.registerMembership(membershipDto)) {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new MessageResponse("Membership registered"));
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new MessageResponse("You cannot renew because your current membership has not yet expired"));
    }


    /*
    * this is just a fake api to cancel membership,
    * we are not able to implement this due to skill issues
    * */
    @DeleteMapping(value = "/membership")
    public ResponseEntity<MessageResponse> cancelMembership(@RequestBody MembershipDto membershipDto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new MessageResponse("Membership canceled, your current membership remains till the expiration date"));
    }

    /*
     * Your request should look something like this
     * !!!!! customerPostcode IS REQUIRED
     * localhost:8081/customers/restaurants?customerPostcode=12342&keyword=RestaurantName&category=CategoryName&distanceFrom=1&distanceTo=10&ratingFrom=1&ratingTo=4
     *
     * if there are no search filter at all
     * localhost:8081/customers/restaurants?customerPostcode=12342&keyword=&category=&distanceFrom=&distanceTo=&ratingFrom=&ratingTo=
     *
     * See RestaurantSearchRequest.java and RestaurantConverter.java for more detail
     *
     * */
    @GetMapping(value = "/restaurants")
    public List<RestaurantDto> findRestaurant(@RequestParam Map<String, Object> params,
                                              @RequestParam("customerPostcode") Integer customerPostcode) {
        return restaurantService.getRestaurantByParams(params, customerPostcode);
    }


    // url example: http://localhost:8081/customers/profile?id=10
    @GetMapping(value = "/profile")
    public CustomerProfileDto getCustomerProfile(@RequestParam(name = "id") Integer customerId) {
        return customerService.getCustomerProfile(customerId);
    }


    /*
    * request body example
    *
    * {
        "customerId": 2, //use customerId gotten from login() here
        "restaurantId" : 120,
        "stars": 2,
        "content": "sucks af"
      }
    *
    * */
    @PutMapping(value = "/feedback")
    public ResponseEntity<MessageResponse> giveFeedback(@RequestBody FeedbackDto feedbackDto) {
        customerService.giveFeedback(feedbackDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new MessageResponse("Feedback sent"));
    }

    /*
    * url example
    *
    * http://localhost:8081/customers/restaurant?id=102
    * */
    @GetMapping(value = "/restaurant")
    public RestaurantProfileDto getRestaurantProfile(@RequestParam(name = "id") Integer restaurantId) {
        return restaurantService.findRestaurantProfileById(restaurantId);
    }


    /* request body example
    *
    * {
            "customerId": 6,
            "restaurantId": 120,
            "total": 100.23,
            "customerPostcode": 202221,
            "customerAddress": "hereherherherh",
            "cardNumber": "213512435",
            "ccv": "223",
            "expiryDate": "02/29",
            "dishRequestDtoList": [
                {
                    "dishId": 1,
                    "quantity": 3
                },
                {
                    "dishId": 2,
                    "quantity": 1
                },
                {
                    "dishId": 3,
                    "quantity": 4
                }
            ],
            "comboRequestDtoList": [
                {
                    "comboId": 1,
                    "quantity": 3
                },
                {
                    "comboId": 5,
                    "quantity": 3
                },
                {
                    "comboId": 2,
                    "quantity": 3
                }
            ]
        }
    * */
    @PostMapping(value = "/order")
    public ResponseEntity<MessageResponse> placeOrder(@RequestBody OrderRequestDto orderDto) {
        customerService.placeOrder(orderDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new MessageResponse("Order placed"));
    }
}