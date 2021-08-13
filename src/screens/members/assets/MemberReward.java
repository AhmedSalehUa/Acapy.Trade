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
public class MemberReward {
    int id;
    String member;
    int member_id;
    int operation_id;
    String operation;
    String amount;
    String date;
    String account;
    int account_id;

    public MemberReward() {
    }

    public MemberReward(int id, String operation, String amount, String date, String account) {
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

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
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
        PreparedStatement ps = db.get.Prepare("INSERT INTO `mem_member_reward`(`id`, `member_id`,`operation_id` , `amount`, `date`,`account_id`) VALUES (?,?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setInt(2, member_id);
        ps.setInt(3, operation_id);
        ps.setString(4, amount);
        ps.setString(5, date);
          ps.setInt(6, account_id);
        ps.execute();
        return true;
    }
     public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `mem_member_reward` SET `member_id`=? ,`operation_id`=? ,`amount`=? , `date`=? ,`account_id`=? WHERE `id`=?");
        ps.setInt(1, member_id);
         ps.setInt(2, operation_id);
        ps.setString(3, amount);
        ps.setString(4, date);
        ps.setInt(5, account_id);
           ps.setInt(6, id);
        ps.execute();
        return true;
    }
      public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `mem_member_reward` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }
      public static ObservableList<MemberReward> getData(int id) throws Exception {
        ObservableList<MemberReward> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `mem_member_reward`.`id`,`cli_clients`.`name`,`mem_member_reward`.`amount`,`mem_member_reward`.`date` ,`acc_accounts`.`name` FROM `mem_member_reward`,`acc_accounts`,`cli_clients`,`cli_operation` where `mem_member_reward`.`account_id`=`acc_accounts`.`id` AND `cli_operation`.`id`= `mem_member_reward`.`operation_id` AND `cli_clients`.`id`= `cli_operation`.`client_id` AND `mem_member_reward`.`member_id`='"+id+"'");
        while (rs.next()) {
            data.add(new MemberReward(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `mem_member_reward`").getValueAt(0, 0).toString();
    }
}
