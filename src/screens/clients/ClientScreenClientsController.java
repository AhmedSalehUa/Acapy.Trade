/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.clients;

import assets.classes.AlertDialogs;
import static assets.classes.statics.DEFAULT_THEME;
import static assets.classes.statics.THEME;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import screens.clients.assets.Clients;

/**
 * FXML Controller class
 *
 * @author ahmed
 */
public class ClientScreenClientsController implements Initializable {

    private JFXTextField searchEscort;
    @FXML
    private TableView<Clients> tab;
    @FXML
    private TableColumn<Clients, String> tabTele2;
    @FXML
    private TableColumn<Clients, String> tabTele1;
    @FXML
    private TableColumn<Clients, String> tabAcc;
    @FXML
    private TableColumn<Clients, String> tabEmail;
    @FXML
    private TableColumn<Clients, String> tabAdress;
    @FXML
    private TableColumn<Clients, String> tabOrg;
    @FXML
    private TableColumn<Clients, String> tabName;
    @FXML
    private TableColumn<Clients, String> tabId;
    @FXML
    private Label id;
    @FXML
    private TextField name;
    @FXML
    private TextField organization;
    @FXML
    private TextField adress;
    @FXML
    private TextField email;
    @FXML
    private TextField acc;
    @FXML
    private TextField tele1;
    @FXML
    private TextField tele2;
    @FXML
    private ProgressIndicator progress;
    @FXML
    private Button New;
    @FXML
    private Button Delete;
    @FXML
    private Button Edite;
    @FXML
    private Button Add;
    @FXML
    private JFXTextField search;

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
                        //Background work                       
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    clear();
                                    setTableColumns();
                                    getData();
                                    fillCombo();

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

                progress.setVisible(false);
                super.succeeded();
            }
        };
        service.start();

        tab.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if (tab.getSelectionModel().getSelectedIndex() == -1) {

            } else {
                Add.setDisable(true);
                Edite.setDisable(false);
                Delete.setDisable(false);
                New.setDisable(false);

                Clients pa = tab.getSelectionModel().getSelectedItem();
                id.setText(Integer.toString(pa.getId()));
                name.setText(pa.getName());
                organization.setText(pa.getOrganization());
                adress.setText(pa.getAdress());
                email.setText(pa.getEmail());
                acc.setText(pa.getAccount_num());
                tele1.setText(pa.getTele1());
                tele2.setText(pa.getTele2());

            }
        });
    }

    private void fillCombo() throws Exception {

    }

    private void setAutoNumber() {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            String idVal;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        try {

                            idVal = Clients.getAutoNum();
                        } catch (Exception ex) {
                            AlertDialogs.showErrors(ex);
                        }

                        return null;
                    }
                };

            }

            @Override
            protected void succeeded() {
                id.setText(idVal);
                progress.setVisible(false);
                super.succeeded();
            }
        };
        service.start();

    }

    private void clear() {
        setAutoNumber();
        name.setText("");
        organization.setText("");
        adress.setText("");
        acc.setText("");
        email.setText("");
        tele1.setText("");
        tele2.setText("");
    }

    private void setTableColumns() {
        tabId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tabName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tabOrg.setCellValueFactory(new PropertyValueFactory<>("organization"));
        tabAdress.setCellValueFactory(new PropertyValueFactory<>("adress"));
        tabEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tabAcc.setCellValueFactory(new PropertyValueFactory<>("account_num"));
        tabTele1.setCellValueFactory(new PropertyValueFactory<>("tele1"));
        tabTele2.setCellValueFactory(new PropertyValueFactory<>("tele2"));

    }

    private void getData() {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            ObservableList<Clients> data;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            data = Clients.getData();
                        } catch (Exception ex) {
                            AlertDialogs.showErrors(ex);
                        }
                        return null;
                    }
                };
            }

            @Override
            protected void succeeded() {
                tab.setItems(data);
                items = data;
                progress.setVisible(false);
                super.succeeded();
            }
        };
        service.start();
    }
    ObservableList<Clients> items;

    @FXML
    private void search(KeyEvent event) {

        FilteredList<Clients> filteredData = new FilteredList<>(items, p -> true);

        filteredData.setPredicate(pa -> {

            if (search.getText() == null || search.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = search.getText().toLowerCase();

            if (pa.getName().contains(lowerCaseFilter)
                    || pa.getOrganization().contains(lowerCaseFilter)
                    || pa.getAccount_num().contains(lowerCaseFilter)
                    || pa.getAdress().contains(lowerCaseFilter)
                    || pa.getTele1().contains(lowerCaseFilter)
                    || pa.getTele2().contains(lowerCaseFilter)) {
                return true;
            } else {
                return false;
            }

        });

        SortedList< Clients> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tab.comparatorProperty());
        tab.setItems(sortedData);
    }

    @FXML
    private void New(ActionEvent event) {
        clear();
    }

    @FXML
    private void Delete(ActionEvent event) {
        progress.setVisible(true);
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
                                Clients cl = new Clients();
                                try {
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Deleting  Clients");
                                    alert.setHeaderText("سيتم حذف العميل ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        cl.setId(Integer.parseInt(id.getText()));
                                        cl.setName(name.getText());
                                        cl.setOrganization(organization.getText());
                                        cl.setAdress(adress.getText());
                                        cl.setAccount_num(acc.getText());
                                        cl.setTele1(tele1.getText());
                                        cl.setTele2(tele2.getText());
                                        cl.setEmail(email.getText());
                                        cl.Delete();
                                        clear();
                                    }
                                } catch (Exception ex) {
                                    AlertDialogs.showErrors(ex);
                                    show(cl);
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
                progress.setVisible(false);

                getData();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void Edite(ActionEvent event) {
        progress.setVisible(true);
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
                                Clients cl = new Clients();
                                try {
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Editting  Clients");
                                    alert.setHeaderText("سيتم تعديل العميل ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        cl.setId(Integer.parseInt(id.getText()));
                                        cl.setName(name.getText());
                                        cl.setOrganization(organization.getText());
                                        cl.setAdress(adress.getText());
                                        cl.setAccount_num(acc.getText());
                                        cl.setTele1(tele1.getText());
                                        cl.setTele2(tele2.getText());
                                        cl.setEmail(email.getText());
                                        cl.Edite();
                                        clear();
                                    }
                                } catch (Exception ex) {
                                    AlertDialogs.showErrors(ex);
                                    show(cl);
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
                progress.setVisible(false);

                getData();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void Add(ActionEvent event) {

        progress.setVisible(true);
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
                                Clients cl = new Clients();
                                try {
                                    cl.setId(Integer.parseInt(id.getText()));
                                    cl.setName(name.getText());
                                    cl.setOrganization(organization.getText());
                                    cl.setAdress(adress.getText());
                                    cl.setAccount_num(acc.getText());
                                    cl.setTele1(tele1.getText());
                                    cl.setTele2(tele2.getText());
                                    cl.setEmail(email.getText());
                                    cl.Add();
                                    clear();
                                } catch (Exception ex) {
                                    AlertDialogs.showErrors(ex);
                                    show(cl);
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
                progress.setVisible(false);

                getData();
                super.succeeded();
            }
        };
        service.start();
    }

    private void show(Clients pa) {
        id.setText(Integer.toString(pa.getId()));
        name.setText(pa.getName());
        organization.setText(pa.getOrganization());
        adress.setText(pa.getAdress());
        email.setText(pa.getEmail());
        acc.setText(pa.getAccount_num());
        tele1.setText(pa.getTele1());
        tele2.setText(pa.getTele2());
    }

}
