/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.store.assets;

import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import screens.store.StoreScreenInvoicesController;
import screens.store.assets.combo.ProductCombo;

/**
 *
 * @author AHMED
 */
public class InvoicesBuyDetails {

    int id;
    ComboBox products;
    TextField amount;
    TextField cost;
    TextField totalcost;
    TextField costOfSell;
    StoreScreenInvoicesController pa;

    String product;
    String amountString;
    String costString;
    String totalCostString;
    int productID;

    public InvoicesBuyDetails(int id, String product, String amountString, String costString, String totalCostString) {
        this.id = id;
        this.product = product;
        this.amountString = amountString;
        this.costString = costString;
        this.totalCostString = totalCostString;
    }
    public InvoicesBuyDetails(int id, String product, String amountString, String costString, String totalCostString, int productID) {
        this.id = id;
        this.product = product;
        this.amountString = amountString;
        this.costString = costString;
        this.productID = productID;
        this.costOfSell = new TextField(totalCostString);
    }

    public InvoicesBuyDetails(int id, ObservableList<Products> data, String selectedpro, String amount, String cost) {
        this.id = id;
        this.products = new ComboBox(data);
        products.setConverter(new StringConverter<Products>() {
            @Override
            public String toString(Products contract) {
                return contract.getName();
            }

            @Override
            public Products fromString(String string) {
                return null;
            }
        });
        products.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        for (Products a : data) {
            if (a.getName().equals(selectedpro)) {
                products.getSelectionModel().select(a);

            }
        }
        products.setCellFactory(cell -> new ListCell<Products>() {

            GridPane gridPane = new GridPane();
            Label lblid = new Label();
            Label lblName = new Label();
            Label lblQuali = new Label();

            {

                gridPane.getColumnConstraints().addAll(
                        new ColumnConstraints(100, 100, 100), new ColumnConstraints(100, 100, 100),
                        new ColumnConstraints(100, 100, 100)
                );

                gridPane.add(lblid, 0, 1);
                gridPane.add(lblName, 1, 1);
                gridPane.add(lblQuali, 2, 1);

            }

            @Override
            protected void updateItem(Products person, boolean empty) {
                super.updateItem(person, empty);

                if (!empty && person != null) {

                    lblid.setText("الاسم: " + person.getName());
                    lblName.setText("الموديل: " + person.getModel());
                    lblQuali.setText("التفاصيل: " + person.getDetails());
                    setGraphic(gridPane);
                } else {
                    // Nothing to display here
                    setGraphic(null);
                }
            }
        });
        this.amount = new TextField(amount);
        this.cost = new TextField(cost);
    }

    public InvoicesBuyDetails(int id, ObservableList<Products> data, String selectedpro, String amount, String cost, String totalCostString) {
        this.id = id;
        this.products = new ComboBox(data); 
        products.setConverter(new StringConverter<Products>() {
            @Override
            public String toString(Products contract) {
                return contract.getName();
            }

            @Override
            public Products fromString(String string) {
                return null;
            }
        });
        products.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        for (Products a : data) {
            if (a.getName().equals(selectedpro)) {
                products.getSelectionModel().select(a);

            }
        }
        products.setCellFactory(cell -> new ListCell<Products>() {

            GridPane gridPane = new GridPane();
            Label lblid = new Label();
            Label lblName = new Label();
            Label lblQuali = new Label();

            {

                gridPane.getColumnConstraints().addAll(
                        new ColumnConstraints(100, 100, 100),
                        new ColumnConstraints(200, 200, 200),
                        new ColumnConstraints(200, 200, 200)
                );

                gridPane.add(lblid, 0, 1);
                gridPane.add(lblName, 1, 1);
                gridPane.add(lblQuali, 0, 2);

            }

            @Override
            protected void updateItem(Products person, boolean empty) {
                super.updateItem(person, empty);

                if (!empty && person != null) {

                    // Update our Labels
                    lblid.setText("الاسم: " + person.getName());
                    lblName.setText("الموديل: " + person.getModel());
                    lblQuali.setText("التفاصيل: " + person.getDetails());
                    // Set this ListCell's graphicProperty to display our GridPane
                    setGraphic(gridPane);
                } else {
                    // Nothing to display here
                    setGraphic(null);
                }
            }
        });
        this.amount = new TextField(amount);
        this.cost = new TextField(cost);
        this.totalCostString = totalCostString;
    }

