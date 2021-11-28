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
import javafx.event.ActionEvent;
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
import screens.sales.assets.SalesMembers;

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
    @FXML
    private ComboBox<SalesMembers> sales;

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
    }
    MemberScreenRewardController memberRewardController;
    MemberScreenSolfaController memberSolfaController;

    SalesScreenRewardController salesRewardController;
    SalesScreenSolfaController salesSolfaController;

    public void configPanelsForMembers() {

        try {
            detailsPane.getChildren().clear();
            FXMLLoader fxShow = new FXMLLoader(getClass().getResource("MemberScreenSolfa.fxml"));
            detailsPane.getChildren().add(fxShow.load());
            memberSolfaController = fxShow.getController();
            memberSolfaController.setParentController(MemberScreenRewardSolfaController.this);

            memberPane.getChildren().clear();
            FXMLLoader fxEdite = new FXMLLoader(getClass().getResource("MemberScreenReward.fxml"));
            memberPane.getChildren().add(fxEdite.load());
            memberRewardController = fxEdite.getController();
            memberRewardController.setParentController(MemberScreenRewardSolfaController.this);
        } catch (IOException ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    public void configPanelsForSales() {

        try {
            detailsPane.getChildren().clear();
            FXMLLoader fxShow = new FXMLLoader(getClass().getResource("SalesScreenSolfa.fxml"));
            detailsPane.getChildren().add(fxShow.load());
            salesSolfaController = fxShow.getController();
            salesSolfaController.setParentController(MemberScreenRewardSolfaController.this);

            memberPane.getChildren().clear();
            FXMLLoader fxEdite = new FXMLLoader(getClass().getResource("SalesScreenReward.fxml"));
            memberPane.getChildren().add(fxEdite.load());
            salesRewardController = fxEdite.getController();
            salesRewardController.setParentController(MemberScreenRewardSolfaController.this);
        } catch (IOException ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void fillCombo1() {

        Service<Void> service = new Service<Void>() {
            ObservableList<AcapyMembers> data;
            ObservableList<SalesMembers> salesData;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            data = AcapyMembers.getData();
                            salesData = SalesMembers.getData();
                        } catch (Exception ex) {
                            AlertDialogs.showErrors(ex);
                        }
                        return null;
                    }
                };

            }

            @Override
            protected void succeeded() {
                sales.setItems(salesData);
                sales.setConverter(new StringConverter<SalesMembers>() {
                    @Override
                    public String toString(SalesMembers patient) {
                        return patient.getName();
                    }

                    @Override
                    public SalesMembers fromString(String string) {
                        return null;
                    }
                });
                sales.setCellFactory(cell -> new ListCell<SalesMembers>() {

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
                    protected void updateItem(SalesMembers person, boolean empty) {
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

    private void clear() {
        tabs.setVisible(false);
    }
    ObservableList<AcapyMembers> items;

    @FXML
    private void getDataFor(ActionEvent event) {
        if (member.getSelectionModel().getSelectedIndex() > -1) {
            if (sales.getSelectionModel().getSelectedIndex() > -1) {
                sales.getSelectionModel().clearSelection();
            } 
            configPanelsForMembers();
           
            AcapyMembers selected = member.getSelectionModel().getSelectedItem();

            ObservableList<AcapyMembers> items3 = member.getItems();
            for (AcapyMembers a : items3) {
                if (a.getName().equals(selected.getName())) {
                    member.getSelectionModel().select(a);

                }
            }
            tabs.setVisible(true);
            memberSolfaController.setId(selected.getId());

            memberRewardController.setId(selected.getId());
        }
    }

    @FXML
    private void getDataForSales(ActionEvent event) {
        if (sales.getSelectionModel().getSelectedIndex() > -1) {
            if (member.getSelectionModel().getSelectedIndex() > -1) {
                member.getSelectionModel().clearSelection();
            }
            configPanelsForSales();
            SalesMembers selected = sales.getSelectionModel().getSelectedItem();

            ObservableList<SalesMembers> items3 = sales.getItems();
            for (SalesMembers a : items3) {
                if (a.getName().equals(selected.getName())) {
                    sales.getSelectionModel().select(a);

                }
            }
            tabs.setVisible(true);
            salesSolfaController.setId(selected.getId());

            salesRewardController.setId(selected.getId());
        }
    }

}
