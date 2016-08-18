/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpt.GUI.GroupManager.OwnResources;

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
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import rpt.GUI.ProgramManager.TableVariants;

/**
 * FXML Controller class
 *
 * @author colak
 */
public class OwnResourcesController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    public TableView<TableOwnResources> tableOwnResources;
    
    @FXML
    public TableColumn<TableOwnResources, String> nameColumn;
    
    @FXML
    public TableColumn<TableOwnResources, String>  resourcesTypeColumn;
    
    @FXML
    public TableColumn<TableOwnResources, String> monthAColumn;
    
    @FXML
    public TableColumn<TableOwnResources, String> monthBColumn;
    
    @FXML
    public TableColumn<TableOwnResources, String> monthCColumn;
    
    @FXML
    public TableColumn<TableOwnResources, String> monthDColumn;
    
    @FXML
    Button addButton;
    
    @FXML
    Button removeButton;
    
    @FXML
    Button saveButton;
    
    @FXML
    Button applyButton;
     
    @FXML
    DatePicker startDatePicker;
    
    @FXML
    DatePicker finishDatePicker;
    

    // ObservableList object enables the tracking of any changes to its elements
    private static ObservableList<TableOwnResources> data = FXCollections.observableArrayList(
            new TableOwnResources("Tupak", "Employee", "100%","0","3%","95%")
    );

    //Create table's data, get all of the items
    public static ObservableList<TableOwnResources> getRoles() {
        return data;
    }

    //Add entry into table
    public static void add(TableOwnResources entry) {
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
    ObservableList<TableOwnResources> removeVariants;
    tableOwnResources.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    removeVariants = tableOwnResources.getSelectionModel().getSelectedItems();
    tableOwnResources.getItems().removeAll(removeVariants);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableOwnResources.setEditable(true);

        //specify a cell factory  and enable it editable
        nameColumn.setCellValueFactory(new PropertyValueFactory("nameColumn")); //connect to the private variables in the table
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableOwnResources, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TableOwnResources, String> event) {
                ((TableOwnResources) event.getTableView().getItems().get(event.getTablePosition().getRow())).setNameColumn(event.getNewValue());
            }
         
        });
        resourcesTypeColumn.setCellValueFactory(new PropertyValueFactory("resourcesType")); //connect to the private variables in the table
        resourcesTypeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        resourcesTypeColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableOwnResources, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TableOwnResources, String> event) {
                ((TableOwnResources) event.getTableView().getItems().get(event.getTablePosition().getRow())).setResourcesType(event.getNewValue());
            }
         
        });
        monthAColumn.setCellValueFactory(new PropertyValueFactory("monthA")); //connect to the private variables in the table
        monthAColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        monthAColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableOwnResources, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TableOwnResources, String> event) {
                ((TableOwnResources) event.getTableView().getItems().get(event.getTablePosition().getRow())).setMonthA(event.getNewValue());
            }
         
        });
        monthBColumn.setCellValueFactory(new PropertyValueFactory("monthB")); //connect to the private variables in the table
        monthBColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        monthBColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableOwnResources, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TableOwnResources, String> event) {
                ((TableOwnResources) event.getTableView().getItems().get(event.getTablePosition().getRow())).setMonthB(event.getNewValue());
            }
         
        });
        monthCColumn.setCellValueFactory(new PropertyValueFactory("monthC")); //connect to the private variables in the table
        monthCColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        monthCColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableOwnResources, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TableOwnResources, String> event) {
                ((TableOwnResources) event.getTableView().getItems().get(event.getTablePosition().getRow())).setMonthC(event.getNewValue());
            }
         
        });
        monthDColumn.setCellValueFactory(new PropertyValueFactory("monthD")); //connect to the private variables in the table
        monthDColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        monthDColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableOwnResources, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TableOwnResources, String> event) {
                ((TableOwnResources) event.getTableView().getItems().get(event.getTablePosition().getRow())).setMonthD(event.getNewValue());
            }
         
        });
         

        //Push into the table
        tableOwnResources.setItems(data);
        
        //tooltips
         addButton.setTooltip(new Tooltip("Add new name"));
         removeButton.setTooltip(new Tooltip("Remove selected names"));  
         saveButton.setTooltip(new Tooltip ("Save"));
        
        
    }

}
