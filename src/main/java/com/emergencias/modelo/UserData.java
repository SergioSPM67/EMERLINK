package main.java.com.emergencias.modelo;
public class UserData {
    private String name, phoneNumber, medicalInfo;
    public UserData(String n, String p, String m) { this.name = n; this.phoneNumber = p; this.medicalInfo = m; }
    public String getName() { return name; }
    public String getPhoneNumber() { return phoneNumber; }
}