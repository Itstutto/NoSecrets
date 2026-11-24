package com.nosecrets.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML TextField userTxt;
    @FXML TextField passTxt;
    @FXML Button loginButton;



    public void initialize(){

    }


    public void iniciarSession(){
        String user = userTxt.getText();
        String password = passTxt.getText();

        if(user == null || user.isEmpty() || password == null || password.isEmpty()){
            return;
        }


    }
}
