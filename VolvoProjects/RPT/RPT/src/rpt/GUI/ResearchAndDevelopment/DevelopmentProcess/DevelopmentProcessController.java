/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package rpt.GUI.ResearchAndDevelopment.DevelopmentProcess;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
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
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;


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
    public TableColumn<TableDevelopmentProcess, String>  beforeColumn;
    
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
    
    ComboBox<String> beforeCellComboBox = new ComboBox<String>();
   
    // ObservableList object for the list of processes
    private static ObservableList<String> processList = FXCollections.observableArrayList("Dummy process");
    
    //String to keep track of current process in order to store gateList properly
    String currentProcess = "";
    
    // ObservableList object for the list of gates
    private static ObservableList<String> gateList = FXCollections.observableArrayList("SOP");
    
    // ObservableList object for the main table view
    private static ObservableList<TableDevelopmentProcess> data =
        FXCollections.observableArrayList(
                    new TableDevelopmentProcess ("Hund", 2,"fdg", "fdf")    
                    );
   
    //create dictionaries, each process will have a set of gates with corresponding data shown in the table
    private static Map<String, ObservableList<TableDevelopmentProcess>> processDictionary = new HashMap<String, ObservableList<TableDevelopmentProcess>>();
    //each process will also have a set of gates to be shown in the comboboxes as "before options"
    private static Map<String, ObservableList<String>> gateDictionary = new HashMap<String, ObservableList<String>>();
    
    //Create table's data, get all of the items
    public static ObservableList<TableDevelopmentProcess> getDevelopmentProcesses(){
        return data;
    }
    
    //Add gate into table
    public static void add(TableDevelopmentProcess entry) {
        gateList.add(entry.getGateColumnString());
        data.add(entry);
    }
    
    //Click addGate button
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
    
    //Give function to the remove button
    public void removeGateButtonClicked (){
        ObservableList<TableDevelopmentProcess> removeGates;
        tableGates.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        removeGates = tableGates.getSelectionModel().getSelectedItems();
        for (TableDevelopmentProcess gate : removeGates){
            System.out.println(gate.getGateColumnString());
            gateList.removeAll(gate.getGateColumnString());
        }
        tableGates.getItems().removeAll(removeGates);
        
    }
  
    //Make it possible to get all contents of processList
    public static ObservableList<String> getProcessList(){
        return processList;
    }
    
    //to be used to extract which gates belong to a set process
    public static  ObservableList<String> getGateList(String process){
        return gateDictionary.get(process);
    }
       //Click to add process 
        public void addProcessButtonPressed(ActionEvent event) throws IOException{
            System.out.println("Add procces button");
            Stage stage;
            Parent root;
        if (event.getSource() == addProcessButton) {
            //Open the PopUp window with implementation fields
            stage = new Stage();
            root = FXMLLoader.load(getClass().getResource("/rpt/GUI/ResearchAndDevelopment/DevelopmentProcess/AddProcessDialog.fxml"));
            stage.setScene(new Scene(root));
            AddProcessDialogController.connect(this);
            stage.setTitle("Add process");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(addProcessButton.getScene().getWindow());
            stage.showAndWait();
        }
        
        }
        //Give function to the remove button
        public void deleteProcessButtonPressed (){
            Object processToRemove = processComboBox.getSelectionModel().getSelectedItem();
            processComboBox.getItems().removeAll(processToRemove);
        }
        
        //Add process into process combo box 
        public void addCombo(String entry) {
            processList.add(entry);
            ObservableList<String> newList = FXCollections.observableArrayList("SOP");
            gateDictionary.put(entry, newList);
            ObservableList<TableDevelopmentProcess> newData =  FXCollections.observableArrayList();
            processDictionary.put(entry, newData);
            
            processComboBox.getSelectionModel().select(entry);
            //tableGates.setItems(processDictionary.get(processComboBox.getSelectionModel().getSelectedItem().toString()));
            data = processDictionary.get(processComboBox.getSelectionModel().getSelectedItem().toString());
            //gateList = gateDictionary.get(processComboBox.getSelectionModel().getSelectedItem().toString()); 
            gateList = gateDictionary.get(processComboBox.getSelectionModel().getSelectedItem().toString());;
            beforeColumn.setCellFactory((ComboBoxTableCell.forTableColumn(gateList))); //make it listen to new list
            tableGates.setItems(data);
        }
        
        
        //Switch process
        public void processSelected(){
          //tableGates.setItems(processDictionary.get(processComboBox.getSelectionModel().getSelectedItem().toString()));
          System.out.println(processComboBox.getSelectionModel().getSelectedItem().toString());
          data = processDictionary.get(processComboBox.getSelectionModel().getSelectedItem().toString());
          gateList = gateDictionary.get(processComboBox.getSelectionModel().getSelectedItem().toString());
          if (gateList.size() > 1)
              System.out.println(gateList.get(1));
          beforeColumn.setCellFactory((ComboBoxTableCell.forTableColumn(gateList))); //make it listen to new list
          tableGates.setItems(data);
        }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Table is editable
        tableGates.setEditable(true);
        tableGates.setFixedCellSize(Region.USE_COMPUTED_SIZE);
        
        //specify a cell factory  and enable it editable
        gateColumn.setCellValueFactory(new PropertyValueFactory<>("gateColumnString"));
        gateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        gateColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableDevelopmentProcess, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TableDevelopmentProcess, String> event) {
                ((TableDevelopmentProcess) event.getTableView().getItems().
                        get(event.getTablePosition().getRow())).setGateColumnString(event.getNewValue());
            }
        });
        
        weeksColumn.setCellValueFactory(new PropertyValueFactory<>("weeksColumnInteger"));
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
                ((TableDevelopmentProcess) event.getTableView().getItems().
                        get(event.getTablePosition().getRow())).setWeeksColumnInteger(event.getNewValue());
            }
        });
        
        beforeColumn.setCellValueFactory(new PropertyValueFactory<>("beforeColumnComboBox"));
        beforeColumn.setCellFactory((ComboBoxTableCell.forTableColumn(gateList)));
        beforeColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableDevelopmentProcess, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TableDevelopmentProcess, String> event) {
                ((TableDevelopmentProcess) event.getTableView().getItems().
                        get(event.getTablePosition().getRow())).setBeforeColumnComboBox(event.getNewValue());
            }
        });
        
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("descriptionColumnString"));
        descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableDevelopmentProcess, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TableDevelopmentProcess, String> event) {
                ((TableDevelopmentProcess) event.getTableView().getItems().
                        get(event.getTablePosition().getRow())).setDescriptionColumnString(event.getNewValue());
            }
        });
        
        //Push into the table
        tableGates.setItems(data);
               
        //Push into combo box
        processComboBox.setItems(processList);

        //Put the dummy process and the list of it's gates into the process dictionary
        processDictionary.put("Dummy process", data);
        gateDictionary.put("Dummy process", gateList);
        
        //pre select the dummy process
        processComboBox.getSelectionModel().select("Dummy process");
        
        //Tooltips
        addGateButton.setTooltip(new Tooltip("Add new gate"));
        removeGateButton.setTooltip(new Tooltip("Remove selected gates"));
        editGateButton.setTooltip((new Tooltip("Edit gate")));
        addProcessButton.setTooltip((new Tooltip("Add new process")));
        processComboBox.setTooltip(new Tooltip("Select process"));
        deleteProcessButton.setTooltip(new Tooltip("Delete process"));
    }  
}