/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package rpt.GUI.Projects;

import rpt.GUI.ResearchAndDevelopment.DevelopmentProcess.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import rpt.GUI.ProgramStrategist.CyclePlans.TableVariant;

/**
 * FXML Controller class
 *
 * @author colak
 */
public class ProjectOverviewController implements Initializable {

    @FXML
    public TableView<TableVariant> tableVariants;
    @FXML
    private Button addButton;
    @FXML
    private Button removeButton;
    @FXML
    private Button editButton;

    @FXML
    Button deleteProjectButton;

    @FXML
    Button addProjectButton;

    @FXML
    ComboBox projectComboBox;

    // ObservableList object for the list of processes
    private static ObservableList<String> projectList = FXCollections.observableArrayList();

    //String to keep track of current process in order to store gateList properly
    String currentProject = "";

    // ObservableList object enables the tracking of any changes to its elements
    private static ObservableList<TableVariant> data = FXCollections.observableArrayList();
    FilteredList<TableVariant> filteredData = new FilteredList<>(data, p -> true); //make it possible to filter
    Map<String, ArrayList<String>> filterList = new HashMap<String, ArrayList<String>>();

    //Add entry into table
    public static void add(TableVariant entry) {
        data.add(entry);
    }

    //Returns what is currently being presented in the variant table
    public static ObservableList<TableVariant> getVariants() {
        return data;
    }

    //** BUTTON ACTION HANDLERS ** //
    //Left to right as they appear
    //Add button action handler
    public void addButtonClicked(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        //Open the PopUp window with implementation fields
        stage = new Stage();
        root = FXMLLoader.load(getClass().getResource("/rpt/GUI/ProgramStrategist/CyclePlans/AddDialog.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Add");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(addButton.getScene().getWindow());
        stage.showAndWait();
    }

    //Remove button action handler
    public void removeButtonClicked() {
        ObservableList<TableVariant> removeVariants;
        tableVariants.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        removeVariants = tableVariants.getSelectionModel().getSelectedItems();
        tableVariants.getItems().removeAll(removeVariants);
    }

    // Save button action handler
    public void saveButtonClicked() {
        // does nothing for now, should save a new version of the selected cycle plan
        // i.e. update the relations between cycle plan and variants.
    }

    //Click to add process 
    public void addProjectButtonPressed(ActionEvent event) throws IOException {
        System.out.println("Add Project Button");
        Stage stage;
        Parent root;
        if (event.getSource() == addProjectButton) {
            //Open the PopUp window with implementation fields
            stage = new Stage();
            root = FXMLLoader.load(getClass().getResource("/rpt/GUI/Projects/AddProjectDialog.fxml"));
            stage.setScene(new Scene(root));
            //AddProjectDialogController.connect(this);
            stage.setTitle("Add Project");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(addProjectButton.getScene().getWindow());
            stage.showAndWait();
        }

    }
    //Give function to the remove button

    public void deleteProjectButtonPressed() {
        Object projectToRemove = projectComboBox.getSelectionModel().getSelectedItem();
        projectComboBox.getItems().removeAll(projectToRemove);

        //TODO
        // Remove from database
    }

    //Add project into process combo box 
    public void addCombo(String entry) {
        //TODO Database code to load data into project combo box, if needed at all
    }

    //Switch process
    public void projectSelected() {
        //tableGates.setItems(processDictionary.get(processComboBox.getSelectionModel().getSelectedItem().toString()));
        System.out.println(projectComboBox.getSelectionModel().getSelectedItem().toString());

        //TODO 
        // Read data from database
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
