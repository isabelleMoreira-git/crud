package com.isabelle.crud.service;

import com.isabelle.crud.entity.Customer;
import com.isabelle.crud.exception.CustomerNotFoundException;
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

    public Customer createCustomer(Customer customer) {
        validateCustomer(customer);
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public void validateCustomer(Customer customer){
        if(customer == null){
            System.out.println("Esse cliente não existe");
            throw new CustomerNotFoundException("Esse cliente não existe.");
        }
        if(customerRepository.findByDocument(customer.getDocument()).isPresent()){
            System.out.println("Já existe um cliente com esse documento.");
            throw new CustomerAlreadyExistsException("Já existe um cliente com esse documento.");
        }
    }

    public void deleteByDocumentAndType(String document, String type){
        Customer customer = customerRepository.findByDocumentAndType(document,type);
        validateCustomer(customer);

        String pfOrPj = customer.getIndicationDocumentType();

        if ("PF".equals(pfOrPj)){
            customerRepository.deleteById(customer.getId());
        } else {
            throw new IllegalArgumentException("Não é possível apagar Pessoa Jurídica.");
        }
    }

    //todo: Fazer um delete by document (só se o documento for válido e se for pessoa física)
    // ( se for PJ lançar EX personalizada)

    public Customer updateCustomer(Long id, Customer updatedCustomer) {

//        Customer customer = customerRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Customer customer;

        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        boolean foundCustomer = optionalCustomer.isPresent();

        if (foundCustomer) {
            customer = optionalCustomer.get();

            customer.setMcc(updatedCustomer.getMcc());
            customer.setAnnualTpv(updatedCustomer.getAnnualTpv());
            customer.setCustomerCompanyFlag(updatedCustomer.getCustomerCompanyFlag());
        }else{
            throw new CustomerNotFoundException("O cliente não foi encontrado.");
        }

        return customerRepository.save(customer);
    }

    public Optional<Customer> getCustomerByDocument(String document) {
        return customerRepository.findByDocument(document);
    }

    public Customer getCustomerByDocumentAndType(String document, String pfOuPj) {


        Customer customer = customerRepository.findByDocumentAndType(document, pfOuPj);


        if (customer != null) {
            System.out.println("O cliente foi encontrado.");
            return customer;
        }else{
            throw new IllegalArgumentException("Não existe um cliente com esse documento e tipo.");
            //Criar exceção personalizada? Crio outro.

            //É pra remover o Optional apenas desse, ou dos outros também? Alguns.
            //É pra retornar o que se encontrar o documento e não for o tipo certo? Ex.
            // Inserir valor que não existe!!
            // Docker stop
        }
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
//        } else {
//            return customerRepository.save(customer);
//        }
*/