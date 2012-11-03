package com.ramaciotti.biblioteca_furb;

public class LoginThread extends Thread {
	
	private BibliotecaFurbLogin mLoginActivity;
	private String mUsuario;
	private String mSenha;
	
	public LoginThread(BibliotecaFurbLogin mLoginActivity, String mUsuario,
			String mSenha) {
		this.mLoginActivity = mLoginActivity;
		this.mUsuario = mUsuario;
		this.mSenha = mSenha;
	}

	public void run() {
		LoginService loginService = new LoginService(new LoginUrl(), new UrlRedirection(), new UrlCookies());
		String cookie = null;
		
		try {
			cookie = loginService.getCookies(mUsuario, mSenha).get(0);
			mLoginActivity.loginComSucesso(cookie);
		} catch(LoginInvalidoException e) {
			mLoginActivity.loginSemSucesso("Nome de usuário ou senha incorretos.");
		} catch(Exception e) {
			mLoginActivity.loginSemSucesso("Falha ao fazer login.");
		}
	}

}
