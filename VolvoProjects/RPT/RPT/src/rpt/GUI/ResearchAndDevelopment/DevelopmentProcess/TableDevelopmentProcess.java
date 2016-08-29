/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpt.GUI.ResearchAndDevelopment.DevelopmentProcess;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

/**
 *
 * @author colak
 */
public class TableDevelopmentProcess {

    //variables
    private String gateColumnString;
    private Integer weeksColumnInteger;
    private String beforeColumnComboBox;
    private String descriptionColumnString;

    public String getGateColumnString() {
        return gateColumnString;
    }

    public void setGateColumnString(String gateColumnString) {
        this.gateColumnString = gateColumnString;
    }

    public Integer getWeeksColumnInteger() {
        return weeksColumnInteger;
    }

    public void setWeeksColumnInteger(Integer weeksColumnInteger) {
        this.weeksColumnInteger = weeksColumnInteger;
    }

    public String getBeforeColumnComboBox() {
        return beforeColumnComboBox;
    }

    public void setBeforeColumnComboBox(String beforeColumnComboBox) {
        this.beforeColumnComboBox = beforeColumnComboBox;
    }

    public String getDescriptionColumnString() {
        return descriptionColumnString;
    }

    public void setDescriptionColumnString(String descriptionColumnString) {
        this.descriptionColumnString = descriptionColumnString;
    }

  
    
//   // Empty constructor
     public TableDevelopmentProcess() {
         this.gateColumnString = "";
         this.weeksColumnInteger = 0;
         this.beforeColumnComboBox= "";
         this.descriptionColumnString ="";
        
    }
//    
//    //Constructor with parameters
//    public TableDevelopmentProcess(String gateColumnString, int weeksColumnInteger, 
//            String beforeColumnObservableList, String descriptionColumnString) {
//        this.gateColumnString = gateColumnString;
//        this.weeksColumnInteger = weeksColumnInteger;
//        this.beforeColumnObservableList = beforeColumnObservableList;
//        this.descriptionColumnString = descriptionColumnString;   
//    }
//    
//    //Getters and setters
//     public String getGateColumnString() {
//        return gateColumnString;
//    }
//
//    public void setGateColumnString(String gateColumnString) {
//        this.gateColumnString = gateColumnString;
//    }
//
//    public String getDescriptionColumnString() {
//        return descriptionColumnString;
//    }
//
//    public void setDescriptionColumnString(String descriptionColumnString) {
//        this.descriptionColumnString = descriptionColumnString;
//    }
//
//    public int getWeeksColumnInteger() {
//        return weeksColumnInteger;
//    }
//
//    public void setWeeksColumnInteger(int weeksColumnInteger) {
//        this.weeksColumnInteger = weeksColumnInteger;
//    }
//    
//    public String getBeforeColumnObservableList() {
//        return beforeColumnObservableList;
//    }
//
//    public void setBeforeColumnObservableList(String beforeColumnObservableList) {
//        this.beforeColumnObservableList = beforeColumnObservableList;
//    }
//   

    public TableDevelopmentProcess(String gateColumnString, Integer weeksColumnInteger, String beforeColumnComboBox, String descriptionColumnString) {
        this.gateColumnString = gateColumnString;
        this.weeksColumnInteger = weeksColumnInteger;
        this.beforeColumnComboBox = beforeColumnComboBox;
        this.descriptionColumnString = descriptionColumnString;
    }

    


}

