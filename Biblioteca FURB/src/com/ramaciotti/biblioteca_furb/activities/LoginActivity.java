package com.ramaciotti.biblioteca_furb.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ramaciotti.biblioteca_furb.Preferencias;
import com.ramaciotti.biblioteca_furb.R;
import com.ramaciotti.biblioteca_furb.threads.LoginThread;

public class LoginActivity extends Activity {
	
	ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biblioteca_furb_login);
        
        Preferencias preferencias = new Preferencias(getPreferences(Context.MODE_PRIVATE));
        String usuario = preferencias.getUsuario();
        String senha = preferencias.getSenha();
        
        if(!usuario.equals("") && !senha.equals("")) {
        	setUsuario(usuario);
        	setSenha(senha);
        	fazLogin(usuario, senha);
        }
    }
    
    private void fazLogin(String usuario, String senha) {
    	mProgressDialog = ProgressDialog.show(this, "", "Fazendo login...", true);
    	new LoginThread(this, usuario, senha).start();
    }
    
    public void loginComSucesso(String cookie) {
    	Preferencias preferencias = new Preferencias(getPreferences(Context.MODE_PRIVATE));
    	preferencias.salvaLogin(getUsuario(), getSenha());
    	
    	mProgressDialog.dismiss();
    	
    	Intent intent = new Intent(this, ListaLivrosActivity.class);
    	intent.putExtra("cookie", cookie);
    	startActivity(intent);
    }
    
    public void loginSemSucesso(final String text) {
    	mProgressDialog.dismiss();
    	
		final Activity thisActivity = this;
		runOnUiThread(new Runnable() {
			public void run() {
				new AlertDialog.Builder(thisActivity)
						.setTitle("Biblioteca FURB")
						.setMessage(text)
						.setPositiveButton("OK", null)
						.show();
			}
		});
    }
    
    public void onLogin(View view) {
    	fazLogin(getUsuario(), getSenha());
    }
    
	private String getSenha() {
		return ((EditText) findViewById(R.id.edit_password)).getText().toString();
	}
	
	private void setSenha(String senha) {
		((EditText) findViewById(R.id.edit_password)).setText(senha);
	}

	private String getUsuario() {
		return ((EditText) findViewById(R.id.edit_username)).getText().toString().toLowerCase();
	}
	
	private void setUsuario(String usuario) {
		((EditText) findViewById(R.id.edit_username)).setText(usuario);
	}
	
}
