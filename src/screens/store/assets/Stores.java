/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.store.assets;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author AHMED
 */
public class Stores {

    int id;
    String name;

    public Stores() {
    }

    public Stores(int id, String name) {
        this.id = id;
        this.name = name;
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

    public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `st_stores`(`id`, `name`) VALUES (?,?)");
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `st_stores` SET `name`=? WHERE `id`=?");
        ps.setInt(2, id);
        ps.setString(1, name);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `st_stores` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<Stores> getData() throws Exception {
        ObservableList<Stores> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT * FROM `st_stores`");
        while (rs.next()) {
            data.add(new Stores(rs.getInt(1), rs.getString(2)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `st_stores`").getValueAt(0, 0).toString();
    }
}
