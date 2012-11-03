package com.ramaciotti.biblioteca_furb;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BibliotecaFurbLogin extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biblioteca_furb_login);
        
        Preferencias preferencias = new Preferencias(getPreferences(Context.MODE_PRIVATE));
        String usuario = preferencias.getUsuario();
        String senha = preferencias.getSenha();
        
        if(!usuario.equals("") && !senha.equals("")) {
        	new LoginThread(this, usuario, senha).start();
        }
    }
    
    public void loginComSucesso(String cookie) {
    	Preferencias preferencias = new Preferencias(getPreferences(Context.MODE_PRIVATE));
    	preferencias.salvaLogin(getUsuario(), getSenha());
    	
    	Intent intent = new Intent(this, ListaLivros.class);
    	intent.putExtra("cookie", cookie);
    	startActivity(intent);
    }
    
    public void loginSemSucesso(final String text) {
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
    	new LoginThread(this, getUsuario(), getSenha()).start();
    }
    
	private String getSenha() {
		return ((EditText) findViewById(R.id.edit_password)).getText().toString();
	}

	private String getUsuario() {
		return ((EditText) findViewById(R.id.edit_username)).getText().toString().toLowerCase();
	}
	
}
