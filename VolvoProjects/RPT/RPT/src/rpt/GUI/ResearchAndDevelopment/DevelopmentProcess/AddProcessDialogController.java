/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package rpt.GUI.ResearchAndDevelopment.DevelopmentProcess;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.AccessibleAttribute;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author colak
 */
public class AddProcessDialogController implements Initializable {
    
    /**
     * Initializes the controller class.
     */
    
    @FXML
            TextField processTextField;
    
    @FXML
            Button addButton;
    
    @FXML
            Button doneButton;
    static DevelopmentProcessController parent;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
        //add item into table
        public void add(){
            //create entry for the combo
            String comboEntry;
            comboEntry = processTextField.getText();
            //insert data in the combo
            parent.addCombo(comboEntry);
            //clear the entry form
            clearForm();
            parent.showSelectedProcess(comboEntry);
        }
        //Add button clicked with the mouse
        public void addItem(ActionEvent event){
            add();
        }
        
        // clear form method
        public void clearForm(){
            processTextField.clear();
        }
        
        
        public void closeDialog(){
            Stage stage = (Stage) doneButton.getScene().getWindow();
            stage.close();
        }
        
        //connect the development process to the dialog box in order to make it
        //possible to set the comboBox to the recently added process
        public static void connect(DevelopmentProcessController dpc){
            parent = dpc;
        }
        
        //Key handler
        public void keyHandler(KeyEvent t) {
            //add current input on ENTER
            if (t.getCode() == KeyCode.ENTER) {
                add();
            }
            //Close on ESCAPE
            else if (t.getCode() == KeyCode.ESCAPE) {
                closeDialog();
            }
        }
}


