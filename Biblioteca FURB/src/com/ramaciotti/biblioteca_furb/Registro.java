package com.ramaciotti.biblioteca_furb;

public class Registro {
	private String mCodigo;
	private String mTitulo;

	public Registro(String codigo, String titulo) {
		this.mCodigo = codigo;
		this.mTitulo = titulo;
	}
	
	@Override
	public String toString() {
		return mTitulo.substring(0, 30) + "...";
	}
	
	public String getCodigo() {
		return mCodigo;
	}
}
