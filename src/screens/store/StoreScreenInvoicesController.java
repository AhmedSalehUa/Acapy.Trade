/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.store;

import acapy.trade.AcapyTrade;
import assets.classes.AlertDialogs;
import static assets.classes.statics.DEFAULT_THEME;
import static assets.classes.statics.THEME;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.prefs.Preferences;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.controlsfx.control.ToggleSwitch;
import screens.Accounts.assets.Accounts;
import screens.clients.assets.Clients;
import screens.store.assets.InvoicesBuy;
import screens.store.assets.InvoicesBuyDetails;
import screens.store.assets.Products;
import screens.store.assets.Provider;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class StoreScreenInvoicesController implements Initializable {

    @FXML
    private TextArea notes;
    @FXML
    private Label id;
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

    Preferences prefs;

    @FXML
    private ComboBox<Accounts> accounts;
    @FXML
    private CheckBox onNote;
    @FXML
    private TextField filesPath;
    @FXML
    private AnchorPane editePanel;
    @FXML
    private AnchorPane show;
    @FXML
    private CheckBox addtionalCost;
    @FXML
    private CheckBox hasTaxes;

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

                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    clear();
                                    intialColumn();
                                    fillCombo();
                                    getData();
                                    ConfigPanels();
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

    private void ConfigPanels() throws Exception {
        editePanel.getChildren().clear();
        editePanel.getChildren().add(FXMLLoader.load(getClass().getResource("StoreScreenInvoicesEdite.fxml")));

        show.getChildren().clear();
        show.getChildren().add(FXMLLoader.load(getClass().getResource("StoreScreenInvoicesShow.fxml")));
    }

    private void intialColumn() {
        invoiceTabCost.setCellValueFactory(new PropertyValueFactory<>("cost"));

        invoiceTabAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        invoiceTabMedicine.setCellValueFactory(new PropertyValueFactory<>("products"));

        invoiceTabId.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    private void getData() {
        try {
            ObservableList<Products> data = Products.getData();
            ObservableList<InvoicesBuyDetails> list = FXCollections.observableArrayList();
            list.add(new InvoicesBuyDetails(1, data, "0", "0", "0", "0"));
            invoiceTable.setItems(list);
            invoiceTable.setOnKeyReleased((event) -> {

                if (event.getCode() == KeyCode.ENTER) {
                    setTotal("");
                }
                if (event.getCode() == KeyCode.ENTER && invoiceTable.getSelectionModel().getSelectedItem().getProducts().getSelectionModel().getSelectedIndex() != -1
                        && !invoiceTable.getSelectionModel().getSelectedItem().getAmount().getText().equals("0")
                        && !invoiceTable.getSelectionModel().getSelectedItem().getCost().getText().equals("0")) {
                    setTotal("");
                    invoiceTable.getItems().add(new InvoicesBuyDetails(invoiceTable.getItems().size() + 1, data, "0", "0", "0", "0"));
                    invoiceTable.getSelectionModel().clearAndSelect(invoiceTable.getItems().size() - 1);
                }

            });
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void clear() {
        try {
            getAutoNum();
            notes.setText("");
            invoiceTotal.setText("");
            invoiceDiscPercent.setText("");
            invoiceLastTotal.setText("");
            invoicedisc.setText("");
            date.setValue(null);
            provider.getSelectionModel().clearSelection();
            accounts.getSelectionModel().clearSelection();
            filesPath.setText("");
            onNote.setSelected(false);
            addtionalCost.setSelected(false);
            ObservableList<Products> data = Products.getData();
            ObservableList<InvoicesBuyDetails> list = FXCollections.observableArrayList();
            list.add(new InvoicesBuyDetails(1, data, "0", "0", "0", "0"));
            invoiceTable.setItems(list);
            invoiceTable.setOnKeyReleased((event) -> {

                if (event.getCode() == KeyCode.ENTER) {
                    setTotal("");
                }
                if (event.getCode() == KeyCode.ENTER && invoiceTable.getSelectionModel().getSelectedItem().getProducts().getSelectionModel().getSelectedIndex() != -1
                        && !invoiceTable.getSelectionModel().getSelectedItem().getAmount().getText().equals("0")
                        && !invoiceTable.getSelectionModel().getSelectedItem().getCost().getText().equals("0")) {
                    setTotal("");
                    invoiceTable.getItems().add(new InvoicesBuyDetails(invoiceTable.getItems().size() + 1, data, "0", "0", "0", "0")); 
                    invoiceTable.getSelectionModel().clearAndSelect(invoiceTable.getItems().size() - 1);
                }

            });
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
//            if (addtionalCost.isSelected()) {
//                invoiceTotal.setText(Double.toString(total + ((14 * total) / 100)));
//               
//            }
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
            if (addtionalCost.isSelected()) {
                invoiceLastTotal.setText(Double.toString(Double.parseDouble(invoiceLastTotal.getText()) + ((14 * Double.parseDouble(invoiceLastTotal.getText())) / 100)));
            }
        } catch (Exception e) {
            AlertDialogs.showErrors(e);
        }
    }

    private void getAutoNum() {
        try {
            id.setText(InvoicesBuy.getAutoNum());
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void fillCombo() {
        try {
            provider.setItems(Provider.getData());
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
            accounts.setItems(Accounts.getData());
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
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
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
                                            in.setId(Integer.parseInt(id.getText()));
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
                                            in.setHasTaxs(addtionalCost.isSelected() ? "true" : hasTaxes.isSelected() ? "true " : "false");
                                            if (filesPath.getText().isEmpty() || filesPath.getText().length() == 0) {
                                                in.AddWithoutPhoto();
                                            } else {
                                                InputStream input = new FileInputStream(new File(filesPath.getText()));
                                                in.setDoc(input);

                                                String[] st = filesPath.getText().split(Pattern.quote("."));
                                                in.setExt(st[st.length - 1]);
                                                in.Add();
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
        hasTaxes.setSelected(false);
        setTotal("");
    }

    @FXML
    private void setDiscounts(KeyEvent event) {
        setTotal("");
    }

    @FXML
    private void removeSelect(ActionEvent event) {
        addtionalCost.setSelected(false);
    }

}
