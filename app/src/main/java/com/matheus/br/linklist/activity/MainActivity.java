package com.matheus.br.linklist.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.matheus.br.linklist.R;
import com.matheus.br.linklist.adapter.LinksAdapter;
import com.matheus.br.linklist.repository.LinkRepository;

public class MainActivity extends AppCompatActivity {

    private LinkRepository linkRepository;
    private RecyclerView recyclerViewLinks;
    private LinksAdapter linksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setLinksAdapter();
    }

    private void initRecycler() {
        recyclerViewLinks = findViewById(R.id.recyclerViewLinks);
        recyclerViewLinks.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewLinks.setLayoutManager(layoutManager);
    }

    private void setLinksAdapter() {
        initRecycler();
        linkRepository = new LinkRepository(this);
        linksAdapter = new LinksAdapter(linkRepository.getLinks());
        recyclerViewLinks.setAdapter(linksAdapter);
    }
}
