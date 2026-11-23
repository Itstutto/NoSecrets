package com.nosecrets.view;

import com.google.gson.Gson;
import com.nosecrets.dto.LoginDTO;
import com.nosecrets.dto.UserDTO;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Pruebahttp {
    public static void main() throws Exception {
        HttpClient cliente = HttpClient.newHttpClient();

        Gson gson = new Gson();
        LoginDTO dto = new LoginDTO("admin","admin1234");
        HttpRequest peticion = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/login")).
                header("Content-Type","aplication/json").POST(HttpRequest.BodyPublishers.ofString(gson.toJson(dto))).build();



        HttpResponse<String> respuesta = cliente.send(peticion, HttpResponse.BodyHandlers.ofString());

        UserDTO u = gson.fromJson(respuesta.body(),UserDTO.class);

        System.out.println(u.getId());
        System.out.println(u.getUser());
    }
}
