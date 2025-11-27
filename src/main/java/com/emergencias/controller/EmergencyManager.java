package main.java.com.emergencias.controller;

import main.java.com.emergencias.alert.AlertSender;
import main.java.com.emergencias.detector.EmergencyDetector;
import main.java.com.emergencias.model.EmergencyEvent;
import main.java.com.emergencias.model.UserData;


// Clase Controladora que orquesta el flujo de detección y notificación.

public class EmergencyManager {
    private final EmergencyDetector detector;
    private final AlertSender sender;
    private final UserData usuarioSimulado;

    // constructor de la clase EmergencyManager
    public EmergencyManager(int umbralActivacion, String destinoAlerta) {
        // Inicialización de los Cores y Modelos
        this.detector = new EmergencyDetector(umbralActivacion);
        this.sender = new AlertSender(destinoAlerta);

        // Datos de usuario harcodeados para la simulación
        this.usuarioSimulado = new UserData(
                "Juan Pérez",
                "600-112-112",
                "Alergia a la Penicilina"
        );

        System.out.println("Sistema de Emergencia Inicializado. Usuario: " + usuarioSimulado.getNombre());
    }

     // Inicia el ciclo de detección y, si es confirmado, la alerta.

    public void startSystem() {
        try {
            EmergencyEvent evento = detector.detectEvent(usuarioSimulado);

            if (evento != null) {
                // La detección y validación fue exitosa
                sender.sendAlert(evento);
                System.out.println("\nProceso de emergencia completado con éxito.");
                // La detección y validación fue infructuosa
            } else {
                System.out.println("\nProceso de emergencia terminado. No se envió ninguna alerta.");
            }
        } catch (Exception e) {
            // Manejo de cualquier excepción no controlada
            System.err.println("\n Fallo durante la orquestación: " + e.getMessage());
        }
    }
}