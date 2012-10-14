package com.ramaciotti.biblioteca_furb;

import java.util.List;

import android.content.Intent;

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
			
			Intent intent = new Intent(mLoginActivity, ListaLivros.class);
			intent.putExtra("com.ramaciotti.biblioteca_furb.cookies", cookies.get(0));
			mLoginActivity.startActivity(intent);
   		} catch(Exception e) {
   			e.printStackTrace();
   		}
   		/*} catch (LoginInvalidoException e) {
			mLoginActivity.showText("Nome de usuário ou senha incorretos.");
		} catch (Exception e) {
			mLoginActivity.showText("Falha ao fazer login.");
		}*/
	}
}
