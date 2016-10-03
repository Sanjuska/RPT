/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package rpt.GUI.ProgramStrategist.CyclePlans;

import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import rpt.RPT;

/**
 * FXML Controller class
 *
 * @author colak
 */
public class CyclePlansController implements Initializable {
    
    
    /**
     * Initializes the controller class.
     */
    
    //Define table and buttons
    @FXML
            public  TableView <TableVariants> tableVariants;
    @FXML
            public TableColumn<TableVariants, Integer> variantsID;
    @FXML
            public TableColumn<TableVariants, String> engineName;
    @FXML
            public TableColumn<TableVariants, String> denomination;
    @FXML
            public TableColumn<TableVariants, String> gearbox;
    @FXML
            public TableColumn<TableVariants, String> emissionsCategory;   
    @FXML
            private Button addButton;        
    @FXML
            private Button removeButton;
    @FXML
            private Button saveButton;
    @FXML
            private Button importButton;
    @FXML
            private ComboBox cyclePlanSelector;
    
    public static String selectedSheet = null;
    public static String importedCyclePlanName = null;
    
    public static List sheetsInFile = null;
    
    public static List getSheets(){
        return sheetsInFile;
    }
            
    private static ObservableList<String> cyclePlanList = FXCollections.observableArrayList("Dummy Cycle Plan");
   
    //Create table's data, get all of the items
    public static ObservableList<TableVariants> getVariants(){
        return data;
    }
    // ObservableList object enables the tracking of any changes to its elements
    private static ObservableList<TableVariants> data = FXCollections.observableArrayList(
           new TableVariants ("Torslanda", "SPA", "V526",  "ICE", "T6", 'G', "VEP4 HP", 
                   1, "B4204T27", "A2", 2.0f, 320, 0, 400, 0, 'A', 8, "AWF22", 
                   "AWD", 'C', "Euro6b", "15w05", "17w17")
    );
    
    //Add entry into table
    public static void add(TableVariants entry){
        data.add(entry); 
    }
    
    //Add button action handler
    public void addButtonClicked(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        
        //Open the PopUp window with implementation fields
        stage = new Stage();
        root = FXMLLoader.load(getClass().getResource("/rpt/GUI/ProgramManager/Variants/AddDialog.fxml"));
        stage.setScene(new Scene(root)); 
        stage.setTitle("Add");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(addButton.getScene().getWindow());
        stage.showAndWait();
    }

    //Remove button action handler
    public void removeButtonClicked (){
        ObservableList<TableVariants> removeVariants;
        tableVariants.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        removeVariants = tableVariants.getSelectionModel().getSelectedItems();
        tableVariants.getItems().removeAll(removeVariants);
    }        
    
    private Object getCellValue(Cell cell) {
        switch (cell.getCellType()) {
        case Cell.CELL_TYPE_STRING:
            return cell.getStringCellValue();
 
        case Cell.CELL_TYPE_BOOLEAN:
            return cell.getBooleanCellValue();
 
        case Cell.CELL_TYPE_NUMERIC:
            return cell.getNumericCellValue();
        }
 
        return null;
    }
    
