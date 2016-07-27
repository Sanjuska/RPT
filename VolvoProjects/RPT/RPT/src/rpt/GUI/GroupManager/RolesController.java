/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpt.GUI.GroupManager;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import rpt.GUI.ProgramManager.TableVariants;

/**
 * FXML Controller class
 *
 * @author colak
 */
public class RolesController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    public TableView <RolesTable> tableRoles;
     @FXML
    public TableColumn<RolesTable, String> rolesColumn;
     
     
       //Create table's data, get all of the items
    public static ObservableList<RolesTable> getRoles(){
        return data;
    }
    // ObservableList object enables the tracking of any changes to its elements
    private static ObservableList<RolesTable> data = FXCollections.observableArrayList(
           new RolesTable ("Group Manager")
    );
    //Add entry into table
    public static void add(RolesTable entry){
        data.add(entry); 
        //System.out.println(data.);
    }
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tableRoles.setEditable(true);
        // specify a cell factory for each column
        rolesColumn.setCellValueFactory(new PropertyValueFactory<>("Group Manager"));
        rolesColumn.setEditable(false);
    }    
    
}
