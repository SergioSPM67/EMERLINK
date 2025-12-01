package com.emergencias.controller;

import com.emergencias.detector.EmergencyDetector;
import com.emergencias.alert.AlertSender;
import com.emergencias.model.EmergencyEvent;

public class EmergencyManager {

    public void startSystem() {
        EmergencyDetector detector = new EmergencyDetector();
        AlertSender sender = new AlertSender();

        EmergencyEvent event = detector.detectEvent();

        if (event != null) {
            sender.sendAlert(event);
        }
    }
}
