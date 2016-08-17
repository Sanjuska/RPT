/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpt.GUI.GroupManager.Roles;

/**
 *
 * @author colak
 */
public class TableRoles {

    
    
   //initialize all variables in Table Views
    private String rolesNamColumn;
    
   //Constructor with no parameters
    public TableRoles(){
        this.rolesNamColumn = "";
    }
   //Constructor with parameters
    public TableRoles(String rolesNamColumn) {
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
