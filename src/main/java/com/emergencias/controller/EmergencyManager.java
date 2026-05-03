package com.emergencias.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import com.emergencias.modelo.EmergencyEvent;
import com.emergencias.modelo.UserData;
import com.emergencias.alerta.AlertSender;
import com.emergencias.detector.EmergencyDetector;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmergencyManager {
    private EmergencyDetector detector;
    private AlertSender remitente;
    private UserData usuario;
    private Scanner sc = new Scanner(System.in);

    // Conexión por JDBC para cumplir con la tarea de bases de datos
    private static final String URL = "jdbc:mysql://localhost:3307/reservas_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtDetalle;

    public EmergencyManager() {
        this.usuario = new UserData("Sergio", "112", "Alerta Manual");
        this.remitente = new AlertSender("112");
    }

    @FXML
    public void handleEnviarAlerta() {
        System.out.println("\n[BOTÓN] ¡Alerta de emergencia activada desde la interfaz!");
        EmergencyEvent eventoGui = new EmergencyEvent("Alerta manual desde Botón Rojo");
        guardarRegistro(eventoGui);
    }

    // --- MÉTODOS DEL CRUD ---

    @FXML
    public void handleCrear() {
        String detalle = txtDetalle.getText();
        String sql = "INSERT INTO reservas (detalle) VALUES (?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, detalle);
            int filasInsertadas = pstmt.executeUpdate();
            
            if (filasInsertadas > 0) {
                System.out.println(">> Registro Creado con éxito.");
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar: " + e.getMessage());
        }
    }

    @FXML
    public void handleConsultar() {
        String sql = "SELECT * FROM reservas";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            System.out.println("\n--- CONSULTA DE REGISTROS (READ) ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + " - Detalle: " + rs.getString("detalle"));
            }
        } catch (SQLException e) {
            System.out.println("Error en la base de datos: " + e.getMessage());
        }
    }

    @FXML
    public void handleActualizar() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String nuevoDetalle = txtDetalle.getText();
            String sql = "UPDATE reservas SET detalle = ? WHERE id = ?";

            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                pstmt.setString(1, nuevoDetalle);
                pstmt.setInt(2, id);
                int filasActualizadas = pstmt.executeUpdate();
                
                if (filasActualizadas > 0) {
                    System.out.println(">> Registro Actualizado con éxito.");
                } else {
                    System.out.println(">> No se encontró ningún registro con ese ID.");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Por favor, introduce un ID válido.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar: " + e.getMessage());
        }
    }

    @FXML
    public void handleEliminar() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String sql = "DELETE FROM reservas WHERE id = ?";

            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                pstmt.setInt(1, id);
                int filasEliminadas = pstmt.executeUpdate();
                
                if (filasEliminadas > 0) {
                    System.out.println(">> Registro Eliminado con éxito.");
                } else {
                    System.out.println(">> No se encontró ningún registro con ese ID.");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Por favor, introduce un ID válido.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }
    }

    // --- MÉTODOS AUXILIARES ---

    private void guardarRegistro(EmergencyEvent e) {
        String nombreArchivo = "registro_sergio.txt";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String idEvento = "E" + System.currentTimeMillis() / 1000;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo, true))) {
            writer.write("==================================================");
            writer.newLine();
            writer.write("    INFORME DE EMERGENCIA - SISTEMA EMERLINK");
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