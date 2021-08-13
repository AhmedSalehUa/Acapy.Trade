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
public class MemberTransactionDetails {
    int id;
    int transaction_id;
    String placefrom;
    String placeto;
    String amount;

    public MemberTransactionDetails() {
    }

    public MemberTransactionDetails(int id, String placefrom, String placeto, String amount) {
        this.id = id;
        this.placefrom = placefrom;
        this.placeto = placeto;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getPlacefrom() {
        return placefrom;
    }

    public void setPlacefrom(String placefrom) {
        this.placefrom = placefrom;
    }

    public String getPlaceto() {
        return placeto;
    }

    public void setPlaceto(String placeto) {
        this.placeto = placeto;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
     public boolean Add() throws Exception {
        PreparedStatement st = db.get.Prepare("INSERT INTO `mem_member_transactions_details`(`id`, `transaction_id`,`place_from`, `place_to`,`amount`) VALUES (?,?,?,?,?)");
        st.setInt(1, id);
        st.setInt(2, transaction_id);
        st.setString(3, placefrom);
          st.setString(4, placeto);
        st.setString(5, amount);
        

        st.execute();
        return true;
    }
      public boolean Edit() throws Exception {
        PreparedStatement st = db.get.Prepare("UPDATE `mem_member_transactions_details` SET `transaction_id`=?,`place_from`=? ,`place_to`=? ,`amount`=?  WHERE `id`=?");

        st.setInt(1, transaction_id);
        st.setString(2, placefrom);
        st.setString(3, placeto);
        st.setString(4, amount);
        st.setInt(5, id);
        st.execute();
        return true;
    }
        public boolean Delete() throws Exception {
        PreparedStatement st = db.get.Prepare("DELETE FROM `mem_member_transactions_details` WHERE `id`=?");
        st.setInt(1, id);
        st.execute();
        return true;
    }

    public static ObservableList<MemberTransactionDetails> getData(int id) throws Exception {

        ObservableList<MemberTransactionDetails> data = FXCollections.observableArrayList();

        String SQL = "SELECT `mem_member_transactions_details`.`id`,`mem_member_transactions_details`.`place_from`,`mem_member_transactions_details`.`place_to`,`mem_member_transactions_details`.`amount` FROM `mem_member_transactions_details` where  `mem_member_transactions_details`.`transaction_id`='"+id+"'";
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery(SQL);

        while (rs.next()) {
            data.add(new MemberTransactionDetails(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(max(`id`) + 1,'1') FROM `mem_member_transactions_details`").getValueAt(0, 0).toString();
    }
    
}
