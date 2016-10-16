/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpt.GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.sql.*;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import rpt.RPT;

/**
 *
 * @author colak
 */
public class LoginController implements Initializable {
    @FXML
            TextField cdsidField;
    @FXML
            TextField passwordField;
    @FXML
            Label wrongLogin;
    @FXML
            private Button loginButton;
    public void loginCheck () throws IOException{
     //Check user password
       String password = "";
       try {
           Statement statement = RPT.conn.createStatement();
           statement.setQueryTimeout(30);
           String query = "SELECT * FROM USERS WHERE cdsid = '" + cdsidField.getText() + "'";
           ResultSet rs = statement.executeQuery(query);
           
           //TODO Add PRAGMA Truncate when creating new database or you may get disk errors
           
           //Extract password
           password = rs.getString("password");
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        
        if (password.equals(passwordField.getText())){
           //Switch to another stage - Main stage
            Parent root = FXMLLoader.load(getClass().getResource("/rpt/GUI/MainWindow.fxml"));
            Scene scene = new Scene(root);
            Stage stage=(Stage) loginButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show(); 
        }
        else{
           cdsidField.clear();
           passwordField.clear();
           wrongLogin.setVisible(true);   
        }    
    }
    
    //Login button click
    @FXML
    private void loginButton(ActionEvent event) throws IOException{
      loginCheck();
    }
    //Login by pressing ENTER
    @FXML
    public void handleEnterPressed(KeyEvent event) throws IOException{
    if (event.getCode() == KeyCode.ENTER) {
        loginCheck();
    }
}
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
