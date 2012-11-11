package com.ramaciotti.biblioteca_furb.threads;

import org.apache.http.impl.client.AbstractHttpClient;

import com.ramaciotti.biblioteca_furb.RegistrosRepository;
import com.ramaciotti.biblioteca_furb.activities.ListaLivrosActivity;

public class ListaLivrosThread extends Thread {
	
	private ListaLivrosActivity mListaLivrosActivity;
	private AbstractHttpClient mHttpClient;
	
	public ListaLivrosThread(ListaLivrosActivity listaLivrosActivity, AbstractHttpClient httpClient) {
		this.mListaLivrosActivity = listaLivrosActivity;
		this.mHttpClient = httpClient; 
	}
	
	public void run() {
		RegistrosRepository repository = new RegistrosRepository(mHttpClient);
		try {
			mListaLivrosActivity.setRegistros(repository.buscaRegistros());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
