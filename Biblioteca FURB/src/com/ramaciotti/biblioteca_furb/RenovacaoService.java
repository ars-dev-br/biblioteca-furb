package com.ramaciotti.biblioteca_furb;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RenovacaoService {
	private static final String urlBase = "http://www.bc.furb.br/consulta/servicosUsuario/";
	private static final String urlSituacao = urlBase + "situacao_usuario.php";
	private static final String urlRenovacao = urlBase + "servicos.php?tpServico=renovar&nrRegistro=%s";
	
	private List<String> mCookies;
	
	public RenovacaoService(List<String> cookies) {
		this.mCookies = cookies;
	}
	
	public List<String> buscaRegistros() throws Exception {
		HttpURLConnection connection = new ConnectionBuilder(urlSituacao)
										   .withCookies(mCookies)
										   .getConnection();
		
		try {
			connection.connect();
			
			List<String> linhas = new ArrayList<String>();
			InputStreamReader input = new InputStreamReader(connection.getInputStream());
			BufferedReader reader = new BufferedReader(input);
			
			while(reader.ready()) {
				linhas.add(reader.readLine());
			}
			
			List<String> registros = new ArrayList<String>();
			Pattern pattern = Pattern.compile("_blank\">(.*)</A>&nbsp");
			for(String linha : linhas) {
				Matcher matcher = pattern.matcher(linha);
				if(matcher.find()) {
					registros.add(matcher.group(1));
				}
			}
			
			return registros;		
		} finally {
			connection.disconnect();
		}
	}

	public boolean renova(String registro) throws Exception {
		HttpURLConnection connection = new ConnectionBuilder(String.format(urlRenovacao, registro))
										   .withCookies(mCookies)
										   .getConnection();
		
		try {
			connection.connect();
			
			InputStreamReader input = new InputStreamReader(connection.getInputStream());
			BufferedReader reader = new BufferedReader(input);
			StringBuilder sb = new StringBuilder();
			while(reader.ready()) {
				sb.append(reader.readLine());
			}
			
			return !sb.toString().contains("Obra possui reservas");			
		} finally {
			connection.disconnect();
		}
	}
}
