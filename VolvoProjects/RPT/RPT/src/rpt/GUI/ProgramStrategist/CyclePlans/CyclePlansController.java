/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package rpt.GUI.ProgramStrategist.CyclePlans;

import com.sun.javafx.scene.control.skin.TableViewSkin;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
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
    public TableView<TableVariant> tableVariants;
    @FXML
    public TableColumn<TableVariant, Integer> variantID;
    @FXML
    public TableColumn<TableVariant, String> engineName;
    @FXML
    public TableColumn<TableVariant, String> denomination;
    @FXML
    public TableColumn<TableVariant, String> gearbox;
    @FXML
    public TableColumn<TableVariant, String> emissionsCategory;
    @FXML
    public TableColumn<TableVariant, String> generation;
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
    @FXML
    private Button compareButton;
    @FXML
    private Button exportButton;

    public static String selectedSheet = null;
    public static String importedCyclePlanName = null;
    public static String selectedCyclePlan = null;
    public static String[] defineChanged = null;
    private ArrayList<TableColumn<TableVariant, String>> columnList;

    public static List sheetsInFile = null;

    private String query = "";

    private Boolean columnsAdded;

    public static List getSheets() {
        return sheetsInFile;
    }

    public static ObservableList<String> cyclePlanList = FXCollections.observableArrayList();

    //Create table's data, get all of the items
    public static ObservableList<TableVariant> getVariants() {
        return data;
    }
    // ObservableList object enables the tracking of any changes to its elements
    private static ObservableList<TableVariant> data = FXCollections.observableArrayList();

    //Add entry into table
    public static void add(TableVariant entry) {
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
    public void removeButtonClicked() {
        ObservableList<TableVariant> removeVariants;
        tableVariants.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        removeVariants = tableVariants.getSelectionModel().getSelectedItems();
        tableVariants.getItems().removeAll(removeVariants);
    }

    public void cyclePlanSelected() {
        // Set cycle plan name to whatever was elected in the comboBox
        selectedCyclePlan = cyclePlanSelector.getSelectionModel().getSelectedItem().toString();

        try {
            Statement statement = RPT.conn.createStatement();
            statement.setQueryTimeout(30);
            // Extract column names from database if no columns exist in the table view
            if (tableVariants.getColumns().isEmpty()) {
                String query = "PRAGMA table_info(VARIANTS)"; //Get all column names
                ResultSet rsColumns = statement.executeQuery(query);

                while (rsColumns.next()) {
                    TableColumn<TableVariant, String> column = new TableColumn<TableVariant, String>(rsColumns.getString("name"));
                    column.setCellValueFactory(new PropertyValueFactory<>(rsColumns.getString("name")));
                    column.setCellFactory(TextFieldTableCell.forTableColumn());
                    column.setOnEditCommit(new EventHandler<CellEditEvent<TableVariant, String>>() {
                        @Override
                        public void handle(CellEditEvent<TableVariant, String> event) {
                            ((TableVariant) event.getTableView().getItems().get(event.getTablePosition().getRow())).setEngineName(event.getNewValue());
                        }
                    });

                    tableVariants.getColumns().add(column);

                }
            }
            // Read variants and add to table
            query = "SELECT * FROM VARIANTS, VariantBelongsToCyclePlan WHERE "
                    + "VariantBelongsToCyclePlan.CyclePlanID= \'" + selectedCyclePlan + "\' AND VARIANTS.VariantID = VariantBelongsToCyclePlan.VariantID";
            ResultSet rs = statement.executeQuery(query);

            data.clear();

            while (rs.next()) {
                TableVariant entry = new TableVariant(rs.getString("Plant"),
                        rs.getString("Platform"), rs.getString("Vehicle"), rs.getString("Propulsion"),
                        rs.getString("Denomination"), rs.getString("Fuel"), rs.getString("EngineFamily"), rs.getString("Generation"),
                        "EngineName not used", rs.getString("EngineCode"), rs.getString("Displacement"), rs.getString("EnginePower"),
                        rs.getString("ElMotorPower"), rs.getString("Torque"), rs.getString("TorqueOverBoost"), rs.getString("GearboxType"),
                        rs.getString("Gears"), rs.getString("Gearbox"), rs.getString("Driveline"), rs.getString("TransmissionCode"),
                        rs.getString("certGroup"), rs.getString("EmissionClass"), rs.getString("StartOfProd"), rs.getString("EndOfProd"));
                add(entry);
            }

            cyclePlanSelector.setItems(cyclePlanList);
        } catch (Exception e) {
            System.err.println("CyclePlansController cyclePlanSelected(): " + e.getMessage());
        }
    }

    public void compareButtonClicked() throws IOException {
        Stage stage;
        Parent root;
        stage = new Stage();
        root = FXMLLoader.load(getClass().getResource("/rpt/GUI/ProgramStrategist/CyclePlans/CompareDialog.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Select Cycle Plan for comparison");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(compareButton.getScene().getWindow());
        stage.showAndWait();

    }

    public void exportButtonClicked() {
        System.out.println("exporting");
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
        List<TableVariant> variants = new ArrayList();

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
            } else {
                workbook = new HSSFWorkbook(inputStream);
            }

            //Use Sheet iterator to extract all sheet names
            Iterator<Sheet> sheetIterator = workbook.sheetIterator();

            //Iterate over all sheets and populate a checkboxfield and let user select on of the sheets
            sheetsInFile = new ArrayList();; //reset just in case
            while (sheetIterator.hasNext()) {
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
            if (selectedSheet != null) {
                //preset the file name to the file name part before the . sign
                importedCyclePlanName = selectedFile.getName().split("\\.")[0];

                //Create dialog
                stage = new Stage();
                root = FXMLLoader.load(getClass().getResource("/rpt/GUI/ProgramStrategist/CyclePlans/dialogSetName.fxml"));
                stage.setScene(new Scene(root));
                stage.setTitle("Set Cycle Plan Name");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait(); // pause until the user has selected a sheet
            }

            if (selectedSheet != null && importedCyclePlanName != null) {
                // Add new cycleplan into Database
                try {
                    Statement statement = RPT.conn.createStatement();
                    statement.setQueryTimeout(30);
                    query = "INSERT INTO CYCLEPLANS (Name, Version) VALUES (\'" + importedCyclePlanName + "\', 1)";
                    statement.executeUpdate(query);

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
                    do {
                        Row nextRow = rowIterator.next();
                        cellIterator = nextRow.cellIterator();
                        nextCell = cellIterator.next();
                        if (getCellValue(nextCell) != null) {//blank cells return null
                            if (getCellValue(nextCell).equals("Plant")) {
                                //dictionary.put(nextCell.getColumnIndex(), (String) getCellValue(nextCell));
                                firstRowFound = true;
                            }
                        }

                    } while (!firstRowFound && rowIterator.hasNext());

                    //First row is now found, loop through entire row and build a
                    while (cellIterator.hasNext()) {
                        if (getCellValue(nextCell) != null) { //blank cells return null
                            dictionary.put(nextCell.getColumnIndex(), getCellValue(nextCell).toString());
                        }
                        nextCell = cellIterator.next();
                    }

                    // Since Excel stores numbers as floats and as an example 1 will turn into 1.0 this will be a problem
                    // Mainly because the cycle plan tends to mix text and numbers in Excel.
                    // Create a Dataformatter which will be used to solve this
                    DataFormatter fmt = new DataFormatter();
                    //loop through all rows in the file
                    while (rowIterator.hasNext()) {
                        Row nextRow = rowIterator.next();
                        cellIterator = nextRow.cellIterator();
                        TableVariant aVariant = new TableVariant();
                        //loop through all columns in the row
                        while (cellIterator.hasNext()) {
                            nextCell = cellIterator.next();
                            int columnIndex = nextCell.getColumnIndex();
                            if (getCellValue(nextCell) != null) {
                                aVariant.setValue(dictionary.get(nextCell.getColumnIndex()), fmt.formatCellValue(nextCell));
                                //aVariant.setValue(dictionary.get(nextCell.getColumnIndex()), getCellValue(nextCell).toString());
                            } else {
                            }

                        }
                        variants.add(aVariant);
                    }
                } catch (Exception e) {
                    System.err.println("CyclePlansController line 343: " + e.getMessage());
                }

                //remove current selection and add the new variants
                data.clear();
                int index = 1;
                for (TableVariant variant : variants) {
                    TableVariant entry = new TableVariant(variant.getPlant(),
                            variant.getPlatform(), variant.getVehicle(), variant.getPropulsion(),
                            variant.getDenomination(), variant.getFuel(), variant.getEngineFamily(), variant.getGeneration(),
                            variant.getEngineName(), variant.getEngineCode(), variant.getDisplacement(), variant.getEnginePower(),
                            variant.getElMotorPower(), variant.getTorque(), variant.getTorqueOverBoost(), variant.getGearboxType(),
                            variant.getGears(), variant.getGearbox(), variant.getDriveline(), variant.getTransmissionCode(), variant.getCertGroup(),
                            variant.getEmissionClass(), variant.getStartOfProd(), variant.getEndOfProd());
                    add(entry);
                    index++;
                    //INSERT INTO "main"."VARIANTS" ("Plant","VariantID") VALUES ('Plant2', 'ID 3')
                    try {
                        Statement statement = RPT.conn.createStatement();
                        statement.setQueryTimeout(30);
                        String variantID = variant.getVehicle() + variant.getEngineCode() + variant.getTransmissionCode()
                                + variant.getEmissionClass() + variant.getStartOfProd();
                        query = "SELECT COUNT(VariantID) FROM VARIANTS WHERE VariantID = '" + variantID + "'";
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
                                    + variant.getPlant() + "\', \'" + variant.getPlatform() + "\', \'" + variant.getVehicle() + "\', \'" + variant.getPropulsion() + "\', \'"
                                    + variant.getDenomination() + "\', \'" + variant.getFuel() + "\', \'" + variant.getEngineFamily() + "\', \'" + variant.getGeneration() + "\', \'"
                                    + variant.getEngineCode() + "\', \'" + variant.getDisplacement() + "\', \'" + variant.getEnginePower() + "\', \'" + variant.getElMotorPower()
                                    + "\', \'" + variant.getTorque() + "\', \'" + variant.getTorqueOverBoost() + "\', \'" + variant.getGearboxType() + "\', \'" + variant.getGears() + "', '"
                                    + variant.getGearbox() + "\', \'" + variant.getDriveline() + "\', \'" + variant.getTransmissionCode() + "\', \'" + variant.getCertGroup() + "\', \'"
                                    + variant.getEmissionClass() + "\', \'" + variant.getStartOfProd() + "\', \'" + variant.getEndOfProd() + "\', \'" + variantID
                                    + "\')";
                            statement.executeUpdate(query);
                        }
                        // Add connection between cycle plan and variant

                        query = "INSERT INTO VariantBelongsToCyclePlan (VariantID, CyclePlanID) VALUES (\'" + variantID + "\', \'" + importedCyclePlanName + "\')";
                        statement.executeUpdate(query);

                    } catch (Exception e) {
                        System.out.println("Query: " + query);
                        System.err.println("CyclePlansController line 394: " + e.getMessage());
                    }
                }
                cyclePlanList.add(importedCyclePlanName);
                cyclePlanSelector.getSelectionModel().select(importedCyclePlanName);
            }// end of reading file after user has selected sheet and name of cycle plan
            inputStream.close();

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableVariants.setEditable(true);
        // specify a cell factory for each column
//        variantID.setCellValueFactory(new PropertyValueFactory<>("variantID"));
//        variantID.setEditable(false);
//
//        //specify a cell factory  and enable it editable
//        engineName.setCellValueFactory(new PropertyValueFactory<>("engineName"));
//        engineName.setCellFactory(TextFieldTableCell.forTableColumn());
//        engineName.setOnEditCommit(new EventHandler<CellEditEvent<TableVariant, String>>() {
//            @Override
//            public void handle(CellEditEvent<TableVariant, String> event) {
//                ((TableVariant) event.getTableView().getItems().get(event.getTablePosition().getRow())).setEngineName(event.getNewValue());
//            }
//        });
//        //specify a cell factory  and enable it editable
//        denomination.setCellValueFactory(new PropertyValueFactory<>("denomination"));
//        denomination.setCellFactory(TextFieldTableCell.forTableColumn());
//        engineName.setOnEditCommit(new EventHandler<CellEditEvent<TableVariant, String>>() {
//            @Override
//            public void handle(CellEditEvent<TableVariant, String> event) {
//                ((TableVariant) event.getTableView().getItems().get(event.getTablePosition().getRow())).setDenomination(event.getNewValue());
//            }
//        });
//        //specify a cell factory  and enable it editable
//        gearbox.setCellValueFactory(new PropertyValueFactory<>("gearbox"));
//        gearbox.setCellFactory(TextFieldTableCell.forTableColumn());
//        gearbox.setOnEditCommit(new EventHandler<CellEditEvent<TableVariant, String>>() {
//            @Override
//            public void handle(CellEditEvent<TableVariant, String> event) {
//                ((TableVariant) event.getTableView().getItems().get(event.getTablePosition().getRow())).setGearbox(event.getNewValue());
//            }
//        });
//        //specify a cell factory  and enable it editable
//        emissionsCategory.setCellValueFactory(new PropertyValueFactory<>("emissionClass"));
//        emissionsCategory.setCellFactory(TextFieldTableCell.forTableColumn());
//        emissionsCategory.setOnEditCommit(new EventHandler<CellEditEvent<TableVariant, String>>() {
//            @Override
//            public void handle(CellEditEvent<TableVariant, String> event) {
//                ((TableVariant) event.getTableView().getItems().get(event.getTablePosition().getRow())).setEmissionClass(event.getNewValue());
//            }
//        });
//        //specify a cell factory  and enable it editable
//        generation.setCellValueFactory(new PropertyValueFactory<>("generation"));
//        generation.setCellFactory(TextFieldTableCell.forTableColumn());
//        generation.setOnEditCommit(new EventHandler<CellEditEvent<TableVariant, String>>() {
//            @Override
//            public void handle(CellEditEvent<TableVariant, String> event) {
//                ((TableVariant) event.getTableView().getItems().get(event.getTablePosition().getRow())).setEmissionClass(event.getNewValue());
//            }
//        });
        //Push into the table
        tableVariants.setItems(data);

        //Add Tooltip to the add and remove icons
        addButton.setTooltip(new Tooltip("Add new item"));
        removeButton.setTooltip(new Tooltip("Remove selected items"));
        saveButton.setTooltip(new Tooltip("Save"));

        // empty cycle plan list in case one arrives from other view
        cyclePlanList.clear();
        //populate cycle plan list
        try {
            Statement statement = RPT.conn.createStatement();
            statement.setQueryTimeout(30);
            query = "SELECT Name FROM CYCLEPLANS";
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                cyclePlanList.add(rs.getString(1));
            }
            cyclePlanSelector.setItems(cyclePlanList);
        } catch (Exception e) {
            System.err.println("CyclePlansController line 470: " + e.getMessage());
        }

    }

}
