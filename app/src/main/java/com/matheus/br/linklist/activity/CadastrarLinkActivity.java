package com.matheus.br.linklist.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;

import com.matheus.br.linklist.R;
import com.matheus.br.linklist.adapter.CategoriasAdapter;
import com.matheus.br.linklist.entity.Categoria;

import java.util.ArrayList;


public class CadastrarLinkActivity extends AppCompatActivity {

    private ArrayList<Categoria> categorias;
    private Spinner spinnerCategoria;
    private CategoriasAdapter categoriasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_link);

        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        initListCategorias();

        categoriasAdapter = new CategoriasAdapter(this, R.layout.item_spinner_categoria, categorias);
        spinnerCategoria.setAdapter(categoriasAdapter);
    }

    private void initListCategorias() {
        categorias = new ArrayList<>();
        Categoria categoria;

        categoria = new Categoria();
        categoria.setNome("Selecione uma categoria");
        categorias.add(categoria);
        categoria = new Categoria();
        categoria.setId(1);
        categoria.setNome("Artigo");
        categorias.add(categoria);
        categoria = new Categoria();
        categoria.setId(2);
        categoria.setNome("Notícia");
        categorias.add(categoria);
        categoria = new Categoria();
        categoria.setId(3);
        categoria.setNome("Outros");
        categorias.add(categoria);
    }


}
