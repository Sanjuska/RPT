/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpt.GUI.Manager.Roles;

/**
 *
 * @author colak
 */
public class TableRoles {

    
    
   //initialize all variables in Table Views
    private String rolesNameColumn;
    
   //Constructor with no parameters
    public TableRoles(){
        this.rolesNameColumn = "";
    }
   //Constructor with parameters
    public TableRoles(String rolesNameColumn) {
        this.rolesNameColumn = rolesNameColumn;
    }
    
    //Getters and setters
    public String getRolesNameColumn() {
        return rolesNameColumn;
    }

    public void setRolesNameColumn(String rolesNameColumn) {
        this.rolesNameColumn = rolesNameColumn;
    } 
    
    
}
