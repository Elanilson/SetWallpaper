package com.apkdoandroid.wallpaper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.apkdoandroid.wallpaper.adapter.AdapterGrid;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class CategoriaActivity extends AppCompatActivity {
    private String categoria;
    private GridView gridView;
    private ArrayAdapter<ParseObject> adapter;
    private ArrayList<ParseObject> lista;
    private View view;
    private ParseQuery<ParseObject> query ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);
        getSupportActionBar().hide();
        gridView = findViewById(R.id.gridCategoria);
        Bundle dados = getIntent().getExtras();
        if(dados !=  null){
            categoria = dados.getString("categoria");

        }
        lista = new ArrayList<>();
        adapter = new AdapterGrid(getApplicationContext(),0,lista);
        recebendoWallpapers();
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ParseObject object= lista.get(position);
                String urlImg = "";
                urlImg= object.getParseFile("img_wallpaper").getUrl();
                Intent intent = new Intent(getApplicationContext(), ImagemActivity.class);
                intent.putExtra("url_imagem",urlImg);
                startActivity(intent);
            }
        });
    }
    private void recebendoWallpapers() {
        query = ParseQuery.getQuery("Wallpapers");
        query.orderByAscending("objectId");
//        query.whereEqualTo("wallpaper",categoria);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null && objects.size() > 0){
                    lista.clear();
                    for(ParseObject x: objects){
                        lista.add(x);
                    }
                    adapter.notifyDataSetChanged();


                }
            }
        });
    }
}