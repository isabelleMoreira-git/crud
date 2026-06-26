package com.isabelle.crud.dto;

import com.isabelle.crud.enums.Mcc;

import java.math.BigDecimal;

public class CustomerResponseDTO {

    private Long id;
    private String document;
    private String indicationDocumentType;
    private Boolean customerCompanyFlag;
    private Mcc mcc;
    private BigDecimal annualTpv;

    public CustomerResponseDTO() {
    }

    public CustomerResponseDTO(
            Long id,
            String document,
            String indicationDocumentType,
            Boolean customerCompanyFlag,
            Mcc mcc,
            BigDecimal annualTpv) {

        this.id = id;
        this.document = document;
        this.indicationDocumentType = indicationDocumentType;
        this.customerCompanyFlag = customerCompanyFlag;
        this.mcc = mcc;
        this.annualTpv = annualTpv;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getIndicationDocumentType() {
        return indicationDocumentType;
    }

    public void setIndicationDocumentType(String indicationDocumentType) {
        this.indicationDocumentType = indicationDocumentType;
    }

    public Boolean getCustomerCompanyFlag() {
        return customerCompanyFlag;
    }

    public void setCustomerCompanyFlag(Boolean customerCompanyFlag) {
        this.customerCompanyFlag = customerCompanyFlag;
    }

    public Mcc getMcc() {
        return mcc;
    }

    public void setMcc(Mcc mcc) {
        this.mcc = mcc;
    }

    public BigDecimal getAnnualTpv() {
        return annualTpv;
    }

    public void setAnnualTpv(BigDecimal annualTpv) {
        this.annualTpv = annualTpv;
    }
}