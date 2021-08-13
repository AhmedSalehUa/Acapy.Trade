/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.members;

import assets.classes.AlertDialogs;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
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
import screens.members.assets.AcapyMembers;
import screens.members.assets.SalesMembers;

/**
 * FXML Controller class
 *
 * @author Ahmed Al-Gazzar
 */
public class MemberScreenSalesMemberController implements Initializable {

    @FXML
    private JFXTextField search;
    @FXML
    private TableView<SalesMembers> tab;
    @FXML
    private TableColumn<SalesMembers, String> tabapptoken;
    @FXML
    private TableColumn<SalesMembers, String> tabnumofsuccess;
    @FXML
    private TableColumn<SalesMembers, String> tabname;
    @FXML
    private TableColumn<SalesMembers, String> tabId;
    @FXML
    private Label id;
    @FXML
    private TextField name;
    @FXML
    private TextField numofsuccess;
    @FXML
    private TextField apptoken;
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
                                   getData();
                                    intialColumn();
                                    clear();
                                    

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

                SalesMembers selected = tab.getSelectionModel().getSelectedItem();

                id.setText(Integer.toString(selected.getId()));
              name.setText(selected.getName());
                apptoken.setText(selected.getApp_token());
               
                numofsuccess.setText(selected.getNum_of_success());
               

            }
        });
    }    
     private void intialColumn() {
        tabId.setCellValueFactory(new PropertyValueFactory<>("id"));
       
        tabname.setCellValueFactory(new PropertyValueFactory<>("name"));
        tabnumofsuccess.setCellValueFactory(new PropertyValueFactory<>("num_of_success"));
        tabapptoken.setCellValueFactory(new PropertyValueFactory<>("app_token"));

    }
     private void clear() {
        getAutoNum();
        
        name.setText("");
         apptoken.setText("");
          numofsuccess.setText("");
     

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
                            idNum = SalesMembers.getAutoNum();

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

      void getData() {
        progress.setVisible(true);
        Service<Void> service;
        service = new Service<Void>() {
            ObservableList<SalesMembers> data;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            data =SalesMembers.getData();
                            
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
      ObservableList<SalesMembers> items;

    @FXML
    private void search(KeyEvent event) {
          FilteredList<SalesMembers> filteredData = new FilteredList<>(items, p -> true);

        filteredData.setPredicate(pa -> {

            if (search.getText() == null || search.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = search.getText().toLowerCase();

            if (pa.getNum_of_success().contains(lowerCaseFilter)
                    || pa.getApp_token().contains(lowerCaseFilter)
            || pa.getName().contains(lowerCaseFilter)){
                return true;
            } else {
                return false;
            }

        });

        SortedList< SalesMembers> sortedData = new SortedList<>(filteredData);
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
                                        SalesMembers mdcd = new SalesMembers();
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
            boolean ok = true;
             SalesMembers mdcd = new SalesMembers();

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
//                                    
                                       

                                        mdcd.setNum_of_success(numofsuccess.getText());
                                        mdcd.setApp_token(apptoken.getText());
                                        mdcd.setName(name.getText());
                                        mdcd.Edite();
                                     
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
          SalesMembers mdcd = new SalesMembers();
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
//                                    
                                       

                                        mdcd.setNum_of_success(numofsuccess.getText());
                                        mdcd.setApp_token(apptoken.getText());
                                        mdcd.setName(name.getText());
                                       
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
                    getData();
                }
                super.succeeded();
            }
        };
        service.start();
    }
    
}
