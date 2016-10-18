/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpt.GUI.ProgramStrategist.CyclePlans;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import rpt.RPT;

/**
 * FXML Controller class
 *
 * @author scolak1
 */
public class DialogDefineChangedController implements Initializable {

    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;
    @FXML
    private HBox mainContainer;
    @FXML
    private VBox leftContainer;
    @FXML
    private VBox rightContainer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Add check boxes to the main window. One check box for each of the
        // columns in the variant table to allow the user to select which columns
        // are minor changes only.
        addCheckBoxes();
    }

    // When the OK button is pressed the majorChanger arrayList is set in the parent controller.
    public void okButtonPressed() {
        ObservableList<Node> checkBoxes = leftContainer.getChildren();
        for (Node n: checkBoxes){
            CheckBox cb = (CheckBox) n;
            if (cb.isSelected())
                CompareDialogController.majorChanges.add(cb.getText());
        }
        checkBoxes = rightContainer.getChildren();
        for (Node n: checkBoxes){
            CheckBox cb = (CheckBox) n;
            if (cb.isSelected())
                CompareDialogController.majorChanges.add(cb.getText());
        }
        closeDialog();
    }

    // Close button pressed leads to shutting down the dialog. Will not set the 
    // Reset the sheet name in order to avoid further processing.
    //Closing of the dialog
    public void closeDialog() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    // method receives an array of sgtrings containing radio button labels
    // every other in left VBox, every other in right VBox.
    public void addCheckBoxes() {
        boolean left = true;

        try {
            Statement statement = RPT.conn.createStatement();
            statement.setQueryTimeout(30);
            String query = "PRAGMA table_info(VARIANTS)"; //Get all column names
            ResultSet rs = statement.executeQuery(query);

            //loop through each of the columns and add them as checkboxes
            while (rs.next()) {
                String buttonText = rs.getString("name");
                CheckBox cb = new CheckBox(buttonText);
                if (left) {
                    leftContainer.getChildren().add(cb);
                    left = false;
                } else {
                    rightContainer.getChildren().add(cb);
                    left = true;
                }
                boolean preChecked = buttonText.contains("Platform") || 
                        buttonText.contains("Vehicle") ||
                        buttonText.contains("Denomination") ||
                        buttonText.contains("GearboxType") ||
                        buttonText.contains("Generation") ||
                        buttonText.contains("EmissionClass");
                if (preChecked) {
                    cb.requestFocus();
                    cb.setSelected(true);
                }
                
            }
        } catch (Exception e) {
            System.err.println("DialogDefineChangedController ERROR 1: " + e.getMessage());
        }
    }

    //add Key listeners
    // ENTER = OK, ESCAPE = CANCEL
    public void keyHandler(KeyEvent t) {
        //add current input on ENTER
        if (t.getCode() == KeyCode.ENTER) {
            okButtonPressed();
        } //Close on ESCAPE
        else if (t.getCode() == KeyCode.ESCAPE) {
            closeDialog();
        }
    }
}
