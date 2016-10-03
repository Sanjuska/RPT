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
import javafx.scene.layout.BorderPane;


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
    //@FXML
        //Pane contentPane;
    @FXML
        BorderPane viewPane;
    
    
    
    
    //Icon for every branch (Manager group)
     private final Node researchAndDevelopmentIcon= new ImageView(
        new Image(getClass().getResourceAsStream("/Icons/manager.png"))
    );
    private final Node programStrategistIcon= new ImageView(
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
    private final Node uplCalibrationManagerIcon= new ImageView(
        new Image(getClass().getResourceAsStream("/Icons/manager.png"))
    );
    private final Node splCalibrationManagerIcon= new ImageView(
        new Image(getClass().getResourceAsStream("/Icons/manager.png"))
    );
    private final Node kaswIcon= new ImageView(
        new Image(getClass().getResourceAsStream("/Icons/manager.png"))
    );
    private final Node swtlIcon= new ImageView(
        new Image(getClass().getResourceAsStream("/Icons/manager.png"))
    );
    private final Node calibrationLeaderIcon= new ImageView(
        new Image(getClass().getResourceAsStream("/Icons/manager.png"))
    );
    private final Node calibrationEngineerIcon= new ImageView(
        new Image(getClass().getResourceAsStream("/Icons/manager.png"))
    );
    private final Node emsTechnicianIcon= new ImageView(
        new Image(getClass().getResourceAsStream("/Icons/manager.png"))
    );
    private final Node meIcon= new ImageView(
        new Image(getClass().getResourceAsStream("/Icons/manager.png"))
    );
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
    //Declaration of branches
    TreeItem<String> root;
    TreeItem <String> researchAndDevelopmentItem;
    TreeItem <String> programManagerItem;
    TreeItem <String> departmentManagerItem;
    TreeItem <String> sectionManagerItem;
    TreeItem <String> groupManagerItem;
    TreeItem <String> splCalibrationManagerItem;
    TreeItem <String>clCalibrationManagerItem;
    TreeItem <String>funkOptManagerItem;
    TreeItem <String>meItem;
    
     //Root  
     root = new TreeItem<>("Root");
     root.setExpanded(true);
     tree.setRoot(root);
     
     //R & D branch branch
     researchAndDevelopmentItem= makeBranchTreeItem ("Research and Development", root,researchAndDevelopmentIcon);
     makeBranchTreeItem ("Development Process", researchAndDevelopmentItem);
  
    //Program Manager branch
     programManagerItem= makeBranchTreeItem ("Program Strategist", root,programStrategistIcon);
     makeBranchTreeItem ("Projects", programManagerItem);
     makeBranchTreeItem ("Cycle Plans", programManagerItem);
     
     //Department branch
     departmentManagerItem= makeBranchTreeItem ("Department Manager", root,departmentManagerIcon);
     makeBranchTreeItem ("Calculations", departmentManagerItem);
     makeBranchTreeItem ("Section status", departmentManagerItem);
     
     //Section branch
     sectionManagerItem= makeBranchTreeItem ("Section Manager", root,sectionManagerIcon);
     makeBranchTreeItem ("Deliveries", sectionManagerItem);
     makeBranchTreeItem ("Calculations", sectionManagerItem);
     makeBranchTreeItem ("Group status", sectionManagerItem);
     
     //Group Manager branch
     groupManagerItem= makeBranchTreeItem ("Group Manager", root, groupManagerIcon);
     makeBranchTreeItem ("Own resources", groupManagerItem);
     makeBranchTreeItem ("Roles", groupManagerItem);
     makeBranchTreeItem ("Roles allocation", groupManagerItem);
     makeBranchTreeItem ("Resources allocation", groupManagerItem);
     
     //UPL Calibration Manager branch
     splCalibrationManagerItem= makeBranchTreeItem ("Unit Program Leader (UPL)", root, uplCalibrationManagerIcon);
     makeBranchTreeItem ("Project information", splCalibrationManagerItem);
     makeBranchTreeItem ("UPL Plan", splCalibrationManagerItem);
     makeBranchTreeItem ("Project status", splCalibrationManagerItem);
     
     //SPL Calibration Manager branch
     splCalibrationManagerItem= makeBranchTreeItem ("System Program Leader (SPL)", root, splCalibrationManagerIcon);
     makeBranchTreeItem ("Project information", splCalibrationManagerItem);
     makeBranchTreeItem ("Team overview", splCalibrationManagerItem);
     makeBranchTreeItem ("SPL Plan", splCalibrationManagerItem);
     makeBranchTreeItem ("Project status", splCalibrationManagerItem);
    
     //KASW Calibration Manager branch
     splCalibrationManagerItem= makeBranchTreeItem ("KASW", root, kaswIcon);
     makeBranchTreeItem ("Project information", splCalibrationManagerItem);
     makeBranchTreeItem ("KASW Plan", splCalibrationManagerItem);
     
     //SWTL Calibration Manager branch
     splCalibrationManagerItem= makeBranchTreeItem ("SWTL", root, swtlIcon);
     makeBranchTreeItem ("Project information", splCalibrationManagerItem);
     makeBranchTreeItem ("Team overview", splCalibrationManagerItem);
     makeBranchTreeItem ("SWTL Plan", splCalibrationManagerItem);
     
     //CL Calibration Manager branch
     clCalibrationManagerItem= makeBranchTreeItem ("Calibration Leader (CL)", root, calibrationLeaderIcon);
     makeBranchTreeItem ("Project Information", clCalibrationManagerItem);
     makeBranchTreeItem ("Team Overview", clCalibrationManagerItem);
     makeBranchTreeItem ("CL Plan", clCalibrationManagerItem);
     makeBranchTreeItem ("Resources", clCalibrationManagerItem);
  
     //Funk Opt Manager branch
     funkOptManagerItem = makeBranchTreeItem("Calibration Engineer", root, calibrationEngineerIcon);
     makeBranchTreeItem("Project Activities", funkOptManagerItem);
     
     //Funk Opt Manager branch
     funkOptManagerItem = makeBranchTreeItem("EMS Technician", root, emsTechnicianIcon);
     makeBranchTreeItem("Rig plans", funkOptManagerItem);
     
     //Funk Opt Manager branch
     funkOptManagerItem = makeBranchTreeItem("Me", root, meIcon);
     makeBranchTreeItem("Late", funkOptManagerItem);
     makeBranchTreeItem("Upcoming", funkOptManagerItem);
     makeBranchTreeItem("Done", funkOptManagerItem);
     makeBranchTreeItem("Project Information", funkOptManagerItem);
    
     //Create tree
     //tree = new TreeView<String>(root);
     tree.setShowRoot(false);
     tree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
     tree.getSelectionModel().selectedItemProperty()
             .addListener((v , oldValue, newValue) -> {
                 if (newValue != null)
                     System.out.println(newValue.getValue());
                 switch (newValue.getValue()){
                      case "Development Process":
                         System.out.println("Open Development Process:");
                         try {
                             //load new view in main view pane 
                             viewPane.setCenter(FXMLLoader.load(getClass().getResource("/rpt/GUI/ResearchAndDevelopment/DevelopmentProcess/DevelopmentProcess.fxml")));
                         } catch (IOException e) {
                             e.printStackTrace();
                         }
                         //contentPane.autosize();
                         break;
                      case "Own resources":
                         System.out.println("Open Own resources:");
                         try {
                             //load new view in main view pane 
                             viewPane.setCenter(FXMLLoader.load(getClass().getResource("/rpt/GUI/GroupManager/OwnResources/OwnResources.fxml")));
                         } catch (IOException e) {
                             e.printStackTrace();
                         }
                         //contentPane.autosize();
                         break;
                     case "Roles":
                         System.out.println("Open roles:");
                         try {
                             //load new view in main view pane 
                             viewPane.setCenter(FXMLLoader.load(getClass().getResource("/rpt/GUI/GroupManager/Roles/Roles.fxml")));
                         } catch (IOException e) {
                             e.printStackTrace();
                         }
                         break;
                     case "Cycle Plans":
                         System.out.println("Open Cycle Plans:");
                         try {
                             //load new view in main view pane   
                             viewPane.setCenter(FXMLLoader.load(getClass().getResource("/rpt/GUI/ProgramStrategist/CyclePlans/CyclePlans.fxml")));
                         } catch (IOException e) {
                             e.printStackTrace();
                         }
                         break;
                         case "Deliveries":
                         System.out.println("Open Deliveries:");
                         try {
                             //load new view in main view pane   
                             viewPane.setCenter(FXMLLoader.load(getClass().getResource("/rpt/GUI/SectionManager/Deliveries/Deliveries.fxml")));
                         } catch (IOException e) {
                             e.printStackTrace();
                         }
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
