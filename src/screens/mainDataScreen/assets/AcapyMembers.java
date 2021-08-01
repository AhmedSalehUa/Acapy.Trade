/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.mainDataScreen.assets;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import screens.sales.assets.SalesMembers;

/**
 *
 * @author Ahmed Al-Gazzar
 */
public class AcapyMembers {
    int id;
    String name;
    String acc_num;
    String app_token;

    public AcapyMembers() {
    }

    public AcapyMembers(int id, String name, String acc_num, String app_token) {
        this.id = id;
        this.name = name;
        this.acc_num = acc_num;
        this.app_token = app_token;
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

    public String getAcc_num() {
        return acc_num;
    }

    public void setAcc_num(String acc_num) {
        this.acc_num = acc_num;
    }

    public String getApp_token() {
        return app_token;
    }

    public void setApp_token(String app_token) {
        this.app_token = app_token;
    }
    public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `mem_acapy_members`(`id`, `name`, `acc_num`, `app_token`) VALUES (?,?,?,?)");
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setString(3, acc_num);
        ps.setString(4, app_token);
        ps.execute();
        return true;
    }
     public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `mem_acapy_members` SET `name`=?,`acc_num`=?,`app_token`=? WHERE `id`=?");
        ps.setString(1, name);
        ps.setString(2, acc_num);
        ps.setString(3, app_token);
        ps.setInt(4, id);
        ps.execute();
        return true;
    }
      public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `mem_acapy_members` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }
      public static ObservableList<AcapyMembers> getData() throws Exception {
        ObservableList<AcapyMembers> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT * FROM `mem_acapy_members` ");
        while (rs.next()) {
            data.add(new AcapyMembers(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `mem_acapy_members`").getValueAt(0, 0).toString();
    }
}
