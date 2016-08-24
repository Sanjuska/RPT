/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package rpt.GUI.ResearchAndDevelopment.DevelopmentProcess;

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
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import rpt.GUI.GroupManager.OwnResources.TableOwnResources;
import rpt.GUI.GroupManager.Roles.TableRoles;
import rpt.GUI.ProgramManager.TableVariants;

/**
 * FXML Controller class
 *
 * @author colak
 */
public class DevelopmentProcessController implements Initializable {
    
    @FXML
    public TableView<TableDevelopmentProcess> tableGates;
    
    @FXML
    public TableColumn<TableDevelopmentProcess, String> gateColumn;
    
    @FXML
    public TableColumn<TableDevelopmentProcess, Integer>  weeksColumn;
    
    @FXML
    public TableColumn<TableDevelopmentProcess, String> descriptionColumn;
    
    @FXML
            Button addGateButton;
    
    @FXML
            Button removeGateButton;
    
    @FXML
            Button editGateButton;
    
    @FXML
            Button deleteProcessButton;
    
    @FXML
            Button addProcessButton;
    
    @FXML
            ComboBox processComboBox;
    
    @FXML
            Separator separator;
    
    
    //Create table's data, get all of the items
    public static ObservableList<TableDevelopmentProcess> getDevelopmentProcesses(){
        return data;
    }
    
    // ObservableList object enables the tracking of any changes to its elements
    private static ObservableList<TableDevelopmentProcess> data =
            FXCollections.observableArrayList(new TableDevelopmentProcess("FC204", 3, "BlBlabla")
            );
    
    //Add entry into table
    public static void add(TableDevelopmentProcess entry) {
        data.add(entry);
    }
    @FXML
    public void addGateButtonClicked(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        if (event.getSource() == addGateButton) {
            //Open the PopUp window with implementation fields
            stage = new Stage();
            root = FXMLLoader.load(getClass().getResource("/rpt/GUI/ResearchAndDevelopment/DevelopmentProcess/AddGateDialog.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Add gate");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(addGateButton.getScene().getWindow());
            stage.showAndWait();
        }
        
        
    }
    //Give the function to remove button
    public void removeGateButtonClicked (){
        ObservableList<TableDevelopmentProcess> removeGates;
        tableGates.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        removeGates = tableGates.getSelectionModel().getSelectedItems();
        tableGates.getItems().removeAll(removeGates);
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Table is editable
        tableGates.setEditable(true);
        
        //specify a cell factory  and enable it editable
        gateColumn.setCellValueFactory(new PropertyValueFactory<>("gateColumnString"));
        gateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        gateColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableDevelopmentProcess, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TableDevelopmentProcess, String> event) {
                ((TableDevelopmentProcess) event.getTableView().getItems().get(event.getTablePosition().getRow())).setGateColumnString(event.getNewValue());
            }
        });
        
        weeksColumn.setCellValueFactory(new PropertyValueFactory<TableDevelopmentProcess,Integer>("weeksColumnInteger"));
        weeksColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>(){
            @Override
            public String toString(Integer object) {
                return object.toString();
            }
            @Override
            public Integer fromString(String string) {
                return Integer.parseInt(string);
            }
            
        }));
        weeksColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableDevelopmentProcess, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TableDevelopmentProcess, Integer> event) {
                ((TableDevelopmentProcess) event.getTableView().getItems().get(event.getTablePosition().getRow())).setWeeksColumnInteger(event.getNewValue());
            }
        });
        
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("descriptionColumnString"));
        descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableDevelopmentProcess, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TableDevelopmentProcess, String> event) {
                ((TableDevelopmentProcess) event.getTableView().getItems().get(event.getTablePosition().getRow())).setDescriptionColumnString(event.getNewValue());
            }
        });
        
        //Push into the table
        tableGates.setItems(data);
        
        //Tooltips
        addGateButton.setTooltip(new Tooltip("Add new gate"));
        removeGateButton.setTooltip(new Tooltip("Remove selected gates"));
        editGateButton.setTooltip((new Tooltip("Edit gate")));
        addProcessButton.setTooltip((new Tooltip("Add new project")));
        processComboBox.setTooltip(new Tooltip("Select process"));
        deleteProcessButton.setTooltip(new Tooltip("Delete process"));
        
        //Separator preferences
        
    }
    
}
