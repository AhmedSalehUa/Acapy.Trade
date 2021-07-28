package screens.Accounts;

import assets.classes.AlertDialogs;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;
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
import screens.Accounts.assets.Accounts;

/**
 * FXML Controller class
 *
 * @author amran
 */
public class AccountScreenAccountsController implements Initializable {

    @FXML
    private JFXTextField search;
    @FXML
    private TableView<Accounts> tab;
    @FXML
    private TableColumn<Accounts, String> tab_accType;
    @FXML
    private TableColumn<Accounts, String> tab_balance;
    @FXML
    private TableColumn<Accounts, String> tab_bankName;
    @FXML
    private TableColumn<Accounts, String> tab_accNum;
    @FXML
    private TableColumn<Accounts, String> tab_Name;
    @FXML
    private TableColumn<Accounts, String> tab_Id;
    @FXML
    private Label id;
    @FXML
    private TextField name;
    @FXML
    private TextField accNum;
    @FXML
    private ComboBox<String> accType;
    @FXML
    private TextField bankName;
    @FXML
    private TextField balance;
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

    /**
     * Initializes the controller class.
     */
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

                Accounts selected = tab.getSelectionModel().getSelectedItem();
                id.setText(Integer.toString(selected.getId()));
                name.setText(selected.getName());
                accNum.setText(selected.getAccountNumber());
                balance.setText(selected.getBalance());
                bankName.setText(selected.getBank());
                accType.getSelectionModel().select(selected.getType());

            }
        });
    }

    private void fillCombo() {
        accType.getItems().add("بنكي");
        accType.getItems().add("نص عهده");
        accType.getItems().add("البشمهندس");
    }

    private void intialColumn() {
        tab_Id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tab_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        tab_accNum.setCellValueFactory(new PropertyValueFactory<>("accountNumber"));
        tab_accType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tab_balance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        tab_bankName.setCellValueFactory(new PropertyValueFactory<>("bank"));
    }

    private void clear() {
        getAutoNum();
        formNew.setDisable(true);

        formDelete.setDisable(true);

        formEdite.setDisable(true);

        formAdd.setDisable(false);

        name.setText("");
        accNum.setText("");
        bankName.setText("");
        balance.setText("");
        accType.getSelectionModel().clearSelection();
    }

    private void getAutoNum() {
        try {
            id.setText(Accounts.getAutoNum());
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
                                    tab.setItems(Accounts.getData());
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
    //fill combo method

    @FXML
    private void search(KeyEvent event) {

    }

    @FXML
    private void AddCat(MouseEvent event) {
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
                                    alert.setTitle("Deleting  Account");
                                    alert.setHeaderText("سيتم حذف المورد ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        Accounts acc = new Accounts();
                                        acc.setId(Integer.parseInt(id.getText()));
                                        acc.Delete();
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
                                    alert.setTitle("Editting  Account");
                                    alert.setHeaderText("سيتم تعديل المورد ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        Accounts acc = new Accounts();
                                        acc.setId(Integer.parseInt(id.getText()));
                                        acc.setName(name.getText());
                                        acc.setAccountNumber(accNum.getText());
                                        acc.setBank(bankName.getText());
                                        acc.setBalance(balance.getText());
                                        acc.setType(accType.getSelectionModel().getSelectedItem());
                                        acc.Edite();
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
                                    Accounts acc = new Accounts();
                                    acc.setId(Integer.parseInt(id.getText()));
                                    acc.setAccountNumber(accNum.getText());
                                    acc.setBalance(balance.getText());
                                    acc.setBank(bankName.getText());
                                    acc.setName(name.getText());
                                    acc.setType(accType.getSelectionModel().getSelectedItem());
                                    acc.Add();
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
