package com.ramaciotti.biblioteca_furb;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BibliotecaFurbLogin extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biblioteca_furb_login);
    }
    
    public void onRenovar(View view) {
    	String usuario = ((EditText) findViewById(R.id.edit_username)).getText().toString();
    	String senha = ((EditText) findViewById(R.id.edit_password)).getText().toString();
    	
    	Renovador renovador = new Renovador(this, usuario, senha);
    	renovador.start();
    }
    
    public void showText(final String text) {
    	final Activity thisActivity = this;
    	
    	runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(thisActivity, text, Toast.LENGTH_SHORT).show();
			}
		});
    }

    public void toggleLoginButton() {
    	final Button renovar = (Button) findViewById(R.id.button_login);
    	
    	runOnUiThread(new Runnable() {
    		public void run() {
    	    	renovar.setEnabled(!renovar.isEnabled());    			
    		}
    	});
    }
}
