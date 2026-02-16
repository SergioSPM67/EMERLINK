package com.emergencias.detector;

import com.emergencias.model.EmergencyEvent;
import java.util.Scanner;

public class EmergencyDetector {

    public EmergencyEvent detectEvent() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Sistema de Emergencias EMERLINK ===");
        System.out.println("¿Deseas activar una emergencia? (S/N)");
        String respuesta = scanner.nextLine();

        if (respuesta.equalsIgnoreCase("S")) {
            System.out.print("Introduce la ubicación de la emergencia: ");
            String ubicacion = scanner.nextLine();

            return new EmergencyEvent("Emergencia General", ubicacion);
        }

        System.out.println("No se detectó ninguna emergencia.");
        return null;
    }
}

