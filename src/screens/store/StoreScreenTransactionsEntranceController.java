/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.store;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class StoreScreenTransactionsEntranceController implements Initializable {

    @FXML
    private TableView<?> invoiceTable;
    @FXML
    private TableColumn<?, ?> invoiceTabNotes;
    @FXML
    private TableColumn<?, ?> invoiceTabTotalCost;
    @FXML
    private TableColumn<?, ?> invoiceTabDiscPerc;
    @FXML
    private TableColumn<?, ?> invoiceTabDisc;
    @FXML
    private TableColumn<?, ?> invoiceTabCost;
    @FXML
    private TableColumn<?, ?> invoiceTabDate;
    @FXML
    private TableColumn<?, ?> invoiceTabComp;
    @FXML
    private TableColumn<?, ?> invoiceTabId;
    @FXML
    private TableView<?> invoiceDetailsTable;
    @FXML
    private TableColumn<?, ?> invoiceDetailsTabCostOfSell;
    @FXML
    private TableColumn<?, ?> invoiceDetailsTabCost;
    @FXML
    private TableColumn<?, ?> invoiceDetailsTabAmount;
    @FXML
    private TableColumn<?, ?> invoiceDetailsTabMediccine;
    @FXML
    private TableColumn<?, ?> invoiceDetailsTabId;
    @FXML
    private ComboBox<?> stores;
    @FXML
    private Button save;
    @FXML
    private ProgressIndicator progress;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void save(ActionEvent event) {
    }
    
}
