/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package rpt.GUI.Tabs;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author colak
 */
public class TabVariantsController implements Initializable {
    
    
    /**
     * Initializes the controller class.
     */
    
    //Define table and buttons
    @FXML
            public  TableView <TableVariants> tableVariants;
    @FXML
            public TableColumn<TableVariants, Integer> variantsID;
    @FXML
            public TableColumn<TableVariants, String> engineName;
    @FXML
            public TableColumn<TableVariants, String> denomination;
    @FXML
            public TableColumn<TableVariants, String> gearbox;
    @FXML
            public TableColumn<TableVariants, String> emissionsCategory;   
    @FXML
            Button addButton;        
    @FXML
            Button removeButton;
    @FXML
            Button saveButton;
            
   
    //Create table's data, get all of the items
    public static ObservableList<TableVariants> getVariants(){
        return data;
    }
    // ObservableList object enables the tracking of any changes to its elements
    private static ObservableList<TableVariants> data = FXCollections.observableArrayList(
           new TableVariants (1, "Engine 1", "denomination2", "gearbox3", "emissions")
    );
    //Add entry into table
    public static void add(TableVariants entry){
        data.add(entry); 
        //System.out.println(data.);
    }
    //Give the function to add button
    @FXML
    public void addButtonClicked(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        if(event.getSource()==addButton)
        {
            //Open the PopUp window with implementation fields
            stage = new Stage();
            root = FXMLLoader.load(getClass().getResource("/rpt/GUI/Tabs/AddDialog.fxml"));
            stage.setScene(new Scene(root)); 
            stage.setTitle("Add");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(addButton.getScene().getWindow());
            stage.showAndWait();
        }
        else
        {
            stage=(Stage)removeButton.getScene().getWindow();
            stage.close();
        }
        
    }
    
    //Give the function to remove button
    public void removeButtonClicked (){
    ObservableList<TableVariants> removeVariants;
    tableVariants.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    removeVariants = tableVariants.getSelectionModel().getSelectedItems();
    tableVariants.getItems().removeAll(removeVariants);
}         
    //Navigate through table using and arrows
    
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableVariants.setEditable(true);
        // specify a cell factory for each column
        variantsID.setCellValueFactory(new PropertyValueFactory<>("variantsID"));
        variantsID.setEditable(false);
       
        //specify a cell factory  and enable it editable
        engineName.setCellValueFactory(new PropertyValueFactory<>("engineName")); 
        engineName.setCellFactory(TextFieldTableCell.forTableColumn());
        engineName.setOnEditCommit(new EventHandler<CellEditEvent<TableVariants, String>>() {
            @Override
            public void handle(CellEditEvent<TableVariants, String> event) {
              ((TableVariants) event.getTableView().getItems().get(event.getTablePosition().getRow())).setEngineName(event.getNewValue());
            }  
        });
        //specify a cell factory  and enable it editable
        denomination.setCellValueFactory(new PropertyValueFactory<>("denomination"));
        denomination.setCellFactory(TextFieldTableCell.forTableColumn());
        engineName.setOnEditCommit(new EventHandler<CellEditEvent<TableVariants, String>>() {
            @Override
            public void handle(CellEditEvent<TableVariants, String> event) {
              ((TableVariants) event.getTableView().getItems().get(event.getTablePosition().getRow())).setDenomination(event.getNewValue());
            }  
        });
        //specify a cell factory  and enable it editable
        gearbox.setCellValueFactory(new PropertyValueFactory<>("gearbox"));
        gearbox.setCellFactory(TextFieldTableCell.forTableColumn());
        gearbox.setOnEditCommit(new EventHandler<CellEditEvent<TableVariants, String>>() {
            @Override
            public void handle(CellEditEvent<TableVariants, String> event) {
              ((TableVariants) event.getTableView().getItems().get(event.getTablePosition().getRow())).setGearbox(event.getNewValue());
            }  
        });
        //specify a cell factory  and enable it editable
        emissionsCategory.setCellValueFactory(new PropertyValueFactory<>("emissionsCategory"));
        emissionsCategory.setCellFactory(TextFieldTableCell.forTableColumn());
        emissionsCategory.setOnEditCommit(new EventHandler<CellEditEvent<TableVariants, String>>() {
            @Override
            public void handle(CellEditEvent<TableVariants, String> event) {
              ((TableVariants) event.getTableView().getItems().get(event.getTablePosition().getRow())).setEmissionsCategory(event.getNewValue());
            }  
        });
        //Push into the table
        tableVariants.setItems(data);
      
        //Add Tooltip to the add and remove icons
         addButton.setTooltip(new Tooltip("Add new item"));
         removeButton.setTooltip(new Tooltip("Remove selected items"));  
         saveButton.setTooltip(new Tooltip("Save"));
    }
    
}
