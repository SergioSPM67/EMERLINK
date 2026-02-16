package com.emergencias.util;

import com.emergencias.model.Alert;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AlertLoader {
    public static List<Alert> loadAlerts(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            Gson gson = new Gson();
            Type alertListType = new TypeToken<ArrayList<Alert>>(){}.getType();
            return gson.fromJson(reader, alertListType);
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo JSON: " + e.getMessage());
            return new ArrayList<>(); // Devolvemos lista vacía para evitar NullPointerException
        }
    }
}












