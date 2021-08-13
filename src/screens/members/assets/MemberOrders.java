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
import screens.clients.assets.Contracts;

/**
 *
 * @author amran
 */
public class MemberOrders {
    int orderID;
    int memID;
    String memName;
    String place;
    String loc;
    String visitType;
    String amount;
    String date;
    String matters;
    String notes;

    public MemberOrders() {
    }

    public MemberOrders(int orderID, String memName, String place, String loc, String visitType, String amount, String date, String matters, String notes) {
        this.orderID = orderID;
        this.memName = memName;
        this.place = place;
        this.loc = loc;
        this.visitType = visitType;
        this.amount = amount;
        this.date = date;
        this.matters = matters;
        this.notes = notes;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getMemID() {
        return memID;
    }

    public void setMemID(int memID) {
        this.memID = memID;
    }

    public String getMemName() {
        return memName;
    }

    public void setMemName(String memName) {
        this.memName = memName;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getVisitType() {
        return visitType;
    }

    public void setVisitType(String visitType) {
        this.visitType = visitType;
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

    public String getMatters() {
        return matters;
    }

    public void setMatters(String matters) {
        this.matters = matters;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `mem_member_orders`(`id`, `member_id`, `place`, `location`, `visit_type`, `amount_to_collect`, `date`, `matters`, `note`) VALUES (?,?,?,?,?,?)");
        ps.setInt(1, orderID);
        ps.setInt(2, memID);
        ps.setString(3, place);
        ps.setString(4,loc);
        ps.setString(5, visitType);
        ps.setString(6, amount);
        ps.setString(7, date);
        ps.setString(8, matters);
        ps.setString(9, notes);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `mem_member_orders` SET `member_id`=?,`place`=?,`location`=?,`visit_type`=?,`amount_to_collect`=?,`date`=?,`matters`=?,`note`=? WHERE `id`=?");
        ps.setInt(1, memID);
        ps.setString(2, place);
        ps.setString(3,loc);
        ps.setString(4, visitType);
        ps.setString(5, amount);
        ps.setString(6, date);
        ps.setString(7, matters);
        ps.setString(8, notes);
        ps.setInt(9, orderID);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `mem_member_orders` WHERE `id`=?");
        ps.setInt(1, orderID);
        ps.execute();
        return true;
    }

    public static ObservableList<MemberOrders> getData() throws Exception {
        ObservableList<MemberOrders> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `mem_member_orders`.`id`, `mem_acapy_members`.`name`, `mem_member_orders`.`place`, `mem_member_orders`.`location`, `mem_member_orders`.`visit_type`, `mem_member_orders`.`amount_to_collect`, `mem_member_orders`.`date`, `mem_member_orders`.`matters`, `mem_member_orders`.`note` FROM `mem_member_orders`,`mem_acapy_members` WHERE `mem_acapy_members`.`id`=`mem_member_orders`.`id`");
        while (rs.next()) {
            data.add(new MemberOrders(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),rs.getString(7), rs.getString(8),rs.getString(9)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `cli_contracts`").getValueAt(0, 0).toString();
    }
}
