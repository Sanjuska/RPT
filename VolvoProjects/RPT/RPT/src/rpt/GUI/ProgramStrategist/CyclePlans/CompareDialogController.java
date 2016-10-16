/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpt.GUI.ProgramStrategist.CyclePlans;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import rpt.RPT;

/**
 * FXML Controller class
 *
 * @author Sime & Sanja Colak
 */
public class CompareDialogController implements Initializable {

    @FXML
    private ComboBox cyclePlanSelector;
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;

    public static ArrayList<String> majorChanges;

    public void okButtonClicked() throws IOException {
        // create two new maps, one for currentCycleplan and one for the comparison plan
        Map<String, TableVariant> currentCyclePlan = new HashMap<String, TableVariant>();
        Map<String, TableVariant> oldCyclePlan = new HashMap<String, TableVariant>();
        Map<String, TableVariant> movedVariants = new HashMap<String, TableVariant>();
        Map<String, TableVariant> changedVariants = new HashMap<String, TableVariant>();
        Map<String, ArrayList<String>> changedInfo = new HashMap<String, ArrayList<String>>();

        Statement statement;
        try {
            // Set current YYwWW and use to ignore variants that are no longer in production
            Calendar cal = Calendar.getInstance();
            String currentWeek = cal.get(Calendar.YEAR) % 100 + "w" + cal.get(Calendar.WEEK_OF_YEAR);

            // Extract all variants in the current cycleplan and put them in an map
            System.out.println("Extracting current variants");
            statement = RPT.conn.createStatement();
            statement.setQueryTimeout(30);
            String query = "SELECT * FROM VARIANTS, VariantBelongsToCyclePlan WHERE "
                    + "VariantBelongsToCyclePlan.CyclePlanID= \'" + CyclePlansController.selectedCyclePlan + "\' "
                    + "AND VARIANTS.VariantID = VariantBelongsToCyclePlan.VariantID "
                    + "AND EndOfProd > '" + currentWeek + "'";
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                TableVariant entry = new TableVariant(rs.getString("Plant"),
                        rs.getString("Platform"), rs.getString("Vehicle"), rs.getString("Propulsion"),
                        rs.getString("Denomination"), rs.getString("Fuel").charAt(0), rs.getString("EngineFamily"), rs.getString("Generation"),
                        "EngineName not used", rs.getString("EngineCode"), rs.getString("Displacement"), rs.getString("EnginePower"),
                        rs.getString("ElMotorPower"), rs.getString("Torque"), rs.getString("TorqueOverBoost"), rs.getString("GearboxType").charAt(0),
                        rs.getString("Gears"), rs.getString("Gearbox"), rs.getString("Driveline"), rs.getString("TransmissionCode").charAt(0),
                        rs.getString("EmissionClass"), rs.getString("StartOfProd"), rs.getString("EndOfProd"));
                currentCyclePlan.put(entry.getVariantID(), entry);
            }
            //Now extract all variants in the cycleplan to compare with
            System.out.println("Extracting comparison variants");
            query = "SELECT * FROM VARIANTS, VariantBelongsToCyclePlan WHERE "
                    + "VariantBelongsToCyclePlan.CyclePlanID= \'" + cyclePlanSelector.getSelectionModel().getSelectedItem().toString() + "\' "
                    + "AND VARIANTS.VariantID = VariantBelongsToCyclePlan.VariantID "
                    + "AND EndOfProd > '" + currentWeek + "'";
            rs = statement.executeQuery(query);

            while (rs.next()) {
                TableVariant entry = new TableVariant(rs.getString("Plant"),
                        rs.getString("Platform"), rs.getString("Vehicle"), rs.getString("Propulsion"),
                        rs.getString("Denomination"), rs.getString("Fuel").charAt(0), rs.getString("EngineFamily"), rs.getString("Generation"),
                        "EngineName not used", rs.getString("EngineCode"), rs.getString("Displacement"), rs.getString("EnginePower"),
                        rs.getString("ElMotorPower"), rs.getString("Torque"), rs.getString("TorqueOverBoost"), rs.getString("GearboxType").charAt(0),
                        rs.getString("Gears"), rs.getString("Gearbox"), rs.getString("Driveline"), rs.getString("TransmissionCode").charAt(0),
                        rs.getString("EmissionClass"), rs.getString("StartOfProd"), rs.getString("EndOfProd"));
                oldCyclePlan.put(entry.getVariantID(), entry);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        //for each variant in current plan, remove from both if it exists in old
        for (Iterator<Map.Entry<String, TableVariant>> entries = currentCyclePlan.entrySet().iterator(); entries.hasNext();) {
            Map.Entry<String, TableVariant> entry = entries.next();
            if (oldCyclePlan.containsKey(entry.getKey())) {
                entries.remove(); // remove from currentCyclePlan
                oldCyclePlan.remove(entry.getKey());
            }
        }

        // Now need to check if some entries were only moved in time
        for (Iterator<Map.Entry<String, TableVariant>> entries = currentCyclePlan.entrySet().iterator(); entries.hasNext();) {
            Map.Entry<String, TableVariant> entry = entries.next();
            try {
                statement = RPT.conn.createStatement();
                statement.setQueryTimeout(30);
                //TODO
                //Add all columns except Star of production, as all will be important to find it correctly
                String query = "SELECT VARIANTS.VariantID, VARIANTS.StartOfProd, VARIANTS.EndOfProd FROM VARIANTS, VariantBelongsToCyclePlan WHERE "
                        + "VARIANTS.VariantID = VariantBelongsToCyclePlan.VariantID AND "
                        + "VariantBelongsToCyclePlan.CyclePlanID= \'" + cyclePlanSelector.getSelectionModel().getSelectedItem().toString() + "\' AND "
                        + "VARIANTS.Plant = \'" + entry.getValue().getPlant() + "\' AND "
                        + "VARIANTS.Platform = \'" + entry.getValue().getPlatform() + "\' AND "
                        + "VARIANTS.Vehicle = \'" + entry.getValue().getVehicle() + "\' AND "
                        + "VARIANTS.Propulsion = \'" + entry.getValue().getPropulsion() + "\' AND "
                        + "VARIANTS.Denomination = \'" + entry.getValue().getDenomination() + "\' AND "
                        + "VARIANTS.Fuel = \'" + entry.getValue().getFuel() + "\' AND "
                        + "VARIANTS.EngineFamily = \'" + entry.getValue().getEngineFamily() + "\' AND "
                        + "VARIANTS.Generation = \'" + entry.getValue().getGeneration() + "\' AND "
                        + "VARIANTS.EngineCode = \'" + entry.getValue().getEngineCode() + "\' AND "
                        + "VARIANTS.Displacement = \'" + entry.getValue().getDisplacement() + "\' AND "
                        + "VARIANTS.EnginePower = \'" + entry.getValue().getEnginePower() + "\' AND "
                        + "VARIANTS.ElMotorPower = \'" + entry.getValue().getElMotorPower() + "\' AND "
                        + "VARIANTS.TorqueOverBoost = \'" + entry.getValue().getTorqueOverBoost() + "\' AND "
                        + "VARIANTS.GearboxType = \'" + entry.getValue().getGearBoxType() + "\' AND "
                        + "VARIANTS.Gears = \'" + entry.getValue().getGears() + "\' AND "
                        + "VARIANTS.Gearbox = \'" + entry.getValue().getGearbox() + "\' AND "
                        + "VARIANTS.Driveline = \'" + entry.getValue().getDriveline() + "\' AND "
                        + "VARIANTS.TransmissionCode = \'" + entry.getValue().getTransmissionCode() + "\' AND "
                        //+ "VARIANTS.CertGroup = \'" + entry.getValue().getCertGroup() + "\' AND " // ignore this, not really used
                        + "VARIANTS.EmissionClass = \'" + entry.getValue().getEmissionClass() + "\'";
                ResultSet rs = statement.executeQuery(query);
                if (rs.next()) {
                    entry.getValue().setOldSOP(rs.getString("StartOfProd"));
                    entry.getValue().setOldEOP(rs.getString("EndOfProd"));
                    movedVariants.put(entry.getKey(), entry.getValue()); //Save variant to moved map
                    entries.remove(); //remove variant from current map
                    oldCyclePlan.remove(rs.getString("VariantID")); //remove variant from old map
                }

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        // Now check for variants that have been slightly changed only.
        // Show a dialog window allowing the user to define what a minor change is
        majorChanges = new ArrayList();
        Stage stage;
        Parent root;
        stage = new Stage();
        root = FXMLLoader.load(getClass().getResource("/rpt/GUI/ProgramStrategist/CyclePlans/dialogDefineChanged.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Set change dfinition");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait(); // pause until the user has selected minor changes

        // Now loop through the remaining Added items and check if they are to be moved to "Modified"
        for (String s : majorChanges) {
            System.out.println(s);
        }
        for (Iterator<Map.Entry<String, TableVariant>> entries = currentCyclePlan.entrySet().iterator(); entries.hasNext();) {
            Map.Entry<String, TableVariant> entry = entries.next();
            try {
                statement = RPT.conn.createStatement();
                statement.setQueryTimeout(30);
                String query = "SELECT VARIANTS.VariantID FROM VARIANTS, VariantBelongsToCyclePlan WHERE "
                        + "VARIANTS.VariantID = VariantBelongsToCyclePlan.VariantID AND "
                        + "VariantBelongsToCyclePlan.CyclePlanID= \'" + cyclePlanSelector.getSelectionModel().getSelectedItem().toString() + "\'";

                for (String s : majorChanges) {
                    query = query + " AND VARIANTS." + s + " = \'" + entry.getValue().getValue(s) + "\'";
                }
                ResultSet rs = statement.executeQuery(query);
                if (rs.next()) {
                    changedVariants.put(entry.getKey(), entry.getValue()); //Save variant to moved map
                    entries.remove(); //remove variant from current map
                    oldCyclePlan.remove(rs.getString("VariantID")); //remove variant from old map
                    ArrayList infoArray = new ArrayList();
                    changedInfo.put(entry.getKey(), infoArray);
                }

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        // Open file selector and let user specify report file
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Information");

        //turn off gridlines
        sheet.setDisplayGridlines(false);
        sheet.setPrintGridlines(false);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);
        PrintSetup printSetup = sheet.getPrintSetup();

        //the following three statements are required only for HSSF
        sheet.setAutobreaks(true);
        printSetup.setFitHeight((short) 1);
        printSetup.setFitWidth((short) 1);

        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("Baseline Cycle plan");
        cell = row.createCell(1);
        cell.setCellValue(CyclePlansController.selectedCyclePlan);

        row = sheet.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue("Comparison Cycle plan");
        cell = row.createCell(1);
        cell.setCellValue(cyclePlanSelector.getSelectionModel().getSelectedItem().toString());

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);

        // Write Added variant information
        sheet = workbook.createSheet("Added");
        //freeze the first row
        sheet.createFreezePane(0, 1);
        row = sheet.createRow(0);
        writeHeaders(workbook, row, false);
        int rowNum = 1;
        int amountOfColumns = 0;
        // loop through added
        for (Iterator<Map.Entry<String, TableVariant>> entries = currentCyclePlan.entrySet().iterator(); entries.hasNext();) {
            Map.Entry<String, TableVariant> entry = entries.next();
            row = sheet.createRow(rowNum);
            amountOfColumns = writeRow(row, entry.getValue(), false, false);

            rowNum++;
        }
        //autosize all columns
        for (int i = 0; i < amountOfColumns; i++) {
            sheet.autoSizeColumn(i);
        }
        amountOfColumns = 0;

        // Write Removed variant information
        sheet = workbook.createSheet("Removed");
        //freeze the first row
        sheet.createFreezePane(0, 1);
        row = sheet.createRow(0);
        writeHeaders(workbook, row, false);
        rowNum = 1;
        // loop through removed
        for (Iterator<Map.Entry<String, TableVariant>> entries = oldCyclePlan.entrySet().iterator(); entries.hasNext();) {
            Map.Entry<String, TableVariant> entry = entries.next();
            row = sheet.createRow(rowNum);
            writeRow(row, entry.getValue(), false, false);

            rowNum++;
        }

        // Write Changed variant information
        sheet = workbook.createSheet("Changed");
        //freeze the first row
        sheet.createFreezePane(0, 1);
        row = sheet.createRow(0);
        writeHeaders(workbook, row, false);
        rowNum = 1;
        // loop through changed
        for (Iterator<Map.Entry<String, TableVariant>> entries = changedVariants.entrySet().iterator(); entries.hasNext();) {
            Map.Entry<String, TableVariant> entry = entries.next();
            row = sheet.createRow(rowNum);
            writeRow(row, entry.getValue(), true, false);

            rowNum++;
        }

        // Write Moved variant information
        sheet = workbook.createSheet("Moved");
        //freeze the first row
        sheet.createFreezePane(0, 1);
        row = sheet.createRow(0);
        writeHeaders(workbook, row, true);
        rowNum = 1;
        for (Iterator<Map.Entry<String, TableVariant>> entries = movedVariants.entrySet().iterator(); entries.hasNext();) {
            Map.Entry<String, TableVariant> entry = entries.next();
            row = sheet.createRow(rowNum);
            writeRow(row, entry.getValue(), false, true);

            rowNum++;

        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Comparison Result File");

        File selectedFile = fileChooser.showSaveDialog(null);

        if (selectedFile != null) {
            try {
                FileOutputStream out = new FileOutputStream(selectedFile);
                workbook.write(out);
                out.close();
                System.out.println("Excel written successfully..");

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        closeDialog();
    }

    public void cancelButtonClicked() {
        closeDialog(); // Just close it, do nothing
    }

    public void cyclePlanSelected() {
        okButton.setDisable(false);
    }

    private void closeDialog() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private void writeHeaders(Workbook wb, Row row, Boolean addOldSOP) {
        Cell cell = row.createCell(0);
        CellStyle style = wb.createCellStyle();
        Font headerFont = wb.createFont();
        headerFont.setBold(true);
        style.setFont(headerFont);

        cell.setCellStyle(style);
        cell.setCellValue("Plant");

        cell = row.createCell(1);
        cell.setCellStyle(style);
        cell.setCellValue("Platform");

        cell = row.createCell(2);
        cell.setCellStyle(style);
        cell.setCellValue("Vehicle");

        cell = row.createCell(3);
        cell.setCellStyle(style);
        cell.setCellValue("Propulsion");

        cell = row.createCell(4);
        cell.setCellStyle(style);
        cell.setCellValue("Denomination");

        cell = row.createCell(5);
        cell.setCellStyle(style);
        cell.setCellValue("Fuel");

        cell = row.createCell(6);
        cell.setCellStyle(style);
        cell.setCellValue("Engine Family");

        cell = row.createCell(7);
        cell.setCellStyle(style);
        cell.setCellValue("Generation");

        cell = row.createCell(8);
        cell.setCellStyle(style);
        cell.setCellValue("Engine Code");

        cell = row.createCell(9);
        cell.setCellStyle(style);
        cell.setCellValue("Displacement");

        cell = row.createCell(10);
        cell.setCellStyle(style);
        cell.setCellValue("Engine power (PS)");

        cell = row.createCell(11);
        cell.setCellStyle(style);
        cell.setCellValue("Electric motor power (PS)");

        cell = row.createCell(12);
        cell.setCellStyle(style);
        cell.setCellValue("Torque");

        cell = row.createCell(13);
        cell.setCellStyle(style);
        cell.setCellValue("Torque overboost");

        cell = row.createCell(14);
        cell.setCellStyle(style);
        cell.setCellValue("Gearbox Type");

        cell = row.createCell(15);
        cell.setCellStyle(style);
        cell.setCellValue("Gears");

        cell = row.createCell(16);
        cell.setCellStyle(style);
        cell.setCellValue("Gearbox");

        cell = row.createCell(17);
        cell.setCellStyle(style);
        cell.setCellValue("Driveline");

        cell = row.createCell(18);
        cell.setCellStyle(style);
        cell.setCellValue("Transmission Code");

        cell = row.createCell(19);
        cell.setCellStyle(style);
        cell.setCellValue("Emission Class");

        cell = row.createCell(20);
        cell.setCellStyle(style);
        cell.setCellValue("SOP");

        cell = row.createCell(21);
        cell.setCellStyle(style);
        cell.setCellValue("EOP");

        if (addOldSOP) {
            cell = row.createCell(22);
            cell.setCellStyle(style);
            cell.setCellValue("Old SOP");
        }

        if (addOldSOP) { //Same boolean flag
            cell = row.createCell(23);
            cell.setCellStyle(style);
            cell.setCellValue("Old EOP");
        }
    }

    private int writeRow(Row row, TableVariant variant, Boolean colorChanges, Boolean addOldSOP) {
        //headerFont.setColor(IndexedColors.RED.getIndex());
        int cols = 0;

        Cell cell = row.createCell(0);
        cell.setCellValue(variant.getPlant());

        cell = row.createCell(1);
        cell.setCellValue(variant.getPlatform());

        cell = row.createCell(2);
        cell.setCellValue(variant.getVehicle());

        cell = row.createCell(3);
        cell.setCellValue(variant.getPropulsion());

        cell = row.createCell(4);
        cell.setCellValue(variant.getDenomination());

        cell = row.createCell(5);
        cell.setCellValue(Character.toString(variant.getFuel()));

        cell = row.createCell(6);
        cell.setCellValue(variant.getEngineFamily());

        cell = row.createCell(7);
        cell.setCellValue(variant.getGeneration());

        cell = row.createCell(8);
        cell.setCellValue(variant.getEngineCode());

        cell = row.createCell(9);
        cell.setCellValue(variant.getDisplacement());

        cell = row.createCell(10);
        cell.setCellValue(variant.getEnginePower());

        cell = row.createCell(11);
        cell.setCellValue(variant.getElMotorPower());

        cell = row.createCell(12);
        cell.setCellValue(variant.getTorque());

        cell = row.createCell(13);
        cell.setCellValue(variant.getTorqueOverBoost());

        cell = row.createCell(14);
        cell.setCellValue(Character.toString(variant.getGearBoxType()));

        cell = row.createCell(15);
        cell.setCellValue(variant.getGears());

        cell = row.createCell(16);
        cell.setCellValue(variant.getGearbox());

        cell = row.createCell(17);
        cell.setCellValue(variant.getDriveline());

        cell = row.createCell(18);
        cell.setCellValue(Character.toString(variant.getTransmissionCode()));

        cell = row.createCell(19);
        cell.setCellValue(variant.getEmissionClass());

        cell = row.createCell(20);
        cell.setCellValue(variant.getStartOfProd());

        cell = row.createCell(21);
        cell.setCellValue(variant.getEndOfProd());
        cols = 22;

        if (addOldSOP) {
            cell = row.createCell(22);
            cell.setCellValue(variant.getOldSOP());
            cols++;
        }

        if (addOldSOP) {
            cell = row.createCell(23);
            cell.setCellValue(variant.getOldEOP());
            cols++;
        }

        return cols;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cyclePlanSelector.setItems(CyclePlansController.cyclePlanList);
    }

}
