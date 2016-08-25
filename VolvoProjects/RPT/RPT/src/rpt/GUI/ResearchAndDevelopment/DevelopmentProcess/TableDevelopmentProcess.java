/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpt.GUI.ResearchAndDevelopment.DevelopmentProcess;

/**
 *
 * @author colak
 */
public class TableDevelopmentProcess {

    //variables
    private String gateColumnString;
    private int weeksColumnInteger;
    private String descriptionColumnString;
    private String processComboBox;

    public TableDevelopmentProcess(String processComboBox) {
        this.processComboBox = processComboBox;
    }
    
    //Empty constructor
     public TableDevelopmentProcess() {
         this.gateColumnString = "";
         this.weeksColumnInteger = 0;
         this.descriptionColumnString ="";
    }
    
    //Constructor with parameters
    public TableDevelopmentProcess(String gateColumnString, int weeksColumnInteger, String descriptionColumnString ) {
        this.gateColumnString = gateColumnString;
        this.weeksColumnInteger = weeksColumnInteger;
        this.descriptionColumnString = descriptionColumnString;
    }
    
    //Getters and setters
     public String getGateColumnString() {
        return gateColumnString;
    }

    public void setGateColumnString(String gateColumnString) {
        this.gateColumnString = gateColumnString;
    }

    public String getDescriptionColumnString() {
        return descriptionColumnString;
    }

    public void setDescriptionColumnString(String descriptionColumnString) {
        this.descriptionColumnString = descriptionColumnString;
    }

    public int getWeeksColumnInteger() {
        return weeksColumnInteger;
    }

    public void setWeeksColumnInteger(int weeksColumnInteger) {
        this.weeksColumnInteger = weeksColumnInteger;
    }
     public String getProcessComboBox() {
        return processComboBox;
    }

    public void setProcessComboBox(String processComboBox) {
        this.processComboBox = processComboBox;
    }
}

