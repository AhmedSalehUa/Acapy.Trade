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

/**
 *
 * @author ahmed
 */
public class Products {

    int id;
    String name;
    String barcode;
    String warnNum;
    int cat;
    String category;
    int unitId;
    String unit;

    public Products() {
    }

    public Products(int id, String name, String barcode, String warnNum, String category, String unit) {
        this.id = id;
        this.name = name;
        this.barcode = barcode;
        this.warnNum = warnNum;
        this.category = category;
        this.unit = unit;
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

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getWarnNum() {
        return warnNum;
    }

    public void setWarnNum(String warnNum) {
        this.warnNum = warnNum;
    }

    public int getCat() {
        return cat;
    }

    public void setCat(int cat) {
        this.cat = cat;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `md_products`(`id`, `cat_id`, `unit_id`, `name`, `barcode`, `warn_num`) VALUES (?,?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setInt(2, cat);
        ps.setInt(3, unitId);
        ps.setString(4, name);
        ps.setString(5, barcode);
        ps.setString(6, warnNum);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `md_products` SET `cat_id`=?,`unit_id`=?,`name`=?,`barcode`=?,`warn_num`=? WHERE `id`=?");
        ps.setInt(6, id);
        ps.setInt(1, cat);
        ps.setInt(2, unitId);
        ps.setString(3, name);
        ps.setString(4, barcode);
        ps.setString(5, warnNum);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `md_products` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<Products> getData() throws Exception {
        ObservableList<Products> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `md_products`.`id`, `md_products`.`name`, `md_products`.`barcode`, `md_products`.`warn_num`,`md_products_cat`.`name`,`md_products_units`.`name` FROM `md_products`,`md_products_units`,`md_products_cat` WHERE  `md_products`.`cat_id`=`md_products_cat`.`id` and  `md_products`.`unit_id` =`md_products_units`.`id`");
        while (rs.next()) {
            data.add(new Products(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `md_products`").getValueAt(0, 0).toString();
    }

}
