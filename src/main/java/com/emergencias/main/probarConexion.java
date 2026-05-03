package com.emergencias.main;

public void probarConexion() {
    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
        System.out.println("✅ CONECTADO A MYSQL");
    } catch (SQLException e) {
        System.out.println("❌ ERROR: " + e.getMessage());
    }

}
