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

/**
 *
 * @author colak
 */
public class LoginController implements Initializable {
    
    @FXML
    private Button loginButton;
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
       //Switch to another stage - Main stage
       Parent root = FXMLLoader.load(getClass().getResource("/rpt/GUI/MainWindow.fxml"));
        Scene scene = new Scene(root);
        Stage stage=(Stage) loginButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
