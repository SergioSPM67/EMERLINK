package main.java.com.emergencias.controller;

import main.java.com.emergencias.alerta.AlertSender;
import main.java.com.emergencias.detector.EmergencyDetector;
import main.java.com.emergencias.modelo.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class EmergencyManager {
    private EmergencyDetector detector;
    private AlertSender remitente;
    private UserData usuario;
    private Scanner sc = new Scanner(System.in);

    public EmergencyManager(int umbral, String destino, String nombre, String tlf, String infoMedica) {
        this.detector = new EmergencyDetector(umbral);
        this.remitente = new AlertSender(destino);
        this.usuario = new UserData(nombre, tlf, infoMedica);
        // Constructor vacío de prints para evitar duplicados
    }

    public void startSystem() {
        System.out.println("\n--- Panel de Control: Detección de Emergencia ---");
        System.out.print("Simular pulsación de botón de emergencia (E/e): ");
        sc.nextLine(); 

        System.out.print("Tipo de Incidente (Ej: Caída, Infarto): ");
        String tipo = sc.nextLine();

        System.out.print("Ubicación actual: ");
        String ubicacion = sc.nextLine();

        System.out.print("Nivel de impacto detectado (mínimo 3): ");
        int impacto = Integer.parseInt(sc.nextLine());

        if (impacto >= 3) {
            EmergencyEvent evento = new EmergencyEvent(tipo + " en " + ubicacion);
            
            System.out.println("\n*** PROCESANDO ALERTA CORE 2 ***");
            System.out.println(">> Destino: 112 Emergencias Sanitarias");
            System.out.println(">> Usuario: " + usuario.getName() + " | Tel: " + usuario.getPhoneNumber());
            
            guardarRegistro(evento);
        } else {
            System.out.println("\n[SISTEMA] Impacto leve. Alerta cancelada.");
        }
    }

    private void guardarRegistro(EmergencyEvent e) {
        String nombreArchivo = "registro_sergio.txt";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String idEvento = "E" + System.currentTimeMillis() / 1000;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo, true))) {
            writer.write("==================================================");
            writer.newLine();
            writer.write("   INFORME DE EMERGENCIA - SISTEMA EMERLINK");
            writer.newLine();
            writer.write("==================================================");
            writer.newLine();
            writer.write("ID EVENTO   : " + idEvento);
            writer.newLine();
            writer.write("FECHA/HORA  : " + LocalDateTime.now().format(dtf));
            writer.newLine();
            writer.write("ESTADO      : ALERTA ENVIADA CON ÉXITO");
            writer.newLine();
            writer.write("--------------------------------------------------");
            writer.newLine();
            writer.write("DATOS DEL TITULAR:");
            writer.newLine();
            writer.write(" > Nombre   : " + usuario.getName());
            writer.newLine();
            writer.write(" > Contacto : " + usuario.getPhoneNumber());
            writer.newLine();
            writer.write("--------------------------------------------------");
            writer.newLine();
            writer.write("DETALLES DEL INCIDENTE:");
            writer.newLine();
            writer.write(" > Descripción: " + e.getDescription());
            writer.newLine();
            writer.write("==================================================");
            writer.newLine();
            writer.newLine(); 

            System.out.println("\n[LOG] Evento guardado correctamente en: " + nombreArchivo);
        } catch (IOException ex) {
            System.out.println("Error al escribir log: " + ex.getMessage());
        }
    }
}