package com.mauricao.cursomc.resources.exception;

import java.io.Serializable;

public class FieldMessage implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String fieldName;
	private String message;
	
	public FieldMessage() {
		
	}

	public FieldMessage(String nome, String message) {
		super();
		this.fieldName = nome;
		this.message = message;
	}

	public String getNome() {
		return fieldName;
	}

	public void setNome(String nome) {
		this.fieldName = nome;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

}
