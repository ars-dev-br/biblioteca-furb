package com.ramaciotti.biblioteca_furb.threads;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.widget.SimpleAdapter;

import com.ramaciotti.biblioteca_furb.R;
import com.ramaciotti.biblioteca_furb.RenovacaoService;
import com.ramaciotti.biblioteca_furb.activities.ListaLivrosActivity;

public class ListaLivrosThread extends Thread {
	
	private ListaLivrosActivity mListaLivrosActivity;
	private String mCookie;
	
	public ListaLivrosThread(ListaLivrosActivity listaLivrosActivity, String cookie) {
		this.mListaLivrosActivity = listaLivrosActivity;
		this.mCookie = cookie;
	}
	
	public void run() {
		RenovacaoService renovacao = new RenovacaoService(mCookie);

		try {
			List<String> registros = renovacao.buscaRegistros();

			List<HashMap<String, String>> fill = new ArrayList<HashMap<String, String>>();
			for(String registro : registros) {
				HashMap<String, String> hash = new HashMap<String, String>();
				hash.put("titulo", registro);
				
				fill.add(hash);
			}
			
			SimpleAdapter adapter = new SimpleAdapter(mListaLivrosActivity, fill, R.layout.lista_livros_item,
					new String[] { "titulo" }, new int[] { R.id.lista_livros_titulo } );
			
			mListaLivrosActivity.setAdaptador(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
