package com.matheus.br.linklist.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.matheus.br.linklist.R;
import com.matheus.br.linklist.activity.MainActivity;
import com.matheus.br.linklist.entity.Link;
import com.matheus.br.linklist.repository.LinkRepository;
import com.matheus.br.linklist.util.ClickListener;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class LinksAdapter extends RecyclerView.Adapter<LinksAdapter.ViewHolder> {

    private final ClickListener clickListener;
    private static List<Link> listLinks = new ArrayList<>();

    public LinksAdapter(List<Link> listLinks, ClickListener clickListener) {
        this.listLinks = listLinks;
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_link, parent, false);
        return new ViewHolder(view, clickListener);
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

        Context context = holder.imageLink.getContext();
        holder.textTituloLink.setText(listLinks.get(position).getTitulo());

        if (document == null) {
            holder.textDescricaoLink.setText(listLinks.get(position).getTitulo());
            Picasso.with(context).load(R.drawable.image_area).into(holder.imageLink);
        } else {
            holder.textDescricaoLink.setText(document.title());
            if (getImageLinkUrl(document) == null || "".equals(getImageLinkUrl(document))) {
                Picasso.with(context).load(R.drawable.image_area).into(holder.imageLink);
            } else {
                Picasso.with(context).load(getImageLinkUrl(document)).into(holder.imageLink);
            }
        }
    }

    @Override
    public int getItemCount() {
        return listLinks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textTituloLink, textDescricaoLink;
        private ImageView imageLink;
        private ImageButton buttonAbrirLink;
        private ImageButton buttonMarcarComoLido;
        private WeakReference<ClickListener> listenerReference;


        public ViewHolder(View view, ClickListener clickListener) {
            super(view);
            listenerReference = new WeakReference<>(clickListener);
            textTituloLink = view.findViewById(R.id.textTituloLink);
            textDescricaoLink = view.findViewById(R.id.textDescricaoLink);
            imageLink = view.findViewById(R.id.imageLink);
            buttonAbrirLink = view.findViewById(R.id.buttonAbrirLink);
            buttonMarcarComoLido = view.findViewById(R.id.buttonMarcarComoLido);

            itemView.setOnClickListener(this);
            buttonAbrirLink.setOnClickListener(this);
            buttonMarcarComoLido.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == buttonAbrirLink.getId()) {
                Intent browserIntent;
                if (URLUtil.isValidUrl(listLinks.get(getAdapterPosition()).getUrl())) {
                    browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(listLinks.get(getAdapterPosition()).getUrl()));
                } else {
                    browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fixUrl(listLinks.get(getAdapterPosition()).getUrl())));
                }
                view.getContext().startActivity(browserIntent);
            } else if (view.getId() == buttonMarcarComoLido.getId()) {

                final Link link;
                link = listLinks.get(getAdapterPosition());
                final LinkRepository linkRepository = new LinkRepository(view.getContext());
                final Context context = view.getContext();

                final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Marcar Como Lido")
                        .setMessage("Deseja marcar este link como lido?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                link.setIdStatus(0);
                                linkRepository.atualizarLink(link);
                                context.startActivity(new Intent(context, MainActivity.class));
                            }
                        });
                builder.create().show();
            }

            listenerReference.get().onPositionButtonClicked(getAdapterPosition());
        }
    }

    public static String fixUrl(String url) {
        String validUrl = "http://";
        validUrl = validUrl.concat(url);
        return validUrl;
    }

    public static String getImageLinkUrl(Document document) {

        String imageUrl;
        Elements metaImage = document.select("meta[property=og:image]");
        imageUrl = metaImage.attr("content");
        return imageUrl;
    }

}
