/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.store;

import assets.classes.AlertDialogs;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
import screens.store.assets.Provider;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class StoreScreenInvoicesShowController implements Initializable {

    @FXML
    private ComboBox<Provider> provider;
    @FXML
    private CheckBox withProvider;
    @FXML
    private JFXDatePicker dateFrom;
    @FXML
    private CheckBox withDateFrom;
    @FXML
    private JFXDatePicker dateTo;
    @FXML
    private CheckBox withDateTo;
    @FXML
    private ProgressIndicator progress;
    @FXML
    private Button showInvoices;
    @FXML
    private Button print;
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
    private TableColumn<InvoicesBuyDetails, String> invoiceDetailsTabCost;
    @FXML
    private TableColumn<InvoicesBuyDetails, String> invoiceDetailsTabAmount;
    @FXML
    private TableColumn<InvoicesBuyDetails, String> invoiceDetailsTabMediccine;
    @FXML
    private TableColumn<InvoicesBuyDetails, String> invoiceDetailsTabId;

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @FXML
    private TableColumn<InvoicesBuyDetails, String> invoiceTabAccId;

    String repName = " ";
    String sql = "SELECT st_invoices.id,st_invoices.date,st_provider.name,st_invoices.cost,st_invoices.discount,st_invoices.total_cost,st_invoices.pay_type,st_invoices.account_id,st_invoices.notes from st_invoices,st_provider where st_invoices.provider_id = st_provider.id";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dateFrom.setConverter(new StringConverter<LocalDate>() {
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
        dateTo.setConverter(new StringConverter<LocalDate>() {
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
                                    initColumn();
                                    filCombo();

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

    private void initColumn() {
        invoiceTabNotes.setCellValueFactory(new PropertyValueFactory<>("notes"));

        invoiceTabTotalCost.setCellValueFactory(new PropertyValueFactory<>("total_cost"));

        invoiceTabAccId.setCellValueFactory(new PropertyValueFactory<>("acc_id"));

        invoiceTabDisc.setCellValueFactory(new PropertyValueFactory<>("dicount"));

        invoiceTabCost.setCellValueFactory(new PropertyValueFactory<>("cost"));

        invoiceTabDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        invoiceTabComp.setCellValueFactory(new PropertyValueFactory<>("provider"));

        invoiceTabId.setCellValueFactory(new PropertyValueFactory<>("id"));

        invoiceDetailsTabCost.setCellValueFactory(new PropertyValueFactory<>("costString"));

        invoiceDetailsTabAmount.setCellValueFactory(new PropertyValueFactory<>("amountString"));

        invoiceDetailsTabMediccine.setCellValueFactory(new PropertyValueFactory<>("product"));

        invoiceDetailsTabId.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    private void filCombo() throws Exception {
        provider.setItems(Provider.getData());
        provider.setConverter(new StringConverter<Provider>() {
            @Override
            public String toString(Provider contract) {
                return contract.getName();
            }

            @Override
            public Provider fromString(String string) {
                return null;
            }
        });
        provider.setCellFactory(cell -> new ListCell<Provider>() {

            GridPane gridPane = new GridPane();
            Label lblid = new Label();
            Label lblName = new Label();
            Label lblType = new Label();

            {
                gridPane.getColumnConstraints().addAll(
                        new ColumnConstraints(100, 100, 100), new ColumnConstraints(100, 100, 100),
                        new ColumnConstraints(100, 100, 100)
                );
                gridPane.add(lblid, 0, 1);
                gridPane.add(lblName, 1, 1);
                gridPane.add(lblType, 2, 1);
            }

            @Override
            protected void updateItem(Provider person, boolean empty) {
                super.updateItem(person, empty);

                if (!empty && person != null) {
                    lblid.setText("م: " + Integer.toString(person.getId()));
                    lblName.setText("الاسم: " + person.getName());
                    lblType.setText("النوع: " + person.getCategory());
                    setGraphic(gridPane);
                } else {
                    setGraphic(null);
                }
            }
        });
    }

    private void getData() {

    }

    private void getInvoiceDetails(int id) {
        try {
            invoiceDetailsTable.setItems(InvoicesBuyDetails.getDataById(id));
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    @FXML
    private void showInvoices(ActionEvent event) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        sql = "SELECT st_invoices.id,st_invoices.date,st_provider.name,st_invoices.cost,st_invoices.discount,st_invoices.total_cost,st_invoices.pay_type,st_invoices.account_id,st_invoices.notes from st_invoices,st_provider where st_invoices.provider_id = st_provider.id";
        repName = "";
        if (withProvider.isSelected()) {
            int compId = provider.getSelectionModel().getSelectedItem().getId();
            sql += " AND st_invoices.provider_id='" + compId + "'";
            repName += "الخاصة بشركة: " + provider.getSelectionModel().getSelectedItem().getName() + " ";
        }
        if (withDateFrom.isSelected()) {
            sql += " AND st_invoices.date >= '" + dateFrom.getValue().format(format) + "'";
            repName += " للفترة من : " + dateFrom.getValue().format(format);
        }
        if (withDateTo.isSelected()) {
            sql += " AND st_invoices.date <= '" + dateTo.getValue().format(format) + "'";
            repName += " للفترة الي: " + dateTo.getValue().format(format);
        }
        try {
            invoiceDetailsTable.setItems(null);
            invoiceTable.setItems(InvoicesBuy.getCutomData(sql));
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    @FXML
    private void printInvoices(ActionEvent event) {
    }

}
