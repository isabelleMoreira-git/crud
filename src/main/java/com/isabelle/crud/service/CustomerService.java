package com.isabelle.crud.service;

//import com.isabelle.crud.eligibility.MccValidationService;
import com.isabelle.crud.entity.Customer;
import com.isabelle.crud.exception.CompanyDeletionNotAllowedException;
import com.isabelle.crud.exception.CustomerNotFoundException;
import com.isabelle.crud.exception.CustomerValidationException;
import com.isabelle.crud.repository.CustomerRepository;
import com.isabelle.crud.exception.CustomerAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
//    @Autowired
//    private MccValidationService mccValidationService;

    public Customer createCustomer(Customer customer) {
        validateCreateCustomer(customer);
        return customerRepository.save(customer);
    }

    private void validateCreateCustomer(Customer customer){
        if (customer == null){
            System.out.println("O cliente não foi informado.");
            throw new CustomerValidationException("O cliente não foi informado.");
        }
        if(customerRepository.findByDocument(customer.getDocument()).isPresent()){
            System.out.println("Já existe um cliente com esse documento.");
            throw new CustomerAlreadyExistsException("Já existe um cliente com esse documento.");
        }
        if (customer.getDocument().length() != 11
                && customer.getDocument().length() != 14
                || customer.getDocument().equals("string")) {
            throw new CustomerValidationException("Documento inválido.");
        }
//        if(customer.getMcc().equals("string")
//                || !mccValidationService.mccIsValid(customer.getMcc())){
//            throw new CustomerValidationException("Mcc inválido.");
//        }
        if((!customer.getIndicationDocumentType().equals("PF")
                && !customer.getIndicationDocumentType().equals("PJ"))
                || customer.getIndicationDocumentType().equals("String")){
            throw new CustomerValidationException("Tipo de documento inválido.");
        }
        if(customer.getIndicationDocumentType().equals("PF")
                && customer.getDocument().length() != 11){
            throw new CustomerValidationException("Documento inválido para Pessoa Física.");
        }
        if(customer.getIndicationDocumentType().equals("PJ")
                && customer.getDocument().length() != 14){
            throw new CustomerValidationException("Documento inválido para Pessoa Jurídica.");
        }
    }
    /*Validar:
     * Cliente nulo OK
     * Cliente duplicado OK
     * Documento, mcc e tipo obrigatórios OK
     * mcc tem que ser númerico, e dentro das opções.
     * Tipo e documento conectados OK
     * Não aceitar exemplo do swagger OK string ou String
     * */

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        boolean foundCustomer = optionalCustomer.isPresent();

        if (foundCustomer){
            Customer customer = optionalCustomer.get();
            return(customer);
        } else {
            throw new CustomerNotFoundException("Cliente não encontrado");
        }
    }

    public void deleteCustomer(Long id) {
        Optional<Customer> optionalCustomer= customerRepository.findById(id);
        boolean foundCustomer = optionalCustomer.isPresent();

        if (foundCustomer){
            customerRepository.deleteById(id);
        } else{
            throw new CustomerNotFoundException("Cliente não encontrado");
        }
    }

    public void deleteByDocumentAndType(String document, String type){
        Customer customer = customerRepository.findByDocumentAndType(document,type);
        if(customer == null){
            throw new CustomerNotFoundException("Esse cliente não existe.");
        }

        String pfOrPj = customer.getIndicationDocumentType();

        if ("PF".equals(pfOrPj)){
            customerRepository.deleteById(customer.getId());
        } else {
            throw new CompanyDeletionNotAllowedException("Não é possível apagar Pessoa Jurídica.");
        }
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {

        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        boolean foundCustomer = optionalCustomer.isPresent();

        if (foundCustomer) {
            Customer customer = optionalCustomer.get();

            customer.setMcc(updatedCustomer.getMcc());
            customer.setAnnualTpv(updatedCustomer.getAnnualTpv());
            customer.setCustomerCompanyFlag(updatedCustomer.getCustomerCompanyFlag());

            return customerRepository.save(customer);
        }else{
            throw new CustomerNotFoundException("O cliente não foi encontrado.");
        }
    }

    public Customer getCustomerByDocument(String document) {
        Optional<Customer> optionalCustomer = customerRepository.findByDocument(document);
        boolean foundCustomer = optionalCustomer.isPresent();

        if (foundCustomer){
            Customer customer = optionalCustomer.get();
            return(customer);
        } else {
            throw new CustomerNotFoundException("Cliente não encontrado");
        }
    }

    public Customer getCustomerByDocumentAndType(String document, String pfOuPj) {

        Customer customer = customerRepository.findByDocumentAndType(document, pfOuPj);
        if(customer == null){
            throw new CustomerNotFoundException("Esse cliente não existe.");
        }

        return customer;
        }

    }



/* Comentários

    // Injeção de dependência pelo construtor

//    public CustomerService(CustomerRepository customerRepository) {
//        this.customerRepository = customerRepository;
//    }

----------------------------------------------------------------------------
    // Validação de cadastrado duplicado (CreateCustomer - Primeira versão)

//        if (customerRepository.existsByDocument(customer.getDocument())){
//            throw new CustomerAlreadyExistsException(
//                    "Cliente já cadastrado com esse documento");
//        }

----------------------------------------------------------------------------
    //Update Customer

//    try {
//        Optional<Customer> optionalCustomer = customerRepository.findById(id);
//        System.out.println(optionalCustomer);
//
//        boolean encontrouCustomer = optionalCustomer.isPresent();
//        System.out.println(encontrouCustomer);
//
//        if (encontrouCustomer) {
//            Customer customer = optionalCustomer.get();
//            System.out.println(customer);
//
//            customer.setMcc(updatedCustomer.getMcc());
//            customer.setAnnualTpv(updatedCustomer.getAnnualTpv());
//            customer.setCustomerCompanyFlag(updatedCustomer.getCustomerCompanyFlag());
//
//            System.out.println(customer);
//            return customerRepository.save(customer);
//        } else {
//            throw new IllegalArgumentException("Cliente não encontrado");
//        }
//    } catch (Exception ex){
//        System.out.println("Entrou no bloco catch");
//        return null;
//    }
//    finally {
//        System.out.println("Alarme de update");
//    }

----------------------------------------------------------------------------
CreateCustomer

//        List<Customer> allCustomers = customerRepository.findAll();
//
//        if (allCustomers.contains(customer)) {
//            System.out.println("O cliente já existe.");
//            throw new CustomerAlreadyExistsException("Cliente já cadastrado com esse documento");
//        } else {return customerRepository.save(customer);
//        }
*/