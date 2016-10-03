/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpt.GUI.ProgramStrategist;

import javafx.css.SimpleStyleableIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
/**
 *
 * @author colak
 */
public class TableVariants {
    
    private  int variantsID;
    private  String engineName;
    private  String denomination;
    private  String gearbox;
    private  String emissionsCategory;
    
    public TableVariants(){
        this.variantsID = 1;
        this.engineName = "";
        this.denomination = "";
        this.gearbox = "";
        this.emissionsCategory ="";
        
    }

    public TableVariants(int variantsID, String engineName, String denomination, String gearbox, String emissionsCategory) {
        this.variantsID = variantsID;
        this.engineName = engineName;
        this.denomination = denomination;
        this.gearbox = gearbox;
        this.emissionsCategory = emissionsCategory;
    }

    public int getVariantsID() {
        return variantsID;
    }

    public void setVariantsID(int variantsID) {
        this.variantsID = variantsID;
    }

    public String getEngineName() {
        return engineName;
    }

    public void setEngineName(String engineName) {
        this.engineName = engineName;
    }

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getGearbox() {
        return gearbox;
    }

    public void setGearbox(String gearbox) {
        this.gearbox = gearbox;
    }

    public String getEmissionsCategory() {
        return emissionsCategory;
    }

    public void setEmissionsCategory(String emissionsCategory) {
        this.emissionsCategory = emissionsCategory;
    }
    
        
    
    
    
//    private final SimpleIntegerProperty variantsID;
//    private final SimpleStringProperty engineName;
//    private final SimpleStringProperty denomination;
//    private final SimpleStringProperty gearbox;
//    private final SimpleStringProperty emissionCategory;
//    
//    public TableVariants (int variantsID, String engineName, String denomination, String gearbox, String emissionCategory){
//        this.variantsID = new SimpleIntegerProperty(variantsID);
//        this.engineName = new SimpleStringProperty(engineName);
//        this.denomination = new SimpleStringProperty(denomination);
//        this.gearbox = new SimpleStringProperty(gearbox);
//        this.emissionCategory = new SimpleStringProperty(emissionCategory);
//    }
//    //getters and setters
//    public Integer getVariantsID(){
//        return variantsID.get();
//    }
//    public void setVariantID(Integer v){
//        variantsID.set(v);
//    }
//    public String getEngineName(){
//        return engineName.get();
//    }
//    public void setEngineName(String v){
//        engineName.set(v);
//    }
//    public String getDenomination(){
//        return denomination.get();
//    }
//    public void setGearbox(String v){
//        gearbox.set(v);
//    }
//     public String getGearbox(){
//        return gearbox.get(); 
//     }
//    public String getEmissionCategory(){
//        return emissionCategory.get();
//    }
//    public void setEmisionsCategory(String v){
//        emissionCategory.set(v);
//}

    
}
