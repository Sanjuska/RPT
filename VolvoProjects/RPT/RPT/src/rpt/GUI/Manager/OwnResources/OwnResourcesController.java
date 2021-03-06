/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpt.GUI.Manager.OwnResources;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author colak
 */
public class OwnResourcesController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    public TableView<TableOwnResources> tableOwnResources;
    
     @FXML
    public TableView<TableOwnResources> tableMonths;
    
    @FXML
    public TableColumn<TableOwnResources, String> nameColumn;
    
    @FXML
    public TableColumn<TableOwnResources, String>  resourcesTypeColumn;
    
    @FXML
    public TableColumn<TableOwnResources, String> monthAColumn;
    
    @FXML
    public TableColumn<TableOwnResources, TableOwnResources> monthBColumn;
    
    @FXML
    public TableColumn<TableOwnResources, String> monthCColumn;
    
    @FXML
    public TableColumn<TableOwnResources, String> monthDColumn;
    
    @FXML
    public TableColumn<TableOwnResources, TableOwnResources> monthColumn;
    
    @FXML
    Button addButton;
    
    @FXML
    Button removeButton;
    
    @FXML
    Button saveButton;
    
    @FXML
    Button applyButton;
     
    @FXML
    DatePicker startDatePicker;
    
    @FXML
    DatePicker finishDatePicker;
   
    @FXML
    Arc arc;
    private final String pattern = "yyyy-MM-dd";
    
    private LocalDate currentColumnMonth;

    
    // ObservableList object enables the tracking of any changes to its elements
    //private static ObservableList<TableOwnResources> data = FXCollections.observableArrayList(
    //        new TableOwnResources("Tupak", "Employee", "100%",35,"3%","95%"),
    //        new TableOwnResources("Bubca", "Employee", "85%", 85,"15%", "100%")
    //);

    // ObservableList object enables the tracking of any changes to its elements
    private static ObservableList<TableOwnResources> data = FXCollections.observableArrayList(
            new TableOwnResources("Tupak", "Employee", new int[] {100,35,3,95, 75, 45, 30}),
            new TableOwnResources("Bubca", "Employee", new int[] {85, 85,15, 100, 90, 30, 30})
    );
    
    //Create table's data, get all of the items
    public static ObservableList<TableOwnResources> getRoles() {
        return data;
    }

    //Add entry into table
    public static void add(TableOwnResources entry) {
        data.add(entry);
    }

    @FXML
    public void addButtonClicked(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        if (event.getSource() == addButton) {
            //Open the PopUp window with implementation fields
            stage = new Stage();
            root = FXMLLoader.load(getClass().getResource("/rpt/GUI/GroupManager/Roles/AddDialog.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Add role");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(addButton.getScene().getWindow());
            stage.showAndWait();
        } 
        

    }
    //The remove button action. Removes highlighted rows.
    public void removeButtonClicked (){
        ObservableList<TableOwnResources> removeVariants;
        tableOwnResources.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        removeVariants = tableOwnResources.getSelectionModel().getSelectedItems();
        tableOwnResources.getItems().removeAll(removeVariants);
    }
    
    //The apply button action. Clears the table and rebuilds with selected dates.
    public void applyButtonClicked (){
        tableMonths.getColumns().clear();
        populateTable();
    }
    
    //calculate difference between months
    private int monthsBetween(LocalDate start, LocalDate end) {
    int count = 0;
    LocalDate curr = start.plusDays(0); // Create copy.
        while (curr.isBefore(end)) {
            count++;
            curr = curr.plusMonths(1); // Increment by a month.
        }
        return count;
    }
    //main method for populating the table.
    //Initially called populating from current date to 6 months ahead
    //later called when the apply button is clicked
    private void populateTable(){
        //TODO
        //new read from database to replace contents in data        
        
        int endColumn = monthsBetween(startDatePicker.getValue(), finishDatePicker.getValue());
        
        //specify a cell factory  and enable it editable
//        nameColumn.setText("Name");
//        nameColumn.setCellValueFactory(new PropertyValueFactory("nameColumn")); //connect to the private variables in the table
//        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//        nameColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableOwnResources, String>>() {
//            @Override
//            public void handle(TableColumn.CellEditEvent<TableOwnResources, String> event) {
//                ((TableOwnResources) event.getTableView().getItems().get(event.getTablePosition().getRow())).setNameColumn(event.getNewValue());
//            }
//         
//        });
//        tableOwnResources.getColumns().add(nameColumn);
        
//        resourcesTypeColumn.setText("Resource Type");
//        resourcesTypeColumn.setCellValueFactory(new PropertyValueFactory("resourcesType")); //connect to the private variables in the table
//        resourcesTypeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//        resourcesTypeColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableOwnResources, String>>() {
//            @Override
//            public void handle(TableColumn.CellEditEvent<TableOwnResources, String> event) {
//                ((TableOwnResources) event.getTableView().getItems().get(event.getTablePosition().getRow())).setResourcesType(event.getNewValue());
//            }
//         
//        });
//        tableOwnResources.getColumns().add(resourcesTypeColumn);
        
        currentColumnMonth = startDatePicker.getValue().plusDays(0);
        while (!(currentColumnMonth.isAfter(finishDatePicker.getValue()))){
            monthColumn = new TableColumn(currentColumnMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH));
            monthColumn.setCellValueFactory(new Callback<CellDataFeatures<TableOwnResources, TableOwnResources>, ObservableValue<TableOwnResources>>() {
              @Override public ObservableValue<TableOwnResources> call(CellDataFeatures<TableOwnResources, TableOwnResources> features) {
                  return new ReadOnlyObjectWrapper(features.getValue());
              }
            });
            //monthColumn.setComparator(new Comparator<TableOwnResources>() {
            //   @Override public int compare(TableOwnResources row1, TableOwnResources row2) {
            //        return row1.getMonthB() - row2.getMonthB();
            //        }
            //   });
            monthColumn.setCellFactory(new Callback<TableColumn<TableOwnResources, TableOwnResources>, TableCell<TableOwnResources, TableOwnResources>>() {
              @Override public TableCell<TableOwnResources, TableOwnResources> call(TableColumn<TableOwnResources, TableOwnResources> monthBColumn) {
                return new TableCell<TableOwnResources, TableOwnResources>() {
                    final Arc arcCell = new Arc(); 
                    {
                        arcCell.setCenterX(5.0f);
                        arcCell.setCenterY(5.0f);
                        arcCell.setRadiusX(10.0f);
                        arcCell.setRadiusY(10.0f);
                        arcCell.setStartAngle(90.0f);
                        arcCell.setType(ArcType.ROUND);
                        arcCell.setStrokeType(StrokeType.OUTSIDE);
                        arcCell.setStroke(Color.BLACK);
                        Color fillColor = Color.rgb(102,220,0,1.0);
                        arcCell.setFill(fillColor);

                  }
                  @Override public void updateItem(final TableOwnResources resource, boolean empty) {
                    super.updateItem(resource, empty);


                    if (resource != null) {
                        TableColumn<TableOwnResources, TableOwnResources> column = getTableColumn();
                        int colIndex = getTableView().getColumns().indexOf(column);
                        //add try and catch nullpointer exception, create empty arc if null
                        try {
                            arcCell.setLength(-(resource.getMonth()[colIndex-2]*360/100));
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("Empty cell");
                            arcCell.setLength(0);
                        }
                        
                        setGraphic(arcCell);
                        setAlignment(Pos.CENTER);

                      }
                  }
                };
              }
            });
            tableMonths.getColumns().add(monthColumn);
            currentColumnMonth = currentColumnMonth.plusMonths(1);
        }
        tableOwnResources.getColumns().add(monthColumn); // add one hidden column for resizing
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Calendar preferences
        //Convert calendar style from / to -
        StringConverter converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        //Start day calendar
        startDatePicker.setConverter(converter);
        startDatePicker.setValue(LocalDate.now());
        //Finish date calendar
        finishDatePicker.setConverter(converter);
        finishDatePicker.setValue(startDatePicker.getValue().plusMonths(6));
         
        tableOwnResources.columnResizePolicyProperty();
        
        tableOwnResources.setEditable(true);

//        //specify a cell factory  and enable it editable
//        nameColumn.setCellValueFactory(new PropertyValueFactory("nameColumn")); //connect to the private variables in the table
//        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//        nameColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableOwnResources, String>>() {
//            @Override
//            public void handle(TableColumn.CellEditEvent<TableOwnResources, String> event) {
//                ((TableOwnResources) event.getTableView().getItems().get(event.getTablePosition().getRow())).setNameColumn(event.getNewValue());
//            }
//         
//        });
//        resourcesTypeColumn.setCellValueFactory(new PropertyValueFactory("resourcesType")); //connect to the private variables in the table
//        resourcesTypeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//        resourcesTypeColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableOwnResources, String>>() {
//            @Override
//            public void handle(TableColumn.CellEditEvent<TableOwnResources, String> event) {
//                ((TableOwnResources) event.getTableView().getItems().get(event.getTablePosition().getRow())).setResourcesType(event.getNewValue());
//            }
//         
//        });
        //monthAColumn.setCellValueFactory(new PropertyValueFactory("monthA")); //connect to the private variables in the table
        //monthAColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        //monthAColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableOwnResources, String>>() {
        //    @Override
        //    public void handle(TableColumn.CellEditEvent<TableOwnResources, String> event) {
        //        ((TableOwnResources) event.getTableView().getItems().get(event.getTablePosition().getRow())).setMonthA(event.getNewValue());
        //    }
         
        //});
        //monthBColumn.setCellValueFactory(new Callback<CellDataFeatures<TableOwnResources, TableOwnResources>, ObservableValue<TableOwnResources>>() {
        //  @Override public ObservableValue<TableOwnResources> call(CellDataFeatures<TableOwnResources, TableOwnResources> features) {
        //      return new ReadOnlyObjectWrapper(features.getValue());
        //  }
        //});
        //monthBColumn.setComparator(new Comparator<TableOwnResources>() {
        //   @Override public int compare(TableOwnResources row1, TableOwnResources row2) {
        //        return row1.getMonthB() - row2.getMonthB();
        //        }
        //   });
//        monthBColumn.setCellFactory(new Callback<TableColumn<TableOwnResources, TableOwnResources>, TableCell<TableOwnResources, TableOwnResources>>() {
//          @Override public TableCell<TableOwnResources, TableOwnResources> call(TableColumn<TableOwnResources, TableOwnResources> monthBColumn) {
//            return new TableCell<TableOwnResources, TableOwnResources>() {
//                final Arc arcCell = new Arc(); 
//                {
//                    arcCell.setCenterX(5.0f);
//                    arcCell.setCenterY(5.0f);
//                    arcCell.setRadiusX(10.0f);
//                    arcCell.setRadiusY(10.0f);
//                    arcCell.setStartAngle(90.0f);
//                    arcCell.setType(ArcType.ROUND);
//                    arcCell.setStrokeType(StrokeType.OUTSIDE);
//                    arcCell.setStroke(Color.BLACK);
//                    Color fillColor = Color.rgb(102,220,0,1.0);
//                    arcCell.setFill(fillColor);
//                    
//              }
//              @Override public void updateItem(final TableOwnResources resource, boolean empty) {
//                super.updateItem(resource, empty);
//                
//                
//                if (resource != null) {
//                      arcCell.setLength(-(resource.getMonthB()*360/100));
//                      setGraphic(arcCell);
//                      setAlignment(Pos.CENTER);
//                      
//                  }
//                else {
//                  System.out.println("Empty row");
//                }
//              }
//            };
//          }
//        });
//         
//        
//        monthCColumn.setCellValueFactory(new PropertyValueFactory("monthC")); //connect to the private variables in the table
//        monthCColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//        monthCColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableOwnResources, String>>() {
//            @Override
//            public void handle(TableColumn.CellEditEvent<TableOwnResources, String> event) {
//                ((TableOwnResources) event.getTableView().getItems().get(event.getTablePosition().getRow())).setMonthC(event.getNewValue());
//            }
//         
//        });
//        monthDColumn.setCellValueFactory(new PropertyValueFactory("monthD")); //connect to the private variables in the table
//        monthDColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//        monthDColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableOwnResources, String>>() {
//            @Override
//            public void handle(TableColumn.CellEditEvent<TableOwnResources, String> event) {
//                ((TableOwnResources) event.getTableView().getItems().get(event.getTablePosition().getRow())).setMonthD(event.getNewValue());
//            }
//         
//        });
         
        //specify a cell factory  and enable it editable
        nameColumn.setCellValueFactory(new PropertyValueFactory("nameColumn")); //connect to the private variables in the table
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableOwnResources, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TableOwnResources, String> event) {
                ((TableOwnResources) event.getTableView().getItems().get(event.getTablePosition().getRow())).setNameColumn(event.getNewValue());
            }
         
        });
        resourcesTypeColumn.setCellValueFactory(new PropertyValueFactory("resourcesType")); //connect to the private variables in the table
        resourcesTypeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        resourcesTypeColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableOwnResources, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TableOwnResources, String> event) {
                ((TableOwnResources) event.getTableView().getItems().get(event.getTablePosition().getRow())).setResourcesType(event.getNewValue());
            }
         
        });
        
        tableMonths.getColumns().clear();
        populateTable();
        //Push into the table
        tableOwnResources.setItems(data);
        tableMonths.setItems(data);
        
        //tooltips
         addButton.setTooltip(new Tooltip("Add new name"));
         removeButton.setTooltip(new Tooltip("Remove selected names"));  
         saveButton.setTooltip(new Tooltip ("Save"));
        
         
    }
}