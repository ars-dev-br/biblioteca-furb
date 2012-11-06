package com.ramaciotti.biblioteca_furb.test;

import com.ramaciotti.biblioteca_furb.LoginInvalidoException;
import com.ramaciotti.biblioteca_furb.networking.UrlRedirection;

import android.test.AndroidTestCase;

public class UrlRedirectionTest extends AndroidTestCase {
	
	public UrlRedirectionTest() {
		super();
	}
	
	public void testLancaExcecaoComPaginaEmBranco() {
		final String pagina = "";
		
		try {
			new UrlRedirection().getUrl(pagina);
			fail("Não lançou exceção esperada.");
		} catch (LoginInvalidoException e) {
		} catch (Exception e) {
			fail("Lançou exceção de tipo inesperado.");
		}
	}
	
	public void testReconheceUrlCorretamente() throws LoginInvalidoException {
		final String pagina = "<script language=\"JavaScript\">\n" +
				"location.replace('http://teste.com&idUnico=feaa886" +
				"fb0ba525c137f77e891b1c980&login=usuario&" +
				"cdPessoa=123456&link=');\n" +
				"</script>\n<html>\n...";
		
		final String esperada =	"http://teste.com&idUnico=feaa886" +
				"fb0ba525c137f77e891b1c980&login=usuario&cdPessoa=123456&link=";
		
		assertEquals(esperada, new UrlRedirection().getUrl(pagina));
	}
	
}
