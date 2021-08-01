///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package screens.mainDataScreen.assets;
//import assets.classes.AlertDialogs;
//import com.jfoenix.controls.JFXDatePicker;
//import com.jfoenix.controls.JFXTextField;
//import java.net.URL;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.Optional;
//import java.util.ResourceBundle;
//import java.util.concurrent.CountDownLatch;
//import javafx.application.Platform;
//import javafx.collections.ObservableList;
//import javafx.collections.transformation.FilteredList;
//import javafx.collections.transformation.SortedList;
//import javafx.concurrent.Service;
//import javafx.concurrent.Task;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.ButtonType;
//import javafx.scene.control.ComboBox;
//import javafx.scene.control.Label;
//import javafx.scene.control.ListCell;
//import javafx.scene.control.ProgressIndicator;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TextArea;
//import javafx.scene.control.TextField;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.input.KeyEvent;
//import javafx.scene.layout.ColumnConstraints;
//import javafx.scene.layout.GridPane;
//import javafx.util.StringConverter;
//import screens.store.assets.ProductCategory;
////import screens.mainDataScreen.assets.maintaince;
////import screens.mainDataScreen.assets.AcapyMembers;
//
//
//public class MaintainceController implements Initializable {
//    
//    @FXML
//    private JFXTextField search;  
//    @FXML
//    private TableView<maintaince> tab; 
//    @FXML
//    private TableColumn<maintaince, String> tabId;
//    @FXML
//    private ProgressIndicator progress;
//    @FXML
//    private Button New;
//    @FXML
//    private Button Delete;
//    @FXML
//    private Button Edite;
//    @FXML
//    private Button Add;
//   
//    @FXML
//    private TableColumn<maintaince, String> tabDate;
//    @FXML
//    private TableColumn<maintaince, String> tabPay_type;
//    @FXML
//    private TableColumn<maintaince, String> tabCost;
//    @FXML
//    private TableColumn<maintaince, String> tabProblem;
//    @FXML
//    private TableColumn<maintaince, String> tabClient_name;
//    @FXML
//    private TableColumn<maintaince, String> tabMember_name;
//     @FXML
//    private Label id;
//    @FXML
//    private ComboBox <Clients>client;
//    @FXML
//    private ComboBox <AcapyMembers>member;
//    @FXML
//    private TextArea problem;
//    @FXML
//    private TextField cost;
//    @FXML
//    private JFXDatePicker date;
//    @FXML
//    private ComboBox <String>pay_type;
//   
//  
//   
//   @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        Service<Void> service = new Service<Void>() {
//            @Override
//            protected Task<Void> createTask() {
//                return new Task<Void>() {
//                    @Override
//                    protected Void call() throws Exception {
//                        //Background work                       
//                        final CountDownLatch latch = new CountDownLatch(1);
//                        Platform.runLater(new Runnable() {
//                            @Override
//                            public void run() {
//                                try {
//                                    clear();
//                                   intialColumn();
//                                    getData();
//                                    fillCombo1();
//                                    fillCombo2();
//                                    fillCombo3();
//                                    
//                                    
//
//                                } catch (Exception ex) {
//                                    AlertDialogs.showErrors(ex);
//                                } finally {
//                                    latch.countDown();
//                                }
//                            }
//                        });
//                        latch.await();
//
//                        return null;
//                    }
//                };
//
//            }
//
//            @Override
//            protected void succeeded() {
//
//                progress.setVisible(false);
//                super.succeeded();
//            }
//        };
//        service.start();
//
//        tab.setOnMouseClicked((e) -> {
//            if (tab.getSelectionModel().getSelectedIndex() == -1)  {
//
//            } else {
//                Add.setDisable(true);
//                Edite.setDisable(false);
//                Delete.setDisable(false);
//                New.setDisable(false);
//
//                maintaince selected = tab.getSelectionModel().getSelectedItem();
//                id.setText(Integer.toString(selected.getId()));
//                ObservableList<Clients> items1 = client.getItems();
//                for (Clients a : items1) {
//                    if (a.getName().equals(selected.getClient_name())) {
//                        client.getSelectionModel().select(a);
//                    }
//                }
//                ObservableList<AcapyMembers> items2 = member.getItems();
//                for (AcapyMembers a : items2) {
//                    if (a.getName().equals(selected.getMember_name())) {
//                        member.getSelectionModel().select(a);
//                    }
//                }
//                
//               
//                problem.setText(selected.getProblem());
//                cost.setText(Integer.toString(selected.getCost()));
//                pay_type.getSelectionModel().select(selected.getPay_type());
//                  
//                DateTimeFormatter format= DateTimeFormatter.ofPattern("yyyy-MM-dd");
//                                        date.getValue().format(format);
//            
//                
//            
//                
//               
//
//            }
//        });
//    }
//    private void fillCombo3(){
//    pay_type.getItems().add("كاش");
//pay_type.getItems().add("فى الضمان");
//pay_type.getItems().add("بالاجل"); }
//   
//     private void fillCombo1() {
//        try {
//                      
//            client.setItems(Clients.getData());
//            client.setConverter(new StringConverter<Clients>() {
//                @Override
//                public String toString(Clients patient) {
//                    return patient.getName();
//                }
//
//                @Override
//                public Clients fromString(String string) {
//                    return null;
//                }
//            });
//            client.setCellFactory(cell -> new ListCell<Clients>() {
//
//                // Create our layout here to be reused for each ListCell
//                GridPane gridPane = new GridPane();
//                Label lblid = new Label();
//                Label lblName = new Label();
//
//                // Static block to configure our layout
//                {
//                    // Ensure all our column widths are constant
//                    gridPane.getColumnConstraints().addAll(
//                            new ColumnConstraints(100, 100, 100),
//                            new ColumnConstraints(100, 100, 100)
//                    );
//
//                    gridPane.add(lblid, 0, 1);
//                    gridPane.add(lblName, 1, 1);
//
//                }
//
//                // We override the updateItem() method in order to provide our own layout for this Cell's graphicProperty
//                @Override
//                protected void updateItem(Clients person, boolean empty) {
//                    super.updateItem(person, empty);
//
//                    if (!empty && person != null) {
//
//                        // Update our Labels
//                        lblid.setText("م: " + Integer.toString(person.getId()));
//                        lblName.setText("الاسم: " + person.getName());
//
//                        setGraphic(gridPane);
//                    } else {
//                        // Nothing to display here
//                        setGraphic(null);
//                    }
//                }
//            });
//        } catch (Exception ex) {
//            AlertDialogs.showErrors(ex);
//        }
//    }
//      private void fillCombo2() {
//        try {
//                      
//            member.setItems(AcapyMembers.getData());
//            member.setConverter(new StringConverter<AcapyMembers>() {
//                @Override
//                public String toString(AcapyMembers patient) {
//                    return patient.getName();
//                }
//
//                @Override
//                public AcapyMembers fromString(String string) {
//                    return null;
//                }
//            });
//            member.setCellFactory(cell -> new ListCell<AcapyMembers>() {
//
//                // Create our layout here to be reused for each ListCell
//                GridPane gridPane = new GridPane();
//                Label lblid = new Label();
//                Label lblName = new Label();
//
//                // Static block to configure our layout
//                {
//                    // Ensure all our column widths are constant
//                    gridPane.getColumnConstraints().addAll(
//                            new ColumnConstraints(100, 100, 100),
//                            new ColumnConstraints(100, 100, 100)
//                    );
//
//                    gridPane.add(lblid, 0, 1);
//                    gridPane.add(lblName, 1, 1);
//
//                }
//
//                // We override the updateItem() method in order to provide our own layout for this Cell's graphicProperty
//                @Override
//                protected void updateItem(AcapyMembers person, boolean empty) {
//                    super.updateItem(person, empty);
//
//                    if (!empty && person != null) {
//
//                        // Update our Labels
//                        lblid.setText("م: " + Integer.toString(person.getId()));
//                        lblName.setText("الاسم: " + person.getName());
//
//                        setGraphic(gridPane);
//                    } else {
//                        // Nothing to display here
//                        setGraphic(null);
//                    }
//                }
//            });
//        } catch (Exception ex) {
//            AlertDialogs.showErrors(ex);
//        }
//    }
//
//
//    private void getAutoNum() {
//        try {
//            id.setText(maintaince.getAutoNum());
//        } catch (Exception ex) {
//            AlertDialogs.showErrors(ex);
//        }
//    }
//
//    private void clear() {
//        getAutoNum();
//        client.getSelectionModel().clearSelection();
//        member.getSelectionModel().clearSelection();
//        problem.setText("");
//        cost.setText("");
//        pay_type.getSelectionModel().clearSelection();
//        
//        Add.setDisable(false);
//        Edite.setDisable(true);
//        Delete.setDisable(true);
//        New.setDisable(true);
//    }
//
//    private void  intialColumn() {
//        tabId.setCellValueFactory(new PropertyValueFactory<>("id"));
//        tabClient_name.setCellValueFactory(new PropertyValueFactory<>("client_name"));
//        tabMember_name.setCellValueFactory(new PropertyValueFactory<>("member_name"));
//        tabDate.setCellValueFactory(new PropertyValueFactory<>("date"));
//        tabProblem.setCellValueFactory(new PropertyValueFactory<>("problem"));
//        tabCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
//        tabPay_type.setCellValueFactory(new PropertyValueFactory<>("pay_type"));
//        
//
//    }
//
//    private void getData() {
//        progress.setVisible(true);
//        Service<Void> service = new Service<Void>() {
//            @Override
//            protected Task<Void> createTask() {
//                return new Task<Void>() {
//                    @Override
//                    protected Void call() throws Exception {
//                        //Background work                       
//                        final CountDownLatch latch = new CountDownLatch(1);
//                        Platform.runLater(new Runnable() {
//                            @Override
//                            public void run() {
//                                try {
//                                    try {
//
//                                        tab.setItems(maintaince.getData());
//
//                                    } catch (Exception ex) {
//                                        AlertDialogs.showErrors(ex);
//                                    }
//                                } finally {
//                                    latch.countDown();
//                                }
//                            }
//                        });
//                        latch.await();
//
//                        return null;
//                    }
//                };
//            }
//
//            @Override
//            protected void succeeded() {
//
//                items = tab.getItems();
//                progress.setVisible(false);
//                super.succeeded();
//            }
//        };
//        service.start();
//    }
//    ObservableList<maintaince> items;
//
//    @FXML
//    private void search(KeyEvent event) {
//
//        FilteredList<maintaince> filteredData = new FilteredList<>(items, p -> true);
//
//        filteredData.setPredicate(pa -> {
//
//            if (search.getText() == null || search.getText().isEmpty()) {
//                return true;
//            }
//
//            String lowerCaseFilter = search.getText().toLowerCase();
//
//            if (pa.getClient_name().contains(lowerCaseFilter)
//                    || pa.getMember_name().contains(lowerCaseFilter)
//                    
//                    || pa.getProblem().contains(lowerCaseFilter)
//                    || pa.getPay_type().contains(lowerCaseFilter))
//                     {
//                return true;
//            } else {
//                return false;
//            }
//
//        });
//
//        SortedList< maintaince> sortedData = new SortedList<>(filteredData);
//        sortedData.comparatorProperty().bind(tab.comparatorProperty());
//        tab.setItems(sortedData);
//    }
//
//    @FXML
//    private void New(ActionEvent event) {
//        clear();
//    }
//
//    @FXML
//    private void Delete(ActionEvent event) {
//        progress.setVisible(true);
//        Service<Void> service = new Service<Void>() {
//            @Override
//            protected Task<Void> createTask() {
//                return new Task<Void>() {
//                    @Override
//                    protected Void call() throws Exception {
//                        //Background work                       
//                        final CountDownLatch latch = new CountDownLatch(1);
//                        Platform.runLater(new Runnable() {
//                            @Override
//                            public void run() {
//                                
//                                try {
//                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                                    alert.setTitle("Deleting  Maintaince");
//                                    alert.setHeaderText("سيتم حذف الصيانة ");
//                                    alert.setContentText("هل انت متاكد؟");
//
//                                   Optional<ButtonType> result = alert.showAndWait();
//                                    if (result.get() == ButtonType.OK) {
//                                       maintaince main=new maintaince();
//                                        main.setId(Integer.parseInt(id.getText()));
//                                        main.Delete();
//                                    }
//                                } catch (Exception ex) {
//                                    AlertDialogs.showErrors(ex);
//                                   
//                                } finally {
//                                    latch.countDown();
//                                }
//                            }
//
//                        });
//                        latch.await();
//
//                        return null;
//                    }
//                };
//
//            }
//
//            @Override
//            protected void succeeded() {
//                progress.setVisible(false);
//                clear();
//                getData();
//                super.succeeded();
//            }
//        };
//        service.start();
//    }
//
//    @FXML
//    private void Edite(ActionEvent event) {
//        progress.setVisible(true);
//        Service<Void> service = new Service<Void>() {
//            @Override
//            protected Task<Void> createTask() {
//                return new Task<Void>() {
//                    @Override
//                    protected Void call() throws Exception {
//                        //Background work                       
//                        final CountDownLatch latch = new CountDownLatch(1);
//                        Platform.runLater(new Runnable() {
//                            @Override
//                            public void run() {
//                                maintaince main = new maintaince();
//                                try {
//                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                                    alert.setTitle("Editting  Clients");
//                                    alert.setHeaderText("سيتم تعديل الصيانة ");
//                                    alert.setContentText("هل انت متاكد؟");
//
//                                    Optional<ButtonType> result = alert.showAndWait();
//                                    if (result.get() == ButtonType.OK) {
//                                        main.setId(Integer.parseInt(id.getText()));
//                                        main.setClient_id(client.getSelectionModel().getSelectedItem().getId());
//                                        main.setMember_id(member.getSelectionModel().getSelectedItem().getId());
//                                        DateTimeFormatter format= DateTimeFormatter.ofPattern("yyyy-MM-dd");
//                                        main.setDate(date.getValue().format(format));
//                                        main.setProblem(problem.getText());
//                                        main.setCost(Integer.parseInt(cost.getText()));
//                                        main.setPay_type( pay_type.getSelectionModel().getSelectedItem());
//                                        
//                                        main.Edit();
//                                        
//                                    }
//                                } catch (Exception ex) {
//                                    AlertDialogs.showErrors(ex);
//                                    
//                                } finally {
//                                    latch.countDown();
//                                }
//                            }
//
//                        });
//                        latch.await();
//
//                        return null;
//                    }
//                };
//
//            }
//
//            @Override
//            protected void succeeded() {
//                progress.setVisible(false);
//                clear();
//                getData();
//                super.succeeded();
//            }
//        };
//        service.start();
//    }
//
//    @FXML
//    private void Add(ActionEvent event) {
//
//        progress.setVisible(true);
//        Service<Void> service = new Service<Void>() {
//            @Override
//            protected Task<Void> createTask() {
//                return new Task<Void>() {
//                    @Override
//                    protected Void call() throws Exception {
//                        //Background work                       
//                        final CountDownLatch latch = new CountDownLatch(1);
//                        Platform.runLater(new Runnable() {
//                            @Override
//                            public void run() {
//                                maintaince main = new maintaince();
//                                try {
//                                     main.setId(Integer.parseInt(id.getText()));
//                                     main.setClient_id(client.getSelectionModel().getSelectedItem().getId());
//                                     main.setMember_id(member.getSelectionModel().getSelectedItem().getId());
//                                      DateTimeFormatter format= DateTimeFormatter.ofPattern("yyyy-MM-dd");
//                                        main.setDate(date.getValue().format(format));
//                                     main.setProblem(problem.getText());
//                                     main.setCost(Integer.parseInt(cost.getText()));
//                                     main.setPay_type(pay_type.getSelectionModel().getSelectedItem());
//                                    
//                                         
//                                     main.Add();
//                                    
//                                } catch (Exception ex) {
//                                    AlertDialogs.showErrors(ex);
//                                   
//                                } finally {
//                                    latch.countDown();
//                                }
//                            }
//
//                        });
//                        latch.await();
//
//                        return null;
//                    }
//                };
//
//            }
//
//            @Override
//            protected void succeeded() {
//                progress.setVisible(false);
//                clear();
//                getData();
//                super.succeeded();
//            }
//        };
//        service.start();
//    }
//
//   
//       
//        
//              
//    
//
//}
//
//
