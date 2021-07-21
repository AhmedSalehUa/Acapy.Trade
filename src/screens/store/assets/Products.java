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
public class Products {

    int id;
    int cat_id;
    String category;
    String name;
    String model;
    String details;

    public Products() {
    }

    public Products(int id, String category, String name, String model, String details) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.model = model;
        this.details = details;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `st_products`(`id`, `cat_id`, `name`, `model`, `details`) VALUES (?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setInt(2, cat_id);
        ps.setString(3, name);
        ps.setString(4, model);
        ps.setString(5, details);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `st_products` SET `cat_id`=?,`name`=?,`model`=?,`details`=? WHERE `id`=?");
        ps.setInt(1, cat_id);
        ps.setString(2, name);
        ps.setString(3, model);
        ps.setString(4, details);
        ps.setInt(5, id);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `st_products` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<Products> getData() throws Exception {
        ObservableList<Products> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `st_products`.`id`,`st_products_category`.`name` , `st_products`.`name`, `st_products`.`model`, `st_products`.`details` FROM `st_products`,`st_products_category` WHERE `st_products`.`cat_id` =`st_products_category`.`id`");
        while (rs.next()) {
            data.add(new Products(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `st_products`").getValueAt(0, 0).toString();
    }
}
