package com.emergencias.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import com.emergencias.modelo.EmergencyEvent;
import com.emergencias.modelo.UserData;
import com.emergencias.alerta.AlertSender;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmergencyManager {

    private AlertSender remitente;
    private UserData usuario;

    private Timeline timeline;
    private int segundosRestantes = 30;

    private static final String URL = "jdbc:mysql://localhost:3307/reservas_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Inyecciones de VBox para el control de pestañas
    @FXML private VBox pantallaInicio;
    @FXML private VBox pantallaGuia;
    @FXML private VBox pantallaFicha;

    @FXML private VBox panelMensaje;
    @FXML private Label lblMensajeError;

    @FXML private Label lblTituloAlerta;
    @FXML private Label lblContador;
    @FXML private Button btnAlerta;
    @FXML private Button btnCancelarAlerta;

    // Inyecciones de campos de texto del formulario médico
    @FXML private TextField txtFichaNombre;
    @FXML private TextField txtFichaTelefono;
    @FXML private TextField txtFichaAlergias;
    @FXML private TextField txtFichaSangre;
    @FXML private TextArea txtFichaNotas;

    public EmergencyManager() {
        // Inicialización por defecto antes de ser configurada en la UI
        this.usuario = new UserData("Sin configurar", "112", "Ninguna", "Desconocido", "Ninguna");
        this.remitente = new AlertSender("112");
    }

    @FXML
    public void initialize() {
        // Cargar los datos vigentes en la vista del formulario
        txtFichaNombre.setText(usuario.getName());
        txtFichaTelefono.setText(usuario.getPhoneNumber());
        txtFichaAlergias.setText(usuario.getAllergies());
        txtFichaSangre.setText(usuario.getBloodType());
        txtFichaNotas.setText(usuario.getChronicNotes());
    }

    @FXML
    public void handleActionAlerta() {
        ocultarErrorVisual();
        segundosRestantes = 30;

        lblTituloAlerta.setText("ENVIANDO ALERTA...");
        lblContador.setText("La alerta se enviará automáticamente en " + segundosRestantes + "s");

        btnAlerta.setVisible(false);
        btnAlerta.setManaged(false);
        btnCancelarAlerta.setVisible(true);
        btnCancelarAlerta.setManaged(true);

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            segundosRestantes--;
            if (segundosRestantes > 0) {
                lblContador.setText("La alerta se enviará automáticamente en " + segundosRestantes + "s");
            } else {
                timeline.stop();
                ejecutarEnvioAlerta();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    public void handleCancelarAlerta() {
        if (timeline != null) {
            timeline.stop();
        }
        restablecerBotonesAlerta();
        mostrarMensajeExito("Alerta cancelada correctamente por el usuario.");
    }

    private void ejecutarEnvioAlerta() {
        restablecerBotonesAlerta();
        EmergencyEvent eventoGui = new EmergencyEvent("Alerta manual desde Botón de Pánico");

        remitente.sendAlert(eventoGui);

        boolean logGuardado = guardarRegistro(eventoGui);

        // Registrar en Base de Datos incluyendo los datos actualizados del titular
        String detalleDB = "Emergencia de: " + usuario.getName() + " - Tel: " + usuario.getPhoneNumber();
        boolean dbGuardado = registrarEmergenciaEnBaseDatos(detalleDB);

        if (logGuardado && dbGuardado) {
            mostrarMensajeExito("¡ALERTA ENVIADA! Datos médicos guardados en registro_sergio.txt");
        }
    }

    private void restablecerBotonesAlerta() {
        lblTituloAlerta.setText("ALERTA DE EMERGENCIA");
        lblContador.setText("Toca para enviar alerta");
        btnAlerta.setVisible(true);
        btnAlerta.setManaged(true);
        btnCancelarAlerta.setVisible(false);
        btnCancelarAlerta.setManaged(false);
    }


    @FXML
    public void handleGuardarFicha() {
        if (txtFichaNombre.getText().trim().isEmpty() || txtFichaTelefono.getText().trim().isEmpty()) {
            mostrarErrorVisual("El Nombre y el Teléfono son campos obligatorios.");
            return;
        }

        // Modificar el objeto en memoria
        usuario.setName(txtFichaNombre.getText().trim());
        usuario.setPhoneNumber(txtFichaTelefono.getText().trim());
        usuario.setAllergies(txtFichaAlergias.getText().trim());
        usuario.setBloodType(txtFichaSangre.getText().trim());
        usuario.setChronicNotes(txtFichaNotas.getText().trim());

        handleIrAInicio();
        mostrarMensajeExito("Ficha Médica de '" + usuario.getName() + "' guardada correctamente.");
    }


    private boolean guardarRegistro(EmergencyEvent e) {
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
            writer.write("DATOS DEL PACIENTE (FICHA MÉDICA):");
            writer.newLine();
            writer.write(" > Nombre       : " + usuario.getName());
            writer.newLine();
            writer.write(" > Teléfono     : " + usuario.getPhoneNumber());
            writer.newLine();
            writer.write(" > Alergias     : " + (usuario.getAllergies().isEmpty() ? "Ninguna" : usuario.getAllergies()));
            writer.newLine();
            writer.write(" > Tipo Sangre  : " + (usuario.getBloodType().isEmpty() ? "No especificado" : usuario.getBloodType()));
            writer.newLine();
            writer.write(" > Obs. Crónicas: " + (usuario.getChronicNotes().isEmpty() ? "Ninguna" : usuario.getChronicNotes()));
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
            return true;
        } catch (IOException ex) {
            mostrarErrorVisual("Error al escribir el Log Médico: " + ex.getMessage());
            return false;
        }
    }

    private boolean registrarEmergenciaEnBaseDatos(String detalle) {
        String sql = "INSERT INTO reservas (detalle) VALUES (?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, detalle);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            mostrarErrorVisual("Error de base de datos al guardar alerta: " + e.getMessage());
            return false;
        }
    }


    @FXML
    public void handleVerGuia() {
        ocultarErrorVisual();
        setPantallasVisibles(false, true, false);
    }

    @FXML
    public void handleVerFicha() {
        ocultarErrorVisual();
        initialize();
        setPantallasVisibles(false, false, true);
    }

    @FXML
    public void handleIrAInicio() {
        setPantallasVisibles(true, false, false);
    }


    private void setPantallasVisibles(boolean inicio, boolean guia, boolean ficha) {
        pantallaInicio.setVisible(inicio);
        pantallaInicio.setManaged(inicio);

        pantallaGuia.setVisible(guia);
        pantallaGuia.setManaged(guia);

        pantallaFicha.setVisible(ficha);
        pantallaFicha.setManaged(ficha);
    }

    @FXML
    public void handleMockClick() {
        mostrarMensajeExito("Módulo en desarrollo para la siguiente versión.");
    }

    private void mostrarErrorVisual(String mensaje) {
        panelMensaje.setStyle("-fx-background-color: #FFEBEE; -fx-border-color: #FFCDD2; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 10;");
        lblMensajeError.setStyle("-fx-text-fill: #C62828; -fx-font-weight: bold;");
        lblMensajeError.setText("⚠️ " + mensaje);
        panelMensaje.setVisible(true);
        panelMensaje.setManaged(true);
    }

    private void mostrarMensajeExito(String mensaje) {
        panelMensaje.setStyle("-fx-background-color: #E8F5E9; -fx-border-color: #C8E6C9; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 10;");
        lblMensajeError.setStyle("-fx-text-fill: #2E7D32; -fx-font-weight: bold;");
        lblMensajeError.setText("✅ " + mensaje);
        panelMensaje.setVisible(true);
        panelMensaje.setManaged(true);
    }

    private void ocultarErrorVisual() {
        panelMensaje.setVisible(false);
        panelMensaje.setManaged(false);
    }
}