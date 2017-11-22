package com.matheus.br.linklist.entity;

public enum Status {

    LIDO("Lido"), NAO_LIDO("Não Lido");

    private String descricao;

    Status(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Status getStatus(int pos) {
        for (Status c : Status.values()) {
            if (c.ordinal() == pos) {
                return c;
            }
        }
        return null;
    }
}