    public TextField getTotalcost() {
        return totalcost;
    }

    public void setTotalcost(TextField totalcost) {
        this.totalcost = totalcost;
    }

    public TextField getCostOfSell() {
        return costOfSell;
    }

    public void setCostOfSell(TextField costOfSell) {
        this.costOfSell = costOfSell;
    }

    public StoreScreenInvoicesController getPa() {
        return pa;
    }

    public void setPa(StoreScreenInvoicesController pa) {
        this.pa = pa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ComboBox getProducts() {
        return products;
    }

    public void setProducts(ComboBox products) {
        this.products = products;
    }

    public TextField getAmount() {
        return amount;
    }

    public void setAmount(TextField amount) {
        this.amount = amount;
    }

    public TextField getCost() {
        return cost;
    }

    public void setCost(TextField cost) {
        this.cost = cost;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getAmountString() {
        return amountString;
    }

    public void setAmountString(String amountString) {
        this.amountString = amountString;
    }

    public String getCostString() {
        return costString;
    }

    public void setCostString(String costString) {
        this.costString = costString;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getTotalCostString() {
        return totalCostString;
    }

    public void setTotalCostString(String totalCostString) {
        this.totalCostString = totalCostString;
    }

    public static ObservableList<InvoicesBuyDetails> getData(int id) throws Exception {
        ObservableList<InvoicesBuyDetails> data = FXCollections.observableArrayList();
        ObservableList<Products> data1 = Products.getData();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `st_invoices_details`.`id`,`st_products`.`name`, `st_invoices_details`.`amount`, `st_invoices_details`.`cost`,cast(`st_invoices_details`.`amount` as UNSIGNED) * cast(`st_invoices_details`.`cost` as UNSIGNED) as 'total' FROM `st_invoices_details`,`st_products` WHERE `st_products`.`id` =`st_invoices_details`.`product_id` AND `st_invoices_details`.`invoice_id`='" + id + "'");
        while (rs.next()) {
            data.add(new InvoicesBuyDetails(rs.getInt(1), data1, rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
        }
        return data;
    }

    public static ObservableList<InvoicesBuyDetails> getDataById(int id) throws Exception {
        ObservableList<InvoicesBuyDetails> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `st_invoices_details`.`id`,`st_products`.`name`, `st_invoices_details`.`amount`, `st_invoices_details`.`cost`,cast(`st_invoices_details`.`amount` as UNSIGNED) * cast(`st_invoices_details`.`cost` as UNSIGNED) as 'total' FROM `st_invoices_details`,`st_products` WHERE `st_products`.`id` =`st_invoices_details`.`product_id` AND `st_invoices_details`.`invoice_id`='" + id + "'");
        while (rs.next()) {
            data.add(new InvoicesBuyDetails(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
        }
        return data;
    }
    public static ObservableList<InvoicesBuyDetails> getDataByIdEntrance(int id) throws Exception {
        ObservableList<InvoicesBuyDetails> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `st_invoices_details`.`invoice_id`,`st_products`.`name`, `st_invoices_details`.`amount`, `st_invoices_details`.`cost`,`st_invoices_details`.`cost`,`st_products`.`id` FROM `st_invoices_details`,`st_products` WHERE `st_products`.`id` =`st_invoices_details`.`product_id` AND `st_invoices_details`.`invoice_id`='" + id + "'");
        while (rs.next()) {
            data.add(new InvoicesBuyDetails(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getInt(6)));
        }
        return data;
    }
}
