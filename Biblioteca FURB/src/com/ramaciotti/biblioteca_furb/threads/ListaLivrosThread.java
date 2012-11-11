package com.ramaciotti.biblioteca_furb.threads;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.impl.client.AbstractHttpClient;

import com.ramaciotti.biblioteca_furb.Registro;
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
			List<String> codigos = repository.buscaRegistros();
			List<Registro> registros = new ArrayList<Registro>();
			
			for(String codigo : codigos) {
				registros.add(new Registro(codigo, repository.buscaTitulo(codigo)));
			}
			
			mListaLivrosActivity.setRegistros(registros);
		} catch (Exception e) {
			e.printStackTrace();
			mListaLivrosActivity.renovacaoConcluida("Houve um erro ao se listarem os livros. " +
					"Tente novamente mais tarde ou, se o problema persistir, " +
					"entre em contato comigo através do Google Play.");
		}
	}
}
