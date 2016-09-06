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
public class AddGateDialogController implements Initializable {
    
    @FXML
    TextField gateTextField;
    
    @FXML
    TextField weeksTextField;
    
    @FXML
    TextField descriptionTextField;
  
    @FXML
    Button addButton;
    
    @FXML
    Button doneButton;
    
  
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
     //add gate into table
    public void add(){
            
        //create entry for the table
        TableDevelopmentProcess entry = new TableDevelopmentProcess();
        entry.setGateColumnString(gateTextField.getText());
        entry.setWeeksColumnInteger(Integer.parseInt(weeksTextField.getText()));
        entry.setDescriptionColumnString(descriptionTextField.getText());
        
       //insert data in the table
       DevelopmentProcessController.add(entry);
       //clear the entry form
        clearForm();
    }
    //Add button clicked with the mouse
    public void addItem(ActionEvent event){
         add();
    }
  
    // clear form method
    public void clearForm(){
        gateTextField.clear();
        weeksTextField.clear();
        descriptionTextField.clear();
    }
   
     
    public void closeDialog(){
        Stage stage = (Stage) doneButton.getScene().getWindow();
        stage.close();
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
   

