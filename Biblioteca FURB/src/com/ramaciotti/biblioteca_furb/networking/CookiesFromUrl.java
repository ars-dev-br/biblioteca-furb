package com.ramaciotti.biblioteca_furb.networking;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;

public class CookiesFromUrl {

	private AbstractHttpClient mHttpClient;

	public CookiesFromUrl(AbstractHttpClient httpClient) {
		this.mHttpClient = httpClient;
	}
	
	public List<Cookie> getCookies(String url) throws ClientProtocolException, IOException {
		mHttpClient.execute(new HttpGet(url));
		return mHttpClient.getCookieStore().getCookies();
	}
}
