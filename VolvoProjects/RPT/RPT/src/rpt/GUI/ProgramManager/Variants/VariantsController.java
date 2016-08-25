/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package rpt.GUI.ProgramManager.Variants;

import rpt.GUI.ProgramManager.*;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * FXML Controller class
 *
 * @author colak
 */
public class VariantsController implements Initializable {
    
    
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
            Button addButton;        
    @FXML
            Button removeButton;
    @FXML
            Button saveButton;
    @FXML
            Button importButton;
            
   
    //Create table's data, get all of the items
    public static ObservableList<TableVariants> getVariants(){
        return data;
    }
    // ObservableList object enables the tracking of any changes to its elements
    private static ObservableList<TableVariants> data = FXCollections.observableArrayList(
           new TableVariants (1, "Engine 1", "denomination2", "gearbox3", "emissions")
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
            System.out.println("File selected: " + selectedFile.getPath());
            FileInputStream inputStream = new FileInputStream(new File(selectedFile.getPath()));
            Workbook workbook = new HSSFWorkbook(inputStream);
            
            //Use Sheet iterator to extract all sheet names
            Iterator<Sheet> sheetIterator = workbook.sheetIterator();
            
            //Iterate over all sheets and populate a checkboxfield and let user select on of the sheets
            while (sheetIterator.hasNext()){
                Sheet nextSheet = sheetIterator.next();
                //TODO
                //Create dialogbox with a radio button selection showing all the available sheets.
                //Clicking on cancel has to break the main if or protect the rest with an if that Cancel wasnt clicked
            }
            
            //set sheet to the sheet selected
            Sheet firstSheet = workbook.getSheet("Raw data CP2016A"); //Change to variable name at later date
            Iterator<Row> rowIterator = firstSheet.iterator();
            
            //find first row
            //TODO
            //set first row keyword into application settings
            Boolean firstRowFound = false;
            Cell nextCell;
            Iterator<Cell> cellIterator;
            do{
                Row nextRow = rowIterator.next();
                cellIterator = nextRow.cellIterator();
                nextCell = cellIterator.next();
                if (getCellValue(nextCell) != null){//blank cells return null
                    if (getCellValue(nextCell).equals("Plant")) {
                        firstRowFound = true;
                    }
                }
                
            }while(!firstRowFound && rowIterator.hasNext());
            
            //First row is now found, loop through entire row and build a
            //dictionary using HashMaps
            Map<Integer, String> dictionary = new HashMap<Integer, String>();
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
//                        switch (columnIndex) {
//                            case 0:
//                                aVariant.setPlant((String) getCellValue(nextCell));
//                                break;
//                            case 1:
//                                Double value = ((Double) getCellValue(nextCell));
//                                aVariant.setPlantCode(value.intValue());
//                                break;
//                            case 2:
//                                aVariant.setPlantName((String) getCellValue(nextCell));
//                                break;
//                            default:
//                                break;      
//                        }
                    } else {
                    }
                    
                }
                variants.add(aVariant);
            }       
            inputStream.close();
            
            //now that we have a list of Variants we put them into the tableView
            data.clear();
            int index = 1;
            for (Variant variant : variants){
                TableVariants entry = new TableVariants(index, variant.getEngineName(), variant.getDenomination(), variant.getGearbox(), variant.getEmiss());
                add(entry);
                index++;
            }
            
        }//TODO remove the else, we don't do anything if the user presses Cancel
        else {
            System.out.println("File selection cancelled.");
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
        emissionsCategory.setCellValueFactory(new PropertyValueFactory<>("emissionsCategory"));
        emissionsCategory.setCellFactory(TextFieldTableCell.forTableColumn());
        emissionsCategory.setOnEditCommit(new EventHandler<CellEditEvent<TableVariants, String>>() {
            @Override
            public void handle(CellEditEvent<TableVariants, String> event) {
              ((TableVariants) event.getTableView().getItems().get(event.getTablePosition().getRow())).setEmissionsCategory(event.getNewValue());
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
