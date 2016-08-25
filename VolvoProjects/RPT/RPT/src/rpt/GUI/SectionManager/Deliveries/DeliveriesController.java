/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package rpt.GUI.SectionManager.Deliveries;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import rpt.GUI.ResearchAndDevelopment.DevelopmentProcess.TableDevelopmentProcess;

/**
 * FXML Controller class
 *
 * @author colak
 */
public class DeliveriesController implements Initializable {
    
    @FXML
    public TableView <TableDeliveries> tableDeliveries;
    
    @FXML
    public TableColumn <TableDeliveries, String> deliveryColumn;
    
    @FXML
    public TableColumn <TableDeliveries, String> descriptionColumn;
    
    @FXML
            Button addButton;
    
    @FXML
            Button removeButton;
    
    @FXML
            Button editButton;
    
    @FXML
            ComboBox processBox;
    
    @FXML
            ComboBox gateBox;
    
    
    // ObservableList object enables the tracking of any changes to its elements
    private static ObservableList<TableDeliveries> data =
            FXCollections.observableArrayList(new TableDeliveries("FC204","BlBlabla")
            );
    
    //Create table's data, get all of the items
    public static ObservableList<TableDeliveries> getDeliveris(){
        return data;
    }
      //Add entry into table
    public static void add(TableDeliveries entry) {
        data.add(entry);
    }
     //Click addGate button
    public void adduttonClicked(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        if (event.getSource() == addButton) {
            //Open the PopUp window with implementation fields
            stage = new Stage();
            root = FXMLLoader.load(getClass().getResource("/rpt/GUI/SectionManager/Deliveries/AddDeliveryDialog.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Add gate");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(addButton.getScene().getWindow());
            stage.showAndWait();
        }
    }
    //Give the function to the remove button
    public void removeButtonClicked (){
        ObservableList<TableDeliveries> removeDeliveries;
        tableDeliveries.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        removeDeliveries = tableDeliveries.getSelectionModel().getSelectedItems();
        tableDeliveries.getItems().removeAll(removeDeliveries);
    }
    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
         //Table is editable
        tableDeliveries.setEditable(true);
        
        //specify a cell factory  and enable it editable
        deliveryColumn.setCellValueFactory(new PropertyValueFactory<>("delieveryColumnString"));
        deliveryColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        deliveryColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableDeliveries, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TableDeliveries, String> event) {
                ((TableDeliveries) event.getTableView().getItems().get(event.getTablePosition().
                        getRow())).setDelieveryColumnString(event.getNewValue());
            }
        });
         //specify a cell factory  and enable it editable
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("descriptionColumnString"));
        descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableDeliveries, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TableDeliveries, String> event) {
                ((TableDeliveries) event.getTableView().getItems().get(event.getTablePosition().
                        getRow())).setDescriptionColumnString(event.getNewValue());
            }
        });
        
        //Push into the table
        tableDeliveries.setItems(data);
        
    }
    
}
