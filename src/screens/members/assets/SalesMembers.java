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
public class SalesMembers {
      int id;
    String name;
    String num_of_success;
    String app_token;

    public SalesMembers() {
    }

    public SalesMembers(int id, String name, String num_of_success, String app_token) {
        this.id = id;
        this.name = name;
        this.num_of_success = num_of_success;
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

    public String getNum_of_success() {
        return num_of_success;
    }

    public void setNum_of_success(String num_of_success) {
        this.num_of_success = num_of_success;
    }

    public String getApp_token() {
        return app_token;
    }

    public void setApp_token(String app_token) {
        this.app_token = app_token;
    }

  
    public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `sl_sales_members`(`id`, `name`, `num_of_success`, `app_token`) VALUES (?,?,?,?)");
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setString(3, num_of_success);
        ps.setString(4, app_token);
        ps.execute();
        return true;
    }
     public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `sl_sales_members` SET `name`=?,`num_of_success`=?,`app_token`=? WHERE `id`=?");
        ps.setString(1, name);
        ps.setString(2, num_of_success);
        ps.setString(3, app_token);
        ps.setInt(4, id);
        ps.execute();
        return true;
    }
      public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `sl_sales_members` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }
      public static ObservableList<SalesMembers> getData() throws Exception {
        ObservableList<SalesMembers> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT * FROM `sl_sales_members` ");
        while (rs.next()) {
            data.add(new SalesMembers(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `sl_sales_members`").getValueAt(0, 0).toString();
    }
}
