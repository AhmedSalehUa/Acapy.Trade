/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.sales;

import acapy.trade.AcapyTrade;
import assets.classes.AlertDialogs;
import assets.classes.statics;
import static assets.classes.statics.USER_ROLE;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.prefs.Preferences;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import screens.sales.assets.Calls;
import screens.sales.assets.SalesClient;
import screens.sales.assets.SalesMembers;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class SalesScreenCallsController implements Initializable {

    @FXML
    private JFXTextField search;
    @FXML
    private TableView<Calls> tab;
    @FXML
    private TableColumn<Calls, String> tabSales;
    @FXML
    private TableColumn<Calls, String> tabDetails;
    @FXML
    private TableColumn<Calls, String> tabTime;
    @FXML
    private TableColumn<Calls, String> tabDate;
    @FXML
    private TableColumn<Calls, String> tabClient;
    @FXML
    private TableColumn<Calls, String> tabId;
    @FXML
    private Label id;
    @FXML
    private ComboBox<SalesMembers> sales;
    @FXML
    private ComboBox<SalesClient> client;
    @FXML
    private JFXDatePicker date;
    @FXML
    private JFXTimePicker time;
    @FXML
    private TextArea details;
    @FXML
    private ProgressIndicator progress;
    @FXML
    private Button formNew;
    @FXML
    private Button formDelete;
    @FXML
    private Button formEdite;
    @FXML
    private Button formAdd;

    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    Preferences prefs;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prefs = Preferences.userNodeForPackage(AcapyTrade.class);
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
                                    clear();
                                    intialColumn();
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
        tab.setOnMouseClicked((e) -> {
            if (tab.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                formNew.setDisable(false);

                formDelete.setDisable(false);

                formEdite.setDisable(false);

                formAdd.setDisable(true);

                Calls selected = tab.getSelectionModel().getSelectedItem();
                id.setText(Integer.toString(selected.getId()));
                details.setText(selected.getDetails());
                date.setValue(LocalDate.parse(selected.getDate()));
                time.setValue(LocalTime.parse(selected.getTime()));
                ObservableList<SalesClient> items1 = client.getItems();
                for (SalesClient a : items1) {
                    if (a.getName().equals(selected.getClient())) {
                        client.getSelectionModel().select(a);
                    }
                }
                ObservableList<SalesMembers> items = sales.getItems();
                for (SalesMembers a : items) {
                    if (a.getName().equals(selected.getSales())) {
                        sales.getSelectionModel().select(a);
                    }
                }
            }
        });
    }

    private void intialColumn() {
        tabSales.setCellValueFactory(new PropertyValueFactory<>("sales"));

        tabDetails.setCellValueFactory(new PropertyValueFactory<>("details"));

        tabTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        tabDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        tabClient.setCellValueFactory(new PropertyValueFactory<>("client"));

        tabId.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    private void clear() {
        getAutoNum();
        formNew.setDisable(true);

        formDelete.setDisable(true);

        formEdite.setDisable(true);

        formAdd.setDisable(false);
        client.getSelectionModel().clearSelection();
        sales.getSelectionModel().clearSelection();
        date.setValue(null);
        time.setValue(null);
        details.setText("");
    }

    private void getAutoNum() {
        try {
            id.setText(Calls.getAutoNum());
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
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
                                        if (prefs.get(USER_ROLE, "user").equals("super_admin")) {
                                            tab.setItems(Calls.getData());
                                        } else {
                                            tab.setItems(Calls.getData(prefs.get(statics.USER_ID, "0")));
                                        }

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
    ObservableList<Calls> items;

    private void fillCombo() {
        try {

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
                protected void updateItem(SalesClient person, boolean empty) {
                    super.updateItem(person, empty);

                    if (!empty && person != null) {

                        // Update our Labels
                        lblid.setText("م: " + Integer.toString(person.getId()));
                        lblName.setText("الاسم: " + person.getName());

                        // Set this ListCell's graphicProperty to display our GridPane
                        setGraphic(gridPane);
                    } else {
                        // Nothing to display here
                        setGraphic(null);
                    }
                }
            });
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

                        // Set this ListCell's graphicProperty to display our GridPane
                        setGraphic(gridPane);
                    } else {
                        // Nothing to display here
                        setGraphic(null);
                    }
                }
            });
        } catch (Exception e) {
            AlertDialogs.showErrors(e);
        }
    }

    @FXML
    private void search(KeyEvent event) {

        FilteredList<Calls> filteredData = new FilteredList<>(items, p -> true);

        filteredData.setPredicate(pa -> {

            if (search.getText() == null || search.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = search.getText().toLowerCase();

            if (pa.getClient().contains(lowerCaseFilter)
                    || pa.getSales().contains(lowerCaseFilter)
                    || pa.getDate().contains(lowerCaseFilter)
                    || pa.getTime().contains(lowerCaseFilter)) {
                return true;
            } else {
                return false;
            }

        });

        SortedList< Calls> sortedData = new SortedList<>(filteredData);
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
                                    alert.setTitle("Deleting Call ");
                                    alert.setHeaderText("سيتم حذف المكالمة ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        Calls sl = new Calls();
                                        sl.setId(Integer.parseInt(id.getText()));
                                        sl.Delete();
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
                                    alert.setTitle("Editing Call ");
                                    alert.setHeaderText("سيتم تعديل المكالمة ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        Calls sl = new Calls();
                                        sl.setId(Integer.parseInt(id.getText()));
                                        sl.setClient_id(client.getSelectionModel().getSelectedItem().getId());
                                        sl.setSales_id(sales.getSelectionModel().getSelectedItem().getId());
                                        sl.setDate(date.getValue().format(format));
                                        sl.setTime(time.getValue().format(DateTimeFormatter.ISO_LOCAL_TIME));
                                        sl.setDetails(details.getText());
                                        sl.Edite();
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
    private void Add(ActionEvent event) {
        if (client.getSelectionModel().getSelectedIndex() == -1 || sales.getSelectionModel().getSelectedIndex() == -1 || date.getValue() == null || time.getValue() == null) {
            AlertDialogs.showError("يوجد بيانات فارغة");
        } else {
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
                                        Calls sl = new Calls();
                                        sl.setId(Integer.parseInt(id.getText()));
                                        sl.setClient_id(client.getSelectionModel().getSelectedItem().getId());
                                        sl.setSales_id(sales.getSelectionModel().getSelectedItem().getId());
                                        sl.setDate(date.getValue().format(format));
                                        sl.setTime(time.getValue().format(DateTimeFormatter.ISO_LOCAL_TIME));
                                        sl.setDetails(details.getText());
                                        sl.Add();
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
    }

}
