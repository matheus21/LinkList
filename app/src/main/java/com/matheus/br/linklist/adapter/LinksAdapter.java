package com.matheus.br.linklist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;

import com.matheus.br.linklist.R;
import com.matheus.br.linklist.entity.Link;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
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
        String url = "";
        if (URLUtil.isValidUrl(listLinks.get(position).getUrl())) {
            Log.d("URL", "Url valida");
            url = listLinks.get(position).getUrl();
        } else {
            url = fixUrl(listLinks.get(position).getUrl());
        }

        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        holder.textTituloLink.setText(listLinks.get(position).getTitulo());
        holder.textDescricaoLink.setText(document.title());
        holder.textUrl.setText(listLinks.get(position).getUrl());
        Context context = holder.imageLink.getContext();

        if (getImageLinkUrl(document) == null || "".equals(getImageLinkUrl(document))) {
            Picasso.with(context).load(R.drawable.image_area).into(holder.imageLink);
        } else {
            Picasso.with(context).load(getImageLinkUrl(document)).into(holder.imageLink);
        }
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

    public String fixUrl(String url) {
        String validUrl = "http://";
        validUrl = validUrl.concat(url);
        return validUrl;
    }

    public String getImageLinkUrl(Document document) {

        String imageUrl;
        Elements metaImage = document.select("meta[property=og:image]");
        imageUrl = metaImage.attr("content");
        return imageUrl;
    }

}
