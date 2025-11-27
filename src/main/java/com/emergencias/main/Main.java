package main.java.com.emergencias.main;

import main.java.com.emergencias.controller.EmergencyManager;

 // Punto de entrada principal para la simulación del Módulo Core de Emergencias.

public class Main {

    // Parámetros de configuración del sistema

    private static final int UMBRAL_REQUERIDO = 5; // Simulación: Valor mínimo de impacto para activar
    private static final String DESTINO_SERVICIO = "112 Servicios de Emergencia";

    public static void main(String[] args) {
        System.out.println("  MÓDULO CORE PARA SISTEMAS DE EMERGENCIA (JAVA)  ");

        // Instancia el controlador y configura los parámetros del sistema
        EmergencyManager manager = new EmergencyManager(UMBRAL_REQUERIDO, DESTINO_SERVICIO);

        // Inicia el flujo de detección y alerta
        manager.startSystem();

        System.out.println("Simulación Finalizada.");
    }

}