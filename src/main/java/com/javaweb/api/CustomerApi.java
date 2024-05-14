package com.javaweb.api;

import com.javaweb.model.dto.*;
import com.javaweb.model.entity.CustomerEntity;
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
public class CustomerApi {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public List<CustomerEntity> getAllCustomer() {
        return customerService.getAllCustomers();
    }

    @PostMapping(value = "/login")
    public ResponseEntity<CustomerLoginResponse> login(@RequestBody CustomerLoginDto customerLoginDto) {
        if (!customerService.existCustomer(customerLoginDto.getEmail(), customerLoginDto.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new CustomerLoginResponse("Incorrect username or password"));
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new CustomerLoginResponse("Customer logged in"));
    }

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

    @GetMapping(value = "/profile")
    public CustomerProfileDto getCustomerProfile(@RequestParam(name = "id") Integer customerId) {
        return customerService.getCustomerProfile(customerId);
    }
}