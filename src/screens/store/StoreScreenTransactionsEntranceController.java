/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.store;

import assets.classes.AlertDialogs;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import screens.store.assets.InvoicesBuy;
import screens.store.assets.InvoicesBuyDetails;
import screens.store.assets.StoreTransactions;
import screens.store.assets.Stores;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class StoreScreenTransactionsEntranceController implements Initializable {

    @FXML
    private TableView<InvoicesBuy> invoiceTable;
    @FXML
    private TableColumn<InvoicesBuy, String> invoiceTabNotes;
    @FXML
    private TableColumn<InvoicesBuy, String> invoiceTabTotalCost;
    @FXML
    private TableColumn<InvoicesBuy, String> invoiceTabDisc;
    @FXML
    private TableColumn<InvoicesBuy, String> invoiceTabCost;
    @FXML
    private TableColumn<InvoicesBuy, String> invoiceTabDate;
    @FXML
    private TableColumn<InvoicesBuy, String> invoiceTabComp;
    @FXML
    private TableColumn<InvoicesBuy, String> invoiceTabId;
    @FXML
    private TableView<InvoicesBuyDetails> invoiceDetailsTable;
    @FXML
    private TableColumn<InvoicesBuyDetails, String> invoiceDetailsTabCostOfSell;
    @FXML
    private TableColumn<InvoicesBuyDetails, String> invoiceDetailsTabCost;
    @FXML
    private TableColumn<InvoicesBuyDetails, String> invoiceDetailsTabAmount;
    @FXML
    private TableColumn<InvoicesBuyDetails, String> invoiceDetailsTabMediccine;
    @FXML
    private TableColumn<InvoicesBuyDetails, String> invoiceDetailsTabId;
    @FXML
    private ComboBox<Stores> stores;
    @FXML
    private Button save;
    @FXML
    private ProgressIndicator progress;
    @FXML
    private TableColumn<InvoicesBuy, String> invoiceTabPayType;

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

                                    initColumn();

                                    filCombo();
                                    getData();
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
        invoiceTable.setOnMouseClicked((e) -> {
            if (invoiceTable.getSelectionModel().getSelectedIndex() == -1) {
                invoiceDetailsTable.setItems(null);
                invoiceTable.getSelectionModel().clearSelection();
            } else {
                getInvoiceDetails(invoiceTable.getSelectionModel().getSelectedItem().getId());
            }
        });
    }

    private void filCombo() throws Exception {
        stores.setItems(Stores.getData());
        stores.setConverter(new StringConverter<Stores>() {
            @Override
            public String toString(Stores st) {
                return st.getName();
            }

            @Override
            public Stores fromString(String string) {
                return null;
            }
        });
        stores.setCellFactory(cell -> new ListCell<Stores>() {

            GridPane gridPane = new GridPane();
            Label lblid = new Label();

            {
                gridPane.getColumnConstraints().addAll(
                        new ColumnConstraints(100, 100, 100)
                );
                gridPane.add(lblid, 0, 1);

            }

            @Override
            protected void updateItem(Stores person, boolean empty) {
                super.updateItem(person, empty);

                if (!empty && person != null) {
                    lblid.setText("الاسم: " + person.getName());

                    setGraphic(gridPane);
                } else {
                    setGraphic(null);
                }
            }
        });
    }

    private void getData() throws Exception {
        invoiceDetailsTable.setItems(null);
        invoiceTable.getSelectionModel().clearSelection();
        invoiceTable.setItems(InvoicesBuy.getDataNotInStore());
    }

    private void initColumn() {
        invoiceTabNotes.setCellValueFactory(new PropertyValueFactory<>("notes"));

        invoiceTabTotalCost.setCellValueFactory(new PropertyValueFactory<>("total_cost"));

        invoiceTabPayType.setCellValueFactory(new PropertyValueFactory<>("payType"));

        invoiceTabDisc.setCellValueFactory(new PropertyValueFactory<>("dicount"));

        invoiceTabCost.setCellValueFactory(new PropertyValueFactory<>("cost"));

        invoiceTabDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        invoiceTabComp.setCellValueFactory(new PropertyValueFactory<>("provider"));

        invoiceTabId.setCellValueFactory(new PropertyValueFactory<>("id"));

        invoiceDetailsTabCostOfSell.setCellValueFactory(new PropertyValueFactory<>("costOfSell"));

        invoiceDetailsTabCost.setCellValueFactory(new PropertyValueFactory<>("costString"));

        invoiceDetailsTabAmount.setCellValueFactory(new PropertyValueFactory<>("amountString"));

        invoiceDetailsTabMediccine.setCellValueFactory(new PropertyValueFactory<>("product"));

        invoiceDetailsTabId.setCellValueFactory(new PropertyValueFactory<>("id"));

    }

    @FXML
    private void save(ActionEvent event) {

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
                                    if (invoiceTable.getSelectionModel().getSelectedIndex() == -1) {
                                        AlertDialogs.showError("اختار الفاتورة اولا");
                                    } else if (stores.getSelectionModel().getSelectedIndex() == -1) {
                                        AlertDialogs.showError("اختار المخزن");
                                    } else {
                                        for (InvoicesBuyDetails a : invoiceDetailsTable.getItems()) {
                                            StoreTransactions ts = new StoreTransactions();
                                            ts.setInvoiceId(a.getId());
                                            ts.setStoreId(stores.getSelectionModel().getSelectedItem().getId());
                                            ts.setProductId(a.getProductID());
                                            ts.setAmount(a.getAmountString());
                                            ts.setCost(a.getCostString());
                                            ts.setCostOfSell(a.getCostOfSell().getText());
                                            ts.AddToStores();

                                        }

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
                try {
                    getData();
                } catch (Exception ex) {
                    AlertDialogs.showErrors(ex);
                }
                super.succeeded();
            }
        };
        service.start();
    }

    private void getInvoiceDetails(int id) {
        try {
            invoiceDetailsTable.setItems(InvoicesBuyDetails.getDataByIdEntrance(id));
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }
}
