package com.matheus.br.linklist.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.matheus.br.linklist.R;
import com.matheus.br.linklist.adapter.LinksAdapter;
import com.matheus.br.linklist.entity.Link;
import com.matheus.br.linklist.repository.LinkRepository;
import com.matheus.br.linklist.util.ClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LinkRepository linkRepository;
    private RecyclerView recyclerViewLinks;
    private LinksAdapter linksAdapter;
    private List<Link> links;

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
        links = new ArrayList<>();
        links = linkRepository.getLinks();
        linksAdapter = new LinksAdapter(links, new ClickListener() {
            @Override
            public void onPositionButtonClicked(int position) {
            }
        });
        recyclerViewLinks.setAdapter(linksAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(MainActivity.this, CadastrarLinkActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_adicionar_link, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.adicionar_link:
                startActivity(new Intent(MainActivity.this, CadastrarLinkActivity.class));

        }

        return super.onOptionsItemSelected(item);
    }
}
