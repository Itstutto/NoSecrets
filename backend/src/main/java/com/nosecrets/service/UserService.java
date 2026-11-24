package com.nosecrets.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.nosecrets.dao.UserDAO;
import com.nosecrets.dto.LoginDTO;
import com.nosecrets.dto.NewUserDTO;
import com.nosecrets.dto.UserDTO;
import com.nosecrets.model.User;

import java.util.Optional;

public class UserService {
    private final UserDAO uDao;

    private String hashear(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    private boolean verificar(String password, String hashGuardado) {
        return BCrypt.verifyer().verify(password.toCharArray(), hashGuardado).verified;
    }

    public UserService(UserDAO uDao){
        this.uDao = uDao;
    }

    public Optional<UserDTO> login(LoginDTO log) throws Exception{
        User u = uDao.buscarPorUser(log.getUser()).orElseThrow(Exception::new);

        if(u.getPassword().equals(log.getPassword())){
            return Optional.of(new UserDTO(u.getId(),u.getUser(),u.getStat()));
        }

        return Optional.empty();
    }

    public Optional<UserDTO> crearUsuario(NewUserDTO newU) throws IllegalArgumentException{

        User u = uDao.buscarPorUser(newU.getUser()).orElse(null);

        if(u != null){
            throw new IllegalArgumentException("Error: el usuario ya existe");
        }
        String contrasena = newU.getPassword();

        if(contrasena.length() < 8){
            throw new IllegalArgumentException("Error: La contraseña tiene menos de 8 caracteres");
        }

        String hash = hashear(contrasena);

        u = new User();
        u.setUser(newU.getUser());
        u.setPassword(hash);

        int id = uDao.guardar(u);

        UserDTO respuesta = new UserDTO(id, u.getUser(), "Activo");

        return Optional.of(respuesta);
    }

}
