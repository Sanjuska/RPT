/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpt.GUI.Utils;

import rpt.GUI.ProgramStrategist.CyclePlans.*;
import javafx.css.SimpleStyleableIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author colak
 */
public class VariantTableRow {

    //Used during initial testing
    private String variantID;
    private String engineName;
    private String denomination;
    private String gearbox;
    private String certGroup;

    //added once base functionality was implemented as base set above was not enough
    private String plant;
    private String vehicle;
    private String platform;
    private String propulsion;
    private String fuel;
    private String engineFamily;
    private String generation;
    private String engineCode;
    private String displacement;
    private String enginePower;
    private String elMotorPower;
    private String torque;
    private String torqueOverBoost;
    private String gearboxType;
    private String gears;
    private String driveline;
    private String transmissionCode;
    private String emissionClass;
    private String startOfProd;
    private String endOfProd;
    private String oldSOP; //Used for setting moved data during cycle plan comparison in CompareDialogController.java
    private String oldEOP; //Used for setting moved data during cycle plan comparison in CompareDialogController.java

    public VariantTableRow() {
        this.variantID = "";
        this.engineName = "";
        this.denomination = "";
        this.gearbox = "";
        this.certGroup = "";
        this.plant = "";
        this.vehicle = "";
        this.platform = "";
        this.propulsion = "";
        this.fuel = "";
        this.engineFamily = "";
        this.generation = "1";
        this.engineCode = "";
        this.displacement = "";
        this.enginePower = "0";
        this.elMotorPower = "0";
        this.torque = "0";
        this.torqueOverBoost = "0";
        this.gearboxType = "";
        this.gears = "0";
        this.driveline = "";
        this.transmissionCode = "";
        this.emissionClass = "";
        this.startOfProd = "";
        this.endOfProd = "";
    }

