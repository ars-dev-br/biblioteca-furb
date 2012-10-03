package com.ramaciotti.biblioteca_furb;

public class Renovador extends Thread {
	private BibliotecaFurbLogin mLoginActivity;
	private String mUsuario;
	private String mSenha;
	
	public Renovador(BibliotecaFurbLogin mLoginActivity, String mUsuario, String mSenha) {
		this.mLoginActivity = mLoginActivity;
		this.mUsuario = mUsuario;
		this.mSenha = mSenha;
	}

	public void run() {
		mLoginActivity.toggleLoginButton();
		
		LoginService loginService = new LoginService(new LoginUrl(), new UrlRedirection(), new UrlCookies());
   		try {
			loginService.getCookies(mUsuario, mSenha);
			mLoginActivity.showText("Login bem sucedido");
		} catch (Exception e) {
			mLoginActivity.showText("Falha ao fazer login");
		}
   		
   		mLoginActivity.toggleLoginButton();
	}
}
