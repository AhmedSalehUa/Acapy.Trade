/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.mainDataScreen;

import acapy.trade.AcapyTrade;
import assets.animation.ZoomInLeft;
import assets.animation.ZoomInRight;
import assets.classes.AlertDialogs;
import assets.classes.statics;
import static assets.classes.statics.*;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import db.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author ahmed
 */
public class MainDataScreenController implements Initializable {

    @FXML
    private BorderPane borderpane;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private JFXHamburger hamburg;
    Preferences prefs;
    @FXML
    private Button clients;
    private Button products;
    private Button provider;
    @FXML
    private Button visits;
    @FXML
    private Button contracts;
    @FXML
    private Button operations;
    @FXML
    private Button clientAccounts;

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
                                        clients.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                                            try {
                                                clients.setStyle(" -fx-background-color: -mainColor-dark; ");
                                                provider.setStyle(" -fx-background-color: -mainColor-light; ");
                                                products.setStyle(" -fx-background-color: -mainColor-light; ");
                                                Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                                                if (User.canAccess("MainDataScreenClients")) {
                                                    node = FXMLLoader.load(getClass().getResource("MainDataScreenClients.fxml"));
                                                }
                                                borderpane.setCenter(node);
                                            } catch (IOException ex) {
                                                ex.printStackTrace();
                                                AlertDialogs.showErrors(ex);
                                            }
                                        });

                                        products.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                                            try {
                                                products.setStyle(" -fx-background-color: -mainColor-dark; ");
                                                clients.setStyle(" -fx-background-color: -mainColor-light; ");
                                                provider.setStyle(" -fx-background-color: -mainColor-light; ");
                                                Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                                                if (User.canAccess("MainDataScreenProducts")) {
                                                    node = FXMLLoader.load(getClass().getResource("MainDataScreenProducts.fxml"));
                                                }
                                                borderpane.setCenter(node);
                                            } catch (IOException ex) {
                                                ex.printStackTrace();
                                                AlertDialogs.showErrors(ex);
                                            }
                                        });
                                        provider.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                                            try {
                                                provider.setStyle(" -fx-background-color: -mainColor-dark; ");
                                                clients.setStyle(" -fx-background-color: -mainColor-light; ");
                                                products.setStyle(" -fx-background-color: -mainColor-light; ");
                                                Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                                                if (User.canAccess("MainDataScreenProvider")) {
                                                    node = FXMLLoader.load(getClass().getResource("MainDataScreenProvider.fxml"));
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
        new ZoomInRight(products).play();
        new ZoomInRight(provider).play();

    }

    private void changeView(String id, MouseEvent e) {

    }

}
