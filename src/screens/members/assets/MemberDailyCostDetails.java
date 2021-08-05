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
import screens.clients.assets.MaintainceDetails;

/**
 *
 * @author Ahmed Al-Gazzar
 */
public class MemberDailyCostDetails {
    int id;
    int dailycost_id;
    String dailycost;
    String product;
    int product_id;

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
    String cost;

    public MemberDailyCostDetails() {
    }

    public MemberDailyCostDetails(int id, String product, String cost) {
        this.id = id;
        this.product = product;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDailycost_id() {
        return dailycost_id;
    }

    public void setDailycost_id(int dailycost_id) {
        this.dailycost_id = dailycost_id;
    }

    public String getDailycost() {
        return dailycost;
    }

    public void setDailycost(String dailycost) {
        this.dailycost = dailycost;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
       public boolean Add() throws Exception {
        PreparedStatement st = db.get.Prepare("INSERT INTO `mem_member_daily_cost_details`(`id`, `daily_cost_id`,`product_id`, `cost`) VALUES (?,?,?,?)");
        st.setInt(1, id);
        st.setInt(2, dailycost_id);
        st.setInt(3, product_id);
        st.setString(4, cost);
        

        st.execute();
        return true;
    }
      public boolean Edit() throws Exception {
        PreparedStatement st = db.get.Prepare("UPDATE `mem_member_daily_cost_details` SET `daily_cost_id`=?,`product_id`=? ,`cost`=?  WHERE `id`=?");

        st.setInt(1, dailycost_id);
        st.setInt(2, product_id);
        
        st.setString(3, cost);
      
        st.setInt(4, id);
        st.execute();
        return true;
    }
        public boolean Delete() throws Exception {
        PreparedStatement st = db.get.Prepare("DELETE FROM `mem_member_daily_cost_details` WHERE `id`=?");
        st.setInt(1, id);
        st.execute();
        return true;
    }

    public static ObservableList<MemberDailyCostDetails> getData(int id) throws Exception {

        ObservableList<MemberDailyCostDetails> data = FXCollections.observableArrayList();

        String SQL = "SELECT `mem_member_daily_cost_details`.`id`,`st_products`.`name`,`mem_member_daily_cost_details`.`cost` FROM `st_products`,`mem_member_daily_cost_details` where `mem_member_daily_cost_details`.`product_id`=`st_products`.`id` AND `mem_member_daily_cost_details`.`daily_cost_id`='"+id+"'";
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery(SQL);

        while (rs.next()) {
            data.add(new MemberDailyCostDetails(rs.getInt(1), rs.getString(2), rs.getString(3)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(max(`id`) + 1,'1') FROM `mem_member_daily_cost_details`").getValueAt(0, 0).toString();
    }
}
