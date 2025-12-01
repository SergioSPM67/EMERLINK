package com.emergencias.model;

public class EmergencyEvent {
    private String tipo;
    private String ubicacion;

    public EmergencyEvent(String tipo, String ubicacion) {
        this.tipo = tipo;
        this.ubicacion = ubicacion;
    }

    public String getTipo() {
        return tipo;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    @Override
    public String toString() {
        return "Emergencia: " + tipo + " | Ubicación: " + ubicacion;
    }
}
