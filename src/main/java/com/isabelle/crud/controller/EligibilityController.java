package com.isabelle.crud.controller;

import com.isabelle.crud.entity.Customer;
import com.isabelle.crud.eligibility.EligibilityService;
import com.isabelle.crud.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eligibility")
public class EligibilityController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EligibilityService eligibilityService;

    // Injeção de dependência pelo construtor
//    private final CustomerService customerService;
//    private final EligibilityService eligibilityService;
//
//    public EligibilityController(
//            CustomerService customerService,
//            EligibilityService eligibilityService) {
//
//        this.customerService = customerService;
//        this.eligibilityService = eligibilityService;
//    }

    @GetMapping("/{document}")
    public boolean checkEligibility(
            @PathVariable String document) {

        //todo: substituir lambda
        Customer customer = customerService
                .getCustomerByDocument(document)
                .orElseThrow(() ->
                        new RuntimeException("Cliente não encontrado"));

        return eligibilityService.isEligible(customer);
    }
}
