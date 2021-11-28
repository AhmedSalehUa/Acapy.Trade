/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.clients.assets;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ahmed
 */
public class Clients {

    int id;
    String name;
    String organization;
    String adress;
    String email;
    String account_num;
    String tele1;
    String tele2;

    public Clients() {
    }

    public Clients(int id, String name, String organization, String adress, String email, String account_num, String tele1, String tele2) {
        this.id = id;
        this.name = name;
        this.organization = organization;
        this.adress = adress;
        this.email = email;
        this.account_num = account_num;
        this.tele1 = tele1;
        this.tele2 = tele2;
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

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccount_num() {
        return account_num;
    }

    public void setAccount_num(String account_num) {
        this.account_num = account_num;
    }

    public String getTele1() {
        return tele1;
    }

    public void setTele1(String tele1) {
        this.tele1 = tele1;
    }

    public String getTele2() {
        return tele2;
    }

    public void setTele2(String tele2) {
        this.tele2 = tele2;
    }

    public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `cli_clients`(`id`, `name`, `organization`, `location`, `email`, `account_id`, `tele1`, `tele2`) VALUES (?,?,?,?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setString(3, organization);
        ps.setString(4, adress);
        ps.setString(5, email);
        ps.setString(6, account_num);
        ps.setString(7, tele1);
        ps.setString(8, tele2);
        ps.execute();
        System.out.println(ps.toString());
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `cli_clients` SET `name`=?,`organization`=?,`location`=?,`email`=?,`account_id`=?,`tele1`=?,`tele2`=? WHERE `id`=?");
        ps.setInt(8, id);
        ps.setString(1, name);
        ps.setString(2, organization);
        ps.setString(3, adress);
        ps.setString(4, email);
        ps.setString(5, account_num);
        ps.setString(6, tele1);
        ps.setString(7, tele2);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `cli_clients` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<Clients> getData() throws Exception {
        ObservableList<Clients> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT * FROM `cli_clients`");
        while (rs.next()) {
            data.add(new Clients(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `cli_clients`").getValueAt(0, 0).toString();
    }

}
