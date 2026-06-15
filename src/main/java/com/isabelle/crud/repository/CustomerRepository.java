package com.isabelle.crud.repository;

import com.isabelle.crud.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByDocument(String document);

    @Query(
            value = "SELECT * FROM customers " +
                    "WHERE document = :document " +
                    "AND indication_document_type = :PfOrPj",
            nativeQuery = true
    )
    Customer findByDocumentAndType(String document, String PfOrPj);
    // Usar o objeto ao invés do nome do banco.

    // todo: outro findByDocument, com query nativa, vai retonar Customer
    // todo: omo parametro (documento, pf/pj), nome do metodo = buscaDocumentoPjPf

    //boolean existsByDocument(String document);
    // query method do Spring Data JPA (SELECT EXISTS(...))
}
