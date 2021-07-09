/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.mainDataScreen;

import assets.classes.AlertDialogs;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import screens.mainDataScreen.assets.Provider;

public class MainDataScreenProviderController implements Initializable {

    private JFXTextField searchEscort;
    @FXML
    private TableView<Provider> tab;
    @FXML
    private TableColumn<Provider, String> tabTele2;
    @FXML
    private TableColumn<Provider, String> tabTele1;
    @FXML
    private TableColumn<Provider, String> tabAcc;
    @FXML
    private TableColumn<Provider, String> tabEmail;
    @FXML
    private TableColumn<Provider, String> tabAdress;
    @FXML
    private TableColumn<Provider, String> tabOrg;
    @FXML
    private TableColumn<Provider, String> tabName;
    @FXML
    private TableColumn<Provider, String> tabId;
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

                Provider pa = tab.getSelectionModel().getSelectedItem();
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
        try {
            id.setText(Provider.getAutoNum());
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
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

                                        tab.setItems(Provider.getData());

                                    } catch (Exception ex) {
                                        AlertDialogs.showErrors(ex);
                                    }
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

                items = tab.getItems();
                progress.setVisible(false);
                super.succeeded();
            }
        };
        service.start();
    }
    ObservableList<Provider> items;

    @FXML
    private void search(KeyEvent event) {

        FilteredList<Provider> filteredData = new FilteredList<>(items, p -> true);

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

        SortedList< Provider> sortedData = new SortedList<>(filteredData);
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
                                Provider cl = new Provider();
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
                                Provider cl = new Provider();
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
                                Provider cl = new Provider();
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

    private void show(Provider pa) {
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
