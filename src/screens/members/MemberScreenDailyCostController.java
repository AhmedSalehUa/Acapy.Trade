/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.members;

import assets.classes.AlertDialogs;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import db.User;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import screens.members.assets.MemberDailyCost;
import screens.Accounts.assets.Accounts;
import screens.clients.ClientScreenOperationsController;
import screens.clients.assets.Clients;
import screens.clients.assets.Operations;
import screens.clients.assets.OperationsDetails;
import screens.sales.assets.SalesMembers;
import screens.store.assets.Products;

/**
 * FXML Controller class
 *
 * @author Ahmed Al-Gazzar
 */
public class MemberScreenDailyCostController implements Initializable {

    @FXML
    private JFXTextField search;
    @FXML
    private TableView<MemberDailyCost> tab;
    @FXML
    private TableColumn<MemberDailyCost, String> tabaccount;
    @FXML
    private TableColumn<MemberDailyCost, String> tabstatus;
    @FXML
    private TableColumn<MemberDailyCost, String> tabDate;
    @FXML
    private TableColumn<MemberDailyCost, String> tabcost;
    @FXML
    private TableColumn<MemberDailyCost, String> tabdestination;
    @FXML
    private TableColumn<MemberDailyCost, String> tabId;
    @FXML
    private Label id;
    @FXML
    private ComboBox<Accounts> account;
    @FXML
    private JFXDatePicker date;
    @FXML
    private TextField cost;
    @FXML
    private TextField status;
    @FXML
    private TextField destination;
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
    private TabPane tabs;
    @FXML
    private AnchorPane detailsPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
                                    fillCombo1();
                                    configPanels();
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

                MemberDailyCost selected = tab.getSelectionModel().getSelectedItem();
                id.setText(Integer.toString(selected.getId()));
                ObservableList<Accounts> items1 = account.getItems();
                for (Accounts a : items1) {
                    if (a.getName().equals(selected.getAccount())) {
                        account.getSelectionModel().select(a);
                    }
                }

                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                date.setValue(LocalDate.parse(selected.getDate()));
                destination.setText(selected.getDestination());
                cost.setText(selected.getCost());
                status.setText(selected.getStatus());

