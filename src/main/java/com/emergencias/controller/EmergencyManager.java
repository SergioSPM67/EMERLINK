package com.emergencias.controller;

import com.emergencias.model.Alert;
import com.emergencias.util.AlertLoader;
import java.util.List;

public class EmergencyManager {
    public void startSystem() {
        System.out.println("=== Sistema de Emergencias EMERLINK ===");
        
        // 1. Cargamos los datos del JSON
        // Nota: Asegúrate de que la ruta coincida con la ubicación de tu archivo
        String path = "src/main/resources/alerts.json";
        List<Alert> alerts = AlertLoader.loadAlerts(path);

        // 2. Utilizamos los datos (los mostramos por consola)
        if (alerts != null && !alerts.isEmpty()) {
            System.out.println("\nAlertas cargadas desde el fichero JSON:");
            for (Alert alert : alerts) {
                System.out.println("-------------------------");
                System.out.println("Tipo: " + alert.getType());
                System.out.println("Ubicación: " + alert.getLocation());
                System.out.println("Prioridad: " + alert.getPriority());
                System.out.println("Descripción: " + alert.getDescription());
            }
        } else {
            System.out.println("No se encontraron alertas en el archivo.");
        }
        
        System.out.println("\n-------------------------");
        System.out.println("Sistema listo y operativo.");
    }
}

