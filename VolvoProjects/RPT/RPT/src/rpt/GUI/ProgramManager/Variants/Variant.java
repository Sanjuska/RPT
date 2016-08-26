/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpt.GUI.ProgramManager.Variants;

/**
 *
 * @author SCOLAK1
 */
public class Variant {
    String plant;
    int plantCode;
    String plantName;
    String vehicle;
    String typeCode;
    String vehicleSOP;
    String vehicleEOP;
    String propulsion;
    String denomination;
    String fuel;
    String engineFamily;
    String generation;
    String engineName;
    String engineCode;
    float displacement;
    int enginePower;
    int elMotorpower;
    int torque;
    int torqueOverboost;
    char gearBoxType;
    int gears;
    String gearbox;
    String driveline;
    char transmissionCode;
    String certGroup;
    String Emiss;
    String startOfProd;
    String endOfProd;
    
    
    public Variant(){
        
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public int getPlantCode() {
        return plantCode;
    }

    public void setPlantCode(int plantCode) {
        this.plantCode = plantCode;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getVehicleSOP() {
        return vehicleSOP;
    }

    public void setVehicleSOP(String vehicleSOP) {
        this.vehicleSOP = vehicleSOP;
    }

    public String getVehicleEOP() {
        return vehicleEOP;
    }

    public void setVehicleEOP(String vehicleEOP) {
        this.vehicleEOP = vehicleEOP;
    }

    public String getPropulsion() {
        return propulsion;
    }

    public void setPropulsion(String propulsion) {
        this.propulsion = propulsion;
    }

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
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

    public String getEngineName() {
        return engineName;
    }

    public void setEngineName(String engineName) {
        this.engineName = engineName;
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

    public int getElMotorpower() {
        return elMotorpower;
    }

    public void setElMotorpower(int elMotorpower) {
        this.elMotorpower = elMotorpower;
    }

    public int getTorque() {
        return torque;
    }

    public void setTorque(int torque) {
        this.torque = torque;
    }

    public int getTorqueOverboost() {
        return torqueOverboost;
    }

    public void setTorqueOverboost(int torqueOverboost) {
        this.torqueOverboost = torqueOverboost;
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

    public String getGearbox() {
        return gearbox;
    }

    public void setGearbox(String gearbox) {
        this.gearbox = gearbox;
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

    public String getCertGroup() {
        return certGroup;
    }

    public void setCertGroup(String certGroup) {
        this.certGroup = certGroup;
    }

    public String getEmiss() {
        return Emiss;
    }

    public void setEmiss(String Emiss) {
        this.Emiss = Emiss;
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
    
    public void setValue(String dataField, Object input){
        switch(dataField){
            case "Engine name":
                setEngineName((String) input);
                break;
            case "Denomination":
                setDenomination((String) input);
                break;
            case "Gearbox":
                setGearbox ((String) input);
                break;
            case "Emiss":
                setEmiss ((String) input);
            default:
                break;
        }
    }
    
}
