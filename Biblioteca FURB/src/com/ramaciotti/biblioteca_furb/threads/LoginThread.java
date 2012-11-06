package com.ramaciotti.biblioteca_furb.threads;

import java.util.List;

import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;

import com.ramaciotti.biblioteca_furb.LoginInvalidoException;
import com.ramaciotti.biblioteca_furb.LoginService;
import com.ramaciotti.biblioteca_furb.activities.LoginActivity;

public class LoginThread extends Thread {
	
	private LoginActivity mLoginActivity;
	private String mUsuario;
	private String mSenha;
	
	public LoginThread(LoginActivity mLoginActivity, String mUsuario,
			String mSenha) {
		this.mLoginActivity = mLoginActivity;
		this.mUsuario = mUsuario;
		this.mSenha = mSenha;
	}

	public void run() {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		LoginService loginService = new LoginService(httpClient);
		
		try {
			List<Cookie> cookies = loginService.getCookies(mUsuario, mSenha); 
			mLoginActivity.loginComSucesso(cookies);
		} catch(LoginInvalidoException e) {
			mLoginActivity.loginSemSucesso("Nome de usuário ou senha incorretos.");
		} catch(Exception e) {
			e.printStackTrace();
			mLoginActivity.loginSemSucesso("Falha ao fazer login.");
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
	}

}
