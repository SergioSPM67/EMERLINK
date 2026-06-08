package com.emergencias.modelo;

public class UserData {
    private String name;
    private String phoneNumber;
    private String allergies;
    private String bloodType;
    private String chronicNotes;

    public UserData(String name, String phoneNumber, String allergies, String bloodType, String chronicNotes) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.allergies = allergies;
        this.bloodType = bloodType;
        this.chronicNotes = chronicNotes;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAllergies() { return allergies; }
    public void setAllergies(String allergies) { this.allergies = allergies; }

    public String getBloodType() { return bloodType; }
    public void setBloodType(String bloodType) { this.bloodType = bloodType; }

    public String getChronicNotes() { return chronicNotes; }
    public void setChronicNotes(String chronicNotes) { this.chronicNotes = chronicNotes; }
}