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
 * @author AHMED
 */
public class OperationCosts {

    int id;
    int operation_id;
    String amount;
    String payFor;
    String date;

    public OperationCosts() {
    }

    public OperationCosts(int id, int operation_id, String amount, String payFor, String date) {
        this.id = id;
        this.operation_id = operation_id;
        this.amount = amount;
        this.payFor = payFor;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOperation_id() {
        return operation_id;
    }

    public void setOperation_id(int operation_id) {
        this.operation_id = operation_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPayFor() {
        return payFor;
    }

    public void setPayFor(String payFor) {
        this.payFor = payFor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `cli_operation_costs`(`id`, `operation_id`, `amount`, `date`, `reason`) VALUES (?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setInt(2, operation_id);
        ps.setString(3, amount);
        ps.setString(4, date);
        ps.setString(5, payFor);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `cli_operation_costs` SET `operation_id`=?,`amount`=?,`date`=?,`reason`=? WHERE `id`=?");
        ps.setInt(1, operation_id);
        ps.setString(2, amount);
        ps.setString(3, date);
        ps.setString(4, payFor);
        ps.setInt(5, id);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `cli_operation_costs` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<OperationCosts> getData(int id) throws Exception {
        ObservableList<OperationCosts> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT * FROM `cli_operation_costs` WHERE `operation_id`='" + id + "'");
        while (rs.next()) {
            data.add(new OperationCosts(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `cli_operation_costs`").getValueAt(0, 0).toString();
    }
}
