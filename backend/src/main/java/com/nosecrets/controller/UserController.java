package com.nosecrets.controller;

import com.google.gson.Gson;
import com.nosecrets.dao.UserDAO;
import com.nosecrets.dto.LoginDTO;
import com.nosecrets.dto.UserDTO;
import com.nosecrets.http.HttpUtil;
import com.nosecrets.service.UserService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class UserController {

    private final UserService  uService;

    public UserController(UserDAO u){
        uService = new UserService(u);
    }

    public void login(HttpExchange ex) throws IOException {

        Gson gson = new Gson();
        LoginDTO ldto = null;

        try{
            String cuerpo = HttpUtil.leerCuerpo(ex);
            ldto = gson.fromJson(cuerpo,LoginDTO.class);
        }catch (Exception e){
            e.printStackTrace();
        }

        if (ldto == null) {   // cuerpo vacío: fromJson devuelve null
            HttpUtil.enviar(ex, 400, "{\"error\":\"Cuerpo vacío\"}");
            return;
        }
        UserDTO udto = null;
        try {
             udto = uService.login(ldto).orElse(new UserDTO());
        }catch (Exception e){
            e.printStackTrace();
        }

        String envio = gson.toJson(udto);

        HttpUtil.enviar(ex,201,envio);
    }
}
