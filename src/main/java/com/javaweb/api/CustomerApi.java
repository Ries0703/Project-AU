package com.javaweb.api;

import com.javaweb.model.dto.CustomerLoginDto;
import com.javaweb.model.dto.CustomerSignUpDto;
import com.javaweb.model.dto.MembershipDto;
import com.javaweb.model.dto.RestaurantDto;
import com.javaweb.model.entity.CustomerEntity;
import com.javaweb.model.response.CustomerLoginResponse;
import com.javaweb.model.response.CustomerSignUpResponse;
import com.javaweb.service.CustomerService;
import com.javaweb.service.RestaurantService;
import org.apache.commons.lang3.StringUtils;
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
    public ResponseEntity<CustomerSignUpResponse> signUp(@RequestBody CustomerSignUpDto customerSignUpDto) {
        if (customerService.existEmail(customerSignUpDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new CustomerSignUpResponse("Email already registered"));
        }
        customerService.createCustomer(customerSignUpDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new CustomerSignUpResponse("Customer account registered"));
    }

    @PostMapping(value = "/membership")
    public ResponseEntity<String> registerMembership(@RequestBody MembershipDto membershipDto) {
        customerService.registerMembership(membershipDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .contentType(MediaType.APPLICATION_JSON)
                .body("Membership registered");
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
}