package com.javaweb.service;


import com.javaweb.model.dto.*;
import com.javaweb.model.request.OrderRequestDto;

public interface CustomerService {
    boolean existCustomer(String email, String password);
    boolean existEmail(String email);
    Integer getCustomerId(String email);
    void createCustomer(CustomerSignUpDto customerSignUpDto);
    boolean registerMembership(MembershipDto membershipDto);
    CustomerProfileDto getCustomerProfile(Integer customerId);
    void giveFeedback(FeedbackDto feedbackDto);
    void placeOrder(OrderRequestDto orderRequestDto);
}
