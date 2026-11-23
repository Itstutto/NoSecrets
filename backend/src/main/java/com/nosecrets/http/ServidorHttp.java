package com.nosecrets.http;

import com.nosecrets.config.Database;
import com.nosecrets.controller.UserController;
import com.nosecrets.dao.UserDAO;
import com.nosecrets.dao.impl.UserDAOImpl;
import com.sun.net.httpserver.HttpServer;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class ServidorHttp {

    public static void main() throws IOException {
        Database db = new Database();
        UserDAO u = new UserDAOImpl(db);


        UserController uController = new UserController(u);

        HttpServer server = HttpServer.create(new InetSocketAddress(8080),0);
        server.createContext("/ping",exchange -> {
            byte[] respuesta = ("pong ").getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200,respuesta.length);
            try (OutputStream os = exchange.getResponseBody()){
                os.write(respuesta);
            }
        });

        server.createContext("/login", uController::login);
        server.start();

        System.out.println("Servidor Escuchando");
    }
}
