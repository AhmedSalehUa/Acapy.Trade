/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Navigator;
 
import acapy.trade.AcapyTrade;
import assets.classes.AlertDialogs;
import assets.classes.statics;
import static assets.classes.statics.*;
import db.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ahmed
 */
public class SideNavigatorController implements Initializable {

    @FXML
    private Label mainLabel;
    @FXML
    private ImageView imgview; 

    Preferences prefs;
    @FXML
    private Button mainDataButton; 
    @FXML
    private Button storeButton; 
    @FXML
    private Button salesButton;
    @FXML
    private Button accountsButton;
    @FXML
    private Button invicesButton;
    @FXML
    private Button hrButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prefs = Preferences.userNodeForPackage(AcapyTrade.class);
        mainLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            try {

                Parent login = FXMLLoader.load(getClass().getResource("/Navigator/Home.fxml"));
                login.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(statics.THEME, statics.DEFAULT_THEME) + ".css").toExternalForm());
                Scene sc = new Scene(login);
                Stage st = (Stage) mainLabel.getScene().getWindow();
                st.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icons/logo.png")));
                st.setTitle("Acapy Trade");
                st.centerOnScreen();
                st.setScene(sc);
                st.show();
            } catch (IOException ex) {
                AlertDialogs.showErrors(ex);
            }
        });
        mainDataButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if (User.canAccess("MainData")) {
                try {

                    Parent login = FXMLLoader.load(getClass().getResource(MainData));
                    login.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(statics.THEME, statics.DEFAULT_THEME) + ".css").toExternalForm());
                    Scene sc = new Scene(login);
                    Stage st = (Stage) mainLabel.getScene().getWindow();
                    st.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icons/logo.png")));
                    st.setTitle("Acapy Trade (البيانات الرئيسية)");
                    st.centerOnScreen();
                    st.setScene(sc);
                    st.show();
                } catch (IOException ex) {
                    AlertDialogs.showErrors(ex);
                }
            } else {
                AlertDialogs.permissionDenied();
            }

        });
        salesButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if (User.canAccess("Sales")) {
                try {

                    Parent login = FXMLLoader.load(getClass().getResource(SalesScreen));
                    login.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(statics.THEME, statics.DEFAULT_THEME) + ".css").toExternalForm());
                    Scene sc = new Scene(login);
                    Stage st = (Stage) mainLabel.getScene().getWindow();
                    st.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icons/logo.png")));
                    st.setTitle("Acapy Trade (المبيعات)");
                    st.centerOnScreen();
                    st.setScene(sc);
                    st.show();
                } catch (IOException ex) {
                    AlertDialogs.showErrors(ex);
                }
            } else {
                AlertDialogs.permissionDenied();
            }

        });
        storeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if (User.canAccess("Store")) {
                try {

                    Parent login = FXMLLoader.load(getClass().getResource(StoreScreen));
                    login.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(statics.THEME, statics.DEFAULT_THEME) + ".css").toExternalForm());
                    Scene sc = new Scene(login);
                    Stage st = (Stage) mainLabel.getScene().getWindow();
                    st.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icons/logo.png")));
                    st.setTitle("Acapy Trade (المخازن)");
                    st.centerOnScreen();
                    st.setScene(sc);
                    st.show();
                } catch (IOException ex) {
                    AlertDialogs.showErrors(ex);
                }
            } else {
                AlertDialogs.permissionDenied();
            }

        });
        accountsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if (User.canAccess("Accounts")) {
                try {

                    Parent login = FXMLLoader.load(getClass().getResource(AccountsScreen));
                    login.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(statics.THEME, statics.DEFAULT_THEME) + ".css").toExternalForm());
                    Scene sc = new Scene(login);
                    Stage st = (Stage) mainLabel.getScene().getWindow();
                    st.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icons/logo.png")));
                    st.setTitle("Acapy Trade (الحسابات)");
                    st.centerOnScreen();
                    st.setScene(sc);
                    st.show();
                } catch (IOException ex) {
                    AlertDialogs.showErrors(ex);
                }
            } else {
                AlertDialogs.permissionDenied();
            }

        });
        hrButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if (User.canAccess("Hr")) {
                try {

                    Parent login = FXMLLoader.load(getClass().getResource(HrScreen));
                    login.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(statics.THEME, statics.DEFAULT_THEME) + ".css").toExternalForm());
                    Scene sc = new Scene(login);
                    Stage st = (Stage) mainLabel.getScene().getWindow();
                    st.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icons/logo.png")));
                    st.setTitle("Acapy Trade (شؤون الموظفين)");
                    st.centerOnScreen();
                    st.setScene(sc);
                    st.show();
                } catch (IOException ex) {
                    AlertDialogs.showErrors(ex);
                }
            } else {
                AlertDialogs.permissionDenied();
            }
        });
        invicesButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if (User.canAccess("Invoice")) {
                try {

                    Parent login = FXMLLoader.load(getClass().getResource(InvoiceScreen));
                    login.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(statics.THEME, statics.DEFAULT_THEME) + ".css").toExternalForm());
                    Scene sc = new Scene(login);
                    Stage st = (Stage) mainLabel.getScene().getWindow();
                    st.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icons/logo.png")));
                    st.setTitle("Acapy Trade (الفواتير)");
                    st.centerOnScreen();
                    st.setScene(sc);
                    st.show();
                } catch (IOException ex) {
                    AlertDialogs.showErrors(ex);
                }
            } else {
                AlertDialogs.permissionDenied();
            }
        });

    }

}
