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
public class SalesReward {
    int id;
    String sales;
    int sales_id;
    int operation_id;
    String operation;
    String amount;
    String date;
    String account;
    int account_id;

    public SalesReward() {
    }

    public SalesReward(int id, String operation, String amount, String date, String account) {
        this.id = id;
        this.operation = operation;
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

    

    public int getOperation_id() {
        return operation_id;
    }

    public void setOperation_id(int operation_id) {
        this.operation_id = operation_id;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
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
        PreparedStatement ps = db.get.Prepare("INSERT INTO `sales_member_reward`(`id`, `sales_id`,`operation_id` , `amount`, `date`,`account_id`) VALUES (?,?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setInt(2, sales_id);
        ps.setInt(3, operation_id);
        ps.setString(4, amount);
        ps.setString(5, date);
          ps.setInt(6, account_id);
        ps.execute();
        return true;
    }
     public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `sales_member_reward` SET `sales_id`=? ,`operation_id`=? ,`amount`=? , `date`=? ,`account_id`=? WHERE `id`=?");
        ps.setInt(1, sales_id);
         ps.setInt(2, operation_id);
        ps.setString(3, amount);
        ps.setString(4, date);
        ps.setInt(5, account_id);
           ps.setInt(6, id);
        ps.execute();
        return true;
    }
      public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `sales_member_reward` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }
      public static ObservableList<SalesReward> getData(int id) throws Exception {
        ObservableList<SalesReward> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `sales_member_reward`.`id`,`sl_sales_members`.`name`,`sales_member_reward`.`amount`,`sales_member_reward`.`date` ,`acc_accounts`.`name` FROM `sales_member_reward`,`acc_accounts`,`sl_sales_members`,`cli_operation` where `sales_member_reward`.`account_id`=`acc_accounts`.`id` AND `cli_operation`.`id`= `sales_member_reward`.`operation_id` AND `sl_sales_members`.`id`= `sales_member_reward`.`sales_id` AND `sales_member_reward`.`sales_id`='"+id+"'");
        while (rs.next()) {
            data.add(new SalesReward(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `sales_member_reward`").getValueAt(0, 0).toString();
    }
}
