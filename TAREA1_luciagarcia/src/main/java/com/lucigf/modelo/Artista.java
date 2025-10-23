package com.lucigf.modelo;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Lucía García Fernández 
 * @version 1.0
 * @since 2025
 */
public class Artista extends Persona {

	private Long idArt;
	private String apodo = null;
	private Set<Especialidad> especialidades = new HashSet<Especialidad>();
	private Set<Numero> numeros = new HashSet<>();

	public Artista() {
		super();

	}

	public Artista(Long idArt, String apodo) {
		super();
		this.idArt = idArt;
		this.apodo = apodo;

	}

	public Artista(Long idArt, String apodo, Set<Especialidad> especialidades, Set<Numero> numeros) {
		super();
		this.idArt = idArt;
		this.apodo = apodo;
		this.especialidades = especialidades;
		this.numeros = numeros;
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

	public Set<Numero> getNumeros() {
		return numeros;
	}

	public void setNumeros(Set<Numero> numeros) {
		this.numeros = numeros;
	}

	@Override
	public String toString() {
		return "Artista [idArt=" + idArt + ", apodo=" + apodo + ", especialidades=" + especialidades + ", numeros="
				+ numeros + "]";
	}

}
