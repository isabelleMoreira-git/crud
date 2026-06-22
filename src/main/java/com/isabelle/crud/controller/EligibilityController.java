package com.isabelle.crud.controller;

import com.isabelle.crud.entity.Customer;
import com.isabelle.crud.eligibility.EligibilityService;
import com.isabelle.crud.exception.CustomerNotFoundException;
import com.isabelle.crud.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eligibility")
public class EligibilityController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EligibilityService eligibilityService;

    @GetMapping("/{document}")
    public ResponseEntity<Boolean> checkEligibility(
            @PathVariable String document) {

        try {
            Customer customer = customerService.getCustomerByDocument(document);
            return ResponseEntity.ok().body(eligibilityService.isEligible(customer));
        }
        catch(CustomerNotFoundException e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }catch(RuntimeException e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }
}
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
