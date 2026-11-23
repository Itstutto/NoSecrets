package com.nosecrets.dao.impl;

import com.nosecrets.config.Database;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Base de las pruebas de integración de los DAO.
 * Antes de CADA prueba deja las tablas vacías, para que ninguna dependa de otra.
 */
abstract class BaseDaoIntegrationTest {

    protected Database db;

    @BeforeEach
    void limpiarBase() throws Exception {
        db = new Database();

        try (Connection c = db.getConnection(); Statement st = c.createStatement()) {
            // Protección: si por error se usa la base real, falla ANTES de borrar nada
            assertTrue(c.getCatalog().toLowerCase().contains("test"),
                    "¡Esta no es la base de pruebas! Revisa src/test/resources/db.properties");

            st.executeUpdate("DELETE FROM letter");   // primero letter (llave foránea hacia user)
            st.executeUpdate("DELETE FROM user");
        }
    }
}
