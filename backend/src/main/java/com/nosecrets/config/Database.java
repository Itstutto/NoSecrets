package com.nosecrets.config;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    private final String url;
    private final String user;
    private final String password;

    public Database(){
        Properties prop = new Properties();
        try(InputStream in = Database.class.getResourceAsStream("/db.properties")){
            prop.load(in);
        }catch (IOException e){
            throw new IllegalStateException("No se pudo cargar db.properties");
        }

        this.url = prop.getProperty("db.url");
        this.user = prop.getProperty("db.user");
        this.password = prop.getProperty("db.password");

    }


    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,user,password);
    }
}
