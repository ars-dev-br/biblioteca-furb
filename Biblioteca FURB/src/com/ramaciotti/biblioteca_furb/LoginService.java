package com.ramaciotti.biblioteca_furb;

import java.net.HttpURLConnection;
import java.util.List;

public class LoginService {
	private LoginUrl mLoginUrl;
	private UrlRedirection mUrlRedirection;
	private UrlCookies mUrlCookies;

	public LoginService(LoginUrl loginUrl, UrlRedirection urlRedirection, UrlCookies urlCookies) {
		this.mLoginUrl = loginUrl;
		this.mUrlRedirection = urlRedirection;
		this.mUrlCookies = urlCookies;
	}
	
	public List<String> getCookies(String user, String password) throws Exception {
		HttpURLConnection connection = mLoginUrl.getConnection(user, password);
		String redirectedUrl = mUrlRedirection.redirectedUrl(connection);
		
		return mUrlCookies.getCookies(redirectedUrl);
	}
	
}
