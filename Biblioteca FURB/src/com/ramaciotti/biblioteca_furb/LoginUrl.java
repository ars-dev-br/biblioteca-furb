package com.ramaciotti.biblioteca_furb;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class LoginUrl {
	private static final String url = "https://www.furb.br/scripts/ssl_ldap/ssl_ldap_bc.php";
	private static final String queryFormat = "acao=validar&"
			+ "pagina=http://www.bc.furb.br/consulta/servicosUsuario/"
			+ "loginServicos.php?acao=validar&login=%s&senha=%s";
	
	private static final String charset = Charset.defaultCharset().name();

	HttpURLConnection getConnection(String user, String password) throws Exception {
		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		
		connection.connect();
		
		OutputStream output = null;
		try {
			output = connection.getOutputStream();
			output.write(String.format(queryFormat, user, password).getBytes(charset));
		} finally {
			if(output != null) {
				output.close();
			}
		}
		
		return connection;
	}
	
}
