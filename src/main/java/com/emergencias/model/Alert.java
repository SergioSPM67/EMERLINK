package com.emergencias.model;

public class Alert {
    private String type;
    private String location;
    private String priority;
    private String description;

    // Constructor vacío (necesario para GSON)
    public Alert() {}

    public Alert(String type, String location, String priority, String description) {
        this.type = type;
        this.location = location;
        this.priority = priority;
        this.description = description;
    }

    // Getters y Setters
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "Alert{" + "type='" + type + '\'' + ", location='" + location + '\'' + 
               ", priority='" + priority + '\'' + ", description='" + description + '\'' + '}';
    }
}








