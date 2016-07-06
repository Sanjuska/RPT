/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package rpt.GUI.Tabs;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
public class AddDialogController implements Initializable {
    
    @FXML
            TextField variantIField;
    @FXML
            TextField engineNameField;
    @FXML
            TextField denominationField;
    @FXML
            TextField gearboxField;
    @FXML
            TextField emissionsCategoryField;
    @FXML
            Button addButton;
    @FXML
            Button doneButton;
    
    private Stage dialogStage;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
     //add item into table
    public void add(){
          //create entry for the table
        TableVariants entry = new TableVariants();
        entry.setVariantsID(Integer.parseInt(variantIField.getText()));
        entry.setEngineName(engineNameField.getText());
        entry.setDenomination(denominationField.getText());
        entry.setGearbox(gearboxField.getText());
        entry.setEmissionsCategory(emissionsCategoryField.getText());
        
       //insert data in the table
       TabVariantsController.add(entry);
       //clear the entry form
        clearForm();
    }
    //Add button clicked with the mouse
    public void addItem(ActionEvent event){
         add();
    }
  
    // clear form method
    public void clearForm(){
        variantIField.clear();
        engineNameField.clear();
        denominationField.clear();
        gearboxField.clear();
        emissionsCategoryField.clear();
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
   

