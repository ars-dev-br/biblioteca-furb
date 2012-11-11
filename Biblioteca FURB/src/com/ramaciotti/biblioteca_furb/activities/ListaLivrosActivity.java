package com.ramaciotti.biblioteca_furb.activities;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.ramaciotti.biblioteca_furb.R;
import com.ramaciotti.biblioteca_furb.networking.CookieImpl;
import com.ramaciotti.biblioteca_furb.networking.CookieStoreImpl;
import com.ramaciotti.biblioteca_furb.threads.ListaLivrosThread;
import com.ramaciotti.biblioteca_furb.threads.RenovaLivrosThread;

public class ListaLivrosActivity extends ListActivity {
	private AbstractHttpClient mHttpClient;
	private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        
        Intent intent = getIntent();
        String cookie = intent.getStringExtra("cookie");

        mHttpClient = new DefaultHttpClient();
        mHttpClient.setCookieStore(new CookieStoreImpl(new CookieImpl(cookie)));
        
        mProgressDialog = ProgressDialog.show(this, "", "Carregando livros...", true);
        new ListaLivrosThread(this, mHttpClient).start();
    }
    
    public void setRegistros(List<String> registros) {
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_multiple_choice, registros);
    	setAdaptador(adapter);
    	
    	mProgressDialog.dismiss();
    }
    
    public void setAdaptador(final ListAdapter adapter) {
    	final ListaLivrosActivity thisActivity = this;
    	
    	runOnUiThread(new Runnable() {
			public void run() {
				thisActivity.setListAdapter(adapter);
				ListView listView = thisActivity.getListView();
				
				for(int i = 0; i < listView.getCount(); i++) {
					listView.setItemChecked(i, true);
				}
			}
		});
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.lista_livros, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
    	// por enquanto, só há um item a ser selecionado.
    	renovaLivrosSelecionados();
    	return true;
    }
    
    public void renovaLivrosSelecionados() {
    	mProgressDialog = ProgressDialog.show(this, "", "Renovando livros...", true);
    	
    	SparseBooleanArray checkedItems = getListView().getCheckedItemPositions();
    	List<String> registros = new ArrayList<String>();
    	
    	for(int i = 0; i < checkedItems.size(); i++) {
    		if(checkedItems.valueAt(i))
    			registros.add((String) getListAdapter().getItem(checkedItems.keyAt(i)));
    	}
    	
    	new RenovaLivrosThread(this, registros, mHttpClient).start();
    }
    
    public void renovacaoConcluida(final String mensagem) {
    	mProgressDialog.dismiss();
    	
    	final Activity thisActivity = this;
    	runOnUiThread(new Runnable() {
    		public void run() {
    			new AlertDialog.Builder(thisActivity)
    					.setTitle("Biblioteca FURB")
    					.setMessage(mensagem)
    					.setPositiveButton("OK", null)
    					.show();
    		}
    	});
    }
    
    @Override
    public void onDestroy() {
    	mHttpClient.getConnectionManager().shutdown();
    }
}
