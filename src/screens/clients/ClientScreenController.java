/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.clients;

import acapy.trade.AcapyTrade;
import assets.animation.ZoomInLeft;
import assets.animation.ZoomInRight;
import assets.classes.AlertDialogs;
import static assets.classes.statics.DEFAULT_THEME;
import static assets.classes.statics.NoPermission;
import static assets.classes.statics.THEME;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import db.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.prefs.Preferences;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class ClientScreenController implements Initializable {

    @FXML
    private BorderPane borderpane;
    @FXML
    private Button clients;
    @FXML
    private Button visits;
    @FXML
    private Button contracts;
    @FXML
    private Button operations; 

    Preferences prefs;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private JFXHamburger hamburg;
    @FXML
    private Button salesOperations;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prefs = Preferences.userNodeForPackage(AcapyTrade.class);
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        //Background work                       
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    try {
                                        configDrawer();
                                        clients.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                                            try {
                                                clients.setStyle(" -fx-background-color: -mainColor-dark; ");
                                                operations.setStyle(" -fx-background-color: -mainColor-light; ");
                                                contracts.setStyle(" -fx-background-color: -mainColor-light; ");
                                                visits.setStyle(" -fx-background-color: -mainColor-light; ");
                                                salesOperations.setStyle(" -fx-background-color: -mainColor-light; ");
                                                Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                                                if (User.canAccess("ClientScreenClients")) {
                                                    node = FXMLLoader.load(getClass().getResource("ClientScreenClients.fxml"));
                                                }
                                                borderpane.setCenter(node);
                                            } catch (IOException ex) {
                                                ex.printStackTrace();
                                                AlertDialogs.showErrors(ex);
                                            }
                                        });

                                        visits.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                                            try {
                                                visits.setStyle(" -fx-background-color: -mainColor-dark; ");
                                                clients.setStyle(" -fx-background-color: -mainColor-light; ");
                                                operations.setStyle(" -fx-background-color: -mainColor-light; ");
                                                contracts.setStyle(" -fx-background-color: -mainColor-light; ");
                                                salesOperations.setStyle(" -fx-background-color: -mainColor-light; ");
                                                Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                                                if (User.canAccess("ClientScreenMaintainces")) {
                                                    node = FXMLLoader.load(getClass().getResource("ClientScreenMaintainces.fxml"));
                                                }
                                                borderpane.setCenter(node);
                                            } catch (IOException ex) {
                                                ex.printStackTrace();
                                                AlertDialogs.showErrors(ex);
                                            }
                                        });

                                        operations.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                                            try {
                                                operations.setStyle(" -fx-background-color: -mainColor-dark; ");
                                                clients.setStyle(" -fx-background-color: -mainColor-light; ");
                                                visits.setStyle(" -fx-background-color: -mainColor-light; ");
                                                contracts.setStyle(" -fx-background-color: -mainColor-light; ");
                                                salesOperations.setStyle(" -fx-background-color: -mainColor-light; ");
                                                Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                                                if (User.canAccess("ClientScreenOperations")) {
                                                    node = FXMLLoader.load(getClass().getResource("ClientScreenOperations.fxml"));
                                                }
                                                borderpane.setCenter(node);
                                            } catch (IOException ex) {
                                                ex.printStackTrace();
                                                AlertDialogs.showErrors(ex);
                                            }
                                        });
                                        contracts.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                                            try {
                                                contracts.setStyle(" -fx-background-color: -mainColor-dark; ");
                                                clients.setStyle(" -fx-background-color: -mainColor-light; ");
                                                visits.setStyle(" -fx-background-color: -mainColor-light; ");
                                                operations.setStyle(" -fx-background-color: -mainColor-light; ");
                                                salesOperations.setStyle(" -fx-background-color: -mainColor-light; ");
                                                Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                                                if (User.canAccess("ClientScreenContract")) {
                                                    node = FXMLLoader.load(getClass().getResource("ClientScreenContract.fxml"));
                                                }
                                                borderpane.setCenter(node);
                                            } catch (IOException ex) {
                                                ex.printStackTrace();
                                                AlertDialogs.showErrors(ex);
                                            }
                                        });
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                        AlertDialogs.showErrors(ex);

                                    }
                                } catch (Exception ex) {
                                    AlertDialogs.showErrors(ex);
                                } finally {
                                    latch.countDown();
                                }
                            }

                            private void configDrawer() {
                                try {

                                    AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/Navigator/SideNavigator.fxml"));

                                    anchorPane.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());

                                    drawer.setSidePane(anchorPane);

                                    double drawerx = drawer.getLayoutX();
                                    double drawery = drawer.getLayoutY();
                                    drawer.setLayoutX(drawerx + 250);
                                    drawer.setLayoutY(drawery);
                                    drawer.setVisible(false);
                                    drawer.setMaxWidth(0);

                                    drawer.setOnDrawerOpening(event
                                            -> {
                                        drawer.setLayoutX(drawerx);
                                        drawer.setLayoutY(drawery);
                                        drawer.setMaxWidth(250);
                                    });

                                    drawer.setOnDrawerClosed(event
                                            -> {
                                        drawer.setLayoutX(drawerx + 250);
                                        drawer.setLayoutY(drawery);
                                        drawer.setVisible(false);
                                        drawer.setMaxWidth(0);
                                    });
                                    for (Node node : anchorPane.getChildren()) {

                                        node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {

                                            changeView(node.getId(), e);
                                        });

                                    }
                                    HamburgerBackArrowBasicTransition nav = new HamburgerBackArrowBasicTransition(hamburg);
                                    nav.setRate(nav.getRate() * -1);
                                    nav.play();
                                    hamburg.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                                        nav.setRate(nav.getRate() * -1);
                                        nav.play();
                                        drawer.setVisible(true);
                                        if (drawer.isOpened()) {
                                            drawer.close();
                                        } else {
                                            drawer.open();
                                        }
                                    });
                                } catch (Exception e) {
                                    AlertDialogs.showErrors(e);
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
        new ZoomInLeft(clients).play();
        new ZoomInRight(contracts).play();
        new ZoomInRight(operations).play();
        new ZoomInRight(visits).play();
        new ZoomInRight(salesOperations).play();
    }

    private void changeView(String id, MouseEvent e) {

    }

}
