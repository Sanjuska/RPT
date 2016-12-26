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
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.CacheHint;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.CheckBoxTreeItem.TreeModificationEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.effect.Effect;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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
    //Define table and buttons from FXML
    @FXML
    public TableView<TableVariant> tableVariants;
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
    @FXML
    private ToggleButton filterButton;
    @FXML
    private ToggleButton settingsButton;
    @FXML
    private TreeView<String> filterTree;
    @FXML
    private VBox filterMenu;
    @FXML
    private Text filterHeader;
    @FXML
    private ImageView filterImage;
    @FXML
    private TreeView<String> settingsTree;
    @FXML
    private VBox settingsMenu;
    @FXML
    private Text settingsHeader;
    @FXML
    private ImageView settingsImage;

    // Declare public static variables used for communication from outside the class
    public static String selectedSheet = null;
    public static String importedCyclePlanName = null;
    public static String selectedCyclePlan = null;
    public static String[] defineChanged = null;
    private ArrayList<TableColumn<TableVariant, String>> columnList;
    public static List sheetsInFile = null;
    public static ArrayList<String> visibleColumns = new ArrayList();

    // variable used for storing SQL queries
    private String query = "";

    //Allows external classes to check sheets from the file read in the import
    //function of this class
    //TODO
    //move the entire import function to seperate class EXCEL IMPORTER
    //this then becomes useless as it will be part of that class
    public static List getSheets() {
        return sheetsInFile;
    }

    //Observable list containing the cycle plans in the combo box
    public static ObservableList<String> cyclePlanList = FXCollections.observableArrayList();

    // ObservableList object enables the tracking of any changes to its elements
    private static ObservableList<TableVariant> data = FXCollections.observableArrayList();
    FilteredList<TableVariant> filteredData = new FilteredList<>(data, p -> true); //make it possible to filter
    Map<String, ArrayList<String>> filterList = new HashMap<String, ArrayList<String>>();

    //Add entry into table
    public static void add(TableVariant entry) {
        data.add(entry);
    }

    //Returns what is currently being presented in the variant table
    public static ObservableList<TableVariant> getVariants() {
        return data;
    }

    //** BUTTON ACTION HANDLERS ** //
    //Left to right as they appear
    //Add button action handler
    public void addButtonClicked(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        //Open the PopUp window with implementation fields
        stage = new Stage();
        root = FXMLLoader.load(getClass().getResource("/rpt/GUI/ProgramStrategist/CyclePlans/AddDialog.fxml"));
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

    // Save button action handler
    public void saveButtonClicked() {
        // does nothing for now, should save a new version of the selected cycle plan
        // i.e. update the relations between cycle plan and variants.
    }

    // Helper method used by import button action handler below
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
                workbook = new XSSFWorkbook(inputStream);
            } else {
                workbook = new HSSFWorkbook(inputStream);
            }

            //Use Sheet iterator to extract all sheet names
            Iterator<Sheet> sheetIterator = workbook.sheetIterator();

            //Iterate over all sheets and populate a checkboxfield and let user select on of the sheets
            sheetsInFile = new ArrayList(); //reset just in case
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

            // only continue if a sheet was selected (=abort if used presses cancel)
            // AND if the cycle plan name is unique, i.e. not already imported
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
                        // Add relation between cycle plan and variant
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

    // Cycle plan combobox action handler
    public void cyclePlanSelected() {
        List<String> columnFilterList = new ArrayList<String>();
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
                    String colName = rsColumns.getString("name");
                    columnFilterList.add(colName);
                    TableColumn<TableVariant, String> column = new TableColumn<TableVariant, String>(colName);
                    column.setCellValueFactory(new PropertyValueFactory<>(colName));
                    column.setCellFactory(TextFieldTableCell.forTableColumn());
                    column.setOnEditCommit(new EventHandler<CellEditEvent<TableVariant, String>>() {
                        @Override
                        public void handle(CellEditEvent<TableVariant, String> event) {
                            ((TableVariant) event.getTableView().getItems().get(event.getTablePosition().getRow())).setEngineName(event.getNewValue());
                        }
                    });
                    // Add the extracted columns into the table
                    //TODO
                    // Add a KV map to translate table names in SQL to nicely presented column names
                    tableVariants.getColumns().add(column);
                    visibleColumns.add(colName);
                    CheckBoxTreeItem<String> root = (CheckBoxTreeItem<String>) settingsTree.getRoot();
                    CheckBoxTreeItem<String> item = makeBranchTreeItem(colName, root);
                    item.selectedProperty().addListener((obs, oldVal, newVal) -> {
                        //System.out.println(item.getValue() + " selection state: " + newVal);
                        if (item.isSelected()) {
                            if (visibleColumns.contains(item.getValue())) {
                                visibleColumns.remove(item.getValue());
                            }
                        } else if (!visibleColumns.contains(item.getValue())) {
                            visibleColumns.add(item.getValue());
                        }
                        updateVisibility();
                    });

                }
            }
            //Populate filter view
            Collections.sort(columnFilterList);
            try {
                CheckBoxTreeItem<String> root = (CheckBoxTreeItem<String>) filterTree.getRoot();
                //For each column, extract distinct values and insert in tree structure below that column CheckBox
                for (String column : columnFilterList) {
                    query = "SELECT DISTINCT VARIANTS." + column + " FROM VARIANTS, VariantBelongsToCyclePlan WHERE "
                            + "VARIANTS.VariantID = VariantBelongsToCyclePlan.VariantID  AND "
                            + "VariantBelongsToCyclePlan.CyclePlanID = \'" + selectedCyclePlan + "\' ORDER BY VARIANTS." + column;
                    ResultSet rsColumns = statement.executeQuery(query);
                    CheckBoxTreeItem<String> parent = makeBranchTreeItem(column, root);

                    //ArrayList<String> allowedValues = new ArrayList();
                    //filterList.put(column, allowedValues);
                    // Create all CheckBoxItems
                    // Add listeners which will be used for filtering of data
                    while (rsColumns.next()) {
                        CheckBoxTreeItem<String> item = makeBranchTreeItem(rsColumns.getString(column), parent);
                        item.selectedProperty().addListener((obs, oldVal, newVal) -> {
                            //On change of state, update data filter
                            filteredData.setPredicate(variant -> {
                                //TODO
                                // implement as Stream to improve performance
                                // Use parrallell streams to improve speed
                                // http://www.drdobbs.com/jvm/lambdas-and-streams-in-java-8-libraries/240166818
                                for (TreeItem branchItem : filterTree.getRoot().getChildren()) {
                                    CheckBoxTreeItem branch = (CheckBoxTreeItem) branchItem;
                                    if (branch.isSelected() || branch.isIndeterminate()) { //New list to be added
                                        ArrayList<String> allowedValues = new ArrayList();
                                        for (Object checkBox : branch.getChildren()) {
                                            if (((CheckBoxTreeItem) checkBox).isSelected()) {
                                                allowedValues.add((String) ((CheckBoxTreeItem) checkBox).getValue());
                                            }
                                        }
                                        filterList.put((String) branch.getValue(), allowedValues);
                                        //System.out.println("Store: "  + (String) branch.getValue());
                                    } else {
                                        filterList.remove((String) branch.getValue()); // remove branch if it no longer has any values
                                    }
                                }
                                for (String key : filterList.keySet()) {
                                    if (!filterList.get(key).contains(variant.getValue(key))) {
                                        return false;
                                    }
                                }
                                return true;
                            });
                        });
                    }
                }
                //item.getValue().setSelected(true);
            } catch (Exception e) {
                System.out.println(query);
                System.err.println("CyclePlansController CyclePlanSelected() ERROR 1: " + e.getMessage());
                e.printStackTrace();
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

            filterButton.setDisable(
                    false);
            settingsButton.setDisable(
                    false);
        } catch (Exception e) {
            System.err.println("CyclePlansController cyclePlanSelected() ERROR 2: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //Compare button action handler
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

    // Export button action handler
    public void exportButtonClicked() {
        System.out.println("exporting");
        //TODO
        //Add a dialog allowing the user to select from:
        // * Timeline
        // * Powertrain map
        // * Proect overviews (based on engine or based on transmission)
        // * Bubble graphs per project (jscript based)
        // * Javascript based timeline per engine
        // * Graphviz?
    }

    //Filter button action handler
    public void filterButtonClicked() {
        if (filterButton.isSelected()) {
            //TODO set filter conditions of data
            //if (filterMenu.getPrefWidth() == 0) {
            //filterTree.setPrefWidth(200);
            filterMenu.setPrefWidth(Region.USE_COMPUTED_SIZE);
            filterHeader.setText("Filtered Values");
        } else {
            //filterTree.setPrefWidth(0);
            filterMenu.setPrefWidth(0);
            filterHeader.setText("");
        }

    }

    //Settings button action handler
    public void settingsButtonClicked() throws IOException {

        if (settingsButton.isSelected()) {
            settingsMenu.setPrefWidth(Region.USE_COMPUTED_SIZE);
            settingsHeader.setText("Hidden Columns");
        } else {
            //filterTree.setPrefWidth(0);
            settingsMenu.setPrefWidth(0);
            settingsHeader.setText("");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableVariants.setEditable(true);
        //Set the filter Predicate whenever the filter changes.
        SortedList<TableVariant> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableVariants.comparatorProperty());
        //Push into the table
        tableVariants.setItems(sortedData);

        //Add Tooltip to the add and remove icons
        addButton.setTooltip(new Tooltip("Add new item"));
        removeButton.setTooltip(new Tooltip("Remove selected items"));
        saveButton.setTooltip(new Tooltip("Save"));
        importButton.setTooltip(new Tooltip("Import Cycleplan"));
        compareButton.setTooltip(new Tooltip("Compare cycleplans"));
        exportButton.setTooltip(new Tooltip("Export data"));
        filterButton.setTooltip(new Tooltip("Filter table"));
        settingsButton.setTooltip(new Tooltip("Setup view"));

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

        //Populate the filter TreeView
        CheckBoxTreeItem<String> root;
        //Root  
        root = new CheckBoxTreeItem<String>("Filtered values");
        root.setExpanded(true);
        //TODO
        //Fix this to allow image to be colored in case a filter is active
//        root.selectedProperty().addListener((obs, oldVal, newVal) -> {
//            System.out.println("Root intermediate: " + root.isIndeterminate());
//            System.out.println("Root selected: " + root.isSelected());
//        });
        //TODO
        //Note, this may be a bit inefficient
        //consider how to map to root only as this will trigger for all changes
        root.addEventHandler(CheckBoxTreeItem.checkBoxSelectionChangedEvent(), new EventHandler<TreeModificationEvent<Object>>() {

            @Override
            public void handle(TreeModificationEvent<Object> event) {
                if (root.isSelected() || root.isIndeterminate()) {
                    ColorAdjust blackout = new ColorAdjust();
                    blackout.setSaturation(-1.0);
                    
                    //Bland blend = new Blend(BlendMode.MULTIPLY, blackout, )

                    filterImage.setEffect(blackout);
                    filterImage.setCache(true);
                    filterImage.setCacheHint(CacheHint.SPEED);
                } else {
                    System.out.println("Inactive");
                }
            }
        });
        filterTree.setRoot(root);
        //filterTree.setPrefWidth(0);
        filterMenu.setPrefWidth(0);
        // Set handlers
        filterTree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        //Create branches without icons
        filterTree.setCellFactory(CheckBoxTreeCell.<String>forTreeView());

        //Populate the Hide columns TreeView
        CheckBoxTreeItem<String> rootSettings;
        rootSettings = new CheckBoxTreeItem<String>("All");
        rootSettings.setExpanded(true);
        settingsTree.setRoot(rootSettings);
        settingsMenu.setPrefWidth(0);
        settingsTree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        settingsTree.setCellFactory(CheckBoxTreeCell.<String>forTreeView());
    }

    //Helper method for creating the filter tree
    public CheckBoxTreeItem<String> makeBranchTreeItem(String title, CheckBoxTreeItem<String> parent) {
        CheckBoxTreeItem<String> item = new CheckBoxTreeItem<String>(title);
        item.setExpanded(false);
        parent.getChildren().add(item);
        return item;
    }

    private void updateVisibility() {
        for (TableColumn<TableVariant, ?> col : tableVariants.getColumns()) {
            if (visibleColumns.contains(col.getText())) {
                col.setVisible(true);
            } else {
                col.setVisible(false);
            }
        }
    }
}
