/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package rpt.GUI.Utils;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import rpt.GUI.ProgramStrategist.CyclePlans.TableVariant;
import rpt.RPT;

/**
 *
 * @author colak
 */
public class VariantTable extends TableView<VariantTableRow> {
    /**
     * Initializes the controller class.
     */
    //Define table and buttons from FXML
    @FXML
    public TableView<TableVariant> variantsTable;
    
    private ArrayList<TableColumn<VariantTableRow, String>> columnList;
    public static ArrayList<String> visibleColumns = new ArrayList();
    // variable used for storing SQL queries
    private String query = "";
    private String selectedCyclePlan = ""; //Used to indicate which cycle plan to use when generating variant list view
    
    // ObservableList object enables the tracking of any changes to its elements
    private static ObservableList<VariantTableRow> data = FXCollections.observableArrayList();
    FilteredList<VariantTableRow> filteredData = new FilteredList<>(data, p -> true); //make it possible to filter
    Map<String, ArrayList<String>> filterList = new HashMap<String, ArrayList<String>>();
    List<String> columnFilterList = new ArrayList<String>();
    
    // TreeView for options to hide columns, sent to parent upon creation
    TreeView<String> hideColumnsTree;
    private TreeView<String> filterTree;
    
    //Add entry into table
    public static void add(VariantTableRow entry) {
        data.add(entry);
    }
    
