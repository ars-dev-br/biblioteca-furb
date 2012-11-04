package com.ramaciotti.biblioteca_furb.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListAdapter;

import com.ramaciotti.biblioteca_furb.threads.ListaLivrosThread;

public class ListaLivrosActivity extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Intent intent = getIntent();
        String cookie = intent.getStringExtra("cookie");
        
        new ListaLivrosThread(this, cookie).start();
    }
    
    public void setAdaptador(final ListAdapter adapter) {
    	final ListaLivrosActivity t = this;
    	
    	runOnUiThread(new Runnable() {
			public void run() {
				t.setListAdapter(adapter);
			}
		});
    }
}
