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
public class ProviderAccountsPays {

    int id;
    int provider_id;
    int provider_acc_id;
    int mem_id;
    String member;
    String amount;
    String date;
    int accId;
    String account;

    public ProviderAccountsPays() {
    }

    public ProviderAccountsPays(int id, int mem_id, String amount, String date, String account) {
        this.id = id;
        this.mem_id = mem_id;
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

    public int getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(int provider_id) {
        this.provider_id = provider_id;
    }

    public int getProvider_acc_id() {
        return provider_acc_id;
    }

    public void setProvider_acc_id(int provider_acc_id) {
        this.provider_acc_id = provider_acc_id;
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
        PreparedStatement ps = db.get.Prepare("INSERT INTO `st_provider_accounts_pays`(`id`, `provider_id`, `provider_acc_id`, `amount`, `date`, `member_id`, `account_id`) VALUES (?,?,?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setInt(2, provider_id);
        ps.setInt(3, provider_acc_id);
        ps.setString(4, amount);
        ps.setString(5, date);
        ps.setInt(6, mem_id);
        ps.setInt(7, accId);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `st_provider_accounts_pays` SET `provider_id`=?,`provider_acc_id`=?,`amount`=?,`date`=?,`member_id`=?,`account_id`=? WHERE `id`=?");
        ps.setInt(1, provider_id);
        ps.setInt(2, provider_acc_id);
        ps.setString(3, amount);
        ps.setString(4, date);
        ps.setInt(5, mem_id);
        ps.setInt(6, accId);
        ps.setInt(7, id);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `st_provider_accounts_pays` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<ProviderAccountsPays> getData(int id) throws Exception {
        ObservableList<ProviderAccountsPays> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `st_provider_accounts_pays`.`id`, `st_provider_accounts_pays`.`member_id`,  `st_provider_accounts_pays`.`amount`, `st_provider_accounts_pays`.`date`, acc_accounts.name FROM `st_provider_accounts_pays`,acc_accounts WHERE  `st_provider_accounts_pays`.`account_id` = acc_accounts.id and `provider_acc_id` = '"+id+"'");
        while (rs.next()) {
            data.add(new ProviderAccountsPays(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `st_provider_accounts_pays`").getValueAt(0, 0).toString();
    }
}
