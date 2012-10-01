package com.ramaciotti.biblioteca_furb;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class UrlCookies {

	public List<String> getCookies(HttpURLConnection connection) {
		try {
			return connection.getHeaderFields().get("Set-Cookie");
		} finally {
			connection.disconnect();
		}
	}
	
	public List<String> getCookies(String url) throws Exception {
		return getCookies((HttpURLConnection) new URL(url).openConnection());
	}
	
}
