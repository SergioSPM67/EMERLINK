package com.emergencias.alerta;

import com.emergencias.modelo.EmergencyEvent;

public class AlertSender {
    public AlertSender(String d) {}

    public void sendAlert(EmergencyEvent e) { 
        System.out.println("ALERTA: " + e.getDescription()); 
    }
}