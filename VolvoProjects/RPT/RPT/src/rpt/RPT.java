/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.*;

/**
 *
 * @author colak
 */
public class RPT extends Application {
    //Database connector 
    public static Connection conn = null;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/rpt/GUI/LoginGUI.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Resource Planning Tool - RPT");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
        
    }
   

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Connect to database
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:RPT.sqlite");
            System.out.println("Opened database successfully");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
            
        
        //launch main view
        launch(args);
    }
    
}
