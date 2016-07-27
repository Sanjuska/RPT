/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpt.GUI.GroupManager;

/**
 *
 * @author colak
 */
public class RolesTable {

    
    
   //initialize all variables in Table Views
    private String rolesNamColumn;
    
   //Constructor with no parameters
    public RolesTable(){
        this.rolesNamColumn = "";
    }
   //Constructor with parameters
    public RolesTable(String rolesNamColumn) {
        this.rolesNamColumn = rolesNamColumn;
    }
    
    //Getters and setters
    public String getRolesNamColumn() {
        return rolesNamColumn;
    }

    public void setRolesNamColumn(String rolesNamColumn) {
        this.rolesNamColumn = rolesNamColumn;
    } 
    
    
}
