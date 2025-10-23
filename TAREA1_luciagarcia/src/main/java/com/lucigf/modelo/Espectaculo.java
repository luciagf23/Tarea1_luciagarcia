package com.lucigf.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Espectaculo implements Serializable {

	private Long id;
	private String nombre;
	private LocalDate fechaini;
	private LocalDate fechafin;
	private Coordinacion coordinacion;
	private Set<Numero> numeros = new HashSet<>();

	private Coordinacion coordinador;

	public Espectaculo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Espectaculo(Long id, String nombre, LocalDate fechaini, LocalDate fechafin) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.fechaini = fechaini;
		this.fechafin = fechafin;
	}

	
	
	public Espectaculo(Long id, String nombre, LocalDate fechaini, LocalDate fechafin, Coordinacion coordinacion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.fechaini = fechaini;
		this.fechafin = fechafin;
		this.coordinacion = coordinacion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDate getFechaini() {
		return fechaini;
	}

	public void setFechaini(LocalDate fechaini) {
		this.fechaini = fechaini;
	}

	public LocalDate getFechafin() {
		return fechafin;
	}

	public void setFechafin(LocalDate fechafin) {
		this.fechafin = fechafin;
	}

	public Coordinacion getCoordinador() {
		return coordinador;
	}

	public void setCoordinador(Coordinacion coordinador) {
		this.coordinador = coordinador;
	}
	

	public Coordinacion getCoordinacion() {
		return coordinacion;
	}

	public void setCoordinacion(Coordinacion coordinacion) {
		this.coordinacion = coordinacion;
	}

	public Set<Numero> getNumeros() {
		return numeros;
	}

	public void setNumeros(Set<Numero> numeros) {
		this.numeros = numeros;
	}

	@Override
	public String toString() {
		return "Espectaculo [id=" + id + ", nombre=" + nombre + ", fechaini=" + fechaini + ", fechafin=" + fechafin
				+ ", coordinacion=" + coordinacion + ", numeros=" + numeros + ", coordinador=" + coordinador + "]";
	}

}
