package com.nosecrets.dao;
import com.nosecrets.model.User;

import java.util.List;
import java.util.Optional;


public interface UserDAO {
    int guardar(User u);                       // devuelve el id generado
    Optional<User> buscarPorId(int id);
    Optional<User> buscarPorUser(String user); // para login y para validar duplicados
    List<User> listarTodos();
    void actualizar(User u);
    void cambiarEstado(int id, String stat);   // en vez de borrar
    boolean existePorUser(String user);
}
