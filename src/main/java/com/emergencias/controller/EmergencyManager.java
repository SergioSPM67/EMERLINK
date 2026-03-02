package com.emergencias.controller;

import com.emergencias.detector.EmergencyDetector;
import com.emergencias.alert.AlertSender;
import com.emergencias.model.EmergencyEvent;
import com.emergencias.model.CentroSalud;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmergencyManager {
    // Lista para guardar los centros cargados del JSON
    private List<CentroSalud> centrosDeSalud = new ArrayList<>();

    public void startSystem() {
        cargarCentros();

        EmergencyDetector detector = new EmergencyDetector();
        AlertSender sender = new AlertSender();
        EmergencyEvent event = detector.detectEvent();

        if (event != null) {
            sender.sendAlert(event);

            System.out.println("\n BUSCANDO CENTROS DE SALUD EN: " + event.getUbicacion().toUpperCase() + " ---");
            boolean centroEncontrado = false;

            for (CentroSalud centro : centrosDeSalud) {
                if (centro.getMunicipio() != null &&
                        centro.getMunicipio().toLowerCase().contains(event.getUbicacion().toLowerCase())) {
                    System.out.println("🏥 " + centro.getNombre() + " | " + centro.getDireccion() + " | Tlf: " + centro.getTelefono());
                    centroEncontrado = true;
                }
            }

            if (!centroEncontrado) {
                System.out.println("No se han encontrado centros de salud registrados en esa ubicación");
            }
            System.out.println("---------------------------------------------------");
        }
    }

    private void cargarCentros() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            CentroSalud[] datos = mapper.readValue(new File("CentrosDeSalud.json"), CentroSalud[].class);
            this.centrosDeSalud = Arrays.asList(datos);
            System.out.println(">>> [JSON] " + centrosDeSalud.size() + " centros de salud cargados.");
        } catch (Exception e) {
            System.err.println(">>> [ERROR] No se pudo cargar el JSON: " + e.getMessage());
        }
    }
}