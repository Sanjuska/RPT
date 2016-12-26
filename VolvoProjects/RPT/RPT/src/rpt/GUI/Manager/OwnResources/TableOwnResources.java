/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpt.GUI.Manager.OwnResources;
/**
 *
 * @author colak
 */
public class TableOwnResources {

    
    
   //initialize all variables in Table Views
    private String nameColumn;
    private String resourcesType;
    //private String monthA,monthC,monthD;  
    //private int monthB;
    private int[] month;
    
   //Constructor with no parameters
    public TableOwnResources(){
        this.nameColumn = "";
        this.resourcesType = "";
        //this.monthA = "";
        //this.monthB = 0;
        //this.monthC = "";
        //this.monthD ="";
        this.month = null; //this is intentional and hasn't been left out
    }
   //Constructor with parameters
    //public TableOwnResources(String nameColumn, String resourcesType, String monthA, int monthB, String monthC, String monthD){
    public TableOwnResources(String nameColumn, String resourcesType, int [] month){
        this.nameColumn = nameColumn;
        this.resourcesType = resourcesType;
        //this.monthA = monthA;
        //this.monthB = monthB;
        //this.monthC = monthC;
        //this.monthD = monthD;
        this.month = month;
    }
    
    //Getters and setters
    public String getNameColumn() {
        return nameColumn;
    }

    public void setNameColumn(String nameColumn) {
        this.nameColumn = nameColumn;
    }

    public String getResourcesType() {
        return resourcesType;
    }

    public void setResourcesType(String resourcesType) {
        this.resourcesType = resourcesType;
    }

    //public String getMonthA() {
    //    return monthA;
    //}

    //public void setMonthA(String monthA) {
    //    this.monthA = monthA;
    //}

    //public int getMonthB() {
    //    return monthB;
    //}

    //public void setMonthB(int monthB) {
    //    this.monthB = monthB;
    //}

    //public String getMonthC() {
    //    return monthC;
    //}

    //public void setMonthC(String monthC) {
    //    this.monthC = monthC;
    //}

    //public String getMonthD() {
    //    return monthD;
    //}

    //public void setMonthD(String monthD) {
    //    this.monthD = monthD;
    //}
    public int [] getMonth(){
        return month;
    }
    public void setMonth(int [] month){
        this.month = month;
    }
}
