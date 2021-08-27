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
public class YieldsCategory {

    int id;
    String name;

    public YieldsCategory() {
    }

    public YieldsCategory(int id, String name) {
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

    public static boolean Add(String cat) throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `acc_yields_category`(`name`) VALUES (?)");
        ps.setString(1, cat); 
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `acc_yields_category` SET `name`=? WHERE `id`=?");
        ps.setString(1, name); 
        ps.setInt(2, id);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `acc_yields_category` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<YieldsCategory> getData() throws Exception {
        ObservableList<YieldsCategory> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT * FROM `acc_yields_category`");
        while (rs.next()) {
            data.add(new YieldsCategory(rs.getInt(1),rs.getString(2) ));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `acc_yields_category`").getValueAt(0, 0).toString();
    }
}
