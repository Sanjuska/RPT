/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpt.GUI.GroupManager.OwnResources;

import static java.awt.PageAttributes.ColorType.COLOR;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import rpt.GUI.ProgramManager.TableVariants;

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
    
    static Pane arcContainer = new Pane();
    @FXML
    Arc arc;

    
    // ObservableList object enables the tracking of any changes to its elements
    private static ObservableList<TableOwnResources> data = FXCollections.observableArrayList(
            new TableOwnResources("Tupak", "Employee", "100%",35,"3%","95%"),
            new TableOwnResources("Bubca", "Employee", "85%", 85,"15%", "100%")
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
    //Give the function to remove button
    public void removeButtonClicked (){
    ObservableList<TableOwnResources> removeVariants;
    tableOwnResources.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    removeVariants = tableOwnResources.getSelectionModel().getSelectedItems();
    tableOwnResources.getItems().removeAll(removeVariants);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableOwnResources.setEditable(true);

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
        monthAColumn.setCellValueFactory(new PropertyValueFactory("monthA")); //connect to the private variables in the table
        monthAColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        monthAColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableOwnResources, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TableOwnResources, String> event) {
                ((TableOwnResources) event.getTableView().getItems().get(event.getTablePosition().getRow())).setMonthA(event.getNewValue());
            }
         
        });
        //TODO
        //Use the code example from below to allow insertion of graphical objects instead of text.
        //insert arc shape filled to the level of employee load. <90% => yellow, 90-105% => greenYellow, >105% => red
        //http://stackoverflow.com/questions/16360323/javafx-table-how-to-add-components
        //monthBColumn.setCellValueFactory(new PropertyValueFactory("monthB")); //connect to the private variables in the table
        //monthBColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        //monthBColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableOwnResources, String>>() {
        //    @Override
        //    public void handle(TableColumn.CellEditEvent<TableOwnResources, String> event) {
        //        ((TableOwnResources) event.getTableView().getItems().get(event.getTablePosition().getRow())).setMonthB(event.getNewValue());
        //    }
         
        //});
        monthBColumn.setCellValueFactory(new Callback<CellDataFeatures<TableOwnResources, TableOwnResources>, ObservableValue<TableOwnResources>>() {
          @Override public ObservableValue<TableOwnResources> call(CellDataFeatures<TableOwnResources, TableOwnResources> features) {
              return new ReadOnlyObjectWrapper(features.getValue());
          }
        });
        monthBColumn.setComparator(new Comparator<TableOwnResources>() {
           @Override public int compare(TableOwnResources row1, TableOwnResources row2) {
                return row1.getMonthB() - row2.getMonthB();
                }
           });
        monthBColumn.setCellFactory(new Callback<TableColumn<TableOwnResources, TableOwnResources>, TableCell<TableOwnResources, TableOwnResources>>() {
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
                    Color fillColor = Color.rgb(102,255,0,1.0);
                    arcCell.setFill(fillColor);
                    
              }
              @Override public void updateItem(final TableOwnResources resource, boolean empty) {
                super.updateItem(resource, empty);
                
                
                if (resource != null) {
                      arcCell.setLength(-(resource.getMonthB()*360/100));
                      setGraphic(arcCell);
                      setAlignment(Pos.CENTER);
                      
                  }
                else {
                  System.out.println("Empty row");
                }
              }
            };
          }
        });
         
        
        monthCColumn.setCellValueFactory(new PropertyValueFactory("monthC")); //connect to the private variables in the table
        monthCColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        monthCColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableOwnResources, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TableOwnResources, String> event) {
                ((TableOwnResources) event.getTableView().getItems().get(event.getTablePosition().getRow())).setMonthC(event.getNewValue());
            }
         
        });
        monthDColumn.setCellValueFactory(new PropertyValueFactory("monthD")); //connect to the private variables in the table
        monthDColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        monthDColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableOwnResources, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TableOwnResources, String> event) {
                ((TableOwnResources) event.getTableView().getItems().get(event.getTablePosition().getRow())).setMonthD(event.getNewValue());
            }
         
        });
         

        //Push into the table
        tableOwnResources.setItems(data);
        
        //tooltips
         addButton.setTooltip(new Tooltip("Add new name"));
         removeButton.setTooltip(new Tooltip("Remove selected names"));  
         saveButton.setTooltip(new Tooltip ("Save"));
        
         //TUPAK Demo Arc
         arcContainer.setPrefSize(12, 12);
         arcContainer.setMaxSize(12, 12);
         arcContainer.setMinSize(12, 12);
         //arc.setCenterX(5.0f);
         //arc.setCenterY(5.0f);
         //arc.setRadiusX(10.0f);
         //arc.setRadiusY(10.0f);
         //arc.setStartAngle(90.0f);
         //arc.setLength(225.0f);
         //arc.setType(ArcType.ROUND);
         //arc.setStrokeType(StrokeType.OUTSIDE);
         //arc.setStroke(Color.GREENYELLOW);
         //arcContainer.getChildren().add(arc);
         arc.setLength(-195);
        
    }

}
