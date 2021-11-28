/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.members.assets;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Ahmed Al-Gazzar
 */
public class SalesSolfa {
    int id;
    String sales;
    int sales_id;
    String amount;
    String date;
    String account;
    int account_id;

    public SalesSolfa() {
    }

    public SalesSolfa(int id,  String amount, String date, String account) {
        this.id = id;
        
        this.amount = amount;
        this.date = date;
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public int getSales_id() {
        return sales_id;
    }

    public void setSales_id(int sales_id) {
        this.sales_id = sales_id;
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }
      public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `sales_member_solfa`(`id`, `sales_id`, `amount`, `date`,`account_id`) VALUES (?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setInt(2, sales_id);
        ps.setString(3, amount);
        ps.setString(4, date);
          ps.setInt(5, account_id);
        ps.execute();
        return true;
    }
     public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `sales_member_solfa` SET `sales_id`=?,`amount`=?,`date`=? ,`account_id`=? WHERE `id`=?");
        ps.setInt(1, sales_id);
        ps.setString(2, amount);
        ps.setString(3, date);
        ps.setInt(4,account_id);
           ps.setInt(5,id);
        ps.execute();
        return true;
    }
      public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `sales_member_solfa` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }
      public static ObservableList<SalesSolfa> getData(int id) throws Exception {
        ObservableList<SalesSolfa> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `sales_member_solfa`.`id`,`sales_member_solfa`.`amount`,`sales_member_solfa`.`date` ,`acc_accounts`.`name` FROM `sales_member_solfa`,`acc_accounts` where `sales_member_solfa`.`account_id`=`acc_accounts`.`id` AND `sales_member_solfa`.`sales_id`='"+id+"'");
        while (rs.next()) {
            data.add(new SalesSolfa(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `sales_member_solfa`").getValueAt(0, 0).toString();
    }
}
