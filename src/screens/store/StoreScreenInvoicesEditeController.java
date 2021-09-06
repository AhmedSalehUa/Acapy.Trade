/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.store;

import assets.classes.AlertDialogs;
import com.jfoenix.controls.JFXDatePicker;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import screens.Accounts.assets.Accounts;
import screens.store.assets.InvoicesBuy;
import screens.store.assets.InvoicesBuyDetails;
import screens.store.assets.Products;
import screens.store.assets.Provider;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class StoreScreenInvoicesEditeController implements Initializable {

    @FXML
    private TextArea notes;
    @FXML
    private Button showInvoice;
    @FXML
    private ComboBox<InvoicesBuy> invoiceId;
    @FXML
    private ComboBox<Provider> provider;
    @FXML
    private JFXDatePicker date;
    @FXML
    private TableView<InvoicesBuyDetails> invoiceTable;
    @FXML
    private MenuItem deleteRow;
    @FXML
    private TableColumn<InvoicesBuyDetails, String> invoiceTabCost;
    @FXML
    private TableColumn<InvoicesBuyDetails, String> invoiceTabAmount;
    @FXML
    private TableColumn<InvoicesBuyDetails, String> invoiceTabMedicine;
    @FXML
    private TableColumn<InvoicesBuyDetails, String> invoiceTabId;
    @FXML
    private Button invoiceDelete;
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
    private ComboBox<Accounts> accounts;
    @FXML
    private CheckBox onNote;
    @FXML
    private TextField filesPath;
    @FXML
    private CheckBox addtionalCost;

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
    }

    private void intialColumn() {
        invoiceTabCost.setCellValueFactory(new PropertyValueFactory<>("cost"));

        invoiceTabAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        invoiceTabMedicine.setCellValueFactory(new PropertyValueFactory<>("products"));

        invoiceTabId.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    private void getData(int id) {
        try {
            InvoicesBuy in = InvoicesBuy.getDataById(id).get(0);
            notes.setText(in.getNotes());
            invoiceTotal.setText(in.getCost());
            onNote.setSelected(in.getPayType() == null ? false : in.getPayType().equals("تقسيط"));
            addtionalCost.setSelected(Boolean.parseBoolean(in.getHasTaxs()));
            invoiceLastTotal.setText(in.getTotal_cost());
            invoicedisc.setText(in.getDicount());
            date.setValue(LocalDate.parse(in.getDate()));

            ObservableList<Provider> items1 = provider.getItems();
            for (Provider a : items1) {
                if (a.getName().equals(in.getProvider())) {
                    provider.getSelectionModel().select(a);
                }
            }
            ObservableList<Accounts> items2 = accounts.getItems();
            for (Accounts a : items2) {
                if (a.getId() == in.getAcc_id()) {
                    accounts.getSelectionModel().select(a);
                }
            }
            ObservableList<Products> data = Products.getData();
            invoiceTable.setItems(InvoicesBuyDetails.getData(id));
            invoiceTable.getItems().add(new InvoicesBuyDetails(1, data, "0", "0", "0", null));
            invoiceTable.setOnKeyReleased((event) -> {

                if (event.getCode() == KeyCode.ENTER) {
                    setTotal("");
                }
                if (event.getCode() == KeyCode.ENTER && invoiceTable.getSelectionModel().getSelectedItem().getProducts().getSelectionModel().getSelectedIndex() != -1
                        && !invoiceTable.getSelectionModel().getSelectedItem().getAmount().getText().equals("0")
                        && !invoiceTable.getSelectionModel().getSelectedItem().getCost().getText().equals("0")) {
                    setTotal("");
                    invoiceTable.getItems().add(new InvoicesBuyDetails(invoiceTable.getItems().size() + 1, data, "0", "0", "0", null));
                    invoiceTable.getSelectionModel().clearAndSelect(invoiceTable.getItems().size() - 1);
                }

            });
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void clear() {
        try {
            notes.setText("");
            invoiceTotal.setText("");
            invoiceDiscPercent.setText("");
            invoiceLastTotal.setText("");
            invoicedisc.setText("");
            date.setValue(null);
            provider.getSelectionModel().clearSelection();
            accounts.getSelectionModel().clearSelection();
            invoiceTable.setItems(null);
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    public void setTotal(String toString) {
        try {

            ObservableList<InvoicesBuyDetails> items1 = invoiceTable.getItems();
            double total = 0;
            for (InvoicesBuyDetails a : items1) {
                total += Double.parseDouble(a.getAmount().getText()) * Double.parseDouble(a.getCost().getText());
            }
            invoiceTotal.setText(Double.toString(total));
            if (addtionalCost.isSelected()) {
                invoiceTotal.setText(Double.toString(total + ((14 * total) / 100)));
            }
            double discount = 0;
            double discountPercent = 0;
            if (invoicedisc.getText().isEmpty()) {
            } else {

                discount = Double.parseDouble(invoicedisc.getText().isEmpty() ? "0" : invoicedisc.getText());

            }
            if (invoiceDiscPercent.getText().isEmpty()) {
            } else {
                String a = invoiceDiscPercent.getText().isEmpty() ? "0" : invoiceDiscPercent.getText();
                discountPercent = ((Double.parseDouble(a) * total) / 100);

            }

            invoiceLastTotal.setText(Double.toString(total - discount - discountPercent));
        } catch (Exception e) {
            AlertDialogs.showErrors(e);
        }
    }

    private void fillCombo() {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            ObservableList<Accounts> accData;
            ObservableList<InvoicesBuy> inData;
            ObservableList<Provider> proData;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            accData = Accounts.getData();
                            inData = InvoicesBuy.getData();
                            proData = Provider.getData();
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

                invoiceId.setItems(inData);
                invoiceId.setConverter(new StringConverter<InvoicesBuy>() {
                    @Override
                    public String toString(InvoicesBuy patient) {
                        return patient.getDate();
                    }

                    @Override
                    public InvoicesBuy fromString(String string) {
                        return null;
                    }
                });
                invoiceId.setCellFactory(cell -> new ListCell<InvoicesBuy>() {

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
                    protected void updateItem(InvoicesBuy person, boolean empty) {
                        super.updateItem(person, empty);

                        if (!empty && person != null) {

                            lblid.setText("م: " + Integer.toString(person.getId()));
                            lblName.setText("التاريخ: " + person.getDate());

                            setGraphic(gridPane);
                        } else {
                            setGraphic(null);
                        }
                    }
                });
                provider.setItems(proData);
                provider.setConverter(new StringConverter<Provider>() {
                    @Override
                    public String toString(Provider patient) {
                        return patient.getName();
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

                    {
                        gridPane.getColumnConstraints().addAll(
                                new ColumnConstraints(100, 100, 100),
                                new ColumnConstraints(100, 100, 100)
                        );

                        gridPane.add(lblid, 0, 1);
                        gridPane.add(lblName, 1, 1);

                    }

                    @Override
                    protected void updateItem(Provider person, boolean empty) {
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
                accounts.setItems(accData);
                accounts.setConverter(new StringConverter<Accounts>() {
                    @Override
                    public String toString(Accounts patient) {
                        return patient.getName();
                    }

                    @Override
                    public Accounts fromString(String string) {
                        return null;
                    }
                });
                accounts.setCellFactory(cell -> new ListCell<Accounts>() {

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

                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void showInvoice(ActionEvent event) {
        getData(invoiceId.getSelectionModel().getSelectedItem().getId());
    }

    @FXML
    private void deleteRow(ActionEvent event) {
        if (invoiceTable.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("اختار الصف اولا");
        } else {
            if (invoiceTable.getSelectionModel().getSelectedItem().getProducts().getSelectionModel().getSelectedIndex() != -1
                    && !invoiceTable.getSelectionModel().getSelectedItem().getAmount().getText().equals("0")
                    && !invoiceTable.getSelectionModel().getSelectedItem().getCost().getText().equals("0")) {
                invoiceTable.getItems().remove(invoiceTable.getSelectionModel().getSelectedIndex());
            }
        }
    }

    @FXML
    private void invoiceDelete(ActionEvent event) {
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
                                    alert.setTitle("Deleting Invoices  ");
                                    alert.setHeaderText("سيتم حذف  الفاتورة ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        InvoicesBuy in = new InvoicesBuy();
                                        in.setId(invoiceId.getSelectionModel().getSelectedItem().getId());
                                        in.Delete();
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
                    AlertDialogs.showmessage("تم");
                }
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void invoiveAdd(ActionEvent event) {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            boolean ok = true;
            InvoicesBuy in = new InvoicesBuy();

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
                                    setTotal("");
                                    if (date.getValue() == null) {
                                        AlertDialogs.showError("برجاء ادخال تاريخ الفاتورة");
                                    } else if (provider.getSelectionModel().getSelectedIndex() == -1) {
                                        AlertDialogs.showError("برجاء اختيار المورد");
                                    } else if (accounts.getSelectionModel().getSelectedIndex() == -1 && onNote.isSelected()) {
                                        AlertDialogs.showError("برجاء اختيار الحساب");
                                    } else if (invoiceTable.getItems().isEmpty()) {
                                        AlertDialogs.showError("لا يجب ان يكون الجدول فارغ");
                                    } else if (invoiceTable.getItems().size() == 1 && invoiceTotal.getText().equals("0") || invoiceTotal.getText().equals("0.0")) {
                                        AlertDialogs.showError("لا يجب ان يكون الجدول فارغ");
                                    } else if (invoiceTotal.getText().equals("0")) {
                                        setTotal("");
                                    } else if (invoiceTable.getItems().size() == 1) {
                                        AlertDialogs.showError("لا يجب ان يكون الجدول فارغ");
                                    } else {
                                        ObservableList<InvoicesBuyDetails> items = invoiceTable.getItems();

                                        if (items.size() - 1 == 0) {
                                            AlertDialogs.showError("اضغط Enter اذا كان الجدول غير فارغ على اخر خانة");
                                        } else {
                                            ok = false;
                                            in = new InvoicesBuy();
                                            in.setId(invoiceId.getSelectionModel().getSelectedItem().getId());
                                            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                            in.setDate(date.getValue().format(format));
                                            in.setProvider_id(provider.getSelectionModel().getSelectedItem().getId());
                                            in.setAcc_id(accounts.getSelectionModel().getSelectedItem().getId());
                                            in.setCost(invoiceTotal.getText());
                                            in.setDicount(invoicedisc.getText().isEmpty() ? "0" : invoicedisc.getText());
                                            in.setTotal_cost(invoiceLastTotal.getText());
                                            in.setNotes(notes.getText().isEmpty() ? "لايوجد" : notes.getText());

                                            items.remove(items.size() - 1);
                                            in.setDetails(items);
                                            in.setPayType(onNote.isSelected() ? "تقسيط" : "كاش");
                                             in.setHasTaxs(addtionalCost.isSelected() ? "true" : "false");
                                            if (filesPath.getText().isEmpty() || filesPath.getText().length() == 0) {
                                                in.EditeWithoutPhoto();
                                            } else {
                                                InputStream input = new FileInputStream(new File(filesPath.getText()));
                                                in.setDoc(input);

                                                String[] st = filesPath.getText().split(Pattern.quote("."));
                                                in.setExt(st[st.length - 1]);
                                                in.Edite();
                                            }
                                            ok = true;
                                        }
                                    }
                                } catch (Exception ex) {
                                    AlertDialogs.showErrors(ex);
                                    ok = false;
                                    try {

                                        in.Delete();
                                    } catch (Exception ex1) {
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
                progress.setVisible(false);
                if (ok) {

                    AlertDialogs.showmessage("تم");

                    clear();
                }

                super.succeeded();
            }

        };
        service.start();
    }

    @FXML
    private void attachFile(MouseEvent event) {
        FileChooser fil_chooser = new FileChooser();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File file = fil_chooser.showOpenDialog(st);

        if (file != null) {
            filesPath.setText(file.getAbsolutePath());
        }
    }

    @FXML
    private void addDariba(ActionEvent event) {
        setTotal("");
    }

}
