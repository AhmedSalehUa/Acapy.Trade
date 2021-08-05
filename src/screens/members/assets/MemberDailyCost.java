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


public class MemberDailyCost {
    int id;
    String destination;
    String cost;
    String date;
    String status;
    int account_id;
    String account;

    public MemberDailyCost() {
    }

    public MemberDailyCost(int id, String destination, String cost, String date, String status, String account) {
        this.id = id;
        this.destination = destination;
        this.cost = cost;
        this.date = date;
        this.status = status;
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
     public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `mem_member_daily_cost`(`id`, `destination`, `cost`, `date`,`statue`,`account_id`) VALUES (?,?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setString(2, destination);
        ps.setString(3, cost);
        ps.setString(4, date);
         ps.setString(5, status);
          ps.setInt(6, account_id);
        ps.execute();
        return true;
    }
     public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `mem_member_daily_cost` SET `destination`=?,`cost`=?,`date`=? ,`statue`=? ,`account_id`=? WHERE `id`=?");
        ps.setString(1, destination);
        ps.setString(2, cost);
        ps.setString(3, date);
         ps.setString(4, status);
          ps.setInt(5, account_id);
        ps.setInt(6, id);
        ps.execute();
        return true;
    }
      public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `mem_member_daily_cost` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }
      public static ObservableList<MemberDailyCost> getData() throws Exception {
        ObservableList<MemberDailyCost> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `mem_member_daily_cost`.`id`,`mem_member_daily_cost`.`destination`, `mem_member_daily_cost`.`cost`,`mem_member_daily_cost`.`date` ,`mem_member_daily_cost`.`statue`, `acc_accounts`.`name` FROM `mem_member_daily_cost`,`acc_accounts` where `mem_member_daily_cost`.`account_id`=`acc_accounts`.`id` ");
        while (rs.next()) {
            data.add(new MemberDailyCost(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `mem_member_daily_cost`").getValueAt(0, 0).toString();
    }
    public static boolean updateCost(int id , String cost) throws Exception {
        PreparedStatement st = db.get.Prepare("UPDATE `mem_member_daily_cost` SET `cost`=? WHERE `id`=?");
        st.setString(1, cost); 
        st.setInt(2, id);
        st.execute();
        return true;
    }
}
