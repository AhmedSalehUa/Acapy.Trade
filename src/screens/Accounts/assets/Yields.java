/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.Accounts.assets;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author AHMED
 */
public class Yields {

    int id;
    String amount;
    String date;
    int acc_id;
    String account;
    int cat_id;
    String category;

    public Yields() {
    }

    public Yields(int id, String amount, String date, String account, String category) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.account = account;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
        PreparedStatement ps = db.get.Prepare("INSERT INTO `acc_yields`(`amount`, `date`, `acc_id`, `acc_cat`) VALUES (?,?,?,?)");
        ps.setString(1, amount);
        ps.setString(2, date);
        ps.setInt(3, acc_id);
        ps.setInt(4, cat_id);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `acc_yields` SET `amount`=?,`date`=?,`acc_id`=?,`acc_cat`=? WHERE `id`=?");
        ps.setString(1, amount);
        ps.setString(2, date);
        ps.setInt(3, acc_id);
        ps.setInt(4, cat_id);
        ps.setInt(5, id);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `acc_yields` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<Yields> getData() throws Exception {
        ObservableList<Yields> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `acc_yields`.`id`, `acc_yields`.`amount`, `acc_yields`.`date`, `acc_accounts`.`name`  ,`acc_yields_category`.`name` FROM `acc_accounts`,`acc_yields_category`,`acc_yields` WHERE `acc_yields`.`acc_id`= `acc_accounts`.`id` AND `acc_yields`.`acc_cat`= `acc_yields_category`.`id`");
        while (rs.next()) {
            data.add(new Yields(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `acc_yields`").getValueAt(0, 0).toString();
    }
}
