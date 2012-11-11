package com.ramaciotti.biblioteca_furb;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.AbstractHttpClient;

import com.ramaciotti.biblioteca_furb.networking.ResponseContent;

public class RegistrosRepository {
	private static final String urlBase = "http://www.bc.furb.br/consulta/servicosUsuario/";
	private static final String urlSituacao = urlBase + "situacao_usuario.php";
	
	private AbstractHttpClient mHttpClient;
	
	public RegistrosRepository(AbstractHttpClient httpClient) {
		this.mHttpClient = httpClient;
	}

	public List<String> buscaRegistros() throws Exception {
		HttpGet httpGet = new HttpGet(urlSituacao);
		HttpResponse response = mHttpClient.execute(httpGet);
		
		List<String> conteudo = new ResponseContent().getLines(response);
		
		List<String> registros = new ArrayList<String>();
		Pattern pattern = Pattern.compile("_blank\">(.*)</A>&nbsp");
		for(String linha : conteudo) {
			Matcher matcher = pattern.matcher(linha);
			if(matcher.find()) {
				registros.add(matcher.group(1));
			}
		}
		
		return registros;
	}
}
