package com.lucigf.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Lucía García Fernández
 * @version 1.0
 * @since 2025
 */
public class Espectaculo implements Serializable {

	private Long id;
	private String nombre;
	private LocalDate fechaIni;
	private LocalDate fechaFin;
	private Coordinacion coordinacion;
	private Set<Numero> numeros = new HashSet<>();

	public Espectaculo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Espectaculo(Long id, String nombre, LocalDate fechaini, LocalDate fechafin) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.fechaIni = fechaini;
		this.fechaFin = fechafin;
	}

	public Espectaculo(Long id, String nombre, LocalDate fechaini, LocalDate fechafin, Coordinacion coordinacion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.fechaIni = fechaini;
		this.fechaFin = fechafin;
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
		return fechaIni;
	}

	public void setFechaini(LocalDate fechaini) {
		this.fechaIni = fechaini;
	}

	public LocalDate getFechafin() {
		return fechaFin;
	}

	public void setFechafin(LocalDate fechafin) {
		this.fechaFin = fechafin;
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
		return "Espectaculo [id=" + id + ", nombre=" + nombre + ", fechaini=" + fechaIni + ", fechafin=" + fechaFin
				+ ", coordinacion=" + coordinacion + ", numeros=" + numeros.size() + "]";
	}

}
