/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.clients.assets;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
        /**
 *
 * @author amran
 */
public class Contracts {
    int id;
    int cli_id;
    String name;
    String date_from;
    String date_to;
    String noVisits;
    String cost;
    String due_after;

    public Contracts() {
    }

    public Contracts(int id, String name, String date_from, String date_to, String noVisits, String cost, String due_after) {
        this.id = id;
        this.name = name;
        this.date_from = date_from;
        this.date_to = date_to;
        this.noVisits = noVisits;
        this.cost = cost;
        this.due_after = due_after;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCli_id() {
        return cli_id;
    }

    public void setCli_id(int cli_id) {
        this.cli_id = cli_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate_from() {
        return date_from;
    }

    public void setDate_from(String date_from) {
        this.date_from = date_from;
    }

    public String getDate_to() {
        return date_to;
    }

    public void setDate_to(String date_to) {
        this.date_to = date_to;
    }

    public String getNoVisits() {
        return noVisits;
    }

    public void setNoVisits(String noVisits) {
        this.noVisits = noVisits;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDue_after() {
        return due_after;
    }

    public void setDue_after(String due_after) {
        this.due_after = due_after;
    }
     public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `cli_contracts`(`id`, `client_id`, `date_from`, `date_to`, `num_of_visits`, `cost`, `due_after`) VALUES (?,?,?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setInt(2, cli_id);
        ps.setString(3, date_from);
        ps.setString(4,date_to);
        ps.setString(5, noVisits);
        ps.setString(6, cost);
        ps.setString(7, due_after);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `cli_contracts` SET `client_id`=?,`date_from`=?,`date_to`=?,`num_of_visits`=?,`cost`=?,`due_after`=? WHERE `id`=?");
        ps.setInt(1, cli_id);
        ps.setString(2, date_from);
        ps.setString(3, date_to);
        ps.setString(4, noVisits);
        ps.setString(5, cost);
        ps.setString(6, due_after);
        ps.setInt(7, id);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `cli_contracts` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<Contracts> getData() throws Exception {
        ObservableList<Contracts> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT cli_contracts.`id`,cli_clients.`name`, cli_contracts.`date_from`, cli_contracts.`date_to`, cli_contracts.`num_of_visits`, cli_contracts.`cost`, cli_contracts.`due_after` FROM `cli_contracts`,cli_clients WHERE cli_clients.id=cli_contracts.client_id");
        while (rs.next()) {
            data.add(new Contracts(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),rs.getString(7)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `cli_contracts`").getValueAt(0, 0).toString();
    }
}
