package com.ramaciotti.biblioteca_furb.networking;

import java.util.Date;

import org.apache.http.cookie.Cookie;

public class CookieImpl implements Cookie {
	private String mCookieValue;
	
	public CookieImpl(String cookieValue) {
		mCookieValue = cookieValue;
	}
	
	public boolean isSecure() { return false; }
	public boolean isPersistent() { return false; }
	public boolean isExpired(Date date) { return false; }
	public int getVersion() { return 0; }
	public String getValue() { return mCookieValue; }
	public int[] getPorts() { return null; }
	public String getPath() { return "/"; }
	public String getName() { return "PHPSESSID"; }
	public Date getExpiryDate() { return null; }
	public String getDomain() { return "www.bc.furb.br"; }
	public String getCommentURL() { return null; }
	public String getComment() { return null; }
}