    public VariantTableRow(String plant, String platform, String vehicle, String propulsion,
            String denomination, String fuel, String engineFamily, String generation,
            String engineName, String engineCode, String displacement, String enginePower,
            String elMotorPower, String torque, String torqueOverBoost, String gearboxType,
            String gears, String gearbox, String driveline, String transmissionCode, String certGroup,
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
        this.gearboxType = gearboxType;
        this.gears = gears;
        this.gearbox = gearbox;
        this.driveline = driveline;
        this.transmissionCode = transmissionCode;
        this.certGroup = certGroup;
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

    public String getCertGroup() {
        return certGroup;
    }

    public void setCertGroup(String certGroup) {
        this.certGroup = certGroup;
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
        //If platform contains the ' character, then the SQLs will be broken. Needs to be replaced with ''
        platform = platform.replaceAll("'", "prim");
        this.platform = platform;
    }

    public String getPropulsion() {
        return propulsion;
    }

    public void setPropulsion(String propulsion) {
        this.propulsion = propulsion;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getEngineFamily() {
        return engineFamily;
    }

    public void setEngineFamily(String engineFamily) {
        this.engineFamily = engineFamily;
    }

    public String getGeneration() {
        return generation;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }

    public String getEngineCode() {
        return engineCode;
    }

    public void setEngineCode(String engineCode) {
        this.engineCode = engineCode;
    }

    public String getDisplacement() {
        return displacement;
    }

    public void setDisplacement(String displacement) {
        this.displacement = displacement;
    }

    public String getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(String enginePower) {
        this.enginePower = enginePower;
    }

    public String getElMotorPower() {
        return elMotorPower;
    }

    public void setElMotorPower(String elMotorPower) {
        this.elMotorPower = elMotorPower;
    }

    public String getTorque() {
        return torque;
    }

    public void setTorque(String torque) {
        this.torque = torque;
    }

    public String getTorqueOverBoost() {
        return torqueOverBoost;
    }

    public void setTorqueOverBoost(String torqueOverBoost) {
        this.torqueOverBoost = torqueOverBoost;
    }

    public String getGearboxType() {
        return gearboxType;
    }

    public void setGearboxType(String gearboxType) {
        this.gearboxType = gearboxType;
    }

    public String getGears() {
        return gears;
    }

    public void setGears(String gears) {
        this.gears = gears;
    }

    public String getDriveline() {
        return driveline;
    }

    public void setDriveline(String driveline) {
        this.driveline = driveline;
    }

    public String getTransmissionCode() {
        return transmissionCode;
    }

    public void setTransmissionCode(String transmissionCode) {
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

    public String getOldEOP() {
        return oldEOP;
    }

    public void setOldEOP(String oldEOP) {
        this.oldEOP = oldEOP;
    }

    public void setValue(String dataField, Object input) {
        Double doubleVal = null;
        String floatVal = null;
        String stringVal = null;

        switch (dataField) {
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
                //setFuel(input.toString().charAt(0));
                setFuel((String) input);
                break;
            case "Engine Family":
                setEngineFamily((String) input);
                break;
            case "VEA4/\n" + "GEP3 Gen":
                //doubleVal = (Double) input;
                //setGeneration(doubleVal.intValue());
                setGeneration((String) input);
                break;
            case "Engine code":
                setEngineCode((String) input);
                break;
            case "Displacement":
                //floatVal = (String) input;
                //setDisplacement (Float.parseFloat(floatVal));
                setDisplacement((String) input);
                break;
            case "Engine power PS":
                //doubleVal = (Double) input;
                //setEnginePower (doubleVal.intValue());
                setEnginePower((String) input);
                break;
            case "EL motor power PS":
                //doubleVal = (Double) input;
                //setElMotorPower (doubleVal.intValue());
                setElMotorPower((String) input);
                break;
            case "Torque":
                //doubleVal = (Double) input;
                //setTorque (doubleVal.intValue());
                setTorque((String) input);
                break;
            case "Torque overboost":
                //doubleVal = (Double) input;
                //setTorqueOverBoost (doubleVal.intValue());
                setTorqueOverBoost((String) input);
                break;
            case "Gearbox type":
                setGearboxType((String) input);
                break;
            case "Gears":
                //doubleVal = (Double) input;
                //stringVal = (String) input;
                //setGears (Integer.valueOf(stringVal));
                setGears((String) input);
                break;
            case "Gearbox":
                setGearbox((String) input);
                break;
            case "Driveline":
                setDriveline((String) input);
                break;
            case "Transm Code":
                //setTransmissionCode(input.toString().charAt(0));
                setTransmissionCode((String) input);
                break;
            case "CertGroup":
                setCertGroup((String) input);
                break;
            case "Emiss":
                setEmissionClass((String) input);
            default:
                break;
            case "Start of prod":
                //convert from YYWW.0 format to YYwWW
                //doubleVal = (Double) input;
                //stringVal = doubleVal.toString();
                stringVal = (String) input;
                setStartOfProd(stringVal.substring(0, 2) + "w" + stringVal.substring(2, 4));
                break;
            case "End of prod":
                //convert from YYWW.0 format to YYwWW
                //doubleVal = (Double) input;
                //stringVal = doubleVal.toString();
                stringVal = (String) input;
                setEndOfProd(stringVal.substring(0, 2) + "w" + stringVal.substring(2, 4));
                break;
        }
    }

    public String getValue(String dataField) {
        Double doubleVal = null;
        String floatVal = null;
        String stringVal = null;

        switch (dataField) {
            case "Plant":
                return plant;
            case "Platform":
                return platform;
            case "Vehicle":
                return vehicle;
            case "Propulsion":
                return propulsion;
            case "Denomination":
                return denomination;
            case "Fuel":
                //return Character.toString(fuel);
                return fuel;
            case "EngineFamily":
                return engineFamily;
            case "Generation":
                return generation;
            case "EngineCode":
                return engineCode;
            case "Displacement":
                return displacement;
            case "EnginePower":
                return enginePower;
            case "ElMotorPower":
                return elMotorPower;
            case "Torque":
                return torque;
            case "TorqueOverBoost":
                return torqueOverBoost;
            case "GearboxType":
                //return Character.toString(gearboxType);
                return gearboxType;
            case "Gears":
                return gears;
            case "Gearbox":
                return gearbox;
            case "Driveline":
                return driveline;
            case "TransmissionCode":
                //return Character.toString(transmissionCode);
                return transmissionCode;
            case "CertGroup":
                return certGroup;
            case "EmissionClass":
                return emissionClass;
            case "StartOfProd":
                return startOfProd;
            case "EndOfProd":
                return endOfProd;
            case "VariantID":
                return variantID;
            default:
                return ""; //in case of no match
        }
    }

}
