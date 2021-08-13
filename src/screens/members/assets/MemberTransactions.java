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
public class MemberTransactions {
    int id;
    String order;
    int order_id;
    String date;
    String totalcost;
    String status;
    String account;
    int account_id;

    public MemberTransactions() {
    }

    public MemberTransactions(int id, String order, String date, String totalcost, String status, String account) {
        this.id = id;
        this.order = order;
        this.date = date;
        this.totalcost = totalcost;
        this.status = status;
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotalcost() {
        return totalcost;
    }

    public void setTotalcost(String totalcost) {
        this.totalcost = totalcost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        PreparedStatement ps = db.get.Prepare("INSERT INTO `mem_member_transactions`(`id`, `order_id`, `date`, `total_cost`,`statue`,`account_id`) VALUES (?,?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setInt(2, order_id);
        ps.setString(3, date);
        ps.setString(4, totalcost);
         ps.setString(5, status);
          ps.setInt(6, account_id);
        ps.execute();
        return true;
    }
     public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `mem_member_transactions` SET `order_id`=?,`date`=?,`total_cost`=? ,`statue`=? ,`account_id`=? WHERE `id`=?");
        ps.setInt(1, order_id);
        ps.setString(2, date);
        ps.setString(3, totalcost);
         ps.setString(4, status);
          ps.setInt(5, account_id);
        ps.setInt(6, id);
        ps.execute();
        return true;
    }
      public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `mem_member_transactions` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }
      
       public static ObservableList<MemberTransactions> getData() throws Exception {
        ObservableList<MemberTransactions> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `mem_member_transactions`.`id`,`mem_acapy_members`.`name`, `mem_member_transactions`.`date`,`mem_member_transactions`.`total_cost` ,`mem_member_transactions`.`statue`, `acc_accounts`.`name` FROM `mem_member_transactions`,`acc_accounts`,`mem_acapy_members`,`mem_member_orders` where `mem_member_transactions`.`account_id`=`acc_accounts`.`id` AND `mem_member_transactions`.`order_id`= `mem_member_orders`.`id` AND `mem_member_orders`.`member_id`= `mem_acapy_members`.`id`");
        while (rs.next()) {
            data.add(new MemberTransactions(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4) , rs.getString(5) , rs.getString(6)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `mem_member_transactions`").getValueAt(0, 0).toString();
    
}
}