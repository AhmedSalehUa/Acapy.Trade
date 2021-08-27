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
public class ClientsAccountPays {

    int id;
    int client_acc_id;
    int mem_id;
    String member;
    String amount;
    String date;
    String payType;
    int accId;
    String account;

    public ClientsAccountPays(int id, int client_acc_id, int member, String amount, String date, String payType, String account) {
        this.id = id;
        this.client_acc_id = client_acc_id;
        this.mem_id = member;
        this.amount = amount;
        this.date = date;
        this.payType = payType;
        this.account = account;
    }

    public ClientsAccountPays() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClient_acc_id() {
        return client_acc_id;
    }

    public void setClient_acc_id(int client_acc_id) {
        this.client_acc_id = client_acc_id;
    }

    public int getMem_id() {
        return mem_id;
    }

    public void setMem_id(int mem_id) {
        this.mem_id = mem_id;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
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

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public int getAccId() {
        return accId;
    }

    public void setAccId(int accId) {
        this.accId = accId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `cli_client_pays`(`id`, `client_acc_id`, `member_id`, `amount`, `date_of_doc`, `date_of_cash`, `pay_type`, `acc_id`) VALUES (?,?,?,?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setInt(2, client_acc_id);
        ps.setInt(3, mem_id);
        ps.setString(4, amount);
        ps.setString(5, date);
        ps.setString(6, date);
        ps.setString(7, payType);
        ps.setInt(8, accId);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `cli_client_pays` SET `client_acc_id`=?,`member_id`=?,`amount`=?,`date_of_doc`=?,`date_of_cash`=?,`pay_type`=?,`acc_id`=? WHERE `id`=?");
        ps.setInt(1, client_acc_id);
        ps.setInt(2, mem_id);
        ps.setString(3, amount);
        ps.setString(4, date);
        ps.setString(5, date);
        ps.setString(6, payType);
        ps.setInt(7, accId);
        ps.setInt(8, id);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `cli_client_pays` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<ClientsAccountPays> getData(int id) throws Exception {
        ObservableList<ClientsAccountPays> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `cli_client_pays`.`id`, `cli_client_pays`.`client_acc_id`, `cli_client_pays`.`member_id`, `cli_client_pays`.`amount`, `cli_client_pays`.`date_of_doc` , `cli_client_pays`.`pay_type`, `acc_accounts`.`name` FROM `cli_client_pays`,`acc_accounts` WHERE `acc_accounts`.`id` = `cli_client_pays`.`acc_id` and `cli_client_pays`.`client_acc_id`='"+id+"'");
        while (rs.next()) {
            data.add(new ClientsAccountPays(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `cli_client_pays`").getValueAt(0, 0).toString();
    }
}
