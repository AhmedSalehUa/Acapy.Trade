/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.store.assets;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author AHMED
 */
public class InvoicesBuy {

    int id;
    int provider_id;
    String provider;
    int acc_id;
    String account;
    String cost;
    String dicount;
    String total_cost;
    String date;
    String payType;
    String hasTaxs;
    InputStream doc;
    String ext;
    String notes;
    ObservableList<InvoicesBuyDetails> details;

    public InvoicesBuy() {
    }

    public InvoicesBuy(int id, String date, String provider, String cost, String dicount, String total_cost, String payType, String hasTaxs, int account_id, String notes) {
        this.id = id;
        this.provider = provider;
        this.acc_id = account_id;
        this.cost = cost;
        this.dicount = dicount;
        this.total_cost = total_cost;
        this.date = date;
        this.payType = payType;
        this.hasTaxs = hasTaxs;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(int provider_id) {
        this.provider_id = provider_id;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public int getAcc_id() {
        return acc_id;
    }

    public void setAcc_id(int acc_id) {
        this.acc_id = acc_id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDicount() {
        return dicount;
    }

    public void setDicount(String dicount) {
        this.dicount = dicount;
    }

    public String getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(String total_cost) {
        this.total_cost = total_cost;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getHasTaxs() {
        return hasTaxs;
    }

    public void setHasTaxs(String hasTaxs) {
        this.hasTaxs = hasTaxs;
    }

    public InputStream getDoc() {
        return doc;
    }

    public void setDoc(InputStream doc) {
        this.doc = doc;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public ObservableList<InvoicesBuyDetails> getDetails() {
        return details;
    }

    public void setDetails(ObservableList<InvoicesBuyDetails> details) {
        this.details = details;
    }

    public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `st_invoices`(`id`, `date`, `provider_id`, `cost`, `discount`, `total_cost`, `pay_type`,`hasTaxs`, `doc`, `doc_ext`, `account_id`, `notes`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setString(2, date);
        ps.setInt(3, provider_id);
        ps.setString(4, cost);
        ps.setString(5, dicount);
        ps.setString(6, total_cost);
        ps.setString(7, payType);
        ps.setString(8, hasTaxs);
        ps.setBlob(9, doc);
        ps.setString(10, ext);
        ps.setInt(11, acc_id);
        ps.setString(12, notes);
        ps.execute();
        AddDetails();
        return true;
    }

    public boolean AddWithoutPhoto() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `st_invoices`(`id`, `date`, `provider_id`, `cost`, `discount`, `total_cost`, `pay_type`,`hasTaxs`, `account_id`, `notes`) VALUES (?,?,?,?,?,?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setString(2, date);
        ps.setInt(3, provider_id);
        ps.setString(4, cost);
        ps.setString(5, dicount);
        ps.setString(6, total_cost);
        ps.setString(7, payType);
         ps.setString(8, hasTaxs);
        ps.setInt(9, acc_id);
        ps.setString(10, notes);
        ps.execute();
        AddDetails();
        return true;
    }

    public boolean Edite() throws Exception {
        DeleteDetails();
        PreparedStatement ps = db.get.Prepare("UPDATE `st_invoices` SET `date`=?,`provider_id`=?,`cost`=?,`discount`=?,`total_cost`=?,`pay_type`=?,`hasTaxs`=?,`doc`=?,`doc_ext`=?,`account_id`=?,`notes`=? WHERE `id`=?");
        ps.setString(1, date);
        ps.setInt(2, provider_id);
        ps.setString(3, cost);
        ps.setString(4, dicount);
        ps.setString(5, total_cost);
        ps.setString(6, payType);
        ps.setString(7, hasTaxs);
        ps.setBlob(8, doc);
        ps.setString(9, ext);
        ps.setInt(10, acc_id);
        ps.setString(11, notes);
        ps.setInt(12, id);
        AddDetails();
        ps.execute();
        return true;
    }

    public boolean EditeWithoutPhoto() throws Exception {
        DeleteDetails();
        PreparedStatement ps = db.get.Prepare("UPDATE `st_invoices` SET `date`=?,`provider_id`=?,`cost`=?,`discount`=?,`total_cost`=?,`pay_type`=?,`hasTaxs`=?,`account_id`=?,`notes`=? WHERE `id`=?");
        ps.setString(1, date);
        ps.setInt(2, provider_id);
        ps.setString(3, cost);
        ps.setString(4, dicount);
        ps.setString(5, total_cost);
        ps.setString(6, payType);
         ps.setString(7, hasTaxs);
        ps.setInt(8, acc_id);
        ps.setString(9, notes);
        ps.setInt(10, id);
        ps.execute();
        AddDetails();
        return true;
    }

    public boolean Delete() throws Exception {
        DeleteDetails();
        PreparedStatement ps = db.get.Prepare("DELETE FROM `st_invoices` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public boolean AddDetails() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `st_invoices_details`(`invoice_id`, `product_id`, `cost`, `amount`, `total_cost`) VALUES (?,?,?,?,?)");

        for (InvoicesBuyDetails a : details) {
            ps.setInt(1, id);
            Products b = (Products) a.getProducts().getSelectionModel().getSelectedItem();
            ps.setInt(2, b.getId());
            ps.setString(3, a.getCost().getText());
            ps.setString(4, a.getAmount().getText());
            ps.setString(5, Double.toString(Double.parseDouble(a.getAmount().getText()) * Double.parseDouble(a.getCost().getText())));
            ps.addBatch();
        }
        ps.executeBatch();
        return true;
    }

    public boolean DeleteDetails() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `st_invoices_details` WHERE `invoice_id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<InvoicesBuy> getData() throws Exception {
        ObservableList<InvoicesBuy> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `st_invoices`.`id`, `st_invoices`.`date`,`st_provider`.`name`, `st_invoices`.`cost`, `st_invoices`.`discount`, `st_invoices`.`total_cost`, `st_invoices`.`pay_type`, `st_invoices`.`hasTaxs` , `st_invoices`.`account_id`, `st_invoices`.`notes` FROM `st_invoices`,`st_provider` WHERE `st_provider`.`id` = `st_invoices`.`provider_id` ");
        while (rs.next()) {
            data.add(new InvoicesBuy(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9), rs.getString(10)));
        }
        return data;
    }

    public static ObservableList<InvoicesBuy> getDataById(int id) throws Exception {
        ObservableList<InvoicesBuy> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `st_invoices`.`id`, `st_invoices`.`date`,`st_provider`.`name`, `st_invoices`.`cost`, `st_invoices`.`discount`, `st_invoices`.`total_cost`, `st_invoices`.`pay_type`, `st_invoices`.`hasTaxs`, `st_invoices`.`account_id`, `st_invoices`.`notes` FROM `st_invoices`,`st_provider` WHERE `st_provider`.`id` = `st_invoices`.`provider_id` AND `st_invoices`.`id`='" + id + "'");
        while (rs.next()) {
            data.add(new InvoicesBuy(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9), rs.getString(10)));
        }
        return data;
    }

    public static ObservableList<InvoicesBuy> getDataNotInStore() throws Exception {
        ObservableList<InvoicesBuy> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `st_invoices`.`id`, `st_invoices`.`date`,`st_provider`.`name`, `st_invoices`.`cost`, `st_invoices`.`discount`, `st_invoices`.`total_cost`, `st_invoices`.`pay_type`, `st_invoices`.`hasTaxs`, `st_invoices`.`account_id`, `st_invoices`.`notes` FROM `st_invoices`,`st_provider` WHERE `st_provider`.`id` = `st_invoices`.`provider_id` AND `st_invoices`.`id` NOT IN (SELECT `invoice_id` FROM `st_store_products` )");
        while (rs.next()) {
            data.add(new InvoicesBuy(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9), rs.getString(10)));
        }
        return data;
    }

    public static ObservableList<InvoicesBuy> getDataForProvider(int providerId) throws Exception {
        ObservableList<InvoicesBuy> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `st_invoices`.`id`, `st_invoices`.`date`,`st_provider`.`name`, `st_invoices`.`cost`, `st_invoices`.`discount`, `st_invoices`.`total_cost`, `st_invoices`.`pay_type`, `st_invoices`.`hasTaxs`, `st_invoices`.`account_id`, `st_invoices`.`notes` FROM `st_invoices`,`st_provider` WHERE `st_provider`.`id` = `st_invoices`.`provider_id` AND `st_invoices`.`provider_id`='" + providerId + "'");
        while (rs.next()) {
            data.add(new InvoicesBuy(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9), rs.getString(10)));
        }
        return data;
    }

    public static ObservableList<InvoicesBuy> getCutomData(String sql) throws Exception {
        ObservableList<InvoicesBuy> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery(sql);
        while (rs.next()) {
            data.add(new InvoicesBuy(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9), rs.getString(10)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `st_invoices`").getValueAt(0, 0).toString();
    }
}
