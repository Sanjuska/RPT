/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpt.GUI.ProgramStrategist.CyclePlans;

import javafx.css.SimpleStyleableIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
/**
 *
 * @author colak
 */
public class TableVariant {
    
    //Used during initial testing
    private  String variantID;
    private  String engineName;
    private  String denomination;
    private  String gearbox;
    private  String emissionsCategory;

    //added once base functionality was implemented as base set above was not enough
    private String plant; 
    private String vehicle;
    private String platform;
    private String propulsion;
    private char fuel;
    private String engineFamily;
    private int generation;
    private String engineCode;
    private float displacement;
    private int enginePower;
    private int elMotorPower;
    private int torque;
    private int torqueOverBoost;
    private char gearBoxType;
    private int gears;
    private String driveline;
    private char transmissionCode;
    private String emissionClass;
    private String startOfProd;
    private String endOfProd;
    private String oldSOP; //Used for setting moved data during cycle plan comparison in CompareDialogController.java
    
    
    public TableVariant(){
        this.variantID = "";
        this.engineName = "";
        this.denomination = "";
        this.gearbox = "";
        this.emissionsCategory ="";
        this.plant = ""; 
        this.vehicle = "";
        this.platform = "";
        this.propulsion = "";
        this.fuel = Character.MIN_VALUE;
        this.engineFamily = "";
        this.generation = 1;
        this.engineCode = "";
        this.displacement = 0.0f;
        this.enginePower = 0;
        this.elMotorPower = 0;
        this.torque = 0;
        this.torqueOverBoost = 0;
        this.gearBoxType = Character.MIN_VALUE;
        this.gears = 0;
        this.driveline = "";
        this.transmissionCode = Character.MIN_VALUE;
        this.emissionClass = "";
        this.startOfProd = "";
        this.endOfProd = "";
    }

    public TableVariant(String plant, String platform, String vehicle,  String propulsion, 
            String denomination, char fuel, String engineFamily, int generation,
            String engineName, String engineCode, float displacement, int enginePower,
            int elMotorPower, int torque, int torqueOverBoost, char gearboxType,
            int gears, String gearbox, String driveline, char transmissionCode, 
            String emissionClass, String startOfProd, String endOfProd) {
        this.variantID = vehicle + engineCode + transmissionCode + emissionClass + startOfProd;
        this.plant = plant;
        this.platform = platform;
        this.vehicle = vehicle;
        this.propulsion = propulsion;
        this.denomination = denomination;
        this.fuel = fuel;
        this.engineFamily = engineFamily;
        this.generation = generation;
        this.engineName = engineName;
        this.engineCode = engineCode;
        this.displacement = displacement;
        this.enginePower = enginePower;
        this.elMotorPower = elMotorPower;
        this.torque = torque;
        this.torqueOverBoost = torqueOverBoost;
        this.gearBoxType = gearBoxType;
        this.gears = gears;
        this.gearbox = gearbox;
        this.driveline = driveline;
        this.transmissionCode = transmissionCode;
        this.emissionClass = emissionClass;
        this.startOfProd = startOfProd;
        this.endOfProd = endOfProd;
    }

    public String getVariantID() {
        return variantID;
    }

