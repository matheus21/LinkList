package com.matheus.br.linklist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.matheus.br.linklist.R;
import com.matheus.br.linklist.entity.Link;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LinksAdapter extends RecyclerView.Adapter<LinksAdapter.ViewHolder> {

    private List<Link> listLinks;

    public LinksAdapter(List<Link> listCredenciados) {
        this.listLinks = listCredenciados;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_link, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textTituloLink.setText(listLinks.get(position).getTitulo());
        holder.textDescricaoLink.setText(listLinks.get(position).getTitulo());
        holder.textUrl.setText(listLinks.get(position).getUrl());
        Context context = holder.imageLink.getContext();
        Picasso.with(context).load(R.drawable.logo).into(holder.imageLink);
    }

    @Override
    public int getItemCount() {
        return listLinks.size();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textTituloLink, textDescricaoLink, textUrl;
        private ImageView imageLink;

        public ViewHolder(View view) {
            super(view);
            textTituloLink = view.findViewById(R.id.textTituloLink);
            textDescricaoLink = view.findViewById(R.id.textDescricaoLink);
            textUrl = view.findViewById(R.id.textUrl);
            imageLink = view.findViewById(R.id.imageLink);
        }
    }

}
