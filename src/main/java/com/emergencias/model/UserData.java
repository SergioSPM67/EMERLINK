package main.java.com.emergencias.model;

/**
 * Modelo para los datos personales del usuario.
 */
public class UserData {
    private final String nombre;
    private final String telefonoContacto;
    private final String infoMedica;

    public UserData(String nombre, String telefonoContacto, String infoMedica) {
        this.nombre = nombre;
        this.telefonoContacto = telefonoContacto;
        this.infoMedica = infoMedica;
    }

    // Getters
    public String getNombre() { return nombre; }
    public String getTelefonoContacto() { return telefonoContacto; }
    public String getInfoMedica() { return infoMedica; }

    @Override
    public String toString() {
        return "Usuario: " + nombre + ", Tel: " + telefonoContacto + ", Info Médica: " + infoMedica;
    }
}