    public void setVariantID(String variantID) {
        this.variantID = variantID;
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

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getPropulsion() {
        return propulsion;
    }

    public void setPropulsion(String propulsion) {
        this.propulsion = propulsion;
    }

    public char getFuel() {
        return fuel;
    }

    public void setFuel(char fuel) {
        this.fuel = fuel;
    }

    public String getEngineFamily() {
        return engineFamily;
    }

    public void setEngineFamily(String engineFamily) {
        this.engineFamily = engineFamily;
    }

    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public String getEngineCode() {
        return engineCode;
    }

    public void setEngineCode(String engineCode) {
        this.engineCode = engineCode;
    }

    public float getDisplacement() {
        return displacement;
    }

    public void setDisplacement(float displacement) {
        this.displacement = displacement;
    }

    public int getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(int enginePower) {
        this.enginePower = enginePower;
    }

    public int getElMotorPower() {
        return elMotorPower;
    }

    public void setElMotorPower(int elMotorPower) {
        this.elMotorPower = elMotorPower;
    }

    public int getTorque() {
        return torque;
    }

    public void setTorque(int torque) {
        this.torque = torque;
    }

    public int getTorqueOverBoost() {
        return torqueOverBoost;
    }

    public void setTorqueOverBoost(int torqueOverBoost) {
        this.torqueOverBoost = torqueOverBoost;
    }

    public char getGearBoxType() {
        return gearBoxType;
    }

    public void setGearBoxType(char gearBoxType) {
        this.gearBoxType = gearBoxType;
    }

    public int getGears() {
        return gears;
    }

    public void setGears(int gears) {
        this.gears = gears;
    }

    public String getDriveline() {
        return driveline;
    }

    public void setDriveline(String driveline) {
        this.driveline = driveline;
    }

    public char getTransmissionCode() {
        return transmissionCode;
    }

    public void setTransmissionCode(char transmissionCode) {
        this.transmissionCode = transmissionCode;
    }

    public String getEmissionClass() {
        return emissionClass;
    }

    public void setEmissionClass(String emissionClass) {
        this.emissionClass = emissionClass;
    }

    public String getStartOfProd() {
        return startOfProd;
    }

    public void setStartOfProd(String startOfProd) {
        this.startOfProd = startOfProd;
    }

    public String getEndOfProd() {
        return endOfProd;
    }

    public void setEndOfProd(String endOfProd) {
        this.endOfProd = endOfProd;
    }

    public String getOldSOP() {
        return oldSOP;
    }

    public void setOldSOP(String oldSOP) {
        this.oldSOP = oldSOP;
    }
    
    public void setValue(String dataField, Object input){
        Double doubleVal = null;
        String floatVal = null;
        String stringVal = null;
        
        switch(dataField){
            case "Plant":
                setPlant((String) input);
                break;
            case "Platform":
                setPlatform((String) input);
                break;
            case "Vehicle":
                setVehicle((String) input);
                break;
            case "Propulsion":
                setPropulsion((String) input);
                break;
            case "Denomination":
                setDenomination((String) input);
                break;
            case "Fuel":
                setFuel(input.toString().charAt(0));
                break;
            case "Engine Family":
                setEngineFamily((String) input);
                break;
            case "VEA4/\n" + "GEP3 Gen":
                doubleVal = (Double) input;
                setGeneration(doubleVal.intValue());
                break;
            case "Engine code":
                setEngineCode((String) input);
                break;
            case "Displacement":
                floatVal = (String) input;
                setDisplacement (Float.parseFloat(floatVal));
                break;
            case "Engine power PS":
                doubleVal = (Double) input;
                setEnginePower (doubleVal.intValue());
                break;
            case "EL motor power PS":
                doubleVal = (Double) input;
                setElMotorPower (doubleVal.intValue());
                break;
            case "Torque":
                doubleVal = (Double) input;
                setTorque (doubleVal.intValue());
                break;
            case "Torque overboost":
                doubleVal = (Double) input;
                setTorqueOverBoost (doubleVal.intValue());
                break;
            case "Gearbox type":
                setGearBoxType (input.toString().charAt(0));
                break;
            case "Gears":
                //doubleVal = (Double) input;
                stringVal = (String) input;
                setGears (Integer.valueOf(stringVal));
                break;
            case "Gearbox":
                setGearbox ((String) input);
                break;
            case "Driveline":
                setDriveline((String) input);
                break;
            case "Transm Code":
                setTransmissionCode (input.toString().charAt(0));
                break;
            case "Emiss":
                setEmissionClass ((String) input);
            default:
                break;
            case "Start of prod":
                //convert from YYWW.0 format to YYwWW
                doubleVal = (Double) input;
                stringVal = doubleVal.toString();
                setStartOfProd (stringVal.substring(0, 2) + "w" + stringVal.substring(2, 4));
                break;
            case "End of prod":
                 //convert from YYWW.0 format to YYwWW
                doubleVal = (Double) input;
                stringVal = doubleVal.toString();
                setEndOfProd (stringVal.substring(0, 2) + "w" + stringVal.substring(2, 4));
                break;
        }
    }
    
}
