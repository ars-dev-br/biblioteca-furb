package com.ramaciotti.biblioteca_furb.threads;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;

import android.widget.SimpleAdapter;

import com.ramaciotti.biblioteca_furb.R;
import com.ramaciotti.biblioteca_furb.RenovacaoService;
import com.ramaciotti.biblioteca_furb.activities.ListaLivrosActivity;

public class ListaLivrosThread extends Thread {
	
	private ListaLivrosActivity mListaLivrosActivity;
	private Cookie mCookie;
	
	public ListaLivrosThread(ListaLivrosActivity listaLivrosActivity, String cookie) {
		this.mListaLivrosActivity = listaLivrosActivity;
		this.mCookie = remontaCookie(cookie);
	}
	
	public void run() {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		httpClient.setCookieStore(remontaCookieStore(mCookie));
		
		RenovacaoService renovacao = new RenovacaoService(httpClient);

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
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
	}
	
	private Cookie remontaCookie(final String cookieValue) {
		return new Cookie() {
			public boolean isSecure() { return false; }
			public boolean isPersistent() { return false; }
			public boolean isExpired(Date date) { return false; }
			public int getVersion() { return 0; }
			public String getValue() { return cookieValue; }
			public int[] getPorts() { return null; }
			public String getPath() { return "/"; }
			public String getName() { return "PHPSESSID"; }
			public Date getExpiryDate() { return null; }
			public String getDomain() { return "www.bc.furb.br"; }
			public String getCommentURL() { return null; }
			public String getComment() { return null; }
		};
	}
	
	private CookieStore remontaCookieStore(final Cookie cookie) {
		return new CookieStore() {
			public List<Cookie> getCookies() {
				List<Cookie> lista = new ArrayList<Cookie>();
				lista.add(cookie);
				return lista;
			}
			
			public boolean clearExpired(Date date) { return false; }
			public void clear() { }
			public void addCookie(Cookie cookie) { }
		};
	}
}
