/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.members;

import assets.classes.AlertDialogs;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.HashSet;
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
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import screens.members.assets.MemberDailyCostDetails;
import screens.members.assets.MemberTransactionDetails;
import screens.store.assets.Products;

/**
 * FXML Controller class
 *
 * @author Ahmed Al-Gazzar
 */
public class MemberScreenTransactionDetailsController implements Initializable {

    @FXML
    private JFXTextField search;
    @FXML
    private TableView<MemberTransactionDetails> tab;
    @FXML
    private TableColumn<MemberTransactionDetails, String> tabamount;
    @FXML
    private TableColumn<MemberTransactionDetails, String> tabto;
    @FXML
    private TableColumn<MemberTransactionDetails, String> tabfrom;
    @FXML
    private TableColumn<MemberTransactionDetails, String> tabid;
    @FXML
    private Label id;
    @FXML
    private TextField from;
    @FXML
    private TextField to;
    @FXML
    private TextField amount;
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
     MemberScreenTransactionController parentController;
    int TRANSACTIONID=0;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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

                                    intialColumn();
                                    

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

                MemberTransactionDetails selected = tab.getSelectionModel().getSelectedItem();

                id.setText(Integer.toString(selected.getId()));
              from.setText(selected.getPlacefrom());
                to.setText(selected.getPlaceto());
               
                amount.setText(selected.getAmount());
               

            }
        });
    }    
    
     private void intialColumn() {
        tabid.setCellValueFactory(new PropertyValueFactory<>("id"));
       
        tabto.setCellValueFactory(new PropertyValueFactory<>("placeto"));
        tabfrom.setCellValueFactory(new PropertyValueFactory<>("placefrom"));
        tabamount.setCellValueFactory(new PropertyValueFactory<>("amount"));

    }
     private void clear() {
        getAutoNum();
        
        to.setText("");
         from.setText("");
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
                            idNum = MemberTransactionDetails.getAutoNum();

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
        Service<Void> service;
        service = new Service<Void>() {
            ObservableList<MemberTransactionDetails> data;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            data =MemberTransactionDetails.getData(id);
                            
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

    void setParentController(MemberScreenTransactionController parentController) {
        this.parentController = parentController;
    }
   
 ObservableList<MemberTransactionDetails> items;


    @FXML
    private void search(KeyEvent event) {
           FilteredList<MemberTransactionDetails> filteredData = new FilteredList<>(items, p -> true);

        filteredData.setPredicate(pa -> {

            if (search.getText() == null || search.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = search.getText().toLowerCase();

            if (pa.getPlaceto().contains(lowerCaseFilter)
                    || pa.getAmount().contains(lowerCaseFilter)
            || pa.getPlaceto().contains(lowerCaseFilter)){
                return true;
            } else {
                return false;
            }

        });

        SortedList< MemberTransactionDetails> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tab.comparatorProperty());
        tab.setItems(sortedData);
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
                                    alert.setTitle("Deleting  opration datails");
                                    alert.setHeaderText("سيتم حذف الصيانة ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        MemberTransactionDetails mdcd = new MemberTransactionDetails();
                                        mdcd.setId(Integer.parseInt(id.getText()));
                                        mdcd.Delete();
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
                getData(TRANSACTIONID);
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
             MemberTransactionDetails mdcd = new MemberTransactionDetails();

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
                                        mdcd.setId(Integer.parseInt(id.getText()));
//                                     opd.setOperation_id(operation.getSelectionModel().getSelectedItem().getId());
                                       

                                        mdcd.setPlacefrom(from.getText());
                                        mdcd.setPlaceto(to.getText());
                                        mdcd.setAmount(amount.getText());
                                       
                                        mdcd.setTransaction_id(TRANSACTIONID);
                                      //  parentController.reduceAccount(TRANSACTIONID, tab.getSelectionModel().getSelectedItem().getCost());
                                        mdcd.Edit();
                                       // parentController.setAccount(TRANSACTIONID, cost.getText());
                                    }
                                } catch (Exception ex) {
                                    AlertDialogs.showErrors(ex);
                                    ok = false;
                                   // parentController.setAccount(TRANSACTIONID, tab.getSelectionModel().getSelectedItem().getCost());
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
                    getData(TRANSACTIONID);
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
          MemberTransactionDetails mdcd = new MemberTransactionDetails();
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
                                     mdcd.setId(Integer.parseInt(id.getText()));
//                                     opd.setOperation_id(operation.getSelectionModel().getSelectedItem().getId());
                                       

                                        mdcd.setPlacefrom(from.getText());
                                        mdcd.setPlaceto(to.getText());
                                        mdcd.setAmount(amount.getText());
                                       
                                        mdcd.setTransaction_id(TRANSACTIONID);
                                       // parentController.reduceAccount(TRANSACTIONID, tab.getSelectionModel().getSelectedItem().getAmount());
                                        mdcd.Edit();
                                       // parentController.setAccount(TRANSACTIONID, totalcost.getText());
                                     mdcd.Add();
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
                    getData(TRANSACTIONID);
                }
                super.succeeded();
            }
        };
        service.start();
    }
  

    void setid(int TRANSACTIONID) {
 this.TRANSACTIONID = TRANSACTIONID;
        clear();
        getData(TRANSACTIONID);    
    }
    }
    

