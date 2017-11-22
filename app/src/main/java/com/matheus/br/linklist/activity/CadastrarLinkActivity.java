package com.matheus.br.linklist.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.matheus.br.linklist.R;
import com.matheus.br.linklist.adapter.CategoriasAdapter;
import com.matheus.br.linklist.entity.Categoria;
import com.matheus.br.linklist.entity.Link;
import com.matheus.br.linklist.entity.Status;
import com.matheus.br.linklist.repository.LinkRepository;

import java.util.ArrayList;


public class CadastrarLinkActivity extends AppCompatActivity {

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
        initSpinnerCategoria();

        actionButtonSalvarLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionarLink(view);
            }
        });
    }

    private void adicionarLink(View view) {

        Link link = new Link();
        link.setUrl(editTextURL.getText().toString());
        link.setTitulo(editTextTitulo.getText().toString());
        link.setIdCategoria(spinnerCategoria.getSelectedItemPosition());
        link.setIdStatus(Status.NAO_LIDO.ordinal());

        if (validaLink(link)) {
            linkRepository = new LinkRepository(getApplicationContext());
            linkRepository.salvarLink(link);
            startActivity(new Intent(CadastrarLinkActivity.this, MainActivity.class));
        } else {
            Snackbar.make(view, "Preencha todos os campos", Snackbar.LENGTH_LONG).show();
        }
    }

    private void initSpinnerCategoria() {
        ArrayList<String> categorias = new ArrayList<>();
        for (Categoria c : Categoria.values()) {
            categorias.add(c.getDescricao());
        }
        categoriasAdapter = new CategoriasAdapter(getApplicationContext(), R.layout.item_spinner_categoria, categorias);
        spinnerCategoria.setAdapter(categoriasAdapter);
    }


    private boolean validaLink(Link link) {

        boolean valido = true;
        if (link.getUrl() == null || "".equals(link.getUrl())) {
            valido = false;
        } else if (link.getTitulo() == null || "".equals(link.getTitulo())) {
            valido = false;
        } else if (link.getIdCategoria() == 0) {
            valido = false;
        }
        return valido;
    }

}
