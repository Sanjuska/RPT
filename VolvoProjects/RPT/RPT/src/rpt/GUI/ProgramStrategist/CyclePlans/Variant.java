/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpt.GUI.ProgramStrategist.CyclePlans;

/**
 *
 * @author SCOLAK1
 */
public class Variant {
    String plant;
    int plantCode;
    String plantName;
    String platform;
    String vehicle;
    String typeCode;
    String vehicleSOP;
    String vehicleEOP;
    String propulsion;
    String denomination;
    char fuel;
    String engineFamily;
    String generation;
    String engineName;
    String engineCode;
    float displacement;
    String enginePower;
    String elMotorPower;
    String torque;
    String torqueOverBoost;
    char gearBoxType;
    String gears;
    String gearbox;
    String driveline;
    char transmissionCode;
    String certGroup;
    String emissionClass;
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

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
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

    public char getGearBoxType() {
        return gearBoxType;
    }

    public void setGearBoxType(char gearBoxType) {
        this.gearBoxType = gearBoxType;
    }

    public String getGears() {
        return gears;
    }

    public void setGears(String gears) {
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
                //doubleVal = (Double) input;
                //setGeneration(doubleVal.intValue());
                setGeneration((String) input);
                break;
            case "Engine code":
                setEngineCode((String) input);
                break;
            case "Displacement":
                floatVal = (String) input;
                setDisplacement (Float.parseFloat(floatVal));
                break;
            case "Engine power PS":
                //doubleVal = (Double) input;
                //setEnginePower (doubleVal.intValue());
                setEnginePower ((String) input);
                break;
            case "EL motor power PS":
                //doubleVal = (Double) input;
                //setElMotorPower (doubleVal.intValue());
                setElMotorPower ((String) input);
            case "Torque":
               //doubleVal = (Double) input;
                //setTorque (doubleVal.intValue());
                setTorque ((String) input);
                break;
            case "Torque overboost":
                //doubleVal = (Double) input;
                //setTorqueOverBoost (doubleVal.intValue());
                setTorqueOverBoost ((String) input);
                break;
            case "Gearbox type":
                setGearBoxType (input.toString().charAt(0));
                break;
            case "Gears":
                //doubleVal = (Double) input;
                //stringVal = (String) input;
                //setGears (Integer.valueOf(stringVal));
                setGears ((String) input);
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
            case "CertGroup":
                setCertGroup ((String) input);
                break;
            case "Emiss":
                setEmissionClass ((String) input);
            default:
                break;
            case "Start of prod":
                //convert from YYWW.0 format to YYwWW
                //doubleVal = (Double) input;
                //stringVal = doubleVal.toString();
                stringVal = (String) input;
                setStartOfProd (stringVal.substring(0, 2) + "w" + stringVal.substring(2, 4));
                break;
            case "End of prod":
                //convert from YYWW.0 format to YYwWW
                //doubleVal = (Double) input;
                //stringVal = doubleVal.toString();
                stringVal = (String) input;
                setEndOfProd (stringVal.substring(0, 2) + "w" + stringVal.substring(2, 4));
                break;
        }
    }
    
}
