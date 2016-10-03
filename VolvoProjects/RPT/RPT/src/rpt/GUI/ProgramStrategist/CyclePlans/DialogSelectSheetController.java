/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpt.GUI.ProgramStrategist.CyclePlans;

import java.awt.Insets;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author scolak1
 */
public class DialogSelectSheetController implements Initializable {

    
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
    
    final ToggleGroup sheetGroup = new ToggleGroup();
    
    private String sheetName = null;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Add radio buttons to the main window. One radio button for each of the
        // sheets in the excel file to allow the user to select which sheet to
        // import data from.
        addSheetButtons();
    }    
    
    // When the OK button is pressed the seelcted sheet will be set in the 
    // parent controller
    public void okButtonPressed(){
        // TODO identify which radio button was selected
        // send text of the selected button to the parent controller
        CyclePlansController.selectedSheet = sheetName;
        
        closeDialog();
    }
    
    // Close button pressed leads to shutting down the dialog. Will not set the 
    // Reset the sheet name in order to avoid further processing.
    
    //Closing of the dialog
    public void closeDialog(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    
    // method receives an array of sgtrings containing radio button labels
    // every other in left VBox, every other in right VBox.
    public void addSheetButtons(){
        List<String> buttons = CyclePlansController.getSheets();
        boolean left = true;
        for (String b : buttons){
            System.out.println(b);
            RadioButton rb = new RadioButton(b);
            rb.setToggleGroup(sheetGroup);
            //rb.setPadding(new Insets(20, 10, 10, 20)););
            if(left){
                leftContainer.getChildren().add(rb);
                left = false;
            }else{
                rightContainer.getChildren().add(rb);
                left = true;
            }
            
            //TODO Check if string contains the words Raw, make it preselected if it does
            if (b.toLowerCase().contains("raw")){
                rb.requestFocus();
                rb.setSelected(true);
                sheetName = b;
            }
        }
        sheetGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
                if (sheetGroup.getSelectedToggle() != null) {
                    RadioButton chk = (RadioButton)new_toggle.getToggleGroup().getSelectedToggle(); 
                    sheetName = chk.getText();
                }                
            }
        });
    

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
