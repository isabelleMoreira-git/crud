package com.isabelle.crud.service;

import com.isabelle.crud.entity.Customer;
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

        List<Customer> allCustomers = customerRepository.findAll();

        if (allCustomers.contains(customer)) {
            System.out.println("O cliente já existe.");
            throw new CustomerAlreadyExistsException("Cliente já cadastrado com esse documento");
        } else {
            return customerRepository.save(customer);
        }

        //todo: Perguntar sobre o try/catch
    }

    /*List<Customer> allCustomers = customerRepository.findAll();

for (int i = 0; i < allCustomers.size(); i++) {

    Customer c = allCustomers.get(i);

    if (c.getDocument().equals(customer.getDocument())) {
        throw new CustomerAlreadyExistsException(
            "Cliente já cadastrado com esse documento"
        );
    }
}

return customerRepository.save(customer);*/

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
            throw new IllegalArgumentException("Esse cliente não existe.");
        }
    }

    public void deleteByDocumentAndType(String document, String type){
        Customer customer = customerRepository.findByDocumentAndType(document,type);
        validateCustomer(customer);

        String pfOrPj = customer.getIndicationDocumentType();

        if ("PF".equals(pfOrPj)){
            customerRepository.deleteById(customer.getId());
        } else {
            throw new IllegalArgumentException("Não é possível deletar Pessoa Jurídica.");
        }
    }

    //todo: Fazer um delete by document (só se o ducumento for válido e se for pessoa fisica)
    // ( se for PJ lançar EX personalizada)

    public Customer updateCustomer(Long id, Customer updatedCustomer) {

        //todo: substituir lambda
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        customer.setMcc(updatedCustomer.getMcc());
        customer.setAnnualTpv(updatedCustomer.getAnnualTpv());
        customer.setCustomerCompanyFlag(updatedCustomer.getCustomerCompanyFlag());

        return customerRepository.save(customer);
    }

    public Optional<Customer> getCustomerByDocument(String document) {
        return customerRepository.findByDocument(document);
    }

    public Customer getCustomerByDocumentAndType(String document, String pfouPj) {
        //O objetivo é substituir o findByDocument()? não

//        Optional<Customer> optionalDocument = customerRepository.findByDocument(document);
//        boolean containsDocument = optionalDocument.isPresent();

        Customer customer = customerRepository.findByDocumentAndType(document, pfouPj);
        // Inserir valor que não existe!!
        // docker stop

        if (customer != null) {
            System.out.println("O cliente foi encontrado.");
            return customer;
        }else{
            throw new IllegalArgumentException("Não existe um cliente com esse documento e tipo.");
            //Criar exceção personalizada? Crio outro.

            //É pra remover o Optional apenas desse, ou dos outros também? alguns.
            //É pra retornar o que se encontrar o documento e não for o tipo certo? ex.
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


//        if (optionalDocument == null) {
//            throw new CustomerNotFoundException("Não existe um cliente com esse documento.");
//        }
*/