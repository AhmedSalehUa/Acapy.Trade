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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;

import screens.members.assets.MemberDailyCostDetails;
import screens.store.assets.Products;
/**
 * FXML Controller class
 *
 * @author Ahmed Al-Gazzar
 */
public class MemberScreenDailyCostDetailsController implements Initializable {

    @FXML
    private JFXTextField search;
    @FXML
    private TableView<MemberDailyCostDetails> tab;
    @FXML
    private TableColumn<MemberDailyCostDetails, String> tabcost;
    @FXML
    private TableColumn<MemberDailyCostDetails, String> tabproduct;
    @FXML
    private TableColumn<MemberDailyCostDetails, String> tabId;
    @FXML
    private Label id;
    @FXML
    private ComboBox<Products> product;
    @FXML
    private TextField cost;
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
    MemberScreenDailyCostController parentController;
    int DAILYCOSTID=0;

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

                MemberDailyCostDetails selected = tab.getSelectionModel().getSelectedItem();

                id.setText(Integer.toString(selected.getId()));
                ObservableList<Products> items3 = product.getItems();
                for (Products a : items3) {
                    if (a.getName().equals(selected.getProduct())) {
                        product.getSelectionModel().select(a);
                    }
                }
               
                cost.setText(selected.getCost());
               

            }
        });
    }  
      private void intialColumn() {
        tabId.setCellValueFactory(new PropertyValueFactory<>("id"));
       
        tabcost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        tabproduct.setCellValueFactory(new PropertyValueFactory<>("product"));

    }

    private void fillCombo1() {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            ObservableList<Products> data;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            data = Products.getData();

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

                product.setItems(data);
                product.setConverter(new StringConverter<Products>() {
                    @Override
                    public String toString(Products patient) {
                        return patient.getName();
                    }

                    @Override
                    public Products fromString(String string) {
                        return null;
                    }
                });
                product.setCellFactory(cell -> new ListCell<Products>() {

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
                    protected void updateItem(Products person, boolean empty) {
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

    private void clear() {
        getAutoNum();
        product.getSelectionModel().clearSelection();
      
        cost.setText("");
     

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
                            idNum = MemberDailyCostDetails.getAutoNum();

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
            ObservableList<MemberDailyCostDetails> data;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            data =MemberDailyCostDetails.getData(id);
                            
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

    void setParentController(MemberScreenDailyCostController parentController) {
        this.parentController = parentController;
    }
   
 ObservableList<MemberDailyCostDetails> items;

    @FXML
    private void search(KeyEvent event) {
           FilteredList<MemberDailyCostDetails> filteredData = new FilteredList<>(items, p -> true);

        filteredData.setPredicate(pa -> {

            if (search.getText() == null || search.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = search.getText().toLowerCase();

            if (pa.getCost().contains(lowerCaseFilter)
                    || pa.getProduct().contains(lowerCaseFilter)) {
                return true;
            } else {
                return false;
            }

        });

        SortedList< MemberDailyCostDetails> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tab.comparatorProperty());
        tab.setItems(sortedData);
    }
MemberDailyCostDetails mdcd=new MemberDailyCostDetails();
  

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
                                        MemberDailyCostDetails mdcd = new MemberDailyCostDetails();
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
                getData(DAILYCOSTID);
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
             MemberDailyCostDetails mdcd = new MemberDailyCostDetails();

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
                                        mdcd.setProduct_id(product.getSelectionModel().getSelectedItem().getId());

                                        mdcd.setCost(cost.getText());
                                       
                                        mdcd.setDailycost_id(DAILYCOSTID);
                                        parentController.reduceAccount(DAILYCOSTID, tab.getSelectionModel().getSelectedItem().getCost());
                                        mdcd.Edit();
                                        parentController.setAccount(DAILYCOSTID, cost.getText());
                                    }
                                } catch (Exception ex) {
                                    AlertDialogs.showErrors(ex);
                                    ok = false;
                                    parentController.setAccount(DAILYCOSTID, tab.getSelectionModel().getSelectedItem().getCost());
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
                    getData(DAILYCOSTID);
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
          MemberDailyCostDetails mdcd = new MemberDailyCostDetails();
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
                                        mdcd.setProduct_id(product.getSelectionModel().getSelectedItem().getId());

                                        mdcd.setCost(cost.getText());
                                       
                                        mdcd.setDailycost_id(DAILYCOSTID);
                                    parentController.setAccount(DAILYCOSTID,cost.getText());
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
                    getData(DAILYCOSTID);
                }
                super.succeeded();
            }
        };
        service.start();
    }
  

    void setid(int DAILYCOSTID) {
 this.DAILYCOSTID = DAILYCOSTID;
        clear();
        getData(DAILYCOSTID);    
    }
    
}
