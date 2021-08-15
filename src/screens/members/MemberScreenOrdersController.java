/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.members;

import assets.classes.AlertDialogs;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import screens.members.assets.AcapyMembers;
import screens.members.assets.MemberOrders;
import screens.store.assets.ProductCategory;
import screens.store.assets.Provider;

/**
 * FXML Controller class
 *
 * @author amran
 */
public class MemberScreenOrdersController implements Initializable {

    @FXML
    private JFXTextField search;
    @FXML
    private TableView<MemberOrders> tab;
    @FXML
    private TableColumn<MemberOrders, String> tab_notes;
    @FXML
    private TableColumn<MemberOrders, String> tab_matters;
    @FXML
    private TableColumn<MemberOrders, String> tab_date;
    @FXML
    private TableColumn<MemberOrders, String> tab_totalAmount;
    @FXML
    private TableColumn<MemberOrders, String> tab_loc;
    @FXML
    private TableColumn<MemberOrders, String> tab_place;
    @FXML
    private TableColumn<MemberOrders, String> tab_memName;
    @FXML
    private TableColumn<MemberOrders, String> tab_id;
    @FXML
    private Label id;
    @FXML
    private TextField place_txtFieled;
    @FXML
    private TextField address_txtFieled;
    @FXML
    private TextField visitType_txtFieled;
    @FXML
    private TextField totalAmount_txtFieled;
    @FXML
    private TextField matters_txtFieled;
    @FXML
    private JFXTextArea notes_txtArea;
    @FXML
    private JFXDatePicker date;
    @FXML
    private ProgressIndicator progress;
    @FXML
    private Button formNew;
    @FXML
    private Button formDelete;
    @FXML
    private Button formEdite;
    @FXML
    private Button formAdd;
    @FXML
    private JFXComboBox<AcapyMembers> memName_comBox;
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @FXML
    private TableColumn<?, ?> tab_visitType;
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
                                    clear();
                                    intialColumn();
                                    getData();
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
        tab.setOnMouseClicked((e) -> {
            if (tab.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                formNew.setDisable(false);

                formDelete.setDisable(false);

                formEdite.setDisable(false);

                formAdd.setDisable(true);

                MemberOrders selected = tab.getSelectionModel().getSelectedItem();
                id.setText(Integer.toString(selected.getOrderID()));
                place_txtFieled.setText(selected.getPlace());
                address_txtFieled.setText(selected.getLoc());
                visitType_txtFieled.setText(selected.getVisitType());
                totalAmount_txtFieled.setText(selected.getAmount());
                date.setValue(LocalDate.parse(selected.getDate()));
                matters_txtFieled.setText(selected.getMatters());
                notes_txtArea.setText(selected.getNotes());

                ObservableList<AcapyMembers> items1 = memName_comBox.getItems();
                for (AcapyMembers a : items1) {
                    if (a.getName().equals(selected.getMemName())) {
                        memName_comBox.getSelectionModel().select(a);
                    }
                }
            }
        });
    }
private void intialColumn() {
        tab_id.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        tab_memName.setCellValueFactory(new PropertyValueFactory<>("memName"));
        tab_place.setCellValueFactory(new PropertyValueFactory<>("place"));
        tab_loc.setCellValueFactory(new PropertyValueFactory<>("loc"));
        tab_visitType.setCellValueFactory(new PropertyValueFactory<>("visitType"));
        tab_totalAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        tab_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        tab_matters.setCellValueFactory(new PropertyValueFactory<>("matters"));
        tab_notes.setCellValueFactory(new PropertyValueFactory<>("notes"));
    }

    private void clear() {
        getAutoNum();
        formNew.setDisable(true);

        formDelete.setDisable(true);

        formEdite.setDisable(true);

        formAdd.setDisable(false);

        place_txtFieled.setText("");
        address_txtFieled.setText("");
        visitType_txtFieled.setText("");
        totalAmount_txtFieled.setText("");
        notes_txtArea.setText("");
        matters_txtFieled.setText("");
        date.setValue(null);
        memName_comBox.getSelectionModel().clearSelection();
    }    
    
  private void getAutoNum() {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            String autoNum;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            autoNum = Provider.getAutoNum();

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
                id.setText(autoNum);
                super.succeeded();
            }
        };
        service.start();
    }

    private void getData() {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            ObservableList<MemberOrders> data;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            data = MemberOrders.getData();
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
                tab.setItems(data);
                super.succeeded();
            }
        };
        service.start();
    }

    
    private void fillCombo() {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            ObservableList<AcapyMembers> data;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {

                            data = AcapyMembers.getData();
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
                memName_comBox.setItems(data);
                memName_comBox.setConverter(new StringConverter<AcapyMembers>() {
                    @Override
                    public String toString(AcapyMembers patient) {
                        return patient.getName();
                    }

                    @Override
                    public AcapyMembers fromString(String string) {
                        return null;
                    }
                });
                memName_comBox.setCellFactory(cell -> new ListCell<AcapyMembers>() {

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
                    protected void updateItem(AcapyMembers person, boolean empty) {
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
    @FXML
    private void search(KeyEvent event) {
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
                                    alert.setTitle("Deleting  order");
                                    alert.setHeaderText("سيتم حذف الطلب ");
                                    alert.setContentText("هل انت الطلب؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        MemberOrders pr = new MemberOrders();
                                        pr.setOrderID(Integer.parseInt(id.getText()));
                                        pr.Delete();
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
                                    alert.setTitle("Editting  order");
                                    alert.setHeaderText("سيتم تعديل الطلب ");
                                    alert.setContentText("هل انت الطلب؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        MemberOrders pr = new MemberOrders();
                                    pr.setOrderID(Integer.parseInt(id.getText()));
                                    pr.setMemID(memName_comBox.getSelectionModel().getSelectedItem().getId());
                                    pr.setPlace(place_txtFieled.getText());
                                    pr.setLoc(address_txtFieled.getText());
                                    pr.setVisitType(visitType_txtFieled.getText());
                                    pr.setAmount(totalAmount_txtFieled.getText());
                                    pr.setDate(date.getValue().format(format));
                                    pr.setMatters(matters_txtFieled.getText());
                                    pr.setNotes(notes_txtArea.getText());
                                        pr.Edite();
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
    private void Add(ActionEvent event) {
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
                                    MemberOrders pr = new MemberOrders();
                                    pr.setOrderID(Integer.parseInt(id.getText()));
                                    pr.setMemID(memName_comBox.getSelectionModel().getSelectedItem().getId());
                                    pr.setPlace(place_txtFieled.getText());
                                    pr.setLoc(address_txtFieled.getText());
                                    pr.setVisitType(visitType_txtFieled.getText());
                                    pr.setAmount(totalAmount_txtFieled.getText());
                                    pr.setDate(date.getValue().format(format));
                                    pr.setMatters(matters_txtFieled.getText());
                                    pr.setNotes(notes_txtArea.getText());
                                    pr.Add();
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
    }
    

