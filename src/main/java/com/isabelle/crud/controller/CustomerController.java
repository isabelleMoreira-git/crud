package com.isabelle.crud.controller;

import com.isabelle.crud.dto.CustomerRequestDTO;
import com.isabelle.crud.dto.CustomerResponseDTO;
import com.isabelle.crud.entity.Customer;
import com.isabelle.crud.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // Injeção de Dependência pelo construtor
//    private final CustomerService customerService;
//
//    public CustomerController(CustomerService customerService) {
//        this.customerService = customerService;
//    }

    @PostMapping
    public CustomerResponseDTO createCustomer(
            @Valid @RequestBody CustomerRequestDTO dto) {

        Customer customer = new Customer();

        customer.setDocument(dto.getDocument());

        customer.setIndicationDocumentType(
                dto.getIndicationDocumentType());

        customer.setCustomerCompanyFlag(
                dto.getCustomerCompanyFlag());

        customer.setMcc(dto.getMcc());

        customer.setAnnualTpv(
                dto.getAnnualTpv());

        Customer savedCustomer =
                customerService.createCustomer(customer);

        return new CustomerResponseDTO(
                savedCustomer.getId(),
                savedCustomer.getDocument(),
                savedCustomer.getIndicationDocumentType(),
                savedCustomer.getCustomerCompanyFlag(),
                savedCustomer.getMcc(),
                savedCustomer.getAnnualTpv()
        );
    }

    @GetMapping
    public List<CustomerResponseDTO> getAllCustomers() {

        //todo: substituir lambda
        return customerService
                .getAllCustomers()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public CustomerResponseDTO getCustomerById(
            @PathVariable Long id) {

        //todo: substituir lambda
        Customer customer =
                customerService
                        .getCustomerById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Cliente não encontrado"));

        return toResponseDTO(customer);
    }

    @DeleteMapping("/{id}")
    public void deleletCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
    }

    @DeleteMapping("/{document}/{type}")
    public void deleteByDocumentAndType(@PathVariable String document,
                                        @PathVariable String type){
        customerService.deleteByDocumentAndType(document,type);
    }

    @PutMapping("/{id}")
    public Customer updateCustomer(
        @PathVariable Long id,
        @Valid @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer);
    }

    @GetMapping("/document/{document}")
    public CustomerResponseDTO getCustomerByDocument(
            @PathVariable String document) {

        //todo: substituir lambda
        Customer customer =
                customerService
                        .getCustomerByDocument(document)
                        .orElseThrow(() ->
                                new RuntimeException("Cliente não encontrado"));

        return toResponseDTO(customer);
    }

    @GetMapping("/document/{document}/{type}")
    public CustomerResponseDTO getCustomerByDocumentAndType(
            @PathVariable String document, @PathVariable String type) {

        //todo: substituir lambda
        Customer customer =
                customerService.getCustomerByDocumentAndType(document,type);


        return toResponseDTO(customer);
    }

    private CustomerResponseDTO toResponseDTO(Customer customer) {

        return new CustomerResponseDTO(
                customer.getId(),
                customer.getDocument(),
                customer.getIndicationDocumentType(),
                customer.getCustomerCompanyFlag(),
                customer.getMcc(),
                customer.getAnnualTpv()
        );
    }




}
