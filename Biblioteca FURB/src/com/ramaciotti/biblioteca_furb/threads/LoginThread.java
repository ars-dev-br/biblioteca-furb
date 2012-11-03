package com.ramaciotti.biblioteca_furb.threads;

import java.util.List;

import com.ramaciotti.biblioteca_furb.LoginInvalidoException;
import com.ramaciotti.biblioteca_furb.LoginService;
import com.ramaciotti.biblioteca_furb.LoginUrl;
import com.ramaciotti.biblioteca_furb.activities.LoginActivity;
import com.ramaciotti.networking.UrlCookies;
import com.ramaciotti.networking.UrlRedirection;

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
		LoginService loginService = new LoginService(new LoginUrl(), new UrlRedirection(), new UrlCookies());
		String cookie = null;
		
		try {
			List<String> cookies = loginService.getCookies(mUsuario, mSenha); 
			cookie = cookies.get(0);
			mLoginActivity.loginComSucesso(cookie);
		} catch(LoginInvalidoException e) {
			mLoginActivity.loginSemSucesso("Nome de usuário ou senha incorretos.");
		} catch(Exception e) {
			e.printStackTrace();
			mLoginActivity.loginSemSucesso("Falha ao fazer login.");
		}
	}

}
