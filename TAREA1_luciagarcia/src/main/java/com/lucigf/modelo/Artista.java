package com.lucigf.modelo;

import java.util.HashSet;
import java.util.Set;

public class Artista extends Persona {

	private Long idArt;
	private String apodo = null;
	private Set<Especialidad> especialidades = new HashSet<Especialidad>();
	private Set<Numero> numeros = new HashSet<>();

	public Artista() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Artista(Long idArt, String apodo) {
		super();
		this.idArt = idArt;
		this.apodo = apodo;
		
	}

	public Long getIdArt() {
		return idArt;
	}

	public void setIdArt(Long idArt) {
		this.idArt = idArt;
	}

	public String getApodo() {
		return apodo;
	}

	public void setApodo(String apodo) {
		this.apodo = apodo;
	}

	public Set<Especialidad> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(Set<Especialidad> especialidades) {
		this.especialidades = especialidades;
	}

	@Override
	public String toString() {
		return "Artista [idArt=" + idArt + ", apodo=" + apodo + ", especialidades=" + especialidades + ", numeros="
				+ numeros + "]";
	}

}
