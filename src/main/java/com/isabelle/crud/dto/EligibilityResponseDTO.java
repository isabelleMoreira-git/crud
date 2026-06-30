package com.isabelle.crud.dto;

public class EligibilityResponseDTO {

    private final String document;
    private final boolean eligible;

    public EligibilityResponseDTO(String document,
                                  boolean eligible) {
        this.document = document;
        this.eligible = eligible;
    }

    public String getDocument() {
        return document;
    }

    public boolean isEligible() {
        return eligible;
    }

}