/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.members;

import assets.classes.AlertDialogs;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;

import screens.members.assets.AcapyMembers;


/**
 * FXML Controller class
 *
 * @author Ahmed Al-Gazzar
 */
public class MemberScreenRewardSolfaController implements Initializable {

    @FXML
    private ComboBox<AcapyMembers> member;
    @FXML
    private TabPane tabs;
    @FXML
    private AnchorPane detailsPane;
    @FXML
    private AnchorPane memberPane;
   
       Label lblid = new Label();
   
       Label lblName = new Label();
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                   clear();
                                  configPanels();
                                    fillCombo1();

                                } catch (Exception ex) {
                                    AlertDialogs.showErrors(ex);
                                } finally {
                                    latch.countDown();
                                }
                            }
                        });
                        latch.await();

                        return null;
                    }
                };

            }

            @Override
            protected void succeeded() {

             
                super.succeeded();
            }
        };
        service.start();
         member.setOnMouseClicked((e) -> {
            if (member.getSelectionModel().getSelectedIndex() == -1) {

            } else {
              

                AcapyMembers selected = member.getSelectionModel().getSelectedItem();

                
                ObservableList<AcapyMembers> items3 = member.getItems();
                for (AcapyMembers a : items3) {
                    if (a.getName().equals(selected.getName())) {
                        member.getSelectionModel().select(a);
                       
                    }
                }
               tabs.setVisible(true);
               solfaController.setId(selected.getId());

                rewardController.setId(selected.getId());

            }
        });
    }
     MemberScreenRewardController rewardController;
    MemberScreenSolfaController solfaController;

    public void configPanels() {

        try {
            detailsPane.getChildren().clear();
            FXMLLoader fxShow = new FXMLLoader(getClass().getResource("MemberScreenSolfa.fxml"));
            detailsPane.getChildren().add(fxShow.load());
            solfaController = fxShow.getController();
            solfaController.setParentController(MemberScreenRewardSolfaController.this);

            memberPane.getChildren().clear();
            FXMLLoader fxEdite = new FXMLLoader(getClass().getResource("MemberScreenReward.fxml"));
            memberPane.getChildren().add(fxEdite.load());
            rewardController = fxEdite.getController();
            rewardController.setParentController(MemberScreenRewardSolfaController.this);
        } catch (IOException ex) {
            AlertDialogs.showErrors(ex);
        }
    }

 private void fillCombo1() {
       
        Service<Void> service = new Service<Void>() {
            ObservableList<AcapyMembers> data;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            data = AcapyMembers.getData();

                        } catch (Exception ex) {
                            AlertDialogs.showErrors(ex);
                        }
                        return null;
                    }
                };

            }

            @Override
            protected void succeeded() {
               

                member.setItems(data);
                member.setConverter(new StringConverter<AcapyMembers>() {
                    @Override
                    public String toString(AcapyMembers patient) {
                        return patient.getName();
                    }

                    @Override
                    public AcapyMembers fromString(String string) {
                        return null;
                    }
                });
                member.setCellFactory(cell -> new ListCell<AcapyMembers>() {

                    // Create our layout here to be reused for each ListCell
                    GridPane gridPane = new GridPane();
                    Label lblid = new Label();
                     Label lblName = new Label();

                    // Static block to configure our layout
                    {
                        // Ensure all our column widths are constant
                        gridPane.getColumnConstraints().addAll(
                                new ColumnConstraints(100, 100, 100),
                                new ColumnConstraints(100, 100, 100)
                        );

                        gridPane.add(lblid, 0, 1);
                        gridPane.add(lblName, 1, 1);

                    }

                    // We override the updateItem() method in order to provide our own layout for this Cell's graphicProperty
                    @Override
                    protected void updateItem(AcapyMembers person, boolean empty) {
                        super.updateItem(person, empty);

                        if (!empty && person != null) {

                            // Update our Labels
                            lblid.setText("م: " + Integer.toString(person.getId()));
                            lblName.setText("الاسم: " + person.getName());
                            

                            setGraphic(gridPane);
                        } else {
                            // Nothing to display here
                            setGraphic(null);
                        }
                    }
                });
                super.succeeded();
            }
        };
        service.start();

    }    
 
    private void clear(){
     tabs.setVisible(false);
    }
    ObservableList<AcapyMembers> items;
    
}
