package com.lucigf.modelo;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Coordinacion extends Persona {

	private Long idCoord;
	private boolean senior = false;
	private LocalDate fechaSenior = null;
	private Set<Espectaculo> espectaculos = new HashSet<>();

	public Coordinacion() {

	}

	public Coordinacion(Long idCoord, boolean senior, LocalDate fechaSenior) {
		super();
		this.idCoord = idCoord;
		this.senior = senior;
		this.fechaSenior = fechaSenior;
	}

	public Long getIdCoord() {
		return idCoord;
	}

	public void setIdCoord(Long idCoord) {
		this.idCoord = idCoord;
	}

	public boolean isSenior() {
		return senior;
	}

	public void setSenior(boolean senior) {
		this.senior = senior;
	}

	public LocalDate getFechaSenior() {
		return fechaSenior;
	}

	public void setFechaSenior(LocalDate fechaSenior) {
		this.fechaSenior = fechaSenior;
	}

	
	public Set<Espectaculo> getEspectaculos() {
		return espectaculos;
	}

	public void setEspectaculos(Set<Espectaculo> espectaculos) {
		this.espectaculos = espectaculos;
	}

	@Override
	public String toString() {
		return "Coordinacion [idCoord=" + idCoord + ", senior=" + senior + ", fechaSenior=" + fechaSenior
				+ ", espectaculos=" + espectaculos + "]";
	}

}
