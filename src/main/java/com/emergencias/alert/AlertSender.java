package com.emergencias.alert;

import com.emergencias.model.EmergencyEvent;
import java.io.FileWriter;
import java.io.IOException;

public class AlertSender {

    public void sendAlert(EmergencyEvent event) {
        if (event == null) return;

        System.out.println("\n--- ENVIANDO ALERTA ---");
        System.out.println("Alerta enviada a 112:");
        System.out.println(event);

        mostrarAlertaFormateada(event);

        try (FileWriter writer = new FileWriter("alertas.txt", true)) {
            writer.write(event.toString() + "\n");
        } catch (IOException e) {
        System.err.println("Error de entrada/salida al guardar la alerta");
        }
    }

private void mostrarAlertaFormateada(EmergencyEvent event) {
    System.out.println("===== ALERTA DE EMERGENCIA =====");
    System.out.println(event.toString());
    System.out.println("================================");
}

}
 

