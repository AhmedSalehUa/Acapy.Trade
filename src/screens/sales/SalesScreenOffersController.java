/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.sales;

import assets.classes.AlertDialogs;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import screens.sales.assets.Offers;
import screens.sales.assets.OffersConditions;
import screens.sales.assets.OffersDetails;
import screens.sales.assets.SalesClient;
import screens.sales.assets.SalesMembers;
import screens.store.assets.Products;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class SalesScreenOffersController implements Initializable {

    @FXML
    private TextArea notes;
    @FXML
    private Label id;
    @FXML
    private ComboBox<SalesClient> client;
    @FXML
    private JFXDatePicker date;
    @FXML
    private TextField filesPath;
    @FXML
    private TableView<OffersDetails> invoiceTable;
    @FXML
    private MenuItem deleteRow;
    @FXML
    private TableColumn<OffersDetails, String> tabTotalCost;
    @FXML
    private TableColumn<OffersDetails, String> tabCost;
    @FXML
    private TableColumn<OffersDetails, String> tabAmount;
    @FXML
    private TableColumn<OffersDetails, String> tabProduct;
    @FXML
    private TableColumn<OffersDetails, String> tabId;
    @FXML
    private TableView<OffersConditions> CondTab;
    @FXML
    private TableColumn<OffersConditions, String> CondTabValue;
    @FXML
    private TableColumn<OffersConditions, String> CondTabAttribute;
    @FXML
    private TableColumn<OffersConditions, String> CondTabId;
    @FXML
    private Label condId;
    @FXML
    private TextField condAtrribute;
    @FXML
    private TextField condValue;
    @FXML
    private ProgressIndicator progressCond;
    @FXML
    private Button New;
    @FXML
    private Button Delete;
    @FXML
    private Button Edite;
    @FXML
    private Button Add;
    @FXML
    private Button invoiveAdd;
    @FXML
    private ProgressIndicator progress;
    @FXML
    private TextField invoiceTotal;
    @FXML
    private TextField invoicedisc;
    @FXML
    private TextField invoiceDiscPercent;
    @FXML
    private TextField invoiceLastTotal;
    @FXML
    private Button showOffer;
    @FXML
    private Button attachOffer;
    @FXML
    private ComboBox<SalesMembers> sales;

    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    Preferences prefs;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        progress.setVisible(true);
        progressCond.setVisible(true);
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
                                    intialColumn();
                                    getData();
                                    fillCombo();
                                    getCondAuto();clearCond();
                                    date.setConverter(new StringConverter<LocalDate>() {
                                        private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                                        @Override
                                        public String toString(LocalDate localDate) {
                                            if (localDate == null) {
                                                return "";
                                            }
                                            return dateTimeFormatter.format(localDate);
                                        }

                                        @Override
                                        public LocalDate fromString(String dateString) {
                                            if (dateString == null || dateString.trim().isEmpty()) {
                                                return null;
                                            }
                                            return LocalDate.parse(dateString, dateTimeFormatter);
                                        }
                                    });
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
                progressCond.setVisible(false);
                super.succeeded();
            }
        };
        service.start();
        CondTab.setOnMouseClicked((e) -> {
            if (CondTab.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                New.setDisable(false);

                Delete.setDisable(false);

                Edite.setDisable(false);

                Add.setDisable(true);

                OffersConditions selected = CondTab.getSelectionModel().getSelectedItem();
                condId.setText(Integer.toString(selected.getId()));
                condAtrribute.setText(selected.getAttribute());
                condValue.setText(selected.getValue());

            }
        });
