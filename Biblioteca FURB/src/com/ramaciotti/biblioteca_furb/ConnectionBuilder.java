package com.ramaciotti.biblioteca_furb;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

public class ConnectionBuilder {
	private static final String charset = Charset.defaultCharset().name();
	
	private HttpURLConnection mConnection;
	
	public ConnectionBuilder(String url) throws Exception {
		mConnection = (HttpURLConnection) new URL(url).openConnection();
	}
	
	public ConnectionBuilder withCookies(List<String> cookies) {
		for(String cookie : cookies) {
			mConnection.addRequestProperty("Cookie", cookie.split(";", 2)[0]);
		}
		
		return this;
	}
	
	public ConnectionBuilder withPostQuery(String query) throws Exception {
		mConnection.setDoOutput(true);
		mConnection.setRequestMethod("POST");
		
		OutputStream output = null;
		try {
			output = mConnection.getOutputStream();
			output.write(query.getBytes(charset));
		} finally {
			if(output != null) {
				output.close();
			}
		}
		
		return this;
	}
	
	public HttpURLConnection getConnection() {
		return mConnection;
	}
}
