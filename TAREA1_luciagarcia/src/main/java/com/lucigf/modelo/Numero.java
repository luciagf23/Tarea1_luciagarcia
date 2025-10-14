package com.lucigf.modelo;

import java.util.HashSet;
import java.util.Set;

public class Numero {

	private Long id;
	private int orden;
	private String nombre;
	private double duracion;
	private Set<Artista> artistas = new HashSet<Artista>();
	private Espectaculo espectaculo;

	public Numero() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Numero(Long id, int orden, String nombre, double duracion) {
		super();
		this.id = id;
		this.orden = orden;
		this.nombre = nombre;
		this.duracion = duracion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<Artista> getArtistas() {
		return artistas;
	}

	public void setArtistas(Set<Artista> artistas) {
		this.artistas = artistas;
	}

	public double getDuracion() {
		return duracion;
	}

	public void setDuracion(double duracion) {
		this.duracion = duracion;
	}

	public Espectaculo getEspectaculo() {
		return espectaculo;
	}

	public void setEspectaculo(Espectaculo espectaculo) {
		this.espectaculo = espectaculo;
	}

	@Override
	public String toString() {
		return "Numero [id=" + id + ", orden=" + orden + ", nombre=" + nombre
				+ ", duracion=" + duracion + ", artistas=" + artistas
				+ ", espectaculo=" + espectaculo + "]";
	}

}
