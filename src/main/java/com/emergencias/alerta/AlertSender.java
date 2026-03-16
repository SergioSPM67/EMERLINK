package main.java.com.emergencias.alerta;
import main.java.com.emergencias.modelo.EmergencyEvent;
public class AlertSender {
    public AlertSender(String d) {}
    public void sendAlert(EmergencyEvent e) { System.out.println("ALERTA: " + e.getDescription()); }
}