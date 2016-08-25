/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpt.GUI.SectionManager.Deliveries;

/**
 *
 * @author colak
 */
public class TableDeliveries {

   
    //Variables
    private String delieveryColumnString;
    private String descriptionColumnString;
    
    //Empty constructor
    public TableDeliveries() {
    }
    
    //Constructor with parameters
    public TableDeliveries(String delieveryColumnString, String descriptionColumnString) {
        this.delieveryColumnString = delieveryColumnString;
        this.descriptionColumnString = descriptionColumnString;
    }
    //Getters and setters
     public String getDelieveryColumnString() {
        return delieveryColumnString;
    }

    public void setDelieveryColumnString(String delieveryColumnString) {
        this.delieveryColumnString = delieveryColumnString;
    }

    public String getDescriptionColumnString() {
        return descriptionColumnString;
    }

    public void setDescriptionColumnString(String descriptionColumnString) {
        this.descriptionColumnString = descriptionColumnString;
    }
    
    
}
