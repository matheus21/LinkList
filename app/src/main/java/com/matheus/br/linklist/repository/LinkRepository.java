package com.matheus.br.linklist.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.matheus.br.linklist.entity.Link;
import com.matheus.br.linklist.util.Constantes;

import java.util.ArrayList;
import java.util.List;


public class LinkRepository extends SQLiteOpenHelper {


    public LinkRepository(Context context) {
        super(context, Constantes.NOME_BD, null, Constantes.VERSAO_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE IF NOT EXISTS LINK( ");
        query.append(" ID INTEGER PRIMARY KEY AUTOINCREMENT, ");
        query.append(" URL TEXT NOT NULL, ");
        query.append(" TITULO TEXT NOT NULL, ");
        query.append(" ID_CATEGORIA INTEGER NOT NULL, ");
        query.append(" ID_STATUS INTEGER NOT NULL)");

        sqLiteDatabase.execSQL(query.toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void salvarLink(Link link) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = getContentValuesLink(link);
        db.insert("LINK", null, contentValues);
        db.close();
    }

    public void atualizarLink(Link link) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = getContentValuesLink(link);

        db.update("LINK", contentValues, "ID = ?", new String[]{String.valueOf(link.getId())});
    }

    public List<Link> getLinks() {

        ArrayList<Link> links = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("LINK", null, "LINK.ID_STATUS = 1", null, null, null, "ID DESC");
        while (cursor.moveToNext()) {
            links.add(setLinkFromCursor(cursor));
        }
        return links;
    }

    @NonNull
    private ContentValues getContentValuesLink(Link link) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("URL", link.getUrl());
        contentValues.put("TITULO", link.getTitulo());
        contentValues.put("ID_CATEGORIA", link.getIdCategoria());
        contentValues.put("ID_STATUS", link.getIdStatus());
        return contentValues;
    }

    private Link setLinkFromCursor(Cursor cursor) {

        Link link = new Link();
        link.setId(cursor.getInt(cursor.getColumnIndex("ID")));
        link.setUrl(cursor.getString(cursor.getColumnIndex("URL")));
        link.setTitulo(cursor.getString(cursor.getColumnIndex("TITULO")));
        link.setIdCategoria(cursor.getInt(cursor.getColumnIndex("ID_CATEGORIA")));
        link.setIdStatus(cursor.getInt(cursor.getColumnIndex("ID_STATUS")));

        return link;
    }

}
