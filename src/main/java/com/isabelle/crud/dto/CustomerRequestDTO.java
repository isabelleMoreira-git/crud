package com.isabelle.crud.dto;

import com.isabelle.crud.enums.Mcc;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class CustomerRequestDTO {

    @NotBlank
    private String document;

    @NotBlank
    private String indicationDocumentType;

    private Boolean customerCompanyFlag;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Mcc mcc;

    @NotNull
    private BigDecimal annualTpv;

    public CustomerRequestDTO() {
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