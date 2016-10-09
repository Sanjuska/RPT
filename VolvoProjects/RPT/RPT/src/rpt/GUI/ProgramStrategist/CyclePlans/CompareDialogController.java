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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
//import javafx.scene.control.Cell;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
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

    public void okButtonClicked() {
        // create two new maps, one for currentCycleplan and one for the comparison plan
        Map<String, TableVariant> currentCyclePlan = new HashMap<String, TableVariant>();
        Map<String, TableVariant> oldCyclePlan = new HashMap<String, TableVariant>();
        Map<String, TableVariant> movedVariants = new HashMap<String, TableVariant>();

        Statement statement;
        try {
            // Extract all variants in the current cycleplan and put them in an map
            System.out.println("Extracting current variants");
            statement = RPT.conn.createStatement();
            statement.setQueryTimeout(30);
            String query = "SELECT * FROM VARIANTS, VariantBelongsToCyclePlan WHERE "
                    + "VariantBelongsToCyclePlan.CyclePlanID= \'" + CyclePlansController.selectedCyclePlan + "\' AND VARIANTS.VariantID = VariantBelongsToCyclePlan.VariantID";
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                TableVariant entry = new TableVariant(rs.getString("Plant"),
                        rs.getString("Platform"), rs.getString("Vehicle"), rs.getString("Propulsion"),
                        rs.getString("Denomination"), rs.getString("Fuel").charAt(0), rs.getString("EngineFamily"), rs.getInt("Generation"),
                        "EngineName not used", rs.getString("EngineCode"), rs.getFloat("Displacement"), rs.getInt("EnginePower"),
                        rs.getInt("ElMotorPower"), rs.getInt("Torque"), rs.getInt("TorqueOverBoost"), rs.getString("Gearbox").charAt(0),
                        rs.getInt("Gears"), rs.getString("Gearbox"), rs.getString("Driveline"), rs.getString("TransmissionCode").charAt(0),
                        rs.getString("EmissionClass"), rs.getString("StartOfProd"), rs.getString("EndOfProd"));
                currentCyclePlan.put(entry.getVariantID(), entry);
            }
            //Now extract all variants in the cycleplan to compare with
            System.out.println("Extracting comparison variants");
            query = "SELECT * FROM VARIANTS, VariantBelongsToCyclePlan WHERE "
                    + "VariantBelongsToCyclePlan.CyclePlanID= \'" + cyclePlanSelector.getSelectionModel().getSelectedItem().toString() + "\' AND VARIANTS.VariantID = VariantBelongsToCyclePlan.VariantID";
            rs = statement.executeQuery(query);

            while (rs.next()) {
                TableVariant entry = new TableVariant(rs.getString("Plant"),
                        rs.getString("Platform"), rs.getString("Vehicle"), rs.getString("Propulsion"),
                        rs.getString("Denomination"), rs.getString("Fuel").charAt(0), rs.getString("EngineFamily"), rs.getInt("Generation"),
                        "EngineName not used", rs.getString("EngineCode"), rs.getFloat("Displacement"), rs.getInt("EnginePower"),
                        rs.getInt("ElMotorPower"), rs.getInt("Torque"), rs.getInt("TorqueOverBoost"), rs.getString("Gearbox").charAt(0),
                        rs.getInt("Gears"), rs.getString("Gearbox"), rs.getString("Driveline"), rs.getString("TransmissionCode").charAt(0),
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
                String query = "SELECT VARIANTS.VariantID, VARIANTS.StartOfProd FROM VARIANTS, VariantBelongsToCyclePlan WHERE "
                        + "VARIANTS.VariantID = VariantBelongsToCyclePlan.VariantID AND "
                        + "VariantBelongsToCyclePlan.CyclePlanID= \'" + cyclePlanSelector.getSelectionModel().getSelectedItem().toString() + "\' AND "
                        + "VARIANTS.Vehicle = \'" + entry.getValue().getVehicle() + "\' AND "
                        + "VARIANTS.EngineCode = \'" + entry.getValue().getEngineCode() + "\' AND "
                        + "VARIANTS.TransmissionCode = \'" + entry.getValue().getTransmissionCode() + "\' AND "
                        + "VARIANTS.EmissionClass = \'" + entry.getValue().getEmissionClass() + "\'";
                System.out.println(query);
                ResultSet rs = statement.executeQuery(query);
                if (rs.next()) {
                    entry.getValue().setOldSOP(rs.getString("StartOfProd"));
                    movedVariants.put(entry.getKey(), entry.getValue()); //Save variant to moved map
                    entries.remove(); //remove variant from current map
                    oldCyclePlan.remove(rs.getString("VariantID")); //remove variant from old map
                }

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        // Open file selector and let user specify report file
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Information");
        sheet.setAutobreaks(true);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
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

        // Write Added variant information
        sheet = workbook.createSheet("Added");
        // loop through added
        for (Iterator<Map.Entry<String, TableVariant>> entries = currentCyclePlan.entrySet().iterator(); entries.hasNext();) {
            Map.Entry<String, TableVariant> entry = entries.next();
            System.out.println("Added: " + entry.getKey());
            row = sheet.createRow(0);
            cell = row.createCell(0);
            
            
            
        }
        
        
        // Write Removed variant information
        sheet = workbook.createSheet("Removed");
        // loop through removed
        for (Iterator<Map.Entry<String, TableVariant>> entries = oldCyclePlan.entrySet().iterator(); entries.hasNext();) {
            Map.Entry<String, TableVariant> entry = entries.next();
            System.out.println("Removed: " + entry.getKey());
        }

        
        // Write Moved variant information
        sheet = workbook.createSheet("Moved");
        // loop through added
        for (Iterator<Map.Entry<String, TableVariant>> entries = movedVariants.entrySet().iterator(); entries.hasNext();) {
            Map.Entry<String, TableVariant> entry = entries.next();
            System.out.println("Moved: " + entry.getKey() + " from: " + entry.getValue().getOldSOP() + " to: " + entry.getValue().getStartOfProd());
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cyclePlanSelector.setItems(CyclePlansController.cyclePlanList);
    }

}
