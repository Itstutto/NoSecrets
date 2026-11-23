package com.nosecrets.dao;

import com.nosecrets.model.Letter;

import java.util.List;
import java.util.Optional;

public interface LetterDao {
    int guardar(Letter l);                                 // devuelve el id generado
    Optional<Letter> buscarPorId(int id);
    List<Letter> listarPorSender(int senderId);            // cartas que envió un usuario
    List<Letter> listarSinResponder();                     // stat = FALSE
    void responder(int id, String reply, int replierId);   // solo si aún no está respondida
}
