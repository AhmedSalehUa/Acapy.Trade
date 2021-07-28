package screens.members;

import acapy.trade.AcapyTrade;
import assets.animation.ZoomInLeft;
import assets.animation.ZoomInRight;
import assets.classes.AlertDialogs;
import static assets.classes.statics.DEFAULT_THEME;
import static assets.classes.statics.THEME;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
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
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class MembersScreenController implements Initializable {

    @FXML
    private BorderPane borderpane;
    @FXML
    private Button members;
    @FXML
    private Button solfaRewards;
    @FXML
    private Button transactions;
    @FXML
    private Button orders;
    @FXML
    private Button dailyCost;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private JFXHamburger hamburg;
    @FXML
    private Button salesMembers;
Preferences prefs;
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
//                                        clients.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
//                                            try {
//                                                clients.setStyle(" -fx-background-color: -mainColor-dark; ");
//                                                operations.setStyle(" -fx-background-color: -mainColor-light; ");
//                                                contracts.setStyle(" -fx-background-color: -mainColor-light; ");
//                                                visits.setStyle(" -fx-background-color: -mainColor-light; ");
//                                                salesOperations.setStyle(" -fx-background-color: -mainColor-light; ");
//                                                Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
//                                                if (User.canAccess("ClientScreenClients")) {
//                                                    node = FXMLLoader.load(getClass().getResource("ClientScreenClients.fxml"));
//                                                }
//                                                borderpane.setCenter(node);
//                                            } catch (IOException ex) {
//                                                ex.printStackTrace();
//                                                AlertDialogs.showErrors(ex);
//                                            }
//                                        });
//
//                                         
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
        new ZoomInLeft(members).play();
        new ZoomInRight(solfaRewards).play();
        new ZoomInRight(transactions).play();
        new ZoomInRight(orders).play();
        new ZoomInRight(dailyCost).play();
        new ZoomInRight(salesMembers).play();
       
    }

}
