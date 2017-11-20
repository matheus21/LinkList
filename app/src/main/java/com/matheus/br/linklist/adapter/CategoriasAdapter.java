package com.matheus.br.linklist.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.matheus.br.linklist.R;
import com.matheus.br.linklist.entity.Categoria;

import java.util.List;


/**
 * Created by Desenvolvimento on 16/11/2017.
 */

public class CategoriasAdapter extends ArrayAdapter<Categoria> {


    private List<Categoria> categorias;
    private Context context;

    public CategoriasAdapter(Context context, int resource, List<Categoria> categorias) {
        super(context, resource, categorias);
        this.context = context;
        this.categorias = categorias;
    }

    @Override
    public int getCount() {
        return categorias.size();
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getViewAux(position, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getViewAux(position, parent);
    }

    @NonNull
    private View getViewAux(int position, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View linhaCategoria = layoutInflater.inflate(R.layout.item_spinner_categoria, parent, false);
        Categoria categoria = categorias.get(position);
        TextView textNomeCategoria = linhaCategoria.findViewById(R.id.textNomeCategoria);
        textNomeCategoria.setText(categoria.getNome());
        return linhaCategoria;
    }

    @Override
    public Categoria getItem(int position) {
        return categorias.get(position);
    }
}
