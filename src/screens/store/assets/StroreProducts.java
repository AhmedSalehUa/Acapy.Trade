/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.store.assets;

import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author AHMED
 */
public class StroreProducts {

    int id;
    int invoice_id;
    int store_id;
    String store;
    int product_id;
    String product;
    String amount;
    String costOfBuy;
    String costOfSell;

    public StroreProducts() {
    }

    public StroreProducts(int id, int invoice_id, String store, String product, String amount, String costOfBuy, String costOfSell) {
        this.id = id;
        this.invoice_id = invoice_id;
        this.store = store;
        this.product = product;
        this.amount = amount;
        this.costOfBuy = costOfBuy;
        this.costOfSell = costOfSell;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(int invoice_id) {
        this.invoice_id = invoice_id;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCostOfBuy() {
        return costOfBuy;
    }

    public void setCostOfBuy(String costOfBuy) {
        this.costOfBuy = costOfBuy;
    }

    public String getCostOfSell() {
        return costOfSell;
    }

    public void setCostOfSell(String costOfSell) {
        this.costOfSell = costOfSell;
    }
     public static ObservableList<StroreProducts> getData() throws Exception {
    ObservableList<StroreProducts> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `st_store_products`.`id`,`st_store_products`.`invoice_id`, `st_stores`.`name` ,  CONCAT(`st_products`.`name`,'  ', `st_products`.`model`) as 'name', `st_store_products`.`amount`, `st_store_products`.`cost_of_buy`, `st_store_products`.`cost_for_sell` FROM `st_store_products`,`st_stores`,`st_products` WHERE `st_products`.`id` = `st_store_products`.`product_id` AND  `st_stores`.`id` = `st_store_products`.`store_id`");
        while(rs.next()){
            data.add(new StroreProducts(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7)));
        }
        return data;
    }
    public static ObservableList<StroreProducts> getDataForStore(int store_id) throws Exception {
    ObservableList<StroreProducts> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `st_store_products`.`id`,`st_store_products`.`invoice_id`,`st_stores`.`name` ,  CONCAT(`st_products`.`name`,'  ', `st_products`.`model`) as 'name', `st_store_products`.`amount`, `st_store_products`.`cost_of_buy`, `st_store_products`.`cost_for_sell` FROM `st_store_products`,`st_stores`,`st_products` WHERE `st_products`.`id` = `st_store_products`.`product_id` AND  `st_stores`.`id` = `st_store_products`.`store_id` AND `st_store_products`.`store_id`='"+store_id+"'");
        while(rs.next()){
            data.add(new StroreProducts(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7)));
        }
        return data;
    }
    public static String getAutoNum() throws Exception {
     return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM ``").getValueAt(0, 0).toString();
    }
}
