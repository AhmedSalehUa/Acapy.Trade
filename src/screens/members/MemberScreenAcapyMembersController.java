/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.members;

import assets.classes.AlertDialogs;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
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
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import screens.clients.assets.Operations;
import screens.members.assets.AcapyMembers;
import screens.members.assets.MemberTransactionDetails;

/**
 * FXML Controller class
 *
 * @author Ahmed Al-Gazzar
 */
public class MemberScreenAcapyMembersController implements Initializable {

    @FXML
    private JFXTextField search;
    @FXML
    private TableView<AcapyMembers> tab;
    @FXML
    private TableColumn<AcapyMembers, String> tabapptoken;
    @FXML
    private TableColumn<AcapyMembers, String> tabaccnum;
    @FXML
    private TableColumn<AcapyMembers, String> tabname;
    @FXML
    private TableColumn<AcapyMembers, String> tabId;
    @FXML
    private Label id;
    @FXML
    private TextField name;
    @FXML
    private TextField accnum;
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
    @FXML
    private Button Doc;
    @FXML
    private TableColumn<AcapyMembers, String> tabNational;
    @FXML
    private TextField nationalId;
    @FXML
    private ImageView docdown;
    @FXML
    private TextField nationalPhoto;

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

                AcapyMembers selected = tab.getSelectionModel().getSelectedItem();

                id.setText(Integer.toString(selected.getId()));
                name.setText(selected.getName());
                apptoken.setText(selected.getApp_token());

                accnum.setText(selected.getAcc_num());

            }
        });
    }

    private void intialColumn() {
        tabId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tabNational.setCellValueFactory(new PropertyValueFactory<>("nationalId"));
        tabname.setCellValueFactory(new PropertyValueFactory<>("name"));
        tabaccnum.setCellValueFactory(new PropertyValueFactory<>("acc_num"));
        tabapptoken.setCellValueFactory(new PropertyValueFactory<>("app_token"));

    }

    private void clear() {
        getAutoNum();

        name.setText("");
        apptoken.setText("");
        accnum.setText("");
        nationalId.setText("");
        nationalPhoto.setText("");

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
                            idNum = AcapyMembers.getAutoNum();

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
                tab.setItems(data);
                items = data;
                progress.setVisible(false);
                super.succeeded();
            }
        };
        service.start();
    }
    ObservableList<AcapyMembers> items;

    @FXML
    private void search(KeyEvent event) {
        FilteredList<AcapyMembers> filteredData = new FilteredList<>(items, p -> true);

        filteredData.setPredicate(pa -> {

            if (search.getText() == null || search.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = search.getText().toLowerCase();

            if (pa.getAcc_num().contains(lowerCaseFilter)
                    || pa.getApp_token().contains(lowerCaseFilter)
                    || pa.getName().contains(lowerCaseFilter)) {
                return true;
            } else {
                return false;
            }

        });

        SortedList< AcapyMembers> sortedData = new SortedList<>(filteredData);
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
                                        AcapyMembers mdcd = new AcapyMembers();
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
            AcapyMembers mdcd = new AcapyMembers();

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
                                        if (nationalPhoto.getText().isEmpty() || nationalPhoto.getText().length() == 0) {
                                            mdcd.setId(Integer.parseInt(id.getText()));
//                                    

                                            mdcd.setAcc_num(accnum.getText());
                                            mdcd.setApp_token(apptoken.getText());
                                            mdcd.setName(name.getText());
                                            mdcd.setNationalId(nationalId.getText());

                                            mdcd.EditeWithoutPhoto();
                                        } else {
                                            mdcd.setId(Integer.parseInt(id.getText()));
//                                    

                                            mdcd.setAcc_num(accnum.getText());
                                            mdcd.setApp_token(apptoken.getText());
                                            mdcd.setName(name.getText());
                                            mdcd.setNationalId(nationalId.getText());

                                            InputStream in = new FileInputStream(new File(nationalPhoto.getText()));
                                            mdcd.setNationalPhoto(in);

                                            String[] st = nationalPhoto.getText().split(Pattern.quote("."));
                                            mdcd.setPhoto_ext(st[st.length - 1]);

                                            mdcd.Edite();
                                        }

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
            AcapyMembers mdcd = new AcapyMembers();
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
                                    if (nationalPhoto.getText().isEmpty() || nationalPhoto.getText().length() == 0) {
                                        mdcd.setId(Integer.parseInt(id.getText()));
//                                    

                                        mdcd.setAcc_num(accnum.getText());
                                        mdcd.setApp_token(apptoken.getText());
                                        mdcd.setName(name.getText());
                                        mdcd.setNationalId(nationalId.getText());

                                        mdcd.AddWithoutPhoto();
                                    } else {
                                        mdcd.setId(Integer.parseInt(id.getText()));
//                                    

                                        mdcd.setAcc_num(accnum.getText());
                                        mdcd.setApp_token(apptoken.getText());
                                        mdcd.setName(name.getText());
                                        mdcd.setNationalId(nationalId.getText());

                                        InputStream in = new FileInputStream(new File(nationalPhoto.getText()));
                                        mdcd.setNationalPhoto(in);

                                        String[] st = nationalPhoto.getText().split(Pattern.quote("."));
                                        mdcd.setPhoto_ext(st[st.length - 1]);

                                        mdcd.Add();
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
    private void getdoc(ActionEvent event) {

        try {
            if (tab.getSelectionModel().getSelectedIndex() == -1) {
                AlertDialogs.showmessage("اختار من الجدول اولا");
            } else {
                AcapyMembers op = new AcapyMembers();
                op.setId(tab.getSelectionModel().getSelectedItem().getId());
                op.getDocdown();
            }
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    @FXML
    private void attachFile(MouseEvent event) {
        FileChooser fil_chooser = new FileChooser();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File file = fil_chooser.showOpenDialog(st);

        if (file != null) {
            nationalPhoto.setText(file.getAbsolutePath());
        }
    }

}
