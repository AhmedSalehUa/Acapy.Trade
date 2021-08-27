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
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import screens.Accounts.assets.Accounts;
import screens.Accounts.assets.Yields;
import screens.Accounts.assets.Expenses;
import screens.Accounts.assets.ExpensesCategory;
import screens.Accounts.assets.YieldsCategory;

public class ExpenseAndYieldsController implements Initializable {

    @FXML
    private JFXTextField searchYields;
    @FXML
    private TableView<Yields> yieldsTab;
    @FXML
    private TableColumn<Yields, String> yieldsTabCat;
    @FXML
    private TableColumn<Yields, String> yieldsTabDate;
    @FXML
    private TableColumn<Yields, String> yieldsTabAmount;
    @FXML
    private TableColumn<Yields, String> yieldsTabId;
    @FXML
    private TableColumn<Yields, String> yieldsTabAcc;
    @FXML
    private Label yieldsId;
    @FXML
    private TextField yieldsAmount;
    @FXML
    private ComboBox<YieldsCategory> yieldsCategory;
    @FXML
    private JFXDatePicker yieldsDate;
    @FXML
    private ProgressIndicator progressYields;
    @FXML
    private Button formNewYields;
    @FXML
    private Button formDeleteYields;
    @FXML
    private Button formEditeYields;
    @FXML
    private Button formAddYields;
    @FXML
    private JFXTextField searchExpenses;
    @FXML
    private TableView<Expenses> expensesTab;
    @FXML
    private TableColumn<Expenses, String> expensesTabCat;
    @FXML
    private TableColumn<Expenses, String> expensesTabDate;
    @FXML
    private TableColumn<Expenses, String> expensesTabAmount;
    @FXML
    private TableColumn<Expenses, String> expensesTabId;
    @FXML
    private TableColumn<Expenses, String> expensesTabAccount;
    @FXML
    private Label expensesId;
    @FXML
    private TextField expensesAmount;
    @FXML
    private ComboBox<ExpensesCategory> expensesCategory;
    @FXML
    private JFXDatePicker expensesDate;
    @FXML
    private ProgressIndicator progressExpenses;
    @FXML
    private Button formNewExpenses;
    @FXML
    private Button formDeleteExpenses;
    @FXML
    private Button formEditeExpenses;
    @FXML
    private Button formAddExpenses;
    @FXML
    private ComboBox<Accounts> yieldsAccount;
    @FXML
    private ComboBox<Accounts> expensesAccounts;

    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        progressExpenses.setVisible(true);
        progressYields.setVisible(true);
        expensesDate.setConverter(new StringConverter<LocalDate>() {

            @Override
            public String toString(LocalDate localDate) {
                if (localDate == null) {
                    return "";
                }
                return format.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if (dateString == null || dateString.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(dateString, format);
            }
        });
        yieldsDate.setConverter(new StringConverter<LocalDate>() {

            @Override
            public String toString(LocalDate localDate) {
                if (localDate == null) {
                    return "";
                }
                return format.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if (dateString == null || dateString.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(dateString, format);
            }
        });
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
                                    clearExpenses();
                                    clearYields();
                                    intialColumn();
                                    getDataExpenses();
                                    getDataYields();
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
                progressExpenses.setVisible(false);
                progressYields.setVisible(false);

                super.succeeded();
            }
        };
        service.start();
        yieldsTab.setOnMouseClicked((e) -> {
            if (yieldsTab.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                formNewYields.setDisable(false);

                formDeleteYields.setDisable(false);

                formEditeYields.setDisable(false);

                formAddYields.setDisable(true);

                Yields selected = yieldsTab.getSelectionModel().getSelectedItem();
                yieldsId.setText(Integer.toString(selected.getId()));
                yieldsDate.setValue(LocalDate.parse(selected.getDate()));
                yieldsAmount.setText(selected.getAmount());
                ObservableList<YieldsCategory> yields = yieldsCategory.getItems();
                for (YieldsCategory a : yields) {
                    if (a.getName().equals(selected.getCategory())) {
                        yieldsCategory.getSelectionModel().select(a);
                    }
                }
                ObservableList<Accounts> accs = yieldsAccount.getItems();
                for (Accounts a : accs) {
                    if (a.getName().equals(selected.getAccount())) {
                        yieldsAccount.getSelectionModel().select(a);
                    }
                }
            }
        });
        expensesTab.setOnMouseClicked((e) -> {
            if (expensesTab.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                formNewExpenses.setDisable(false);

                formDeleteExpenses.setDisable(false);

                formEditeExpenses.setDisable(false);

                formAddExpenses.setDisable(true);

                Expenses selected = expensesTab.getSelectionModel().getSelectedItem();
                expensesId.setText(Integer.toString(selected.getId()));
                expensesDate.setValue(LocalDate.parse(selected.getDate()));
                expensesAmount.setText(selected.getAmount());

                ObservableList<ExpensesCategory> expenses = expensesCategory.getItems();
                for (ExpensesCategory a : expenses) {
                    if (a.getName().equals(selected.getCategory())) {
                        expensesCategory.getSelectionModel().select(a);
                    }
                }
                ObservableList<Accounts> accs = expensesAccounts.getItems();
                for (Accounts a : accs) {
                    if (a.getName().equals(selected.getAccount())) {
                        expensesAccounts.getSelectionModel().select(a);
                    }
                }
            }
        });
    }

    private void intialColumn() {
        expensesTabCat.setCellValueFactory(new PropertyValueFactory<>("category"));

        expensesTabDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        expensesTabAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        expensesTabId.setCellValueFactory(new PropertyValueFactory<>("id"));

        expensesTabAccount.setCellValueFactory(new PropertyValueFactory<>("account"));

        yieldsTabAcc.setCellValueFactory(new PropertyValueFactory<>("account"));

        yieldsTabCat.setCellValueFactory(new PropertyValueFactory<>("category"));

        yieldsTabDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        yieldsTabAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        yieldsTabId.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    private void clearExpenses() {
        getAutoNumExpenses();
        formNewExpenses.setDisable(true);

        formDeleteExpenses.setDisable(true);

        formEditeExpenses.setDisable(true);

        formAddExpenses.setDisable(false);
        expensesDate.setValue(null);
        expensesAmount.setText("");

        expensesCategory.getSelectionModel().clearSelection();

        expensesAccounts.getSelectionModel().clearSelection();
    }

    private void getAutoNumExpenses() {
        progressExpenses.setVisible(true);
        Service<Void> service = new Service<Void>() {
            String autoNum;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            autoNum = Expenses.getAutoNum();

                        } catch (Exception ex) {
                            AlertDialogs.showErrors(ex);
                        }
                        return null;
                    }
                };

            }

            @Override
            protected void succeeded() {
                progressExpenses.setVisible(false);
                expensesId.setText(autoNum);
                super.succeeded();
            }
        };
        service.start();
    }

    private void getDataExpenses() {
        progressExpenses.setVisible(true);
        Service<Void> service = new Service<Void>() {
            ObservableList<Expenses> data;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            data = Expenses.getData();

                        } catch (Exception ex) {
                            AlertDialogs.showErrors(ex);
                        }
                        return null;
                    }
                };

            }

            @Override
            protected void succeeded() {
                progressExpenses.setVisible(false);
                expensesTab.setItems(data);
                expensesItems = data;
                super.succeeded();
            }
        };
        service.start();
    }
    ObservableList<Expenses> expensesItems;

    private void clearYields() {
        getAutoNumYields();
        formNewYields.setDisable(true);

        formDeleteYields.setDisable(true);

        formEditeYields.setDisable(true);

        formAddYields.setDisable(false);
        yieldsDate.setValue(null);
        yieldsAmount.setText("");
        yieldsCategory.getSelectionModel().clearSelection();
        yieldsAccount.getSelectionModel().clearSelection();
    }

    private void getAutoNumYields() {
        progressYields.setVisible(true);
        Service<Void> service = new Service<Void>() {
            String autoNum;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            autoNum = Yields.getAutoNum();

                        } catch (Exception ex) {
                            AlertDialogs.showErrors(ex);
                        }
                        return null;
                    }
                };

            }

            @Override
            protected void succeeded() {
                progressYields.setVisible(false);
                yieldsId.setText(autoNum);
                super.succeeded();
            }
        };
        service.start();

    }

    private void getDataYields() {
        progressYields.setVisible(true);
        Service<Void> service = new Service<Void>() {
            ObservableList<Yields> data;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            data = Yields.getData();

                        } catch (Exception ex) {
                            AlertDialogs.showErrors(ex);
                        }
                        return null;
                    }
                };

            }

            @Override
            protected void succeeded() {
                progressYields.setVisible(false);
                yieldsTab.setItems(data);
                yieldsItems = data;
                super.succeeded();
            }
        };
        service.start();
    }
    ObservableList<Yields> yieldsItems;

    private void fillCombo() {

        Service<Void> service = new Service<Void>() {
            ObservableList<Accounts> accountsData;
            ObservableList<ExpensesCategory> expensesCats;
            ObservableList<YieldsCategory> yieldsCats;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {

                            accountsData = Accounts.getData();
                            expensesCats = ExpensesCategory.getData();
                            yieldsCats = YieldsCategory.getData();
                        } catch (Exception ex) {
                            AlertDialogs.showErrors(ex);
                        }
                        return null;
                    }
                };

            }

            @Override
            protected void succeeded() {
                expensesCategory.setItems(expensesCats);
                expensesCategory.setConverter(new StringConverter<ExpensesCategory>() {
                    @Override
                    public String toString(ExpensesCategory patient) {
                        return patient.getName();
                    }

                    @Override
                    public ExpensesCategory fromString(String string) {
                        return null;
                    }
                });
                expensesCategory.setCellFactory(cell -> new ListCell<ExpensesCategory>() {

                    GridPane gridPane = new GridPane();
                    Label lblid = new Label();
                    Label lblName = new Label();

                    {
                        gridPane.getColumnConstraints().addAll(
                                new ColumnConstraints(30, 30, 30),
                                new ColumnConstraints(150, 150, 150)
                        );

                        gridPane.add(lblid, 0, 1);
                        gridPane.add(lblName, 1, 1);

                    }

                    @Override
                    protected void updateItem(ExpensesCategory person, boolean empty) {
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
                yieldsCategory.setItems(yieldsCats);
                yieldsCategory.setConverter(new StringConverter<YieldsCategory>() {
                    @Override
                    public String toString(YieldsCategory patient) {
                        return patient.getName();
                    }

                    @Override
                    public YieldsCategory fromString(String string) {
                        return null;
                    }
                });
                yieldsCategory.setCellFactory(cell -> new ListCell<YieldsCategory>() {
                    GridPane gridPane = new GridPane();
                    Label lblid = new Label();
                    Label lblName = new Label();

                    {
                        gridPane.getColumnConstraints().addAll(
                                new ColumnConstraints(30, 30, 30),
                                new ColumnConstraints(150, 150, 150)
                        );

                        gridPane.add(lblid, 0, 1);
                        gridPane.add(lblName, 1, 1);

                    }

                    @Override
                    protected void updateItem(YieldsCategory person, boolean empty) {
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

                yieldsAccount.setItems(accountsData);
                yieldsAccount.setConverter(new StringConverter<Accounts>() {
                    @Override
                    public String toString(Accounts patient) {
                        return patient.getName();
                    }

                    @Override
                    public Accounts fromString(String string) {
                        return null;
                    }
                });
                yieldsAccount.setCellFactory(cell -> new ListCell<Accounts>() {
                    GridPane gridPane = new GridPane();
                    Label lblid = new Label();
                    Label lblName = new Label();

                    {
                        gridPane.getColumnConstraints().addAll(
                                new ColumnConstraints(30, 30, 30),
                                new ColumnConstraints(150, 150, 150)
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
                expensesAccounts.setItems(accountsData);
                expensesAccounts.setConverter(new StringConverter<Accounts>() {
                    @Override
                    public String toString(Accounts patient) {
                        return patient.getName();
                    }

                    @Override
                    public Accounts fromString(String string) {
                        return null;
                    }
                });
                expensesAccounts.setCellFactory(cell -> new ListCell<Accounts>() {

                    GridPane gridPane = new GridPane();
                    Label lblid = new Label();
                    Label lblName = new Label();

                    {
                        gridPane.getColumnConstraints().addAll(
                                new ColumnConstraints(30, 30, 30),
                                new ColumnConstraints(150, 150, 150)
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
    private void searchYields(KeyEvent event) {
        FilteredList<Yields> filteredData = new FilteredList<>(yieldsItems, p -> true);

        filteredData.setPredicate(pa -> {

            if (searchYields.getText() == null || searchYields.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = searchYields.getText().toLowerCase();

            return (pa.getDate().contains(lowerCaseFilter)
                    || pa.getAccount().contains(lowerCaseFilter)
                    || pa.getAmount().contains(lowerCaseFilter)
                    || pa.getCategory().contains(lowerCaseFilter));

        });

        SortedList<Yields> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(yieldsTab.comparatorProperty());
        yieldsTab.setItems(sortedData);
    }

    @FXML
    private void NewYields(ActionEvent event) {
        clearYields();
    }

    @FXML
    private void DeleteYields(ActionEvent event) {
        progressYields.setVisible(true);
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
                                    alert.setTitle("Deleting Yields  ");
                                    alert.setHeaderText("سيتم الحذف ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        Yields y = new Yields();
                                        y.setId(Integer.parseInt(yieldsId.getText()));
                                        y.Delete();
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
                progressYields.setVisible(false);
                clearYields();
                getDataYields();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void EditeYields(ActionEvent event) {
        progressYields.setVisible(true);
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
                                    alert.setTitle("Editing Yields");
                                    alert.setHeaderText("سيتم التعديل ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        Yields y = new Yields();
                                        y.setId(Integer.parseInt(yieldsId.getText()));
                                        y.setAmount(yieldsAmount.getText());
                                        y.setDate(yieldsDate.getValue().format(format));
                                        y.setCat_id(yieldsCategory.getSelectionModel().getSelectedItem().getId());
                                        y.setAcc_id(yieldsAccount.getSelectionModel().getSelectedItem().getId());
                                        y.Edite();

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
                progressYields.setVisible(false);
                clearYields();
                getDataYields();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void AddYields(ActionEvent event) {
        progressYields.setVisible(true);
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
                                    Yields y = new Yields();
                                    y.setId(Integer.parseInt(yieldsId.getText()));
                                    y.setAmount(yieldsAmount.getText());
                                    y.setDate(yieldsDate.getValue().format(format));
                                    y.setCat_id(yieldsCategory.getSelectionModel().getSelectedItem().getId());
                                    y.setAcc_id(yieldsAccount.getSelectionModel().getSelectedItem().getId());
                                    y.Add();

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
                progressYields.setVisible(false);
                clearYields();
                getDataYields();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void NewExpenses(ActionEvent event) {
        clearExpenses();
    }

    @FXML
    private void DeleteExpenses(ActionEvent event) {
        progressExpenses.setVisible(true);
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
                                    alert.setTitle("Deleting Expenses  ");
                                    alert.setHeaderText("سيتم الحذف ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        Expenses ex = new Expenses();
                                        ex.setId(Integer.parseInt(expensesId.getText()));
                                        ex.Delete();
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
                progressExpenses.setVisible(false);
                clearExpenses();
                getDataExpenses();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void EditeExpenses(ActionEvent event) {
        progressExpenses.setVisible(true);
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
                                    alert.setTitle("Editing Expenses  ");
                                    alert.setHeaderText("سيتم التعديل ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        Expenses ex = new Expenses();
                                        ex.setId(Integer.parseInt(expensesId.getText()));
                                        ex.setAmount(expensesAmount.getText());
                                        ex.setDate(expensesDate.getValue().format(format));
                                        ex.setCat_id(expensesCategory.getSelectionModel().getSelectedItem().getId());
                                        ex.setAcc_id(expensesAccounts.getSelectionModel().getSelectedItem().getId());
                                        ex.Edite();
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
                progressExpenses.setVisible(false);
                clearExpenses();
                getDataExpenses();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void AddExpenses(ActionEvent event) {
        progressExpenses.setVisible(true);
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
                                    Expenses ex = new Expenses();
                                    ex.setId(Integer.parseInt(expensesId.getText()));
                                    ex.setAmount(expensesAmount.getText());
                                    ex.setDate(expensesDate.getValue().format(format));
                                    ex.setCat_id(expensesCategory.getSelectionModel().getSelectedItem().getId());
                                    ex.setAcc_id(expensesAccounts.getSelectionModel().getSelectedItem().getId());
                                    ex.Add();

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
                progressExpenses.setVisible(false);
                clearExpenses();
                getDataExpenses();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void AddYieldsCat(MouseEvent event) {
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
                                            YieldsCategory.Add(results);
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
    private void searchExpenses(KeyEvent event) {
        FilteredList<Expenses> filteredData = new FilteredList<>(expensesItems, p -> true);

        filteredData.setPredicate(pa -> {

            if (searchExpenses.getText() == null || searchExpenses.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = searchExpenses.getText().toLowerCase();

            return (pa.getDate().contains(lowerCaseFilter)
                    || pa.getAmount().contains(lowerCaseFilter)
                    || pa.getAccount().contains(lowerCaseFilter)
                    || pa.getCategory().contains(lowerCaseFilter));

        });

        SortedList<Expenses> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(expensesTab.comparatorProperty());
        expensesTab.setItems(sortedData);
    }

    @FXML
    private void AddExpensesCat(MouseEvent event) {
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
                                            ExpensesCategory.Add(results);
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

}
