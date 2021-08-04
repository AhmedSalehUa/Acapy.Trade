package screens.clients;

import assets.classes.AlertDialogs;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import screens.clients.assets.Clients;
import screens.clients.assets.Contracts;
import screens.store.assets.ProductCategory;
import screens.store.assets.Provider;

/**
 * FXML Controller class
 *
 * @author amran
 */
public class ClientScreenContractController implements Initializable {

    @FXML
    private JFXTextField search;
    @FXML
    private TableView<Contracts> tab;
    @FXML
    private TableColumn<Contracts, Date> tab_dueto;
    @FXML
    private TableColumn<Contracts, String> tab_cost;
    @FXML
    private TableColumn<Contracts, String> tab_noVisits;
    @FXML
    private TableColumn<Contracts, String> tab_dateto;
    @FXML
    private TableColumn<Contracts, String> tab_datefrom;
    @FXML
    private TableColumn<Contracts, String> tab_clientName;
    @FXML
    private TableColumn<Contracts, String> tab_id;
    @FXML
    private Label contrctId;
    @FXML
    private ComboBox<Clients> clientName;
    @FXML
    private TextField noVisits;
    @FXML
    private TextField cost;
    @FXML
    private ComboBox<String> due_to;
    @FXML
    private DatePicker date_from;
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
    @FXML
    private DatePicker date_to;

    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        date_from.setConverter(new StringConverter<LocalDate>() {
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
        }); date_to.setConverter(new StringConverter<LocalDate>() {
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

                Contracts selected = tab.getSelectionModel().getSelectedItem();
                contrctId.setText(Integer.toString(selected.getId()));
                date_from.setValue(LocalDate.parse(selected.getDate_from())); 
                date_to.setValue(LocalDate.parse(selected.getDate_to()));
                noVisits.setText(selected.getNoVisits());
                cost.setText(selected.getCost());
                due_to.getSelectionModel().select(selected.getDue_after());

                ObservableList<Clients> items1 = clientName.getItems();
                for (Clients a : items1) {
                    if (a.getName().equals(selected.getName())) {
                        clientName.getSelectionModel().select(a);
                    }
                }
            }
        });
    }

    private void intialColumn() {
        tab_id.setCellValueFactory(new PropertyValueFactory<>("id"));

        tab_clientName.setCellValueFactory(new PropertyValueFactory<>("name"));

        tab_datefrom.setCellValueFactory(new PropertyValueFactory<>("date_from"));

        tab_dateto.setCellValueFactory(new PropertyValueFactory<>("date_to"));

        tab_noVisits.setCellValueFactory(new PropertyValueFactory<>("noVisits"));

        tab_cost.setCellValueFactory(new PropertyValueFactory<>("cost"));

        tab_dueto.setCellValueFactory(new PropertyValueFactory<>("due_after"));

    }

    private void clear() {
        getAutoNum();
        formNew.setDisable(true);

        formDelete.setDisable(true);

        formEdite.setDisable(true);

        formAdd.setDisable(false);

        clientName.getSelectionModel().clearSelection();
        date_from.setValue(null);
        date_to.setValue(null);
        noVisits.setText("");
        cost.setText("");
        due_to.getSelectionModel().clearSelection();
    }

    private void getAutoNum() {
        try {
            contrctId.setText(Contracts.getAutoNum());
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
                                    tab.setItems(Contracts.getData());
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
    }

    private void fillCombo() {
        due_to.getItems().addAll("سنوي", "نص سنوي", "شهري");
        try {
            clientName.setItems(Clients.getData());
            clientName.setConverter(new StringConverter<Clients>() {
                @Override
                public String toString(Clients patient) {
                    return patient.getName();
                }

                @Override
                public Clients fromString(String string) {
                    return null;
                }
            });
            clientName.setCellFactory(cell -> new ListCell<Clients>() {

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
                protected void updateItem(Clients person, boolean empty) {
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
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    @FXML
    private void search(KeyEvent event) {
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
                                    alert.setTitle("Deleting  Cotract");
                                    alert.setHeaderText("سيتم حذف العقد ");
                                    alert.setContentText("هل انتالعقد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        Contracts pr = new Contracts();
                                        pr.setId(Integer.parseInt(contrctId.getText()));
                                        pr.Delete();
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
            boolean ok=true;
             Contracts pr = new Contracts();
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
                                    alert.setTitle("Editting  Contract");
                                    alert.setHeaderText("سيتم تعديل العقد ");
                                    alert.setContentText("هل انتالعقد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                       
                                        pr.setId(Integer.parseInt(contrctId.getText()));
                                        pr.setCli_id(clientName.getSelectionModel().getSelectedItem().getId());
                                        pr.setDate_from(date_from.getValue().format(format));
                                        pr.setDate_to(date_to.getValue().format(format));
                                        pr.setNoVisits(noVisits.getText());
                                        pr.setCost(cost.getText());
                                        pr.setDue_after(due_to.getSelectionModel().getSelectedItem());

                                        pr.Edite();
                                    }
                                } catch (Exception ex) {
                                    AlertDialogs.showErrors(ex);
                                    ok=false;
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
               if(ok){ clear();
                getData();}
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void Add(ActionEvent event) {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            boolean ok = true;
             Contracts pr = new Contracts();
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

                                   
                                    pr.setId(Integer.parseInt(contrctId.getText()));
                                    pr.setCli_id(clientName.getSelectionModel().getSelectedItem().getId());
                                    pr.setDate_from(date_from.getValue().format(format));
                                    pr.setDate_to(date_to.getValue().format(format));
                                    pr.setNoVisits(noVisits.getText());
                                    pr.setCost(cost.getText());
                                    pr.setDue_after(due_to.getSelectionModel().getSelectedItem());
                                    pr.Add();
                                } catch (Exception ex) {
                                    AlertDialogs.showErrors(ex);
                                    ok=false;
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
                if(ok){clear();
                getData();}
                super.succeeded();
            }
        };
        service.start();
    }

}
