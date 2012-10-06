package com.ramaciotti.biblioteca_furb;

import java.net.HttpURLConnection;

public class LoginUrl {
	private static final String url = "https://www.furb.br/scripts/ssl_ldap/ssl_ldap_bc.php";
	private static final String queryFormat = "acao=validar&"
			+ "pagina=http://www.bc.furb.br/consulta/servicosUsuario/"
			+ "loginServicos.php?acao=validar&login=%s&senha=%s";
	
	HttpURLConnection getConnection(String user, String password) throws Exception {
		return new ConnectionBuilder(url)
				   .withPostQuery(String.format(queryFormat, user, password))
				   .getConnection();
	}
	
}
