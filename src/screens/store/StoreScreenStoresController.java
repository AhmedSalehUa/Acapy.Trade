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
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import screens.store.assets.Stores;
import screens.store.assets.StroreProducts;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class StoreScreenStoresController implements Initializable {

    @FXML
    private HBox doctorBox;
    @FXML
    private JFXTextField search;
    @FXML
    private TableView<Stores> storeTable;
    @FXML
    private TableColumn<Stores, String> storeTabName;
    @FXML
    private TableColumn<Stores, String> storeTabId;
    @FXML
    private Label storeId;
    @FXML
    private TextField storeName;
    @FXML
    private ProgressIndicator progress;
    @FXML
    private Button storeNew;
    @FXML
    private Button storeDelete;
    @FXML
    private Button storeEdite;
    @FXML
    private Button storeAdd;
    @FXML
    private TableView<StroreProducts> productsTable;
    @FXML
    private TableColumn<StroreProducts, String> productsTabInvoice;
    @FXML
    private TableColumn<StroreProducts, String> productsTabProduct;
    @FXML
    private TableColumn<StroreProducts, String> productsTabAmount;
    @FXML
    private TableColumn<StroreProducts, String> productsTabCost;
    @FXML
    private TableColumn<StroreProducts, String> productsTabCostOfSell;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        progress.setVisible(true);

        clear();
        intialColumn();
        getData();

        progress.setVisible(false);

        storeTable.setOnMouseClicked((e) -> {
            if (storeTable.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                storeNew.setDisable(false);

                storeDelete.setDisable(false);

                storeEdite.setDisable(false);

                storeAdd.setDisable(true);

                Stores selected = storeTable.getSelectionModel().getSelectedItem();
                storeId.setText(Integer.toString(selected.getId()));
                storeName.setText(selected.getName());
                getDataForStore(selected.getId());
            }
        });

    }

    private void intialColumn() {
        storeTabName.setCellValueFactory(new PropertyValueFactory<>("name"));

        storeTabId.setCellValueFactory(new PropertyValueFactory<>("id"));

        productsTabInvoice.setCellValueFactory(new PropertyValueFactory<>("invoice_id"));

        productsTabProduct.setCellValueFactory(new PropertyValueFactory<>("product"));

        productsTabAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        productsTabCost.setCellValueFactory(new PropertyValueFactory<>("costOfBuy"));

        productsTabCostOfSell.setCellValueFactory(new PropertyValueFactory<>("costOfSell"));
    }

    private void clear() {
        getAutoNum();
        storeNew.setDisable(true);

        storeDelete.setDisable(true);

        storeEdite.setDisable(true);

        storeAdd.setDisable(false);
        productsTable.setItems(null);
        storeName.setText("");

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
                            autoNum = Stores.getAutoNum();

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
                storeId.setText(autoNum);
                super.succeeded();
            }
        };
        service.start();
    }

    private void getData() {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            ObservableList<Stores> data;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {

                            data = Stores.getData();

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
                storeTable.setItems(data);
                super.succeeded();
            }
        };
        service.start();

    }

    @FXML
    private void search(KeyEvent event) {
    }

    @FXML
    private void storeNew(ActionEvent event) {
        clear();
    }

    @FXML
    private void storeDelete(ActionEvent event) {
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
                                    alert.setTitle("Deleting Store");
                                    alert.setHeaderText("سيتم حذف  المخزن ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        Stores st = new Stores();
                                        st.setId(Integer.parseInt(storeId.getText()));
                                        st.Delete();
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
    private void storeEdite(ActionEvent event) {
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
                                    alert.setTitle("Editing Store");
                                    alert.setHeaderText("سيتم  تعديل المخزن ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        Stores st = new Stores();
                                        st.setId(Integer.parseInt(storeId.getText()));
                                        st.setName(storeName.getText());
                                        st.Edite();
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
    private void storeAdd(ActionEvent event) {
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
                                    Stores st = new Stores();
                                    st.setId(Integer.parseInt(storeId.getText()));
                                    st.setName(storeName.getText());
                                    st.Add();

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

    private void getDataForStore(int id) {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            ObservableList<StroreProducts> dataForStore;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            dataForStore = StroreProducts.getDataForStore(id);

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
                productsTable.setItems(dataForStore);
                super.succeeded();
            }
        };
        service.start();
    }

}
