package com.ramaciotti.biblioteca_furb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

public class ResponseContent {

	public String getFullContent(HttpResponse response) throws IllegalStateException, IOException {
		HttpEntity entity = response.getEntity();
		
		BufferedReader input = new BufferedReader(new InputStreamReader(entity.getContent()));
		StringBuilder sb = new StringBuilder();
		String line = null;
		
		while((line = input.readLine()) != null) {
			sb.append(line);
		}
		
		return sb.toString();
	}
	
	public List<String> getLines(HttpResponse response) throws IOException {
		HttpEntity entity = response.getEntity();
		
		BufferedReader input = new BufferedReader(new InputStreamReader(entity.getContent()));
		List<String> linhas = new ArrayList<String>();
		String linha = null;
		
		while((linha = input.readLine()) != null) {
			linhas.add(linha);
		}
		
		return linhas;
	}
}
