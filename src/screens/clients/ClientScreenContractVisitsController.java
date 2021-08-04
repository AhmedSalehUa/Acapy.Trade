/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.clients;

import assets.classes.AlertDialogs;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
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
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import screens.clients.assets.Clients;
import screens.clients.assets.ContractVisits;
import screens.clients.assets.Contracts;
import screens.clients.assets.Operations;
import screens.members.assets.AcapyMembers;
import screens.sales.assets.SalesMembers;

/**
 * FXML Controller class
 *
 * @author amran
 */
public class ClientScreenContractVisitsController implements Initializable {

    @FXML
    private JFXTextField search;
    @FXML
    private Button DocShow_btn;
    @FXML
    private TableView<ContractVisits> tab;
    @FXML
    private TableColumn<ContractVisits, String> tabReport;
    @FXML
    private TableColumn<ContractVisits, String> tabDate;
    @FXML
    private TableColumn<ContractVisits, String> tabMemName;
    @FXML
    private TableColumn<ContractVisits, String> tabId;
    @FXML
    private Label visitID;
    @FXML
    private ComboBox<AcapyMembers> memName_cBox;
    @FXML
    private JFXDatePicker visitDate;
    @FXML
    private ImageView docdown;
    @FXML
    private TextField docpath;
    @FXML
    private JFXTextArea report_txtAr;
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

    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private ClientScreenContractController parentController;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         visitDate.setConverter(new StringConverter<LocalDate>() {
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
                Add.setDisable(true);
                Edite.setDisable(false);
                Delete.setDisable(false);
                New.setDisable(false);

                ContractVisits selected = tab.getSelectionModel().getSelectedItem();
                visitID.setText(Integer.toString(selected.getId()));
                ObservableList<AcapyMembers> items1 = memName_cBox.getItems();
                for (AcapyMembers a : items1) {
                    if (a.getName().equals(selected.getMemName())) {
                        memName_cBox.getSelectionModel().select(a);
                    }
                }
                
                visitDate.setValue(LocalDate.parse(selected.getDate()));

                report_txtAr.setText(selected.getReport());

               // detailsController.getData(selected.getId());
            }
        });
    }
        ObservableList<ContractVisits> items;

    @FXML
    private void search(KeyEvent event) {
        
    }

    


    @FXML
    private void attachFile(MouseEvent event) {
        FileChooser fil_chooser = new FileChooser();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File file = fil_chooser.showOpenDialog(st);

        if (file != null) {
            docpath.setText(file.getAbsolutePath());
        }
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
                                    alert.setTitle("Deleting  Visit");
                                    alert.setHeaderText("سيتم حذف العقد ");
                                    alert.setContentText("هل انتالعقد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        ContractVisits pr = new ContractVisits();
                                        pr.setId(Integer.parseInt(visitID.getText()));
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
        ContractVisits pr = new ContractVisits();
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
                                    alert.setTitle("Editting  Visit");
                                    alert.setHeaderText("سيتم تعديل العقد ");
                                    alert.setContentText("هل انتالعقد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        if (result.get() == ButtonType.OK) {
                                        if (docpath.getText().isEmpty() || docpath.getText().length() == 0) {
                                            pr.setId(Integer.parseInt(visitID.getText()));
                                            pr.setMemID(memName_cBox.getSelectionModel().getSelectedItem().getId());
                                            pr.setDate(visitDate.getValue().format(format));
                                            pr.setReport(report_txtAr.getText());

                                            pr.EditeWithouPhoto();
                                        } else {
                                        
                                        pr.setId(Integer.parseInt(visitID.getText()));
                                        pr.setMemID(memName_cBox.getSelectionModel().getSelectedItem().getId());
                                        pr.setDate(visitDate.getValue().format(format));
                                        pr.setReport(report_txtAr.getText());
                                        InputStream in = new FileInputStream(new File(docpath.getText()));
                                        pr.setDoc(in);

                                        String[] st = docpath.getText().split(Pattern.quote("."));
                                        pr.setDoc_ext(st[st.length - 1]);
                                       

                                        pr.Edite();
                                    }}}
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
        ContractVisits pr = new ContractVisits();
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
                                     if (docpath.getText().isEmpty() || docpath.getText().length() == 0) {
                                            pr.setId(Integer.parseInt(visitID.getText()));
                                            pr.setMemID(memName_cBox.getSelectionModel().getSelectedItem().getId());
                                            pr.setDate(visitDate.getValue().format(format));
                                            pr.setReport(report_txtAr.getText());

                                            pr.AddWithouPhoto();
                                        } else {
                                        
                                        pr.setId(Integer.parseInt(visitID.getText()));
                                        pr.setMemID(memName_cBox.getSelectionModel().getSelectedItem().getId());
                                        pr.setDate(visitDate.getValue().format(format));
                                        pr.setReport(report_txtAr.getText());
                                        InputStream in = new FileInputStream(new File(docpath.getText()));
                                        pr.setDoc(in);

                                        String[] st = docpath.getText().split(Pattern.quote("."));
                                        pr.setDoc_ext(st[st.length - 1]);

                                        pr.Add();
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

                }
                getData();
                super.succeeded();
            }
        };
        service.start();
    }
    public void clear() {
        getAutoNum();
        memName_cBox.getSelectionModel().clearSelection();
        visitID.setText("");
        visitDate.setValue(null);
        report_txtAr.setText("");
        docpath.setText("");
        Add.setDisable(false);
        Edite.setDisable(true);
        Delete.setDisable(true);
        New.setDisable(true);
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
                            autoNum = ContractVisits.getAutoNum();
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
                visitID.setText(autoNum);
                super.succeeded();
            }
        };
        service.start();
    }
 private void intialColumn() {
        tabId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tabMemName.setCellValueFactory(new PropertyValueFactory<>("memName"));
        tabDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tabReport.setCellValueFactory(new PropertyValueFactory<>("report"));

    }
 private void getData() {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            ObservableList<ContractVisits> data;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        try {
                            data = ContractVisits.getData();

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
 
 private void fillCombo(){
     try {
            memName_cBox.setItems(AcapyMembers.getData());
            memName_cBox.setConverter(new StringConverter<AcapyMembers>() {
                @Override
                public String toString(AcapyMembers patient) {
                    return patient.getName();
                }

                @Override
                public AcapyMembers fromString(String string) {
                    return null;
                }
            });
            memName_cBox.setCellFactory(cell -> new ListCell<AcapyMembers>() {

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
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
 }
    @FXML
    private void getDocument(ActionEvent event) {
         try {
            if (tab.getSelectionModel().getSelectedIndex() == -1) {
                AlertDialogs.showmessage("اختار من الجدول اولا");
            } else {
                ContractVisits cv = new ContractVisits();
                cv.setId(tab.getSelectionModel().getSelectedItem().getId());
                cv.getDocdown();
            }
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    /*void setParentController(ClientScreenContractController aThis) {
        this.parentController = parentController;
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
    void setParentController(ClientScreenContractController parentController) {
        this.parentController = parentController;
    }
     public void getData(int id ) {
           /*    ,String memName,String date,String report
        
        memName_cBox.getSelectionModel().select(id);
        visitDate.setValue(LocalDate.parse(date));
        report_txtAr.setText(report);
        */visitID.setText(Integer.toString(id));
    }
   
   
    }
    

