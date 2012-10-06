package com.ramaciotti.biblioteca_furb;

import java.util.List;

public class Renovador extends Thread {
	private BibliotecaFurbLogin mLoginActivity;
	private String mUsuario;
	private String mSenha;
	
	public Renovador(BibliotecaFurbLogin loginActivity, String usuario, String senha) {
		this.mLoginActivity = loginActivity;
		this.mUsuario = usuario;
		this.mSenha = senha;
	}

	public void run() {
		mLoginActivity.toggleLoginButton();
		
		LoginService loginService = new LoginService(new LoginUrl(), new UrlRedirection(), new UrlCookies());
		List<String> cookies = null;
   		try {
			cookies = loginService.getCookies(mUsuario, mSenha);
			
			RenovacaoService renovacaoService = new RenovacaoService(cookies);
			List<String> registros = renovacaoService.buscaRegistros();
			
			for(String registro : registros) {
				renovacaoService.renova(registro);
			}
			
			mLoginActivity.showText(registros.toString());
		} catch (LoginInvalidoException e) {
			mLoginActivity.showText("Nome de usuário ou senha incorretos.");
		} catch (Exception e) {
			mLoginActivity.showText("Falha ao fazer login.");
		}
   		
   		mLoginActivity.toggleLoginButton();
	}
}
