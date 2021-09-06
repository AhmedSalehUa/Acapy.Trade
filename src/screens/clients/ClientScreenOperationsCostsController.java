/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.clients;

import assets.classes.AlertDialogs;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;
import screens.clients.assets.OperationCosts;
import screens.clients.assets.OperationsDetails;
import screens.store.assets.Products;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class ClientScreenOperationsCostsController implements Initializable {

    @FXML
    private JFXTextField search;
    @FXML
    private TableView<OperationCosts> tab;
    @FXML
    private TableColumn<OperationCosts, String> tabDate;
    @FXML
    private TableColumn<OperationCosts, String> tabPayFor;
    @FXML
    private TableColumn<OperationCosts, String> tabAmount;
    @FXML
    private TableColumn<OperationCosts, String> tabId;
    @FXML
    private Label id;
    @FXML
    private TextField amount;
    @FXML
    private TextField payFor;
    @FXML
    private JFXDatePicker date;
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
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    ClientScreenOperationsController parentController;
    int OPERATION_ID = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        date.setConverter(new StringConverter<LocalDate>() {

            @Override
            public String toString(LocalDate localDate) {
                if (localDate == null) {
                    return "";
                }
                return format.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if (dateString == null || dateString.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(dateString, format);
            }
        });
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

                                    intialColumn();

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
        tab.setOnMouseClicked((e) -> {
            if (tab.getSelectionModel().getSelectedIndex() == -1) {

            } else {
                Add.setDisable(true);
                Edite.setDisable(false);
                Delete.setDisable(false);
                New.setDisable(false);

                OperationCosts selected = tab.getSelectionModel().getSelectedItem();

                id.setText(Integer.toString(selected.getId()));

                amount.setText(selected.getAmount());
                date.setValue(LocalDate.parse(selected.getDate()));
                payFor.setText(selected.getPayFor());

            }
        });
    }

    private void intialColumn() {
        tabId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tabPayFor.setCellValueFactory(new PropertyValueFactory<>("payFor"));
        tabAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        tabDate.setCellValueFactory(new PropertyValueFactory<>("date"));

    }

    private void clear() {
        getAutoNum();
        date.setValue(null);
        payFor.setText("");
        amount.setText("");

        Add.setDisable(false);
        Edite.setDisable(true);
        Delete.setDisable(true);
        New.setDisable(true);
    }

    private void getAutoNum() {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            String idNum;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            idNum = OperationCosts.getAutoNum();

                        } catch (Exception ex) {
                            AlertDialogs.showErrors(ex);
                        }
                        return null;
                    }
                };

            }

            @Override
            protected void succeeded() {
                progress.setVisible(false);
                id.setText(idNum);
                super.succeeded();
            }
        };
        service.start();

    }

    void getData(int id) {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            ObservableList<OperationCosts> data;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            data = OperationCosts.getData(id);
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
    ObservableList<OperationCosts> items;

    @FXML
    private void search(KeyEvent event) {
        FilteredList<OperationCosts> filteredData = new FilteredList<>(items, p -> true);

        filteredData.setPredicate(pa -> {

            if (search.getText() == null || search.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = search.getText().toLowerCase();

            if (pa.getPayFor().contains(lowerCaseFilter) || pa.getAmount().contains(lowerCaseFilter)
                    || pa.getDate().contains(lowerCaseFilter)) {
                return true;
            } else {
                return false;
            }

        });

        SortedList< OperationCosts> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tab.comparatorProperty());
        tab.setItems(sortedData);
    }

    @FXML
    private void setTotal(KeyEvent event) {

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
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {

                                try {
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Deleting  OperationCosts");
                                    alert.setHeaderText("سيتم حذف البند ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        OperationCosts opd = new OperationCosts();
                                        opd.setId(Integer.parseInt(id.getText()));
                                        opd.Delete();
                                        parentController.reduceAccount(OPERATION_ID, tab.getSelectionModel().getSelectedItem().getAmount());
                                        
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
                progress.setVisible(false);
                clear();
                getData(OPERATION_ID);
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void Edite(ActionEvent event) {
         progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            boolean ok = true;
            OperationCosts opd = new OperationCosts();

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
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Editting  Cost");
                                    alert.setHeaderText("سيتم تعديل البند ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        opd.setId(Integer.parseInt(id.getText())); 
                                        opd.setDate(date.getValue().format(format));
                                        opd.setAmount(amount.getText());
                                        opd.setPayFor(payFor.getText());
                                        opd.setOperation_id(OPERATION_ID);
                                        parentController.reduceAccount(OPERATION_ID, tab.getSelectionModel().getSelectedItem().getAmount());
                                        opd.Edite();
                                        parentController.setAccount(OPERATION_ID, amount.getText());
                                    }
                                } catch (Exception ex) {
                                    AlertDialogs.showErrors(ex);
                                    ok = false;
                                    parentController.setAccount(OPERATION_ID, tab.getSelectionModel().getSelectedItem().getAmount());
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
                if (ok) {
                    clear();
                    getData(OPERATION_ID);
                }
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void Add(ActionEvent event) {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            OperationCosts opd = new OperationCosts();
            boolean ok = true;

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
                                    opd.setId(Integer.parseInt(id.getText()));
                                    opd.setOperation_id(OPERATION_ID);
                                    opd.setAmount(amount.getText());
                                    opd.setPayFor(payFor.getText());
                                    opd.setDate(date.getValue().format(format));
                                    opd.Add();
                                    parentController.setAccount(OPERATION_ID, amount.getText());
                                } catch (Exception ex) {ok = false;
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
                if (ok) {
                    clear();
                    getData(OPERATION_ID);
                }
                super.succeeded();
            }
        };
        service.start();
    }

    public void setParentController(ClientScreenOperationsController parentController) {
        this.parentController = parentController;

    }

    public void setId(int id) {
        this.OPERATION_ID = id;
        
        clear();
        getData(OPERATION_ID);
    }

}
