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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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
            BorderPane viewPane;
    @FXML
            Label messageLabel;
    
    //Icon for every branch (Manager group)
    private final Node ceoIcon = new ImageView(
            new Image(getClass().getResourceAsStream("/Icons/manager.png"))
    );
    private final Node researchAndDevelopmentIcon = new ImageView(
            new Image(getClass().getResourceAsStream("/Icons/manager.png"))
    );
    private final Node unitIcon = new ImageView(
            new Image(getClass().getResourceAsStream("/Icons/manager.png"))
    );
    private final Node programStrategistIcon = new ImageView(
            new Image(getClass().getResourceAsStream("/Icons/manager.png"))
    );
    private final Node departmentManagerIcon = new ImageView(
            new Image(getClass().getResourceAsStream("/Icons/manager.png"))
    );
    private final Node sectionManagerIcon = new ImageView(
            new Image(getClass().getResourceAsStream("/Icons/manager.png"))
    );
    private final Node groupManagerIcon = new ImageView(
            new Image(getClass().getResourceAsStream("/Icons/manager.png"))
    );
    private final Node uplCalibrationManagerIcon = new ImageView(
            new Image(getClass().getResourceAsStream("/Icons/manager.png"))
    );
    private final Node splCalibrationManagerIcon = new ImageView(
            new Image(getClass().getResourceAsStream("/Icons/manager.png"))
    );
    private final Node calibrationLeaderIcon = new ImageView(
            new Image(getClass().getResourceAsStream("/Icons/manager.png"))
    );
    private final Node calibrationEngineerIcon = new ImageView(
            new Image(getClass().getResourceAsStream("/Icons/manager.png"))
    );
    private final Node emsTechnicianIcon = new ImageView(
            new Image(getClass().getResourceAsStream("/Icons/manager.png"))
    );
    private final Node kaswIcon = new ImageView(
            new Image(getClass().getResourceAsStream("/Icons/manager.png"))
    );
    private final Node swtlIcon = new ImageView(
            new Image(getClass().getResourceAsStream("/Icons/manager.png"))
    );
    private final Node swintIcon = new ImageView(
            new Image(getClass().getResourceAsStream("/Icons/manager.png"))
    );
    private final Node sa1Icon = new ImageView(
            new Image(getClass().getResourceAsStream("/Icons/manager.png"))
    );
    private final Node sa2Icon = new ImageView(
            new Image(getClass().getResourceAsStream("/Icons/manager.png"))
    );
    private final Node sa3Icon = new ImageView(
            new Image(getClass().getResourceAsStream("/Icons/manager.png"))
    );
    private final Node sa4Icon = new ImageView(
            new Image(getClass().getResourceAsStream("/Icons/manager.png"))
    );
    private final Node functionDeveloperIcon = new ImageView(
            new Image(getClass().getResourceAsStream("/Icons/manager.png"))
    );
    private final Node meIcon = new ImageView(
            new Image(getClass().getResourceAsStream("/Icons/manager.png"))
    );
    
    public void logOutClicked(ActionEvent event) throws IOException{
        LoginController.userID = ""; // reset user ID
        //Switch to another stage - Main stage
        Parent root = FXMLLoader.load(getClass().getResource("/rpt/GUI/LoginGUI.fxml"));
        Scene scene = new Scene(root);
        Stage stage=(Stage) tree.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    // Exit program when user clicks File -> Close
    public void closeClicked(){
        System.out.println("Closing...");
        System.exit(0);
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Declaration of branches
        TreeItem<String> root;
        TreeItem<String> researchAndDevelopmentItem;
        TreeItem<String> programManagerItem;
        TreeItem<String> unitItem;
        TreeItem<String> departmentManagerItem;
        TreeItem<String> sectionManagerItem;
        TreeItem<String> groupManagerItem;
        TreeItem<String> splCalibrationManagerItem;
        TreeItem<String> clCalibrationManagerItem;
        TreeItem<String> funkOptManagerItem;
        TreeItem<String> kaswItem;
        TreeItem<String> swtlItem;
        TreeItem<String> swintItem;
        TreeItem<String> sa1Item;
        TreeItem<String> sa2Item;
        TreeItem<String> sa3Item;
        TreeItem<String> sa4Item;
        TreeItem<String> functionDeveloperItem;
        TreeItem<String> meItem;
        
        //Root
        root = new TreeItem<>("Root");
        root.setExpanded(true);
        tree.setRoot(root);
        
        // ----  LINE ORGANISATION ---- //
        
        //Program Strategist branch
        programManagerItem = makeBranchTreeItem("PS & VLM", root, programStrategistIcon);
        makeBranchTreeItem("Cycle Plans", programManagerItem);
        
        //R & D branch
        researchAndDevelopmentItem = makeBranchTreeItem("R&D Manager", root, researchAndDevelopmentIcon);
        makeBranchTreeItem("Own Resources", researchAndDevelopmentItem);
        makeBranchTreeItem("Roles", researchAndDevelopmentItem);
        makeBranchTreeItem("Roles Allocation", researchAndDevelopmentItem);
        makeBranchTreeItem("Resource allocation", researchAndDevelopmentItem);
        makeBranchTreeItem("Manage Organisation", researchAndDevelopmentItem);
        makeBranchTreeItem("Manage Processes", researchAndDevelopmentItem);
        makeBranchTreeItem("Process Deliveries", researchAndDevelopmentItem);
        makeBranchTreeItem("Manage Projects", researchAndDevelopmentItem);
        makeBranchTreeItem("R&D Overview", researchAndDevelopmentItem);
        
        //Unit branch
        unitItem = makeBranchTreeItem("Unit Manager", root, unitIcon);
        makeBranchTreeItem("Own Resources", unitItem);
        makeBranchTreeItem("Roles", unitItem);
        makeBranchTreeItem("Roles Allocation", unitItem);
        makeBranchTreeItem("Resource allocation", unitItem);
        makeBranchTreeItem("Manage Organisation", unitItem);
        makeBranchTreeItem("Process Deliveries", unitItem);
        makeBranchTreeItem("Manage Projects", unitItem);
        makeBranchTreeItem("Unit Overview", unitItem);
        
        //Department branch
        departmentManagerItem = makeBranchTreeItem("Department Manager", root, departmentManagerIcon);
        makeBranchTreeItem("Own Resources", departmentManagerItem);
        makeBranchTreeItem("Roles", departmentManagerItem);
        makeBranchTreeItem("Roles Allocation", departmentManagerItem);
        makeBranchTreeItem("Resource allocation", departmentManagerItem);
        makeBranchTreeItem("Manage Organisation", departmentManagerItem);
        makeBranchTreeItem("Process Deliveries", departmentManagerItem);
        makeBranchTreeItem("Manage Projects", departmentManagerItem);
        makeBranchTreeItem("Department Overview", departmentManagerItem);
        
        //Section branch
        sectionManagerItem = makeBranchTreeItem("Section Manager", root, sectionManagerIcon);
        makeBranchTreeItem("Own Resources", sectionManagerItem);
        makeBranchTreeItem("Roles", sectionManagerItem);
        makeBranchTreeItem("Roles Allocation", sectionManagerItem);
        makeBranchTreeItem("Resource allocation", sectionManagerItem);
        makeBranchTreeItem("Manage Organisation", sectionManagerItem);
        makeBranchTreeItem("Process Deliveries", sectionManagerItem);
        makeBranchTreeItem("Manage Projects", sectionManagerItem);
        makeBranchTreeItem("Section Overview", sectionManagerItem);
        
        //Group Manager branch
        groupManagerItem = makeBranchTreeItem("Group Manager", root, groupManagerIcon);
        makeBranchTreeItem("Own Resources", groupManagerItem);
        makeBranchTreeItem("Roles", groupManagerItem);
        makeBranchTreeItem("Roles Allocation", groupManagerItem);
        makeBranchTreeItem("Resource allocation", groupManagerItem);
        makeBranchTreeItem("Process Deliveries", groupManagerItem);
        makeBranchTreeItem("Manage Projects", groupManagerItem);
        makeBranchTreeItem("Group Overview", groupManagerItem);
        
        // ----  PROJECT ORGANISATION ---- //
        
        //UPL Calibration Manager branch
        splCalibrationManagerItem = makeBranchTreeItem("Unit Program Leader (UPL)", root, uplCalibrationManagerIcon);
        makeBranchTreeItem("Project information", splCalibrationManagerItem);
        makeBranchTreeItem("Team overview", splCalibrationManagerItem);
        makeBranchTreeItem("UPL Plan", splCalibrationManagerItem);
        makeBranchTreeItem("Project Activities", splCalibrationManagerItem);
        
        //SPL Calibration Manager branch
        splCalibrationManagerItem = makeBranchTreeItem("System Program Leader (SPL)", root, splCalibrationManagerIcon);
        makeBranchTreeItem("Project information", splCalibrationManagerItem);
        makeBranchTreeItem("Team overview", splCalibrationManagerItem);
        makeBranchTreeItem("SPL Plan", splCalibrationManagerItem);
        makeBranchTreeItem("Project Activities", splCalibrationManagerItem);
        
        //CL Calibration Manager branch
        clCalibrationManagerItem = makeBranchTreeItem("Calibration Leader (CL)", root, calibrationLeaderIcon);
        makeBranchTreeItem("Project Information", clCalibrationManagerItem);
        makeBranchTreeItem("Team Overview", clCalibrationManagerItem);
        makeBranchTreeItem("CL Plan", clCalibrationManagerItem);
        makeBranchTreeItem("Project Activities", clCalibrationManagerItem);
        
        // Calibration Engineer branch
        funkOptManagerItem = makeBranchTreeItem("Calibration Engineer", root, calibrationEngineerIcon);
        makeBranchTreeItem("Project Information", funkOptManagerItem);
        makeBranchTreeItem("Team Overview", funkOptManagerItem);
        makeBranchTreeItem("Calibration Engineer Plan", funkOptManagerItem);
        makeBranchTreeItem("Project Activities", funkOptManagerItem);
        
        //EMS Technician branch
        funkOptManagerItem = makeBranchTreeItem("EMS Technician", root, emsTechnicianIcon);
        makeBranchTreeItem("Project Information", funkOptManagerItem);
        makeBranchTreeItem("Team Overview", funkOptManagerItem);
        makeBranchTreeItem("EMS Technician Plan", funkOptManagerItem);
        makeBranchTreeItem("Rig Pans", funkOptManagerItem);
        
        //KASW branch
        kaswItem = makeBranchTreeItem("KASW", root, kaswIcon);
        makeBranchTreeItem("Project information", kaswItem);
        makeBranchTreeItem("Team overview", kaswItem);
        makeBranchTreeItem("KASW Plan", kaswItem);
        makeBranchTreeItem("Project Activities", kaswItem);
        
        //SWTL branch
        swtlItem = makeBranchTreeItem("SWTL", root, swtlIcon);
        makeBranchTreeItem("Project information", swtlItem);
        makeBranchTreeItem("Team overview", swtlItem);
        makeBranchTreeItem("SWTL Plan", swtlItem);
        makeBranchTreeItem("Project Activities", swtlItem);
        
        //SWINT branch
        swintItem = makeBranchTreeItem("SWINT", root, swintIcon);
        makeBranchTreeItem("Project information", swintItem);
        makeBranchTreeItem("Team overview", swintItem);
        makeBranchTreeItem("SWINT Plan", swintItem);
        makeBranchTreeItem("Project Activities", swintItem);
        
        //SA1 branch
        splCalibrationManagerItem = makeBranchTreeItem("SA1", root, sa1Icon);
        makeBranchTreeItem("Project information", splCalibrationManagerItem);
        makeBranchTreeItem("Team overview", splCalibrationManagerItem);
        makeBranchTreeItem("SA1 Plan", splCalibrationManagerItem);
        makeBranchTreeItem("Project Activities", splCalibrationManagerItem);
        
        //SA2 branch
        splCalibrationManagerItem = makeBranchTreeItem("SA2", root, sa2Icon);
        makeBranchTreeItem("Project information", splCalibrationManagerItem);
        makeBranchTreeItem("Team overview", splCalibrationManagerItem);
        makeBranchTreeItem("SA2 Plan", splCalibrationManagerItem);
        makeBranchTreeItem("Project Activities", splCalibrationManagerItem);
        
        //SA3 branch
        splCalibrationManagerItem = makeBranchTreeItem("SA3", root, sa3Icon);
        makeBranchTreeItem("Project information", splCalibrationManagerItem);
        makeBranchTreeItem("Team overview", splCalibrationManagerItem);
        makeBranchTreeItem("SA3 Plan", splCalibrationManagerItem);
        makeBranchTreeItem("Project Activities", splCalibrationManagerItem);
        
        //SA4 branch
        splCalibrationManagerItem = makeBranchTreeItem("SA4", root, sa4Icon);
        makeBranchTreeItem("Project information", splCalibrationManagerItem);
        makeBranchTreeItem("Team overview", splCalibrationManagerItem);
        makeBranchTreeItem("SA4 Plan", splCalibrationManagerItem);
        makeBranchTreeItem("Project Activities", splCalibrationManagerItem);
        
        //Function Developer branch
        splCalibrationManagerItem = makeBranchTreeItem("Function Developer", root, functionDeveloperIcon);
        makeBranchTreeItem("Project information", splCalibrationManagerItem);
        makeBranchTreeItem("Team overview", splCalibrationManagerItem);
        makeBranchTreeItem("Function Developer Plan", splCalibrationManagerItem);
        makeBranchTreeItem("Project Activities", splCalibrationManagerItem);
        
        //My branch
        funkOptManagerItem = makeBranchTreeItem("Me", root, meIcon);
        makeBranchTreeItem("Project Information", funkOptManagerItem);
        makeBranchTreeItem("Late", funkOptManagerItem);
        makeBranchTreeItem("Upcoming", funkOptManagerItem);
        makeBranchTreeItem("Done", funkOptManagerItem);
        
        
        //Create tree
        //tree = new TreeView<String>(root);
        tree.setShowRoot(false);
        tree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tree.getSelectionModel().selectedItemProperty()
                .addListener((v, oldValue, newValue) -> {
                    if (newValue != null) {
                        System.out.println(newValue.getValue());
                    }
                    switch (newValue.getValue()) {
                        case "Manage Processes":
                            System.out.println("Open Development Process:");
                            try {
                                //load new view in main view pane
                                viewPane.setCenter(FXMLLoader.load(getClass().getResource("/rpt/GUI/ResearchAndDevelopment/DevelopmentProcess/DevelopmentProcess.fxml")));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            //contentPane.autosize();
                            break;
                        case "Own Resources":
                            System.out.println("Open Own resources:");
                            try {
                                //load new view in main view pane
                                viewPane.setCenter(FXMLLoader.load(getClass().getResource("/rpt/GUI/Manager/OwnResources/OwnResources.fxml")));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            //contentPane.autosize();
                            break;
                        case "Roles":
                            System.out.println("Open roles:");
                            try {
                                //load new view in main view pane
                                viewPane.setCenter(FXMLLoader.load(getClass().getResource("/rpt/GUI/Manager/Roles/Roles.fxml")));
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
        messageLabel.setText("No messages");
    }
    
    //Create branches with icons
    public TreeItem<String> makeBranchTreeItem(String title, TreeItem<String> parent, Node icon) {
        TreeItem<String> item = new TreeItem<>(title);
        item.setExpanded(false);
        parent.getChildren().add(item);
        item.setGraphic(icon);
        return item;
    }
    
    //Create branches without icons
    public TreeItem<String> makeBranchTreeItem(String title, TreeItem<String> parent) {
        TreeItem<String> item = new TreeItem<>(title);
        item.setExpanded(false);
        parent.getChildren().add(item);
        return item;
    }
    
}
