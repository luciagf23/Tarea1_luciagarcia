package com.lucigf.modelo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuracion {

	private static Properties propiedades = new Properties();

    // Cargar el archivo de configuración
    public static void cargar() {
        try (FileInputStream fis = new FileInputStream("src/main/resources/project.properties")) {
            propiedades.load(fis);
        } catch (IOException e) {
            System.out.println("Error cargando configuración: " + e.getMessage());
        }
    }

    // Obtener un valor por clave
    public static String get(String clave) {
        return propiedades.getProperty(clave);
    }
}

