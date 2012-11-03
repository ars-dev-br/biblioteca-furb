package com.ramaciotti.networking;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ramaciotti.biblioteca_furb.LoginInvalidoException;

public class UrlRedirection {

	public String redirectedUrl(HttpURLConnection connection) throws Exception {
		try {
			connection.connect();
			
			StringBuilder sb = new StringBuilder();
			InputStreamReader input = new InputStreamReader(connection.getInputStream());
			BufferedReader reader = new BufferedReader(input);
			
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			
			Matcher matcher = Pattern.compile(".*location.replace\\('(.*?)'\\).*?").matcher(sb.toString());
			matcher.find();

			return matcher.group(1);
		} catch(IllegalStateException ex) {
			throw new LoginInvalidoException();
		} finally {
			connection.disconnect();
		}
	}
	
}
