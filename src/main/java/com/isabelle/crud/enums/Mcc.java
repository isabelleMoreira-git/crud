package com.isabelle.crud.enums;

public enum Mcc {

    VETERINARIO("742"),
    SERVICOS_ESPECIALIZADOS("1799"),
    TAXISTA("4121"),
    LOJA_ALIMENTOS("5499"),
    COSTUREIRA_ALFAIATE("5697"),
    VAREJO("5699"),
    SALAO_BELEZA("7230"),
    REPARO_AUTOMOTIVO("7538"),
    MEDICO("8011"),
    DENTISTA("8021"),
    SERVICOS_MEDICOS("8099"),
    ADVOGADO("8111"),
    SERVICOS_PROFISSIONAIS("8999");

    private final String code;

    Mcc(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}