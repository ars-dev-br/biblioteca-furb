package com.ramaciotti.biblioteca_furb;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class BibliotecaFurbLogin extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biblioteca_furb_login);
    }
    
    public void onRenovar(View view) {
    	final BibliotecaFurbLogin thisActivity = this;
    	
    	new Thread(new Runnable() {
			public void run() {
				EditText usuario = (EditText) findViewById(R.id.edit_username);
		    	EditText password = (EditText) findViewById(R.id.edit_password);
		    	
		   		LoginService loginService = new LoginService(new LoginUrl(), new UrlRedirection(), new UrlCookies());
		   		try {
					loginService.getCookies(usuario.getText().toString(), password.getText().toString());
					thisActivity.showText("Login bem sucedido");
				} catch (Exception e) {
					thisActivity.showText("Falha ao fazer login");
				}
			}
		}).start();
    }
    
    public void showText(final String text) {
    	final Activity thisActivity = this;
    	
    	runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(thisActivity, text, Toast.LENGTH_SHORT).show();
			}
		});
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_biblioteca_furb_login, menu);
        return true;
    }*/
}
