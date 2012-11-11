package com.ramaciotti.biblioteca_furb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.AbstractHttpClient;

import com.ramaciotti.biblioteca_furb.networking.ResponseContent;

public class RegistrosRepository {
	private static final String urlSituacao = "http://www.bc.furb.br/consulta/servicosUsuario/situacao_usuario.php";
	private static final String urlDados = "http://www.bc.furb.br/consulta/novaConsulta/recuperaMfn.php?NrRegistro=%s"; 
	
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
	
	public String buscaTitulo(String registro) throws ClientProtocolException, IOException {
		HttpGet httpGet = new HttpGet(String.format(urlDados, registro));
		HttpResponse response = mHttpClient.execute(httpGet);
		
		String conteudo = new ResponseContent().getFullContent(response);
		Matcher matcher = Pattern.compile("tulo</th><td>(.*?)</td>").matcher(conteudo);
		
		matcher.find();
		
		String titulo = matcher.group(1);
		return titulo;
	}
}
