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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
        Map<String, Map<String, String>> changedInfo = new HashMap<String, Map<String, String>>();
        Map<String, String> diffValues = new HashMap<String, String>();

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
                        rs.getString("Denomination"), rs.getString("Fuel"), rs.getString("EngineFamily"), rs.getString("Generation"),
                        "EngineName not used", rs.getString("EngineCode"), rs.getString("Displacement"), rs.getString("EnginePower"),
                        rs.getString("ElMotorPower"), rs.getString("Torque"), rs.getString("TorqueOverBoost"), rs.getString("GearboxType"),
                        rs.getString("Gears"), rs.getString("Gearbox"), rs.getString("Driveline"), rs.getString("TransmissionCode"),
                        rs.getString("CertGroup"), rs.getString("EmissionClass"), rs.getString("StartOfProd"), rs.getString("EndOfProd"));
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
                        rs.getString("Denomination"), rs.getString("Fuel"), rs.getString("EngineFamily"), rs.getString("Generation"),
                        "EngineName not used", rs.getString("EngineCode"), rs.getString("Displacement"), rs.getString("EnginePower"),
                        rs.getString("ElMotorPower"), rs.getString("Torque"), rs.getString("TorqueOverBoost"), rs.getString("GearboxType"),
                        rs.getString("Gears"), rs.getString("Gearbox"), rs.getString("Driveline"), rs.getString("TransmissionCode"),
                        rs.getString("CertGroup"), rs.getString("EmissionClass"), rs.getString("StartOfProd"), rs.getString("EndOfProd"));
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
                //Add all columns except Start of production, as all will be important to find it correctly
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
                        + "VARIANTS.GearboxType = \'" + entry.getValue().getGearboxType() + "\' AND "
                        + "VARIANTS.Gears = \'" + entry.getValue().getGears() + "\' AND "
                        + "VARIANTS.Gearbox = \'" + entry.getValue().getGearbox() + "\' AND "
                        + "VARIANTS.Driveline = \'" + entry.getValue().getDriveline() + "\' AND "
                        + "VARIANTS.TransmissionCode = \'" + entry.getValue().getTransmissionCode() + "\' AND "
                        + "VARIANTS.CertGroup = \'" + entry.getValue().getCertGroup() + "\' AND " // may remove once
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
        stage.setTitle("Set change definition");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait(); // pause until the user has selected minor changes

        // Now loop through the remaining Added items and check if they are to be moved to "Modified"
        //for (String s : majorChanges) {
        //    System.out.println(s);
        //}
        // Create string for extracting data which has been judged as minor
        //String dataString = ""; // Data which will be used for difference check
        //for (String s : majorChanges) {
        //    dataString = dataString + ", VARIANTS." + s;
        //}
        // Build list of parameters to extract and compare with the new variant
        ArrayList<String> infoArray = new ArrayList();
        String query = "PRAGMA table_info(VARIANTS)"; //Get all column names
        String extractionData = "";
        try {
            statement = RPT.conn.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rsColumns = statement.executeQuery(query);
            //traverser through list of columns and add those not pointed out as MAJOR
            boolean first = true;
            while (rsColumns.next()) {
                if (!(majorChanges.contains(rsColumns.getString("name")))) {
                    infoArray.add(rsColumns.getString("name"));
                    if (first) {
                        extractionData = extractionData + "VARIANTS." + rsColumns.getString("name");
                        first = false;
                    } else {
                        extractionData = extractionData + ", VARIANTS." + rsColumns.getString("name");
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("CompareDialogController error when building extraction data: " + e.getMessage());
        }

        for (Iterator<Map.Entry<String, TableVariant>> entries = currentCyclePlan.entrySet().iterator(); entries.hasNext();) {
            Map.Entry<String, TableVariant> entry = entries.next();
            try {
                statement = RPT.conn.createStatement();
                statement.setQueryTimeout(30);
                query = "SELECT ";
                query = query + extractionData;
                query = query + " FROM VARIANTS, VariantBelongsToCyclePlan WHERE "
                        + "VARIANTS.VariantID = VariantBelongsToCyclePlan.VariantID AND "
                        + "VariantBelongsToCyclePlan.CyclePlanID= \'" + cyclePlanSelector.getSelectionModel().getSelectedItem().toString() + "\'";

                for (String s : majorChanges) {
                    query = query + " AND VARIANTS." + s + " = \'" + entry.getValue().getValue(s) + "\'";
                }
                //System.out.println(query);
                ResultSet rs = statement.executeQuery(query);
                if (rs.next()) {
                    // Found "similar enough"
                    changedVariants.put(entry.getKey(), entry.getValue()); //Save variant to moved map
                    entries.remove(); //remove variant from current map
                    oldCyclePlan.remove(rs.getString("VariantID")); //remove variant from old map

                    // now loop through all non major columns and check for difference between variant in new and old cycle plan
                    diffValues = new HashMap<String, String>();
                    for (String s : infoArray) {
                        if (!rs.getString(s).equals(entry.getValue().getValue(s))) {
                            diffValues.put(s, rs.getString(s));
                        }
                    }

                    changedInfo.put(entry.getKey(), diffValues); //Add information about differences between new and old variant
                }

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        // Open file selector and let user specify report file
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Information");

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

        // print out information about baseline cycle plan
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        CellStyle style = workbook.createCellStyle();
        style.setFont(headerFont);
        cell.setCellStyle(style);
        cell.setCellValue("Cycle plan:");
        cell = row.createCell(1);
        cell.setCellValue(CyclePlansController.selectedCyclePlan);

        // print out information about comaparison cycle plan
        row = sheet.createRow(1);
        cell = row.createCell(0);
        headerFont = workbook.createFont();
        headerFont.setBold(true);
        style.setFont(headerFont);
        cell.setCellStyle(style);
        cell.setCellValue("Compared to:");
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
            amountOfColumns = writeRow(workbook, sheet, row, entry.getValue(), null, false, false);

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
            amountOfColumns = writeRow(workbook, sheet, row, entry.getValue(), null, false, false);

            rowNum++;
        }
        //autosize all columns
        for (int i = 0; i < amountOfColumns; i++) {
            sheet.autoSizeColumn(i);
        }
        amountOfColumns = 0;

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
            amountOfColumns = writeRow(workbook, sheet, row, entry.getValue(), changedInfo, true, false);

            rowNum++;
        }
        //autosize all columns
        for (int i = 0; i < amountOfColumns; i++) {
            sheet.autoSizeColumn(i);
        }
        amountOfColumns = 0;

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
            amountOfColumns = writeRow(workbook, sheet, row, entry.getValue(), null, false, true);

            rowNum++;

        }
        //autosize all columns
        for (int i = 0; i < amountOfColumns; i++) {
            sheet.autoSizeColumn(i);
        }
        amountOfColumns = 0;

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
        XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
        Font headerFont = wb.createFont();
        headerFont.setBold(true);
        style.setFillForegroundColor(new XSSFColor(new java.awt.Color(220, 220, 220)));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
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
        cell.setCellValue("Cert Group");

        cell = row.createCell(20);
        cell.setCellStyle(style);
        cell.setCellValue("Emission Class");

        cell = row.createCell(21);
        cell.setCellStyle(style);
        cell.setCellValue("SOP");

        cell = row.createCell(22);
        cell.setCellStyle(style);
        cell.setCellValue("EOP");

        if (addOldSOP) {
            cell = row.createCell(23);
            cell.setCellStyle(style);
            cell.setCellValue("Old SOP");
        }

        if (addOldSOP) { //Same boolean flag
            cell = row.createCell(24);
            cell.setCellStyle(style);
            cell.setCellValue("Old EOP");
        }
    }

    private int writeRow(Workbook wb, Sheet sheet, Row row, TableVariant variant, Map<String, Map<String, String>> diffList, Boolean colorChanges, Boolean addOldSOP) {
        //Used for placing comment at the right position
        CreationHelper factory = wb.getCreationHelper();
        Drawing drawing = sheet.createDrawingPatriarch();
        ClientAnchor anchor = factory.createClientAnchor();

        //Create new style
        XSSFCellStyle styleRed = (XSSFCellStyle) wb.createCellStyle();
        XSSFCellStyle styleBlack = (XSSFCellStyle) wb.createCellStyle();
        XSSFFont fontRed = (XSSFFont) wb.createFont();
        fontRed.setColor(new XSSFColor(new java.awt.Color(255, 0, 0)));
        XSSFFont fontBlack = (XSSFFont) wb.createFont();
        fontBlack.setColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
        styleRed.setFont(fontRed);
        styleBlack.setFont(fontBlack);

        //xEtract differences to highlight
        Map<String, String> differences;

        if (diffList != null) {
            differences = diffList.get(variant.getVariantID());
        } else {
            differences = new HashMap<String, String>();
        }

        //Start with column 0
        int cols = 0;

        //Create string with columns to print
        String[] columns = {"Plant", "Platform", "Vehicle", "Propulsion", "Denomination",
            "Fuel", "EngineFamily", "Generation", "EngineCode", "Displacement",
            "EnginePower", "ElMotorPower", "Torque", "TorqueOverBoost", "GearboxType",
            "Gears", "Gearbox", "Driveline", "TransmissionCode", "CertGroup", "EmissionClass",
            "StartOfProd", "EndOfProd"};

        Cell cell;

        for (int i = 0; i < columns.length; i++) {
            cell = row.createCell(i);

            if (differences.containsKey(columns[i])) {
                cell.setCellStyle(styleRed);

                // position the comment
                anchor.setCol1(cell.getColumnIndex());
                anchor.setCol2(cell.getColumnIndex() + 1);
                anchor.setRow1(row.getRowNum());
                anchor.setRow2(row.getRowNum() + 3);

                // Create the comment and set the text+author
                Comment comment = drawing.createCellComment(anchor);
                RichTextString str = factory.createRichTextString(differences.get(columns[i]));
                comment.setString(str);
                comment.setAuthor("RPT");

                // Assign the comment to the cell
                cell.setCellComment(comment);
            } else {
                cell.setCellStyle(styleBlack);
            }
            cell.setCellValue(variant.getValue(columns[i]));
            cols++;
        }

        if (addOldSOP) {
            cell = row.createCell(23);
            cell.setCellValue(variant.getOldSOP());
            cols++;
        }

        if (addOldSOP) {
            cell = row.createCell(24);
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
