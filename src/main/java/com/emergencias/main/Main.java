package main.java.com.emergencias.main;

import main.java.com.emergencias.controller.EmergencyManager;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("==================================================");
        System.out.println(" SISTEMA DE DETECCIÓN DE EMERGENCIAS: EMERLINK ");
        System.out.println("==================================================");

        System.out.print("Nombre del titular: ");
        String nombre = sc.nextLine();
        System.out.print("Información médica: ");
        String med = sc.nextLine();
        System.out.print("Teléfono de contacto: ");
        String tlf = sc.nextLine();

        // --- EL MANUAL QUE FALTABA ---
        System.out.println("\n[MANUAL RÁPIDO]");
        System.out.println("> El dispositivo detectará caídas o impactos fuertes.");
        System.out.println("> En caso de falso positivo, pulse 'Cancelar' en los 5s de espera.");
        System.out.println("> Asegúrese de tener cobertura para el envío de coordenadas.");
        System.out.println("--------------------------------------------------");

        EmergencyManager manager = new EmergencyManager(3, "112", nombre, tlf, med);
        
        System.out.println("\n[SISTEMA ACTIVO] Iniciando monitoreo de sensores...");
        manager.startSystem();

        System.out.println("\n==================================================");
        System.out.println("           SIMULACIÓN FINALIZADA");
        System.out.println("==================================================");
        
        sc.close();
    }
}