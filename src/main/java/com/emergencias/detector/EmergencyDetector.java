package com.emergencias.detector;

import com.emergencias.modelo.EmergencyEvent;
import com.emergencias.modelo.UserData;

public class EmergencyDetector {
    public EmergencyDetector(int t) {}

    public EmergencyEvent detectEvent(UserData u) { 
        return new EmergencyEvent("Caída de " + u.getName()); 
    }
}