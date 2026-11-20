package com.nosecrets.dao.impl;

import com.nosecrets.dao.UserDAO;
import com.nosecrets.model.User;

import java.util.List;
import java.util.Optional;

public class UserDAOImp implements UserDAO {

    @Override
    public int guardar(User u) {
        return 0;
    }

    @Override
    public Optional<User> buscarPorId(int id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> buscarPorUser(String user) {
        return Optional.empty();
    }

    @Override
    public List<User> listarTodos() {
        return List.of();
    }

    @Override
    public void actualizar(User u) {

    }

    @Override
    public void cambiarEstado(int id, String stat) {

    }

    @Override
    public boolean existePorUser(String user) {
        return false;
    }
}
