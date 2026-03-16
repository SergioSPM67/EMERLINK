package main.java.com.emergencias.detector;
import main.java.com.emergencias.modelo.*;
public class EmergencyDetector {
    public EmergencyDetector(int t) {}
    public EmergencyEvent detectEvent(UserData u) { return new EmergencyEvent("Caída de " + u.getName()); }
}