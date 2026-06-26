package com.isabelle.crud.controller;

import com.isabelle.crud.dto.CustomerRequestDTO;
import com.isabelle.crud.dto.CustomerResponseDTO;
import com.isabelle.crud.entity.Customer;
import com.isabelle.crud.exception.CompanyDeletionNotAllowedException;
import com.isabelle.crud.exception.CustomerAlreadyExistsException;
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

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(
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
            Customer savedCustomer = customerService.createCustomer(customer);
            CustomerResponseDTO response = new CustomerResponseDTO(
                    savedCustomer.getId(),
                    savedCustomer.getDocument(),
                    savedCustomer.getIndicationDocumentType(),
                    savedCustomer.getCustomerCompanyFlag(),
                    savedCustomer.getMcc(),
                    savedCustomer.getAnnualTpv()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (CustomerValidationException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).build();
                    //body("Não veio");

        }catch(CustomerAlreadyExistsException e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).build();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers() {

       try {
           List<CustomerResponseDTO> AllCustomers = new ArrayList<>();
           List<Customer> customersList = customerService.getAllCustomers();

           for (int i = 0; i < customersList.size(); i++) {
               CustomerResponseDTO dto = toResponseDTO(customersList.get(i));
               AllCustomers.add(dto);
           }
           return ResponseEntity.ok().body(AllCustomers);

        }catch (RuntimeException e) {
           System.out.println(e.getMessage());
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getCustomerById(
            @PathVariable Long id) {

        try {
            Customer customer = customerService.getCustomerById(id);
            return ResponseEntity.ok().body(toResponseDTO(customer));

        }catch(CustomerNotFoundException e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            //.body

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/document/{document}")
    public ResponseEntity<CustomerResponseDTO> getCustomerByDocument(
            @PathVariable String document) {

        try {
            Customer customer = customerService.getCustomerByDocument(document);
            return ResponseEntity.ok().body(toResponseDTO(customer));

        } catch(CustomerNotFoundException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }catch(RuntimeException e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/document/{document}/{type}")
    public ResponseEntity<CustomerResponseDTO> getCustomerByDocumentAndType(
            @PathVariable String document, @PathVariable String type) {

        try {
            Customer customer = customerService.getCustomerByDocumentAndType(document, type);
            return ResponseEntity.ok().body(toResponseDTO(customer));

        }catch(CustomerNotFoundException e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }catch(RuntimeException e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody Customer customer) {
        try {
            return ResponseEntity.ok().body(customerService.updateCustomer(id, customer));

        }catch(CustomerNotFoundException e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }catch(RuntimeException e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id){
        try {
            customerService.deleteCustomer(id);
            return ResponseEntity.noContent().build();
            //Pra retornar void

        } catch(CustomerNotFoundException e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }catch(RuntimeException e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{document}/{type}")
    public ResponseEntity<Void> deleteByDocumentAndType(@PathVariable String document,
                                        @PathVariable String type){
        try {
            customerService.deleteByDocumentAndType(document, type);
            return ResponseEntity.noContent().build();

        } catch(CompanyDeletionNotAllowedException e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).build();

        }catch(RuntimeException e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

/*Comentários
   getAllCustomers com Lambda:
//        return customerService.getAllCustomers()
//                .stream()
//                .map(this::toResponseDTO)
//                .toList();



* */