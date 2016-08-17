/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpt.GUI;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javafx.scene.image.Image;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


/**
 * FXML Controller class
 *
 * @author colak
 */
public class MainWindowController implements Initializable {
  

    /**
     * Initializes the controller class.
     */
    @FXML
        TreeView<String> tree;
    @FXML
        Pane contentPane;
    
    
    //Icon for every branch (Manager group)
    private final Node programManagerIcon= new ImageView(
        new Image(getClass().getResourceAsStream("/Icons/manager.png"))
    );
    private final Node departmentManagerIcon= new ImageView(
        new Image(getClass().getResourceAsStream("/Icons/manager.png"))
    );
    private final Node sectionManagerIcon= new ImageView(
        new Image(getClass().getResourceAsStream("/Icons/manager.png"))
    );
    private final Node groupManagerIcon= new ImageView(
        new Image(getClass().getResourceAsStream("/Icons/manager.png"))
    );
    private final Node splCalibrationManagerIcon= new ImageView(
        new Image(getClass().getResourceAsStream("/Icons/manager.png"))
    );
    private final Node clCalibrationManagerIcon= new ImageView(
        new Image(getClass().getResourceAsStream("/Icons/manager.png"))
    );
     private final Node funkOptManagerIcon= new ImageView(
        new Image(getClass().getResourceAsStream("/Icons/manager.png"))
    );
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
    //Declaration of branches
    TreeItem<String> root;
    TreeItem <String> programManagerItem;
    TreeItem <String> departmentManagerItem;
    TreeItem <String> sectionManagerItem;
    TreeItem <String> groupManagerItem;
    TreeItem <String> splCalibrationManagerItem;
    TreeItem <String>clCalibrationManagerItem;
    TreeItem <String>funkOptManagerItem;
    
     //Root  
     root = new TreeItem<>("Root");
     root.setExpanded(true);
     tree.setRoot(root);
  
    //Program Manager branch
     programManagerItem= makeBranchTreeItem ("Program Manager", root,programManagerIcon);
     makeBranchTreeItem ("Projects", programManagerItem);
     makeBranchTreeItem ("Variants", programManagerItem);
  
     
     //Department branch
     departmentManagerItem= makeBranchTreeItem ("Department Manager", root,departmentManagerIcon);
     makeBranchTreeItem ("xx", departmentManagerItem);
     makeBranchTreeItem ("xx", departmentManagerItem);
     
      //Section branch
     sectionManagerItem= makeBranchTreeItem ("Section Manager", root,sectionManagerIcon);
     makeBranchTreeItem ("xx", sectionManagerItem);
     makeBranchTreeItem ("xx", sectionManagerItem);
     
     //Group Manager branch
     groupManagerItem= makeBranchTreeItem ("Group Manager", root, groupManagerIcon);
     makeBranchTreeItem ("Own resources", groupManagerItem);
     makeBranchTreeItem ("Roles", groupManagerItem);
     makeBranchTreeItem ("Roles allocation", groupManagerItem);
     makeBranchTreeItem ("Resources allocation", groupManagerItem);
     makeBranchTreeItem ("Resources overview", groupManagerItem);
     
      //SPL Calibration Manager branch
     splCalibrationManagerItem= makeBranchTreeItem ("SPL Calibration Manager", root, splCalibrationManagerIcon);
     makeBranchTreeItem ("Project information", splCalibrationManagerItem);
     makeBranchTreeItem ("Team overview", splCalibrationManagerItem);
     makeBranchTreeItem ("SPL Plan", splCalibrationManagerItem);
     makeBranchTreeItem ("Project status", splCalibrationManagerItem);
    
     //CL Calibration Manager branch
     clCalibrationManagerItem= makeBranchTreeItem ("CL Calibration Manager", root, clCalibrationManagerIcon);
     makeBranchTreeItem ("xx", clCalibrationManagerItem);
     makeBranchTreeItem ("xx", clCalibrationManagerItem);
  
     //Funk Opt Manager branch
     funkOptManagerItem = makeBranchTreeItem("Funk OPT Manager", root, funkOptManagerIcon);
     makeBranchTreeItem("xx", funkOptManagerItem);
    
     //Create tree
     //tree = new TreeView<String>(root);
     tree.setShowRoot(false);
     tree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
     tree.getSelectionModel().selectedItemProperty()
             .addListener((v , oldValue, newValue) -> {
                 if (newValue != null)
                     System.out.println(newValue.getValue());
                 switch (newValue.getValue()){
                     case "Roles":
                         System.out.println("Open roles:");
                         try {

                             //Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("/rpt/GUI/GroupManager/Roles.fxml"));           
                             // contentPane.getChildren().add(newLoadedPane);
                             //komentar
                             contentPane.getChildren().clear();

                             contentPane.getChildren().add(FXMLLoader.load(getClass().getResource("/rpt/GUI/GroupManager/Roles/Roles.fxml")));
                         } catch (IOException e) {
                             e.printStackTrace();
                         }
                         contentPane.autosize();
                         break;
                     case "Variants":
                         System.out.println("Open Variants:");
                         try {

                             //remove old content in main view pane
                             contentPane.getChildren().clear();

                             //load new view in main view pane
                             contentPane.getChildren().add(FXMLLoader.load(getClass().getResource("/rpt/GUI/ProgramManager/Variants/Variants.fxml")));
                         } catch (IOException e) {
                             e.printStackTrace();
                         }
                         contentPane.autosize();
                         break;
                     
                 }
             });
    
    }    
    //Create branches with icons
    public TreeItem<String> makeBranchTreeItem (String title, TreeItem <String> parent,Node icon){
        TreeItem<String> item = new TreeItem<>(title);
        item.setExpanded(false);
        parent.getChildren().add(item);
        item.setGraphic(icon);
        return item;
    }
    //Create branches withot icons
    public TreeItem<String> makeBranchTreeItem (String title, TreeItem <String> parent){
        TreeItem<String> item = new TreeItem<>(title);
        item.setExpanded(false);
        parent.getChildren().add(item);
        return item;
    }
    
//    public void mouseClick(MouseEvent mouseEvent){
//        if (mouseEvent.getClickCount() == 1) {
//            TreeItem<String> item = tree.getSelectionModel().getSelectedItem();
//            System.out.println("Selected: " + item.getValue());
//        }
//        
//   
//}
}
