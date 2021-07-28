/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.sales;

import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import screens.sales.assets.OffersConditions;
import screens.sales.assets.OffersDetails;
import screens.sales.assets.SalesClient;
import screens.sales.assets.SalesMembers;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class SalesScreenOffersEditeController implements Initializable {

    @FXML
    private TextArea notes;
    @FXML
    private ComboBox<SalesClient> client;
    @FXML
    private JFXDatePicker date;
    @FXML
    private TextField filesPath;
    @FXML
    private ComboBox<SalesMembers> sales;
    @FXML
    private Button showInvoice;
    @FXML
    private ComboBox<String> id;
    @FXML
    private TableView<OffersDetails> invoiceTable;
    @FXML
    private MenuItem deleteRow;
    @FXML
    private TableColumn<OffersDetails, String> tabCost;
    @FXML
    private TableColumn<OffersDetails, String> tabAmount;
    @FXML
    private TableColumn<OffersDetails, String> tabProduct;
    @FXML
    private TableColumn<OffersDetails, String> tabId;
    @FXML
    private TableView<OffersConditions> CondTab;
    @FXML
    private TableColumn<OffersConditions, String> CondTabValue;
    @FXML
    private TableColumn<OffersConditions, String> CondTabAttribute;
    @FXML
    private TableColumn<OffersConditions, String> CondTabId;
    @FXML
    private Label condId;
    @FXML
    private TextField condAtrribute;
    @FXML
    private TextField condValue;
    @FXML
    private ProgressIndicator progressCond;
    @FXML
    private Button New;
    @FXML
    private Button Delete;
    @FXML
    private Button Edite;
    @FXML
    private Button Add;
    @FXML
    private Button deleteInvoive;
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
 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void attachFile(MouseEvent event) {
    }

    @FXML
    private void showInvoice(ActionEvent event) {
    }

    @FXML
    private void deleteRow(ActionEvent event) {
    }

    @FXML
    private void NewCond(ActionEvent event) {
    }

    @FXML
    private void DeleteCond(ActionEvent event) {
    }

    @FXML
    private void EditeCond(ActionEvent event) {
    }

    @FXML
    private void AddCond(ActionEvent event) {
    }

    @FXML
    private void deleteInvoice(ActionEvent event) {
    }

    @FXML
    private void invoiveAdd(ActionEvent event) {
    }
    
}