                tabs.setVisible(true);
                detailsController.setid(selected.getId());

            }
        });
    }
    MemberScreenDailyCostDetailsController detailsController;

    public void configPanels() {

        try {
            detailsPane.getChildren().clear();
            FXMLLoader fxShow = new FXMLLoader(getClass().getResource("MemberScreenDailyCostDetails.fxml"));
            detailsPane.getChildren().add(fxShow.load());
            detailsController = fxShow.getController();
            detailsController.setParentController(MemberScreenDailyCostController.this);
        } catch (IOException ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void fillCombo1() {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            ObservableList<Accounts> data;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            data = Accounts.getData();

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

                account.setItems(data);
                account.setConverter(new StringConverter<Accounts>() {
                    @Override
                    public String toString(Accounts patient) {
                        return patient.getName();
                    }

                    @Override
                    public Accounts fromString(String string) {
                        return null;
                    }
                });
                account.setCellFactory(cell -> new ListCell<Accounts>() {

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
                    protected void updateItem(Accounts person, boolean empty) {
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

    public void setAccount(int id, String amount) {
        try {

            String total = cost.getText().isEmpty() ? "0" : cost.getText();
            cost.setText(Double.toString(Double.parseDouble(total) + Double.parseDouble(amount)));
            Operations.updateCost(id, cost.getText());
            getData();
            ObservableList<MemberDailyCost> values = tab.getItems();
            for (MemberDailyCost a : values) {
                if (a.getId() == id) {
                    tab.getSelectionModel().select(a);
                }
            }
        } catch (Exception e) {
            AlertDialogs.showErrors(e);
        }
    }

    public void reduceAccount(int id, String amount) {
        try {
            String total = cost.getText().isEmpty() ? "0" : cost.getText();
            cost.setText(Double.toString(Double.parseDouble(total) - Double.parseDouble(amount)));
            Operations.updateCost(id, cost.getText());
            getData();
        } catch (Exception e) {
            AlertDialogs.showErrors(e);
        }
    }

    private void getAutoNum() {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            String autoNum;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            autoNum = MemberDailyCost.getAutoNum();
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
                id.setText(autoNum);
                super.succeeded();
            }
        };
        service.start();
    }

    private void clear() {
        getAutoNum();
        account.getSelectionModel().clearSelection();

        cost.setText("");
        destination.setText("");
        date.setValue(null);
        status.setText("");
        Add.setDisable(false);
        Edite.setDisable(true);
        Delete.setDisable(true);
        New.setDisable(true);
        tabs.setVisible(false);
    }

    private void intialColumn() {
        tabId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tabcost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        tabstatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tabDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tabdestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        tabaccount.setCellValueFactory(new PropertyValueFactory<>("account"));

    }

    private void getData() {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            ObservableList<MemberDailyCost> data;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        try {
                            data = MemberDailyCost.getData();

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
    ObservableList<MemberDailyCost> items;

    @FXML
    private void search(KeyEvent event) {
        FilteredList<MemberDailyCost> filteredData = new FilteredList<>(items, p -> true);

        filteredData.setPredicate(pa -> {

            if (search.getText() == null || search.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = search.getText().toLowerCase();

            if (pa.getDestination().contains(lowerCaseFilter)
                    || pa.getStatus().contains(lowerCaseFilter)
                    || pa.getCost().contains(lowerCaseFilter)
                    || pa.getDate().contains(lowerCaseFilter)) {
                return true;
            } else {
                return false;
            }

        });

        SortedList< MemberDailyCost> sortedData = new SortedList<>(filteredData);
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

                                try {
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Deleting  operation");
                                    alert.setHeaderText("سيتم حذف العملية ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        MemberDailyCost mdc = new MemberDailyCost();
                                        mdc.setId(Integer.parseInt(id.getText()));
                                        mdc.Delete();
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
            boolean ok = true;
            MemberDailyCost mdc = new MemberDailyCost();

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
                                    alert.setTitle("Editting  DailyCost");
                                    alert.setHeaderText("سيتم تعديل الحساب اليومى ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        mdc.setId(Integer.parseInt(id.getText()));
//                                     opd.setOperation_id(operation.getSelectionModel().getSelectedItem().getId());
                                        mdc.setAccount_id(account.getSelectionModel().getSelectedItem().getId());
                                        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                        mdc.setDate(date.getValue().format(format));
                                        mdc.setCost(cost.getText());
                                        mdc.setDestination(destination.getText());
                                        mdc.setStatus(status.getText());
                                        //  mdc.setOperation_id(OPERATION_ID);
                                        //        parentController.reduceAccount(OPERATION_ID, tab.getSelectionModel().getSelectedItem().getTotal_cost());
                                        mdc.Edite();
                                        //   parentController.setAccount(OPERATION_ID, totalcost.getText());
                                    }
                                } catch (Exception ex) {
                                    AlertDialogs.showErrors(ex);
                                    ok = false;
                                    // parentController.setAccount(OPERATION_ID, tab.getSelectionModel().getSelectedItem().getTotal_cost());
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
                    getData();
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
            MemberDailyCost mdc = new MemberDailyCost();
            boolean ok = true;

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
                                    mdc.setId(Integer.parseInt(id.getText()));
//                                     opd.setOperation_id(operation.getSelectionModel().getSelectedItem().getId());
                                    mdc.setAccount_id(account.getSelectionModel().getSelectedItem().getId());
                                    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                    mdc.setDate(date.getValue().format(format));
                                    mdc.setCost(cost.getText());
                                    mdc.setDestination(destination.getText());
                                    mdc.setStatus(status.getText());
                                    mdc.Add();
                                    //  parentController.setAccount(OPERATION_ID, totalcost.getText());
                                } catch (Exception ex) {
                                    AlertDialogs.showErrors(ex);
                                    ok = false;
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
                    getData();
                }
                super.succeeded();
            }
        };
        service.start();
    }

}
