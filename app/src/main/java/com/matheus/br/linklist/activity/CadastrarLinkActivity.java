package com.matheus.br.linklist.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.matheus.br.linklist.R;
import com.matheus.br.linklist.adapter.CategoriasAdapter;
import com.matheus.br.linklist.entity.Categoria;
import com.matheus.br.linklist.entity.Link;
import com.matheus.br.linklist.repository.LinkRepository;

import java.util.ArrayList;


public class CadastrarLinkActivity extends AppCompatActivity {

    private ArrayList<Categoria> categorias;
    private Spinner spinnerCategoria;
    private CategoriasAdapter categoriasAdapter;
    private FloatingActionButton actionButtonSalvarLink;
    private EditText editTextURL;
    private EditText editTextTitulo;
    private LinkRepository linkRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_link);

        actionButtonSalvarLink = findViewById(R.id.actionButtonSalvarLink);
        editTextURL = findViewById(R.id.editTextURL);
        editTextTitulo = findViewById(R.id.editTextTitulo);

        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        initListCategorias();

        categoriasAdapter = new CategoriasAdapter(this, R.layout.item_spinner_categoria, categorias);
        spinnerCategoria.setAdapter(categoriasAdapter);

        actionButtonSalvarLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionarLink();
            }
        });
    }

    private void adicionarLink() {
        boolean valido = true;
        Link link = new Link();
        link.setUrl(editTextURL.getText().toString());
        link.setTitulo(editTextTitulo.getText().toString());
        link.setIdCategoria(spinnerCategoria.getSelectedItemPosition());
        link.setIdStatus(1);

        if (link.getUrl() == null || "".equals(link.getUrl())) {
            valido = false;
        } else if (link.getTitulo() == null || "".equals(link.getTitulo())) {
            valido = false;
        } else if (link.getIdCategoria() == 0) {
            valido = false;
        }

        if (valido) {
            linkRepository = new LinkRepository(getApplicationContext());
            linkRepository.salvarLink(link);
        } else {
            Toast.makeText(getApplicationContext(), "Preencha todos os campos", Toast.LENGTH_LONG).show();
        }
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
