package com.nosecrets.service;

import com.nosecrets.dao.UserDAO;
import com.nosecrets.dto.LoginDTO;
import com.nosecrets.dto.UserDTO;
import com.nosecrets.model.User;

import java.util.Optional;

public class UserService {
    private final UserDAO uDao;

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


}
