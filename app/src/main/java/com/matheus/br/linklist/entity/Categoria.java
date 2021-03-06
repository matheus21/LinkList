package com.matheus.br.linklist.entity;


public enum Categoria {

    SELECIONE("Selecione uma categoria"), ARTIGO("Artigo"), NOTICIA("Noticia"), OUTROS("Outros");

    private String descricao;

    Categoria(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Categoria getCategoria(int pos) {
        for (Categoria c : Categoria.values()) {
            if (c.ordinal() == pos) {
                return c;
            }
        }
        return null;
    }
}
