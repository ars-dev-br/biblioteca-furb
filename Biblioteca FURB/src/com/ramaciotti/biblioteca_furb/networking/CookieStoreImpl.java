package com.ramaciotti.biblioteca_furb.networking;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;

public class CookieStoreImpl implements CookieStore {
	List<Cookie> mLista = new ArrayList<Cookie>();
	
	public CookieStoreImpl(Cookie cookie) {
		mLista.add(cookie);
	}
	
	public List<Cookie> getCookies() {
		return mLista;
	}
	
	public boolean clearExpired(Date date) { return false; }
	public void clear() { }
	public void addCookie(Cookie cookie) { }
}