    //Import button action handler
    public void importButtonClicked(ActionEvent event) throws IOException {
        //List with all variants read from the imported Excel file
        List<Variant> variants = new ArrayList();
        
        //Create File Chooser window
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Excel File");
        
        //Set filter to allow only Excel files
        ExtensionFilter filter = new ExtensionFilter("Excel Files", "*.xls", "*.xlsx");
        fileChooser.getExtensionFilters().addAll(filter);
        
        //Show File Selector
        File selectedFile = fileChooser.showOpenDialog(null);
        
        //import Excel file if a file has been selected, if not, do nothing
        //based on good example on:
        //http://www.codejava.net/coding/how-to-read-excel-files-in-java-using-apache-poi
        if (selectedFile != null) {
            //open dialog box and show available sheets in the file
            // the dialog box will then process the file and add data into the table
            FileInputStream inputStream = new FileInputStream(new File(selectedFile.getPath()));
            Workbook workbook;
            
            if (selectedFile.getPath().endsWith("xlsx")) {
                workbook = new XSSFWorkbook(inputStream);;
            }else {
                workbook = new HSSFWorkbook(inputStream);   
            }
            
            //Use Sheet iterator to extract all sheet names
            Iterator<Sheet> sheetIterator = workbook.sheetIterator();
            
            //Iterate over all sheets and populate a checkboxfield and let user select on of the sheets
            sheetsInFile = new ArrayList();; //reset just in case
            while (sheetIterator.hasNext()){
                String nextSheet = sheetIterator.next().getSheetName();
                sheetsInFile.add(nextSheet); //add found sheet into list of available sheets.
            }
            selectedSheet = null;
            //Show dialog box presenting all the available sheets for the user to select from
            Stage stage;
            Parent root;
            stage = new Stage();
            root = FXMLLoader.load(getClass().getResource("/rpt/GUI/ProgramStrategist/CyclePlans/dialogSelectSheet.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Select Sheet");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait(); // pause until the user has selected a sheet
            
            // If user has selected sheet to read, show next dialog box allowing
            // user to set the name of the imported cycle plan
            if (selectedSheet != null){
              //preset the file name to the file name part before the . sign
              importedCyclePlanName   = selectedFile.getName().split("\\.")[0];
              
              //Create dialog
              stage = new Stage(); 
              root = FXMLLoader.load(getClass().getResource("/rpt/GUI/ProgramStrategist/CyclePlans/dialogSetName.fxml"));
              stage.setScene(new Scene(root));
              stage.setTitle("Set Cycle Plan Name");
              stage.initModality(Modality.APPLICATION_MODAL);
              stage.showAndWait(); // pause until the user has selected a sheet
            }
            
            if (selectedSheet != null && importedCyclePlanName != null){
                //set sheet to the sheet selected
                Sheet firstSheet = workbook.getSheet(selectedSheet);
                Iterator<Row> rowIterator = firstSheet.iterator();
            
                //find first row
                //TODO
                //set first row keyword into application settings
                Boolean firstRowFound = false;
                Cell nextCell;
                Iterator<Cell> cellIterator;
                
                //dictionary using HashMaps
                Map<Integer, String> dictionary = new HashMap<Integer, String>();
                do{
                    Row nextRow = rowIterator.next();
                    cellIterator = nextRow.cellIterator();
                    nextCell = cellIterator.next();
                    if (getCellValue(nextCell) != null){//blank cells return null
                        if (getCellValue(nextCell).equals("Plant")) {
                            //dictionary.put(nextCell.getColumnIndex(), (String) getCellValue(nextCell));
                            firstRowFound = true;
                        }
                    }
                    
                }while(!firstRowFound && rowIterator.hasNext());
                
                //First row is now found, loop through entire row and build a
                while(cellIterator.hasNext()){
                    if(getCellValue(nextCell)!= null){ //blank cells return null
                        dictionary.put(nextCell.getColumnIndex(), (String) getCellValue(nextCell));
                    }
                    nextCell = cellIterator.next();
                }
            
                //loop through all rows in the file
                while (rowIterator.hasNext()) {
                    Row nextRow = rowIterator.next();
                    cellIterator = nextRow.cellIterator();
                    Variant aVariant = new Variant();
                    
                    //loop through all columns in the row
                    while (cellIterator.hasNext()) {
                        nextCell = cellIterator.next();
                        int columnIndex = nextCell.getColumnIndex();
                        if (getCellValue(nextCell) != null) {
                            aVariant.setValue(dictionary.get(nextCell.getColumnIndex()),getCellValue(nextCell));
                        } else {
                        }
                        
                    }
                    variants.add(aVariant);
                }       
            
            //remove current selection and add the new variants
            data.clear();
            int index = 1;
            for (Variant variant : variants){ 
                TableVariants entry = new TableVariants(variant.getPlant(), 
                        variant.getPlatform(), variant.getVehicle(), variant.getPropulsion(),
                        variant.getDenomination(), variant.getFuel(), variant.getEngineFamily(), variant.getGeneration(), 
                        variant.getEngineName(), variant.getEngineCode(), variant.getDisplacement(), variant.getEnginePower(),
                        variant.getElMotorPower(), variant.getTorque(), variant.getTorqueOverBoost(), variant.getGearBoxType(),
                        variant.getGears(), variant.getGearbox(), variant.getDriveline(), variant.getTransmissionCode(),
                        variant.getEmissionClass(), variant.getStartOfProd(), variant.getEndOfProd());
                add(entry);
                index++;
                //INSERT INTO "main"."VARIANTS" ("Plant","VariantID") VALUES ('Plant2', 'ID 3')
                try {
                    Statement statement = RPT.conn.createStatement();
                    statement.setQueryTimeout(30);
                    String variantID = variant.getVehicle() + variant.getEngineCode() + variant.getTransmissionCode() 
                            + variant.getEmissionClass() + variant.getStartOfProd();
                    String query = "SELECT COUNT(VariantID) FROM VARIANTS WHERE VariantID = '" + variantID + "'";
                    ResultSet rs = statement.executeQuery(query);
           
                    //check count of previous query, 0 = new variant, 1 = it already exists
                    Integer count = rs.getInt(1);
                    // add variant if it does not exist
                    if (count == 0) { // entry did not existbefore
                        //INSERT INTO VARIANTS (Plant,VariantID) VALUES ('VCG','Variant6')
                        query = "INSERT INTO VARIANTS ("
                                + "Plant, Platform, Vehicle, Propulsion, Denomination, Fuel, EngineFamily, Generation, EngineCode, Displacement, "
                                + "EnginePower, ElMotorPower, Torque, TorqueOverBoost, GearboxType, Gears, Gearbox, Driveline, TransmissionCode, "
                                + "CertGroup, EmissionClass, StartOfProd, EndOfProd, VariantID"
                                + ")"
                                + ""
                                + " VALUES (\'"
                                + variant.getPlant() + "\', \'" + variant.getPlatform()  + "\', \'" + variant.getVehicle() + "\', \'" + variant.getPropulsion() + "\', \'"
                                + variant.getDenomination() + "\', \'" + variant.getFuel() + "\', \'" + variant.getEngineFamily() + "\', \'" + variant.getGeneration() + "\', \'"
                                + variant.getEngineCode() + "\', \'" + variant.getDisplacement() + "\', \'" + variant.getEnginePower() + "\', \'" + variant.getElMotorPower()
                                + "\', \'" + variant.getTorque() + "\', \'" + variant.getTorqueOverBoost() + "\', \'" + variant.getGearBoxType() + "\', \'" + variant.getGears() + "', '"
                                + variant.getGearbox() + "\', \'" + variant.getDriveline() + "\', \'" + variant.getTransmissionCode() + "\', \'" + variant.getCertGroup() + "\', \'"
                                + variant.getEmissionClass() + "\', \'" + variant.getStartOfProd() + "\', \'" + variant.getEndOfProd() + "\', \'" + variantID
                                + "\')";
                        statement.executeUpdate(query);
                    }
                    // Add connection between cycle plan and variant
                    
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
            }// end of readin file after user has selected sheet
            inputStream.close();
            //now that we have a list of Variants we put them into the tableView
            
        }
    }
    
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableVariants.setEditable(true);
        // specify a cell factory for each column
        variantsID.setCellValueFactory(new PropertyValueFactory<>("variantsID"));
        variantsID.setEditable(false);
       
        //specify a cell factory  and enable it editable
        engineName.setCellValueFactory(new PropertyValueFactory<>("engineName")); 
        engineName.setCellFactory(TextFieldTableCell.forTableColumn());
        engineName.setOnEditCommit(new EventHandler<CellEditEvent<TableVariants, String>>() {
            @Override
            public void handle(CellEditEvent<TableVariants, String> event) {
              ((TableVariants) event.getTableView().getItems().get(event.getTablePosition().getRow())).setEngineName(event.getNewValue());
            }  
        });
        //specify a cell factory  and enable it editable
        denomination.setCellValueFactory(new PropertyValueFactory<>("denomination"));
        denomination.setCellFactory(TextFieldTableCell.forTableColumn());
        engineName.setOnEditCommit(new EventHandler<CellEditEvent<TableVariants, String>>() {
            @Override
            public void handle(CellEditEvent<TableVariants, String> event) {
              ((TableVariants) event.getTableView().getItems().get(event.getTablePosition().getRow())).setDenomination(event.getNewValue());
            }  
        });
        //specify a cell factory  and enable it editable
        gearbox.setCellValueFactory(new PropertyValueFactory<>("gearbox"));
        gearbox.setCellFactory(TextFieldTableCell.forTableColumn());
        gearbox.setOnEditCommit(new EventHandler<CellEditEvent<TableVariants, String>>() {
            @Override
            public void handle(CellEditEvent<TableVariants, String> event) {
              ((TableVariants) event.getTableView().getItems().get(event.getTablePosition().getRow())).setGearbox(event.getNewValue());
            }  
        });
        //specify a cell factory  and enable it editable
        emissionsCategory.setCellValueFactory(new PropertyValueFactory<>("emissionClass"));
        emissionsCategory.setCellFactory(TextFieldTableCell.forTableColumn());
        emissionsCategory.setOnEditCommit(new EventHandler<CellEditEvent<TableVariants, String>>() {
            @Override
            public void handle(CellEditEvent<TableVariants, String> event) {
              ((TableVariants) event.getTableView().getItems().get(event.getTablePosition().getRow())).setEmissionClass(event.getNewValue());
            }  
        });
        //Push into the table
        tableVariants.setItems(data);
      
        //Add Tooltip to the add and remove icons
         addButton.setTooltip(new Tooltip("Add new item"));
         removeButton.setTooltip(new Tooltip("Remove selected items"));  
         saveButton.setTooltip(new Tooltip("Save"));
    }
    
}