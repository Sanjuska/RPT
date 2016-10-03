/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpt.GUI.ProgramStrategist.CyclePlans;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author scolak1
 */
public class SetNameController implements Initializable {

    @FXML
        private Button okButton;
    @FXML
        private Button cancelButton;
    @FXML
        private TextField textField;
    
    private String cyclePlanName = null;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        textField.setText(CyclePlansController.importedCyclePlanName);
    }    
  
    
    // When the OK button is pressed the seelcted sheet will be set in the 
    // parent controller
    public void okButtonPressed(){
        // send text of the textField to the parent controller
        CyclePlansController.importedCyclePlanName = textField.getText();
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }
    
    // Close button pressed leads to shutting down the dialog. Will not set the 
    // Reset the sheet name in order to avoid further processing.
    
    //Closing of the dialog
    public void closeDialog(){
        CyclePlansController.importedCyclePlanName = null;// in order to inform parent controller
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    
    
    //add Key listeners
    // ENTER = OK, ESCAPE = CANCEL
    public void keyHandler(KeyEvent t) {
                //add current input on ENTER
                if (t.getCode() == KeyCode.ENTER) {
                    okButtonPressed();
                } 
                //Close on ESCAPE
                else if (t.getCode() == KeyCode.ESCAPE) {
                   closeDialog();
            }
    }
}
