package com.ramaciotti.biblioteca_furb;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;

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
			salvaLogin();
			
			RenovacaoService renovacaoService = new RenovacaoService(cookies);
			List<String> reservados = new ArrayList<String>();
			
			for(String registro : renovacaoService.buscaRegistros()) {
				if(!renovacaoService.renova(registro))
					reservados.add(registro);
			}

			if(reservados.isEmpty()) {
				mLoginActivity.showText("Empréstimos renovados com sucesso.");
			} else if(reservados.size() == 1) {
				mLoginActivity.showText("Algum empréstimo não pode ser reservado: " + reservados);
			} else {
				mLoginActivity.showText("Alguns empréstimos não puderam ser reservados: " + reservados);
			}
		} catch (LoginInvalidoException e) {
			mLoginActivity.showText("Nome de usuário ou senha incorretos.");
		} catch (Exception e) {
			mLoginActivity.showText("Falha ao fazer login.");
		}
   		
   		mLoginActivity.toggleLoginButton();
	}

	private void salvaLogin() {
		SharedPreferences.Editor editor = mLoginActivity.getPreferences(Context.MODE_PRIVATE).edit();
		editor.putString(BibliotecaFurbLogin.sharedPrefUsuario, mUsuario);
		editor.putString(BibliotecaFurbLogin.sharedPrefSenha, mSenha);
		editor.commit();
	}
}
