package com.nosecrets.dao.impl;

import com.nosecrets.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOImplTest extends BaseDaoIntegrationTest {

    private UserDAOImpl dao;

    @BeforeEach
    void prepararDao() {
        dao = new UserDAOImpl(db);
    }

    private User nuevoUsuario(String nombre) {
        return new User(0, nombre, "hash_" + nombre, null);
    }

    @Test
    void guardar_devuelveIdGenerado() {
        int id = dao.guardar(nuevoUsuario("ana"));

        assertTrue(id > 0);
    }

    @Test
    void guardar_usuarioRepetido_lanzaExcepcion() {
        dao.guardar(nuevoUsuario("ana"));

        assertThrows(RuntimeException.class, () -> dao.guardar(nuevoUsuario("ana")));
    }

    @Test
    void buscarPorId_existente_devuelveElUsuarioConEstadoActivoPorDefecto() {
        int id = dao.guardar(nuevoUsuario("ana"));

        User u = dao.buscarPorId(id).orElseThrow();

        assertEquals(id, u.getId());
        assertEquals("ana", u.getUser());
        assertEquals("hash_ana", u.getPassword());
        assertEquals("Activo", u.getStat());   // viene del DEFAULT de la tabla
    }

    @Test
    void buscarPorId_inexistente_devuelveVacio() {
        assertTrue(dao.buscarPorId(-1).isEmpty());
    }

    @Test
    void buscarPorUser_existente_devuelveElUsuario() {
        int id = dao.guardar(nuevoUsuario("ana"));

        User u = dao.buscarPorUser("ana").orElseThrow();

        assertEquals(id, u.getId());
    }

    @Test
    void buscarPorUser_inexistente_devuelveVacio() {
        assertTrue(dao.buscarPorUser("nadie").isEmpty());
    }

    @Test
    void existePorUser_devuelveTrueSiExisteYFalseSiNo() {
        dao.guardar(nuevoUsuario("ana"));

        assertTrue(dao.existePorUser("ana"));
        assertFalse(dao.existePorUser("nadie"));
    }

    @Test
    void listarTodos_devuelveLosUsuariosGuardados() {
        dao.guardar(nuevoUsuario("ana"));
        dao.guardar(nuevoUsuario("luis"));

        var lista = dao.listarTodos();

        assertEquals(2, lista.size());
    }

    @Test
    void actualizar_cambiaUserYPassword() {
        int id = dao.guardar(nuevoUsuario("ana"));

        dao.actualizar(new User(id, "ana2", "nuevo_hash", null));

        User u = dao.buscarPorId(id).orElseThrow();
        assertEquals("ana2", u.getUser());
        assertEquals("nuevo_hash", u.getPassword());
    }

    @Test
    void cambiarEstado_modificaElEstado() {
        int id = dao.guardar(nuevoUsuario("ana"));

        dao.cambiarEstado(id, "Inactivo");

        assertEquals("Inactivo", dao.buscarPorId(id).orElseThrow().getStat());
    }

    @Test
    void cambiarEstado_idInexistente_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> dao.cambiarEstado(-1, "Inactivo"));
    }
}
