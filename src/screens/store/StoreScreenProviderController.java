/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.store;

import assets.classes.AlertDialogs;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import screens.store.assets.ProductCategory;
import screens.store.assets.Provider;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class StoreScreenProviderController implements Initializable {

    @FXML
    private JFXTextField search;
    @FXML
    private TableView<Provider> tab;
    @FXML
    private TableColumn<Provider, String> tabCat;
    @FXML
    private TableColumn<Provider, String> tabAccountNum;
    @FXML
    private TableColumn<Provider, String> tabTotalNumber;
    @FXML
    private TableColumn<Provider, String> tabAdress;
    @FXML
    private TableColumn<Provider, String> tabName;
    @FXML
    private TableColumn<Provider, String> tabId;
    @FXML
    private Label id;
    @FXML
    private TextField name;
    @FXML
    private TextArea address;
    @FXML
    private ComboBox<ProductCategory> category;
    @FXML
    private TextField total_account;
    @FXML
    private TextField account_num;
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
    private TableColumn<Provider, String> tabMobile;
    @FXML
    private TableColumn<Provider, String> tabOrganization;
    @FXML
    private TextField organization;
    @FXML
    private TextArea mobiles;

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

                Provider selected = tab.getSelectionModel().getSelectedItem();
                id.setText(Integer.toString(selected.getId()));
                name.setText(selected.getName());
                address.setText(selected.getAddress());
                total_account.setText(selected.getTotalAccount());
                account_num.setText(selected.getAccountNumber());
                organization.setText(selected.getOrganization());
                mobiles.setText(selected.getMobiles());
                ObservableList<ProductCategory> items1 = category.getItems();
                for (ProductCategory a : items1) {
                    if (a.getName().equals(selected.getCategory())) {
                        category.getSelectionModel().select(a);
                    }
                }
            }
        });
    }

    private void intialColumn() {
        tabMobile.setCellValueFactory(new PropertyValueFactory<>("mobiles"));

        tabOrganization.setCellValueFactory(new PropertyValueFactory<>("organization"));

        tabCat.setCellValueFactory(new PropertyValueFactory<>("category"));

        tabAccountNum.setCellValueFactory(new PropertyValueFactory<>("accountNumber"));

        tabTotalNumber.setCellValueFactory(new PropertyValueFactory<>("totalAccount"));

        tabAdress.setCellValueFactory(new PropertyValueFactory<>("address"));

        tabName.setCellValueFactory(new PropertyValueFactory<>("name"));

        tabId.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    private void clear() {
        getAutoNum();
        formNew.setDisable(true);

        formDelete.setDisable(true);

        formEdite.setDisable(true);

        formAdd.setDisable(false);

        name.setText("");
        address.setText("");
        total_account.setText("");
        account_num.setText("");
        organization.setText("");
        mobiles.setText("");
        category.getSelectionModel().clearSelection();
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
                            autoNum = Provider.getAutoNum();

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

    private void getData() {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            ObservableList<Provider> data;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            data = Provider.getData();
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
                tab.setItems(data);
                super.succeeded();
            }
        };
        service.start();
    }

    private void fillCombo() {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            ObservableList<ProductCategory> data;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {

                            data = ProductCategory.getData();
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
                category.setItems(data);
                category.setConverter(new StringConverter<ProductCategory>() {
                    @Override
                    public String toString(ProductCategory patient) {
                        return patient.getName();
                    }

                    @Override
                    public ProductCategory fromString(String string) {
                        return null;
                    }
                });
                category.setCellFactory(cell -> new ListCell<ProductCategory>() {

                    // Create our layout here to be reused for each ListCell
                    GridPane gridPane = new GridPane();
                    Label lblid = new Label();
                    Label lblName = new Label();

                    // Static block to configure our layout
                    {
                        // Ensure all our column widths are constant
                        gridPane.getColumnConstraints().addAll(
                                new ColumnConstraints(50, 50, 50),
                                new ColumnConstraints(150, 150, 150)
                        );

                        gridPane.add(lblid, 0, 1);
                        gridPane.add(lblName, 1, 1);

                    }

                    // We override the updateItem() method in order to provide our own layout for this Cell's graphicProperty
                    @Override
                    protected void updateItem(ProductCategory person, boolean empty) {
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

    @FXML
    private void search(KeyEvent event) {
    }

    @FXML
    private void AddCat(MouseEvent event) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Add Cat Name");
        dialog.setHeaderText("اضافة تصنيف جديد");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (result.get().isEmpty() || result.get() == null) {
                AlertDialogs.showError("خطا!! يرجي ادخال اسم نوع");
            } else {
                final String results = result.get();
                try {
                    Service service = new Service() {
                        @Override
                        protected Task createTask() {
                            return new Task() {
                                @Override
                                protected Object call() throws Exception {
                                    final CountDownLatch latch = new CountDownLatch(1);
                                    Platform.runLater(() -> {
                                        try {
                                            ProductCategory.Add(results);
                                        } catch (Exception ex) {
                                            AlertDialogs.showErrors(ex);
                                        } finally {
                                            latch.countDown();
                                        }
                                    });
                                    latch.await();

                                    return null;
                                }

                                @Override
                                protected void succeeded() {
                                    try {
                                        fillCombo();
                                    } catch (Exception ex) {
                                        AlertDialogs.showErrors(ex);
                                    }
                                }
                            };
                        }
                    };
                    service.start();

                } catch (Exception ex) {
                    AlertDialogs.showErrors(ex);
                }
            }
        }
    }

    @FXML
    private void New(ActionEvent event) {
        clear();
    }

    @FXML
    private void Delete(ActionEvent event) {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
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
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Deleting  Provider");
                                    alert.setHeaderText("سيتم حذف المورد ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        ok = false;
                                        Provider pr = new Provider();
                                        pr.setId(Integer.parseInt(id.getText()));
                                        pr.Delete();
                                        ok = true;
                                    }
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

    @FXML
    private void Edite(ActionEvent event) {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
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
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Editting  Provider");
                                    alert.setHeaderText("سيتم تعديل المورد ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        ok = false;
                                        Provider pr = new Provider();
                                        pr.setId(Integer.parseInt(id.getText()));
                                        pr.setName(name.getText());
                                        pr.setOrganization(organization.getText());
                                        pr.setMobiles(mobiles.getText());
                                        pr.setAddress(address.getText());
                                        pr.setAccountNumber(account_num.getText());
                                        pr.setTotalAccount(total_account.getText());
                                        pr.setCat_id(category.getSelectionModel().getSelectedItem().getId());
                                        pr.Edite();
                                        ok = true;
                                    }
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

    @FXML
    private void Add(ActionEvent event) {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
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
                                    ok = false;
                                    Provider pr = new Provider();
                                    pr.setId(Integer.parseInt(id.getText()));
                                    pr.setName(name.getText());
                                    pr.setOrganization(organization.getText());
                                    pr.setMobiles(mobiles.getText());
                                    pr.setAddress(address.getText());
                                    pr.setAccountNumber(account_num.getText());
                                    pr.setTotalAccount(total_account.getText());
                                    pr.setCat_id(category.getSelectionModel().getSelectedItem().getId());
                                    pr.Add();
                                    ok = true;
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
