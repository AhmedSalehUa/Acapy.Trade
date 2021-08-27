/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.store.assets;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author AHMED
 */
public class Provider {

    int id;
    String name;
    String address;
    String accountNumber;
    String totalAccount;
    int cat_id;
    String category; 
    
    public Provider() {
    }

    public Provider(int id, String name, String address, String accountNumber, String totalAccount, String category) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.accountNumber = accountNumber;
        this.totalAccount = totalAccount;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getTotalAccount() {
        return totalAccount;
    }

    public void setTotalAccount(String totalAccount) {
        this.totalAccount = totalAccount;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `st_provider`(`id`, `name`, `adress`, `credite`, `account_number`, `cat_id`) VALUES (?,?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setString(3, address);
        ps.setString(4, totalAccount);
        ps.setString(5, accountNumber);
        ps.setInt(6, cat_id);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `st_provider` SET `name`=?,`adress`=?,`credite`=?,`account_number`=?,`cat_id`=? WHERE `id`=?");
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, totalAccount);
        ps.setString(4, accountNumber);
        ps.setInt(5, cat_id);
        ps.setInt(6, id);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `st_provider` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<Provider> getData() throws Exception {
        ObservableList<Provider> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `st_provider`.`id`, `st_provider`.`name`, `st_provider`.`adress`, `st_provider`.`account_number`, `st_provider`.`credite`,st_products_category.name FROM `st_provider`,st_products_category WHERE st_products_category.id = st_provider.cat_id");
        while (rs.next()) {
            data.add(new Provider(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `st_provider`").getValueAt(0, 0).toString();
    }
}
