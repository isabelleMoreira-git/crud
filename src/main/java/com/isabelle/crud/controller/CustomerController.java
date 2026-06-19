package com.isabelle.crud.controller;

import com.isabelle.crud.dto.CustomerRequestDTO;
import com.isabelle.crud.dto.CustomerResponseDTO;
import com.isabelle.crud.entity.Customer;
import com.isabelle.crud.exception.CustomerNotFoundException;
import com.isabelle.crud.exception.CustomerValidationException;
import com.isabelle.crud.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

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

    // Injeção de Dependência pelo construtor
//    private final CustomerService customerService;
//
//    public CustomerController(CustomerService customerService) {
//        this.customerService = customerService;
//    }

    @PostMapping
    public ResponseEntity<String> createCustomer(
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

        try {
            Customer savedCustomer =
                    customerService.createCustomer(customer);

                return ResponseEntity.ok("Estabelecimento criado");
                // fazer com 201

//            return new CustomerResponseDTO(
//                    savedCustomer.getId(),
//                    savedCustomer.getDocument(),
//                    savedCustomer.getIndicationDocumentType(),
//                    savedCustomer.getCustomerCompanyFlag(),
//                    savedCustomer.getMcc(),
//                    savedCustomer.getAnnualTpv()
//            );
        } catch (CustomerValidationException e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body("Não veio");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //Outro catch pra CustomerAlreadyExists
        //Não vou retornar String


    }

    @GetMapping
    public List<CustomerResponseDTO> getAllCustomers() {

//        return customerService.getAllCustomers()
//                .stream()
//                .map(this::toResponseDTO)
//                .toList();

        List<CustomerResponseDTO> AllCustomers = new ArrayList<>();

        for (int i=0; i<customerService.getAllCustomers().size(); i++){

            CustomerResponseDTO dto = toResponseDTO(customerService.getAllCustomers().get(i));
            AllCustomers.add(dto);
        }

        return AllCustomers;
    }

    @GetMapping("/{id}")
    public CustomerResponseDTO getCustomerById(
            @PathVariable Long id) {

//        Customer customer =
//                customerService
//                        .getCustomerById(id)
//                        .orElseThrow(() ->
//                                new RuntimeException("Cliente não encontrado"));

        Optional<Customer> optionalCustomer = customerService.getCustomerById(id);
        boolean foundCustomer = optionalCustomer.isPresent();
        if (foundCustomer) {
            Customer customer = optionalCustomer.get();
            return toResponseDTO(customer);
        }else{
            throw new CustomerNotFoundException("O cliente não foi encontrado.");
        }
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

        //Customer customer = customerService.getCustomerByDocument(document);
        // Alterar pra Customer

        Optional<Customer> optionalCustomer = customerService.getCustomerByDocument(document);
        boolean foundDocument = optionalCustomer.isPresent();

        if(foundDocument){
            Customer customer = optionalCustomer.get();
            return toResponseDTO(customer);
        }else{
            throw new CustomerNotFoundException("O cliente não foi encontrado");
        }

    }
    // Response Status. (Exceção no controller X)
    // Retornar Customer.


    @GetMapping("/document/{document}/{type}")
    public CustomerResponseDTO getCustomerByDocumentAndType(
            @PathVariable String document, @PathVariable String type) {

        Customer customer =
                customerService.getCustomerByDocumentAndType(document,type);

        return toResponseDTO(customer);
    }






}