//        invoiceDiscPercent.setOnKeyPressed((w) -> {
//            setTotal("");
//        });
//        invoicedisc.setOnKeyPressed((w) -> {
//            setTotal("");
//        });
    }

    private void intialColumn() {
        tabTotalCost.setCellValueFactory(new PropertyValueFactory<>(""));

        tabCost.setCellValueFactory(new PropertyValueFactory<>(""));

        tabAmount.setCellValueFactory(new PropertyValueFactory<>(""));

        tabProduct.setCellValueFactory(new PropertyValueFactory<>(""));

        tabId.setCellValueFactory(new PropertyValueFactory<>(""));

        CondTabValue.setCellValueFactory(new PropertyValueFactory<>("value"));

        CondTabAttribute.setCellValueFactory(new PropertyValueFactory<>("attribute"));

        CondTabId.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    private void clearCond() {getCondAuto();
        condAtrribute.setText("");
        condValue.setText("");
        
         New.setDisable(true);
        Delete.setDisable(true);
        Edite.setDisable(true);
        Add.setDisable(false);
    }

    private void clear() {
        getAutoNum();

       
    }

    private void getAutoNum() {
        try {
            id.setText(Offers.getAutoNum());

        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void getCondAuto() {
        try {
            String autoNum = OffersConditions.getAutoNum();
            int name = Integer.parseInt(autoNum) + CondTab.getItems().get(CondTab.getItems().size()).getId();
            condId.setText(Integer.toString(name));
            
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void getData() {
//ObservableList<Products> data = Products.getData();
//
//        ObservableList<OffersDetails> list = FXCollections.observableArrayList();
//        list.add(new OffersDetails(1, data, "0", "0", StoreScreenInvoicesController.this));
//        invoiceTable.setItems(list);
//        invoiceTable.setOnKeyReleased((event) -> {
//
//            if (event.getCode() == KeyCode.ENTER) {
//                setTotal("");
//            }
//            if (event.getCode() == KeyCode.ENTER && invoiceTable.getSelectionModel().getSelectedItem().getP().getSelectionModel().getSelectedIndex() != -1
//                    && !invoiceTable.getSelectionModel().getSelectedItem().getAmount().getText().equals("0")
//                    && !invoiceTable.getSelectionModel().getSelectedItem().getCost().getText().equals("0")) {
//                setTotal("");
//                invoiceTable.getItems().add(new OffersDetails(invoiceTable.getItems().size() + 1, data, "0", "0", StoreScreenInvoicesController.this));
//                invoiceTable.getSelectionModel().clearAndSelect(invoiceTable.getItems().size() - 1);
//            }
//
//        });
    }

    private void fillCombo() {
        try {
            sales.setItems(SalesMembers.getData());
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

                GridPane gridPane = new GridPane();
                Label lblid = new Label();
                Label lblName = new Label();

                {
                    gridPane.getColumnConstraints().addAll(
                            new ColumnConstraints(100, 100, 100),
                            new ColumnConstraints(100, 100, 100)
                    );
                    gridPane.add(lblid, 0, 1);
                    gridPane.add(lblName, 1, 1);
                }

                @Override
                protected void updateItem(SalesMembers person, boolean empty) {
                    super.updateItem(person, empty);

                    if (!empty && person != null) {

                        lblid.setText("م: " + Integer.toString(person.getId()));
                        lblName.setText("الاسم: " + person.getName());

                        setGraphic(gridPane);
                    } else {
                        setGraphic(null);
                    }
                }
            });
            client.setItems(SalesClient.getData());
            client.setConverter(new StringConverter<SalesClient>() {
                @Override
                public String toString(SalesClient patient) {
                    return patient.getName();
                }

                @Override
                public SalesClient fromString(String string) {
                    return null;
                }
            });
            client.setCellFactory(cell -> new ListCell<SalesClient>() {

                GridPane gridPane = new GridPane();
                Label lblid = new Label();
                Label lblName = new Label();

                {
                    gridPane.getColumnConstraints().addAll(
                            new ColumnConstraints(100, 100, 100),
                            new ColumnConstraints(100, 100, 100)
                    );
                    gridPane.add(lblid, 0, 1);
                    gridPane.add(lblName, 1, 1);
                }

                @Override
                protected void updateItem(SalesClient person, boolean empty) {
                    super.updateItem(person, empty);

                    if (!empty && person != null) {
                        lblid.setText("م: " + Integer.toString(person.getId()));
                        lblName.setText("الاسم: " + person.getName());

                        setGraphic(gridPane);
                    } else {
                        setGraphic(null);
                    }
                }
            });
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    @FXML
    private void attachFile(MouseEvent event) {
    }

    @FXML
    private void showOffer(ActionEvent event) {
    }

    @FXML
    private void attachOffer(ActionEvent event) {
    }

    @FXML
    private void deleteRow(ActionEvent event) {
    }

    @FXML
    private void NewCond(ActionEvent event) {clearCond();
    }

    @FXML
    private void DeleteCond(ActionEvent event) {
         progressCond.setVisible(true);
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
 
                                    int i =CondTab.getSelectionModel().getSelectedIndex();
                                    CondTab.getItems().remove(i); 
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
                progressCond.setVisible(false);
                clearCond(); 
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void EditeCond(ActionEvent event) {
        progressCond.setVisible(true);
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

                                    OffersConditions of = new OffersConditions();
                                    of.setId(Integer.parseInt(condId.getText()));
                                    of.setAttribute(condAtrribute.getText());
                                    of.setValue(condValue.getText());
                                    of.setInvoice_id(Integer.parseInt(id.getText()));
                                    int i =CondTab.getSelectionModel().getSelectedIndex();
                                    CondTab.getItems().remove(i);
                                    CondTab.getItems().add(i, of);
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
                progressCond.setVisible(false);
                clearCond(); 
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void AddCond(ActionEvent event) {
        progressCond.setVisible(true);
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

                                    OffersConditions of = new OffersConditions();
                                    of.setId(Integer.parseInt(condId.getText()));
                                    of.setAttribute(condAtrribute.getText());
                                    of.setValue(condValue.getText());
                                    of.setInvoice_id(Integer.parseInt(id.getText()));
                                    CondTab.getItems().add(of);

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
                progressCond.setVisible(false);
                clearCond(); 
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void invoiveAdd(ActionEvent event) {
    }

}
