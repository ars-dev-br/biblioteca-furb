package com.ramaciotti.biblioteca_furb;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.AbstractHttpClient;

public class RenovacaoService {
	private static final String urlBase = "http://www.bc.furb.br/consulta/servicosUsuario/";
	private static final String urlSituacao = urlBase + "situacao_usuario.php";
	private static final String urlRenovacao = urlBase + "servicos.php?tpServico=renovar&nrRegistro=%s";
	
	private AbstractHttpClient mHttpClient;
	
	public RenovacaoService(AbstractHttpClient httpClient) {
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
	
	/*public boolean renova(String registro) throws Exception {
		HttpURLConnection connection = new ConnectionBuilder(String.format(urlRenovacao, registro))
										   .withCookie(mCookie)
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
	}*/
}
