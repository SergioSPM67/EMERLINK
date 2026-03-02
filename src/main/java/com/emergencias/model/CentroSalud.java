package com.emergencias.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CentroSalud {

    @JsonProperty("Nombre")
    private String nombre;

    @JsonProperty("Dirección")
    private String direccion;

    @JsonProperty("Municipio")
    private String municipio;

    @JsonProperty("Teléfono")
    private String telefono;


    public CentroSalud() {
    }


    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }
    public String getMunicipio() { return municipio; }
    public String getTelefono() { return telefono; }

    @Override
    public String toString() {
        return "Centro: " + nombre + " | Municipio: " + municipio + " | Tlf: " + telefono;
    }
}