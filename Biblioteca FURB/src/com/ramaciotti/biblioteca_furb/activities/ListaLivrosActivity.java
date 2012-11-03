package com.ramaciotti.biblioteca_furb.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.SimpleAdapter;

import com.ramaciotti.biblioteca_furb.R;

public class ListaLivrosActivity extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Intent intent = getIntent();
        String cookie = intent.getStringExtra("cookie");
        
        List<HashMap<String, String>> fill = new ArrayList<HashMap<String, String>>();
        
        HashMap<String, String> hash = new HashMap<String, String>();
        hash.put("titulo", cookie);
        
        fill.add(hash);
        
        SimpleAdapter adapter = new SimpleAdapter(this, fill, R.layout.lista_livros_item,
        		new String[] { "titulo" }, new int[] { R.id.lista_livros_titulo } );
        
        setListAdapter(adapter);
    }
}
