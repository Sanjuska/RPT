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
public class TableVariants {
    
    //Used during initial testing
    private  String variantsID;
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
    
    
    public TableVariants(){
        this.variantsID = "";
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

    public TableVariants(String plant, String platform, String vehicle,  String propulsion, 
            String denomination, char fuel, String engineFamily, int generation,
            String engineName, String engineCode, float displacement, int enginePower,
            int elMotorPower, int torque, int torqueOverBoost, char gearboxType,
            int gears, String gearbox, String driveline, char transmissionCode, 
            String emissionClass, String startOfProd, String endOfProd) {
        this.variantsID = vehicle + engineCode + transmissionCode + emissionClass + startOfProd;
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

    public String getVariantsID() {
        return variantsID;
    }

    public void setVariantsID(String variantsID) {
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
    
}
