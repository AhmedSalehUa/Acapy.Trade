/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.Accounts;

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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import screens.Accounts.assets.Accounts;
import screens.Accounts.assets.ClientsAccountPays;
import screens.Accounts.assets.ProviderAccounts;
import screens.Accounts.assets.ProviderAccountsPays;
import screens.clients.assets.Clients;
import screens.members.assets.AcapyMembers;
import screens.store.assets.Provider;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class ProvidersAccountsController implements Initializable {

    @FXML
    private ComboBox<Provider> providers;
    @FXML
    private JFXTextField searchAccounts;
    @FXML
    private TableView<ProviderAccounts> accTab;
    @FXML
    private TableColumn<ProviderAccounts, String> accTabDate;
    @FXML
    private TableColumn<ProviderAccounts, String> accTabAmount;
    @FXML
    private TableColumn<ProviderAccounts, String> accTabInvoice;
    @FXML
    private TableColumn<ProviderAccounts, String> accTabId;
    @FXML
    private JFXTextField searchPays;
    @FXML
    private TableView<ProviderAccountsPays> paysTab;
    @FXML
    private TableColumn<ProviderAccountsPays, String> paysTabAccount;
    @FXML
    private TableColumn<ProviderAccountsPays, String> paysTabMember;
    @FXML
    private TableColumn<ProviderAccountsPays, String> paysTabDate;
    @FXML
    private TableColumn<ProviderAccountsPays, String> paysTabAmount;
    @FXML
    private TableColumn<ProviderAccountsPays, String> paysTabId;
    @FXML
    private Label paysId;
    @FXML
    private TextField paysAmount;
    @FXML
    private JFXDatePicker paysDate;
    @FXML
    private ComboBox<Accounts> paysAccount;
    @FXML
    private ComboBox<AcapyMembers> paysMember;
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
    private Label total;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        accTab.setOnMouseClicked((e) -> {
            if (accTab.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                getPaysData(accTab.getSelectionModel().getSelectedItem().getId());
            }
        });
        paysTab.setOnMouseClicked((e) -> {
            if (paysTab.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                formNew.setDisable(false);

                formDelete.setDisable(false);

                formEdite.setDisable(false);

                formAdd.setDisable(true);

                ProviderAccountsPays selected = paysTab.getSelectionModel().getSelectedItem();
                paysId.setText(Integer.toString(selected.getId()));
                paysAmount.setText(selected.getAmount());
                paysDate.setValue(LocalDate.parse(selected.getDate()));
                ObservableList<Accounts> accounts = paysAccount.getItems();
                for (Accounts a : accounts) {
                    if (a.getName().equals(selected.getAccount())) {
                        paysAccount.getSelectionModel().select(a);
                    }
                }
                ObservableList<AcapyMembers> members = paysMember.getItems();
                for (AcapyMembers a : members) {
                    if (a.getId() == selected.getMem_id()) {
                        paysMember.getSelectionModel().select(a);
                    }
                }

            }
        });
    }

    private void intialColumn() {
        accTabDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        accTabAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        accTabInvoice.setCellValueFactory(new PropertyValueFactory<>("invoiceId"));

        accTabId.setCellValueFactory(new PropertyValueFactory<>("id"));

        paysTabAccount.setCellValueFactory(new PropertyValueFactory<>("account"));

        paysTabMember.setCellValueFactory(new PropertyValueFactory<>("mem_id"));

        paysTabDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        paysTabAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        paysTabId.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    private void clear() {
        getAutoNum();
        formNew.setDisable(true);

        formDelete.setDisable(true);

        formEdite.setDisable(true);

        formAdd.setDisable(false);

        paysAmount.setText("");

        paysDate.setValue(null);

        paysAccount.getSelectionModel().clearSelection();

        paysMember.getSelectionModel().clearSelection();

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

                            autoNum = ProviderAccountsPays.getAutoNum();
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
                paysId.setText(autoNum);
                super.succeeded();
            }
        };
        service.start();
    }
    ObservableList<AcapyMembers> membersItems;
    ObservableList<Accounts> accountsItems;

    private void fillCombo() {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            ObservableList<AcapyMembers> members;
            ObservableList<Accounts> accounts;
            ObservableList<Provider> providersItems;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            accounts = Accounts.getData();
                            members = AcapyMembers.getData();
                            providersItems = Provider.getData();
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
                accountsItems = accounts;
                membersItems = members;
                providers.setItems(providersItems);
                providers.setConverter(new StringConverter<Provider>() {
                    @Override
                    public String toString(Provider patient) {
                        return patient.getName();
                    }

                    @Override
                    public Provider fromString(String string) {
                        return null;
                    }
                });
                providers.setCellFactory(cell -> new ListCell<Provider>() {

                    // Create our layout here to be reused for each ListCell
                    GridPane gridPane = new GridPane();
                    Label lblid = new Label();
                    Label lblName = new Label();
                    Label lblOrg = new Label();

                    {
                        gridPane.getColumnConstraints().addAll(
                                new ColumnConstraints(100, 100, 100),
                                new ColumnConstraints(100, 100, 100),
                                new ColumnConstraints(100, 100, 100)
                        );

                        gridPane.add(lblid, 0, 1);
                        gridPane.add(lblName, 1, 1);
                        gridPane.add(lblOrg, 2, 1);

                    }

                    @Override
                    protected void updateItem(Provider person, boolean empty) {
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
                paysAccount.setItems(accounts);
                paysAccount.setConverter(new StringConverter<Accounts>() {
                    @Override
                    public String toString(Accounts patient) {
                        return patient.getName();
                    }

                    @Override
                    public Accounts fromString(String string) {
                        return null;
                    }
                });
                paysAccount.setCellFactory(cell -> new ListCell<Accounts>() {

                    GridPane gridPane = new GridPane();
                    Label lblid = new Label();
                    Label lblName = new Label();

                    {
                        gridPane.getColumnConstraints().addAll(
                                new ColumnConstraints(30, 30, 30),
                                new ColumnConstraints(170, 170, 170)
                        );

                        gridPane.add(lblid, 0, 1);
                        gridPane.add(lblName, 1, 1);

                    }

                    @Override
                    protected void updateItem(Accounts person, boolean empty) {
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
                paysMember.setItems(members);
                paysMember.setConverter(new StringConverter<AcapyMembers>() {
                    @Override
                    public String toString(AcapyMembers patient) {
                        return patient.getName();
                    }

                    @Override
                    public AcapyMembers fromString(String string) {
                        return null;
                    }
                });
                paysMember.setCellFactory(cell -> new ListCell<AcapyMembers>() {

                    GridPane gridPane = new GridPane();
                    Label lblid = new Label();
                    Label lblName = new Label();

                    {
                        gridPane.getColumnConstraints().addAll(
                                new ColumnConstraints(30, 30, 30),
                                new ColumnConstraints(170, 170, 170)
                        );

                        gridPane.add(lblid, 0, 1);
                        gridPane.add(lblName, 1, 1);

                    }

                    @Override
                    protected void updateItem(AcapyMembers person, boolean empty) {
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
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void getAccountsData(ActionEvent event) {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            ObservableList<ProviderAccounts> data;String totalAcc;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {

                            data = ProviderAccounts.getData(providers.getSelectionModel().getSelectedItem().getId());
                              totalAcc = ProviderAccounts.getTotalAcc(providers.getSelectionModel().getSelectedItem().getId());
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
                accTab.setItems(data);
                total.setText(totalAcc);
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void searchAccounts(KeyEvent event) {
    }

    @FXML
    private void searchPays(KeyEvent event) {
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
                                    alert.setTitle("Deleting pays  ");
                                    alert.setHeaderText("سيتم حذف  الدفعة ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        ProviderAccountsPays pr = new ProviderAccountsPays();
                                        pr.setId(Integer.parseInt(paysId.getText()));
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
                getPaysData(accTab.getSelectionModel().getSelectedItem().getId());

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
                                    alert.setTitle("  Editing  pays");
                                    alert.setHeaderText("سيتم  تعديل الدفعة ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        ProviderAccountsPays pr = new ProviderAccountsPays();
                                        pr.setId(Integer.parseInt(paysId.getText()));
                                        pr.setAccId(paysAccount.getSelectionModel().getSelectedItem().getId());
                                        pr.setAmount(paysAmount.getText());
                                        pr.setMem_id(paysMember.getSelectionModel().getSelectedItem().getId());
                                        pr.setProvider_id(providers.getSelectionModel().getSelectedItem().getId());
                                        pr.setProvider_acc_id(accTab.getSelectionModel().getSelectedItem().getId());
                                        pr.setDate(paysDate.getValue().format(format));
                                        pr.Edite();
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
                getPaysData(accTab.getSelectionModel().getSelectedItem().getId());

                super.succeeded();
            }
        };
        service.start();
    }
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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
                                try {
                                    ProviderAccountsPays pr = new ProviderAccountsPays();
                                    pr.setId(Integer.parseInt(paysId.getText()));
                                    pr.setAccId(paysAccount.getSelectionModel().getSelectedItem().getId());
                                    pr.setAmount(paysAmount.getText());
                                    pr.setMem_id(paysMember.getSelectionModel().getSelectedItem().getId());
                                    pr.setProvider_id(providers.getSelectionModel().getSelectedItem().getId());
                                    pr.setProvider_acc_id(accTab.getSelectionModel().getSelectedItem().getId());
                                    pr.setDate(paysDate.getValue().format(format));
                                    pr.Add();
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
                getPaysData(accTab.getSelectionModel().getSelectedItem().getId());

                super.succeeded();
            }
        };
        service.start();
    }

    private void getPaysData(int id) {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            ObservableList<ProviderAccountsPays> data;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            data = ProviderAccountsPays.getData(id);

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
                paysTab.setItems(data);
                super.succeeded();
            }
        };
        service.start();
    }

}
