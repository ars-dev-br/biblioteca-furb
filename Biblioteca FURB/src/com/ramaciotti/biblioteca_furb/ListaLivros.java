package com.ramaciotti.biblioteca_furb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;

public class ListaLivros extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Intent intent = getIntent();
        
        List<HashMap<String, String>> fill = new ArrayList<HashMap<String, String>>();
        
        HashMap<String, String> hash = new HashMap<String, String>();
        hash.put("titulo", intent.getStringExtra("com.ramaciotti.biblioteca_furb.cookies"));
        
        fill.add(hash);
        
        SimpleAdapter adapter = new SimpleAdapter(this, fill, R.layout.lista_livros_item,
        		new String[] { "titulo" }, new int[] { R.id.lista_livros_titulo } );
        
        setListAdapter(adapter);
    }

}
