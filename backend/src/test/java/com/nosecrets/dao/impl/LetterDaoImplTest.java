package com.nosecrets.dao.impl;

import com.nosecrets.model.Letter;
import com.nosecrets.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LetterDaoImplTest extends BaseDaoIntegrationTest {

    private LetterDaoImpl letterDao;
    private UserDAOImpl userDao;
    private int userId;

    @BeforeEach
    void prepararDatos() {
        letterDao = new LetterDaoImpl(db);
        userDao = new UserDAOImpl(db);
        // Toda carta necesita un usuario que exista (llave foránea)
        userId = userDao.guardar(new User(0, "ana", "hash", null));
    }

    private Letter nuevaCarta(String mensaje, int sender) {
        Letter l = new Letter();
        l.setMessage(mensaje);
        l.setSender(sender);
        return l;
    }

    @Test
    void guardar_devuelveIdGenerado() {
        int id = letterDao.guardar(nuevaCarta("Hola", userId));

        assertTrue(id > 0);
    }

    @Test
    void guardar_conSenderInexistente_lanzaExcepcion() {
        assertThrows(RuntimeException.class, () -> letterDao.guardar(nuevaCarta("Hola", -1)));
    }

    @Test
    void buscarPorId_devuelveLaCartaSinResponder() {
        int id = letterDao.guardar(nuevaCarta("Hola", userId));

        Letter l = letterDao.buscarPorId(id).orElseThrow();

        assertEquals("Hola", l.getMessage());
        assertEquals(userId, l.getSender());
        assertFalse(l.isStat());
        assertNull(l.getReply());
        assertNull(l.getReplier());
        assertNull(l.getRepliedAt());
        assertNotNull(l.getSentAt());
    }

    @Test
    void buscarPorId_inexistente_devuelveVacio() {
        assertTrue(letterDao.buscarPorId(-1).isEmpty());
    }

    @Test
    void responder_marcaLaCartaComoRespondida() {
        int id = letterDao.guardar(nuevaCarta("Hola", userId));

        letterDao.responder(id, "Gracias", userId);

        Letter l = letterDao.buscarPorId(id).orElseThrow();
        assertTrue(l.isStat());
        assertEquals("Gracias", l.getReply());
        assertEquals(Integer.valueOf(userId), l.getReplier());
        assertNotNull(l.getRepliedAt());
    }

    @Test
    void responder_dosVeces_lanzaExcepcion() {
        int id = letterDao.guardar(nuevaCarta("Hola", userId));
        letterDao.responder(id, "Gracias", userId);

        assertThrows(IllegalArgumentException.class,
                () -> letterDao.responder(id, "Otra respuesta", userId));
    }

    @Test
    void responder_cartaInexistente_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> letterDao.responder(-1, "Gracias", userId));
    }

    @Test
    void listarSinResponder_noIncluyeLasRespondidas() {
        int pendiente = letterDao.guardar(nuevaCarta("Pendiente", userId));
        int respondida = letterDao.guardar(nuevaCarta("Respondida", userId));
        letterDao.responder(respondida, "Listo", userId);

        var lista = letterDao.listarSinResponder();

        assertEquals(1, lista.size());
        assertEquals(pendiente, lista.get(0).getId());
    }

    @Test
    void listarPorSender_devuelveSoloLasCartasDeEseUsuario() {
        int otroUsuario = userDao.guardar(new User(0, "luis", "hash", null));
        letterDao.guardar(nuevaCarta("De ana 1", userId));
        letterDao.guardar(nuevaCarta("De ana 2", userId));
        letterDao.guardar(nuevaCarta("De luis", otroUsuario));

        var deAna = letterDao.listarPorSender(userId);

        assertEquals(2, deAna.size());
        assertTrue(deAna.stream().allMatch(l -> l.getSender() == userId));
    }
}
