/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpt.GUI.GroupManager.Roles;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    public TableView<TableRoles> tableRoles;
    @FXML
    public TableColumn<TableRoles, String> rolesColumn;
    @FXML
    Button addButton;
    @FXML
    Button removeButton;

    // ObservableList object enables the tracking of any changes to its elements
    private static ObservableList<TableRoles> data = FXCollections.observableArrayList(new TableRoles("Group Manager")
    );

    //Create table's data, get all of the items
    public static ObservableList<TableRoles> getRoles() {
        return data;
    }

    //Add entry into table
    public static void add(TableRoles entry) {
        data.add(entry);
    }

    @FXML
    public void addButtonClicked(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        if (event.getSource() == addButton) {
            //Open the PopUp window with implementation fields
            stage = new Stage();
            root = FXMLLoader.load(getClass().getResource("/rpt/GUI/GroupManager/Roles/AddDialog.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Add role");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(addButton.getScene().getWindow());
            stage.showAndWait();
        } 
        

    }
    //Give the function to remove button
    public void removeButtonClicked (){
    ObservableList<TableRoles> removeVariants;
    tableRoles.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    removeVariants = tableRoles.getSelectionModel().getSelectedItems();
    tableRoles.getItems().removeAll(removeVariants);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableRoles.setEditable(true);

        //specify a cell factory  and enable it editable
        rolesColumn.setCellValueFactory(new PropertyValueFactory("rolesNamColumn")); //connect to the private variables in the table
        rolesColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        rolesColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableRoles, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TableRoles, String> event) {
                ((TableRoles) event.getTableView().getItems().get(event.getTablePosition().getRow())).setRolesNamColumn(event.getNewValue());
            }
        });

        //Push into the table
        tableRoles.setItems(data);
        
        //tooltips
         addButton.setTooltip(new Tooltip("Add new role"));
         removeButton.setTooltip(new Tooltip("Remove selected roles"));  
        
        
    }

}
