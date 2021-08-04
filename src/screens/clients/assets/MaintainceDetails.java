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


public class MaintainceDetails {
    int id;
    int maintaince_id;
    int product_id;
    String maintaince;
    String product;
    String amount;
    String cost;
    String totalcost;

    public MaintainceDetails() {
    }

    public MaintainceDetails(int id, String product, String amount, String cost, String totalcost) {
        this.id = id;
        this.product = product;
        this.amount = amount;
        this.cost = cost;
        this.totalcost = totalcost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaintaince_id() {
        return maintaince_id;
    }

    public void setMaintaince_id(int maintaince_id) {
        this.maintaince_id = maintaince_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getMaintaince() {
        return maintaince;
    }

    public void setMaintaince(String maintaince) {
        this.maintaince = maintaince;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getTotalcost() {
        return totalcost;
    }

    public void setTotalcost(String totalcost) {
        this.totalcost = totalcost;
    }
     public boolean Add() throws Exception {
        PreparedStatement st = db.get.Prepare("INSERT INTO `cli_maintaince_details`(`id`, `maintaince_id`,`product_id`, `amount`,`cost`, `total_cost`) VALUES (?,?,?,?,?,?)");
        st.setInt(1, id);
        st.setInt(2, maintaince_id);
        st.setInt(3, product_id);
       
        st.setString(4, amount);
         st.setString(5, cost);
        st.setString(6, totalcost);

        st.execute();
        return true;
    }

    public boolean Edit() throws Exception {
        PreparedStatement st = db.get.Prepare("UPDATE `cli_maintaince_details` SET `maintaince_id`=?,`product_id`=? ,`amount`=? ,`cost`=?,`total_cost`=? WHERE `id`=?");

        st.setInt(1, maintaince_id);
        st.setInt(2, product_id);
        
        st.setString(3, amount);
        st.setString(4, cost);
        st.setString(5, totalcost);
        st.setInt(6, id);
        st.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement st = db.get.Prepare("DELETE FROM `cli_maintaince_details` WHERE `id`=?");
        st.setInt(1, id);
        st.execute();
        return true;
    }

    public static ObservableList<MaintainceDetails> getData(int id) throws Exception {

        ObservableList<MaintainceDetails> data = FXCollections.observableArrayList();

        String SQL = "SELECT `cli_maintaince_details`.`id`,`st_products`.`name`,`cli_maintaince_details`.`amount`,`cli_maintaince_details`.`cost` , `cli_maintaince_details`.`total_cost` FROM `st_products`,`cli_maintaince_details` where `cli_maintaince_details`.`product_id`=`st_products`.`id` AND `cli_maintaince_details`.`maintaince_id`='"+id+"'";
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery(SQL);

        while (rs.next()) {
            data.add(new MaintainceDetails(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(max(`id`) + 1,'1') FROM `cli_maintaince_details`").getValueAt(0, 0).toString();
    }
     
}