    // Cycle plan combobox action handler
    public void loadProject(String project) {
        // load table with provided project
        try {
            Statement statement = RPT.conn.createStatement();
            statement.setQueryTimeout(30);
            // Extract column names from database if no columns exist in the table view
            if (this.getColumns().isEmpty()) {
                String query = "PRAGMA table_info(VARIANTS)"; //Get all column names
                ResultSet rsColumns = statement.executeQuery(query);
                
                while (rsColumns.next()) {
                    String colName = rsColumns.getString("name");
                    columnFilterList.add(colName);
                    TableColumn<VariantTableRow, String> column = new TableColumn<VariantTableRow, String>(colName);
                    column.setCellValueFactory(new PropertyValueFactory<>(colName));
                    column.setCellFactory(TextFieldTableCell.forTableColumn());
                    column.setOnEditCommit(new EventHandler<CellEditEvent<VariantTableRow, String>>() {
                        @Override
                        public void handle(CellEditEvent<VariantTableRow, String> event) {
                            ((VariantTableRow) event.getTableView().getItems().get(event.getTablePosition().getRow())).setEngineName(event.getNewValue());
                        }
                    });
                    // Add the extracted columns into the table
                    //TODO
                    // Add a KV map to translate table names in SQL to nicely presented column names
                    this.getColumns().add(column);
                    visibleColumns.add(colName);
                    CheckBoxTreeItem<String> root = (CheckBoxTreeItem<String>) hideColumnsTree.getRoot();
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
                        //updateVisibility();
                    });
                    
                }
            }
            //Populate filter view
            Collections.sort(columnFilterList);
            try {
                CheckBoxTreeItem<String> root = (CheckBoxTreeItem<String>) filterTree.getRoot();
                //For each column, extract distinct values and insert in tree structure below that column CheckBox
                for (String column : columnFilterList) {
                    query = "SELECT DISTINCT VARIANTS." + column + " FROM VARIANTS, VariantBelongsToProject WHERE "
                            + "VARIANTS.VariantID = VariantBelongsToProject.VariantID  AND "
                            + "VariantBelongsToProject.ProjectName = \'" + project + "\' ORDER BY VARIANTS." + column;
                    ResultSet rsColumns = statement.executeQuery(query);
                    CheckBoxTreeItem<String> parent = makeBranchTreeItem(column, root);
                    
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
                System.err.println("VariantTable loadProject() ERROR 1: " + e.getMessage());
                e.printStackTrace();
            }
//HERE ******************
// Change from cycle plan to project
// Read variants and add to table
query = "SELECT * FROM VARIANTS, VariantBelongsToCyclePlan WHERE "
        + "VariantBelongsToCyclePlan.CyclePlanID= \'" + selectedCyclePlan + "\' AND VARIANTS.VariantID = VariantBelongsToCyclePlan.VariantID";
ResultSet rs = statement.executeQuery(query);

data.clear();

while (rs.next()) {
    VariantTableRow entry = new VariantTableRow(rs.getString("Plant"),
            rs.getString("Platform"), rs.getString("Vehicle"), rs.getString("Propulsion"),
            rs.getString("Denomination"), rs.getString("Fuel"), rs.getString("EngineFamily"), rs.getString("Generation"),
            "EngineName not used", rs.getString("EngineCode"), rs.getString("Displacement"), rs.getString("EnginePower"),
            rs.getString("ElMotorPower"), rs.getString("Torque"), rs.getString("TorqueOverBoost"), rs.getString("GearboxType"),
            rs.getString("Gears"), rs.getString("Gearbox"), rs.getString("Driveline"), rs.getString("TransmissionCode"),
            rs.getString("certGroup"), rs.getString("EmissionClass"), rs.getString("StartOfProd"), rs.getString("EndOfProd"));
    add(entry);
}

//cyclePlanSelector.setItems(cyclePlanList);
//TODO Add buttons for table view control
//filterButton.setDisable(false);
//settingsButton.setDisable(false);
        } catch (Exception e) {
            System.err.println("VariantTable  ERROR 1: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    //Filter button action handler
//    public void filterButtonClicked() {
//        if (filterButton.isSelected()) {
//            //TODO set filter conditions of data
//            //if (filterMenu.getPrefWidth() == 0) {
//            //filterTree.setPrefWidth(200);
//            filterMenu.setPrefWidth(Region.USE_COMPUTED_SIZE);
//            filterHeader.setText("Filtered Values");
//        } else {
//            //filterTree.setPrefWidth(0);
//            filterMenu.setPrefWidth(0);
//            filterHeader.setText("");
//        }
//
//    }
//
//    //Settings button action handler
//    public void settingsButtonClicked() throws IOException {
//
//        if (settingsButton.isSelected()) {
//            settingsMenu.setPrefWidth(Region.USE_COMPUTED_SIZE);
//            settingsHeader.setText("Hidden Columns");
//        } else {
//            //filterTree.setPrefWidth(0);
//            settingsMenu.setPrefWidth(0);
//            settingsHeader.setText("");
//        }
//    }
    //Helper method for creating the filter tree
    public CheckBoxTreeItem<String> makeBranchTreeItem(String title, CheckBoxTreeItem<String> parent) {
        CheckBoxTreeItem<String> item = new CheckBoxTreeItem<String>(title);
        item.setExpanded(false);
        parent.getChildren().add(item);
        return item;
    }
    
//    private void updateVisibility() {
//        for (TableColumn<VariantTableRow, ?> col : tableVariants.getColumns()) {
//            if (visibleColumns.contains(col.getText())) {
//                col.setVisible(true);
//            } else {
//                col.setVisible(false);
//            }
//        }
//    }
//}
    
    
    public void variantTable() {
        variantsTable.setEditable(true);
//Set the filter Predicate whenever the filter changes.
SortedList<VariantTable> sortedData = new SortedList<>(filteredData);
sortedData.comparatorProperty().bind(variantsTable.comparatorProperty());
//Push into the table
variantsTable.setItems(sortedData);

//Add Tooltip to the add and remove icons
//addButton.setTooltip(new Tooltip("Add new item"));
//removeButton.setTooltip(new Tooltip("Remove selected items"));
//saveButton.setTooltip(new Tooltip("Save"));
//importButton.setTooltip(new Tooltip("Import Cycleplan"));
//compareButton.setTooltip(new Tooltip("Compare cycleplans"));
//exportButton.setTooltip(new Tooltip("Export data"));
//filterButton.setTooltip(new Tooltip("Filter table"));
//settingsButton.setTooltip(new Tooltip("Setup view"));

// empty cycle plan list in case one arrives from other view
/cyclePlanList.clear();
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
    
    
}
