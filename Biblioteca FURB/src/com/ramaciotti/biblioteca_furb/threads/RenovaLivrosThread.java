package com.ramaciotti.biblioteca_furb.threads;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.impl.client.AbstractHttpClient;

import com.ramaciotti.biblioteca_furb.RenovacaoService;
import com.ramaciotti.biblioteca_furb.activities.ListaLivrosActivity;

public class RenovaLivrosThread extends Thread {
	private ListaLivrosActivity mListaLivrosActivity;
	private List<String> mRegistros;
	private AbstractHttpClient mHttpClient;
	
	public RenovaLivrosThread(ListaLivrosActivity listaLivrosActivity,
			List<String> registros, AbstractHttpClient httpClient) {
		this.mListaLivrosActivity = listaLivrosActivity;
		this.mRegistros = registros;
		this.mHttpClient = httpClient;
	}

	public void run() {
		RenovacaoService renovacaoService = new RenovacaoService(mHttpClient);
		List<String> naoRenovados = new ArrayList<String>();
		
		try {
			for(String registro : mRegistros) {
				if(!renovacaoService.renova(registro))
					naoRenovados.add(registro);
			}
			
			if(naoRenovados.size() == 0)
				mListaLivrosActivity.renovacaoConcluida("Livros renovados com sucesso.");
			else if(naoRenovados.size() == 1)
				mListaLivrosActivity.renovacaoConcluida("Um livro não pode ser renovado " +
						"por já possuir reservas: " + naoRenovados);
			else
				mListaLivrosActivity.renovacaoConcluida("Alguns livros não puderam ser renovados " +
						"por já possuírem reservas: " + naoRenovados);
		} catch(Exception e) {
			mListaLivrosActivity.renovacaoConcluida("Houve um erro ao se fazer a renovação. " +
					"Tente novamente mais tarde ou, se o problema persistir, " +
					"entre em contato comigo através do Google Play.");
		}
	}
}
