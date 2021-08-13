/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.members;

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
import javafx.collections.ObservableList;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import screens.members.assets.MemberReward;
import screens.Accounts.assets.Accounts;
import screens.clients.assets.Operations;
import screens.members.assets.MemberSolfa;

/**
 * FXML Controller class
 *
 * @author Ahmed Al-Gazzar
 */
public class MemberScreenRewardController implements Initializable {

    @FXML
    private JFXTextField search;
    @FXML
    private TableView<MemberReward> tab;
    @FXML
    private TableColumn<MemberReward, String> tabaccount;
    @FXML
    private TableColumn<MemberReward, String> tabdate;
    @FXML
    private TableColumn<MemberReward, String> tabamount;
    @FXML
    private TableColumn<MemberReward, String> taboperation;
    @FXML
    private TableColumn<MemberReward, String> tabid;
    @FXML
    private Label id;
    @FXML
    private TextField amount;
    @FXML
    private JFXDatePicker date;
    @FXML
    private ComboBox<Accounts> account;
    @FXML
    private ComboBox<Operations> operation;
    @FXML
    private ProgressIndicator progress;
    @FXML
    private Button New;
    @FXML
    private Button Delete;
    @FXML
    private Button Edite;
    @FXML
    private Button Add;
 MemberScreenRewardSolfaController parentController;
    int REWARDID = 0;
   
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
                                    
                                    intialColumn();
                                    
                                    fillCombo1();
                                   
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
                Add.setDisable(true);
                Edite.setDisable(false);
                Delete.setDisable(false);
                New.setDisable(false);

                MemberReward selected = tab.getSelectionModel().getSelectedItem();
                id.setText(Integer.toString(selected.getId()));
                ObservableList<Accounts> items1 = account.getItems();
                for (Accounts a : items1) {
                    if (a.getName().equals(selected.getAccount())) {
                        account.getSelectionModel().select(a);
                    }
                }
                 ObservableList<Operations> items2 = operation.getItems();
                for (Operations a : items2) {
                    if (a.getClient_name().equals(selected.getOperation())) {
                        operation.getSelectionModel().select(a);
                    }
                }
                
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                date.setValue(LocalDate.parse(selected.getDate()));

                amount.setText(selected.getAmount());   
              
            }
        });
    } 
    
    
 

    private void fillCombo1() {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            ObservableList<Accounts> data;
             ObservableList<Operations> odata;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            data = Accounts.getData();
                            odata = Operations.getData();

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

                account.setItems(data);
                account.setConverter(new StringConverter<Accounts>() {
                    @Override
                    public String toString(Accounts patient) {
                        return patient.getName();
                    }

                    @Override
                    public Accounts fromString(String string) {
                        return null;
                    }
                });
                account.setCellFactory(cell -> new ListCell<Accounts>() {

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
                    protected void updateItem(Accounts person, boolean empty) {
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
                   operation.setItems(odata);
                operation.setConverter(new StringConverter<Operations>() {
                    @Override
                    public String toString(Operations patient) {
                        return patient.getClient_name();
                    }

                    @Override
                    public Operations fromString(String string) {
                        return null;
                    }
                });
                operation.setCellFactory(cell -> new ListCell<Operations>() {

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
                    protected void updateItem(Operations person, boolean empty) {
                        super.updateItem(person, empty);

                        if (!empty && person != null) {

                            // Update our Labels
                            lblid.setText("م: " + Integer.toString(person.getId()));
                            lblName.setText("الاسم: " + person.getClient_name());

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
private void intialColumn() {
        tabid.setCellValueFactory(new PropertyValueFactory<>("id"));
        tabdate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tabamount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        tabaccount.setCellValueFactory(new PropertyValueFactory<>("account"));
        taboperation.setCellValueFactory(new PropertyValueFactory<>("operation"));

    }
    private void clear() {
        getAutoNum();
        account.getSelectionModel().clearSelection();
        date.setValue(null);
         operation.getSelectionModel().clearSelection();
        amount.setText("");

        Add.setDisable(false);
        Edite.setDisable(true);
        Delete.setDisable(true);
        New.setDisable(true);
    }

    private void getAutoNum() {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            String idNum;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            idNum = MemberReward.getAutoNum();

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
                id.setText(idNum);
                super.succeeded();
            }
        };
        service.start();

    }

   void getData(int id) {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            ObservableList<MemberReward> data;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            data = MemberReward.getData(id);
                        } catch (Exception ex) {
                            AlertDialogs.showErrors(ex);
                        }
                        return null;
                    }
                };
            }

            @Override
            protected void succeeded() {
                tab.setItems(data);
                items = data;
                progress.setVisible(false);
                super.succeeded();
            }
        };
        service.start();
    }

    void setParentController(MemberScreenRewardSolfaController parentController) {
        this.parentController = parentController;
    }
    ObservableList<MemberReward> items;
    @FXML
    private void search(KeyEvent event) {
         FilteredList<MemberReward> filteredData = new FilteredList<>(items, p -> true);

        filteredData.setPredicate(pa -> {

            if (search.getText() == null || search.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = search.getText().toLowerCase();

            if (pa.getAccount().contains(lowerCaseFilter)||
                    pa.getAmount().contains(lowerCaseFilter)||
                    pa.getOperation().contains(lowerCaseFilter)
                    || pa.getDate().contains(lowerCaseFilter)) {
                return true;
            } else {
                return false;
            }

        });

        SortedList< MemberReward> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tab.comparatorProperty());
        tab.setItems(sortedData);
    }
  MemberReward opd = new MemberReward();
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
                                    alert.setTitle("Deleting  opration datails");
                                    alert.setHeaderText("سيتم حذف الصيانة ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        MemberReward opd = new MemberReward();
                                        opd.setId(Integer.parseInt(id.getText()));
                                        opd.Delete();
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
                getData(REWARDID);
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
            MemberReward opd = new MemberReward();

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
                                    alert.setTitle("Editting  Clients");
                                    alert.setHeaderText("سيتم تعديل الصيانة ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        opd.setId(Integer.parseInt(id.getText()));                                     
                                        opd.setAccount_id(account.getSelectionModel().getSelectedItem().getId());
                                           opd.setOperation_id(operation.getSelectionModel().getSelectedItem().getId());
                                         DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                            opd.setDate(date.getValue().format(format));
                                        opd.setAmount(amount.getText());
                                       opd.setMember_id(REWARDID);
                                                                               
                                        opd.Edite();
                                      
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
                    getData(REWARDID);
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
            MemberReward opd = new MemberReward();
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
                                     opd.setId(Integer.parseInt(id.getText()));                                   
                                        opd.setAccount_id(account.getSelectionModel().getSelectedItem().getId());
                                         opd.setOperation_id(operation.getSelectionModel().getSelectedItem().getId());
                                         DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                            opd.setDate(date.getValue().format(format));
                                        opd.setAmount(amount.getText());
                                       
                                        opd.setMember_id(REWARDID);
                                    opd.Add();
                                   
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
                    getData(REWARDID);
                }
                super.succeeded();
            }
        };
        service.start();
    }
     void setId(int REWARDID) {
        this.REWARDID = REWARDID;
        clear();
        getData(REWARDID);

    }
    
}
