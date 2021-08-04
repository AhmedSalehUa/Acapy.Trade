package screens.clients.assets;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Maintaince {

    int id;
    String client_name;
    String member_name;
    String date;
    String cost;
    String problem;
    String pay_type;
    int client_id;
    int member_id;

    public Maintaince() {
    }

    public Maintaince(int id, String client_name, String member_name, String date, String problem, String cost, String pay_type) {
        this.id = id;
        this.client_name = client_name;
        this.member_name = member_name;
        this.date = date;
        this.cost = cost;
        this.problem = problem;
        this.pay_type = pay_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public boolean Add() throws Exception {
        PreparedStatement st = db.get.Prepare("INSERT INTO `cli_maintaince`(`id`, `client_id`,`member_id`, `date` ,`problem`, `cost`,`pay_type`) VALUES (?,?,?,?,?,?,?)");
        st.setInt(1, id);
        st.setInt(2, client_id);
        st.setInt(3, member_id);
        st.setString(4, date);
        st.setString(5, problem);
        st.setString(6, cost);
        st.setString(7, pay_type);

        st.execute();
        return true;
    }

    public boolean Edit() throws Exception {
        PreparedStatement st = db.get.Prepare("UPDATE `cli_maintaince` SET `client_id`=?,`member_id`=? ,`date`=? ,`problem`=?,`cost`=?,`pay_type`=? WHERE `id`=?");
        st.setInt(1, client_id);
        st.setInt(2, member_id);
        st.setString(3, date);
        st.setString(4, problem);
        st.setString(5, cost);
        st.setString(6, pay_type);
        st.setInt(7, id);
        st.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement st = db.get.Prepare("DELETE FROM `cli_maintaince` WHERE `id`=?");
        st.setInt(1, id);
        st.execute();
        return true;
    }

    public static ObservableList<Maintaince> getData() throws Exception {

        ObservableList<Maintaince> data = FXCollections.observableArrayList();

        String SQL = "SELECT `cli_maintaince`.`id`,`cli_clients`.`name`, `mem_acapy_members`.`name`,`cli_maintaince`.`date` ,`cli_maintaince`.`problem`, `cli_maintaince`.`cost`,`cli_maintaince`.`pay_type` FROM `cli_maintaince`,`cli_clients`,`mem_acapy_members`where `cli_maintaince`.`client_id`=`cli_clients`.`id`AND`cli_maintaince`.`member_id`= `mem_acapy_members`.`id`";
         ResultSet rs = db.get.getReportCon().createStatement().executeQuery(SQL);
        while (rs.next()) {
            data.add(new Maintaince(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(max(`id`) + 1,'1') FROM `cli_maintaince`").getValueAt(0, 0).toString();
    }
    public static boolean updateCost(int id , String cost) throws Exception {
        PreparedStatement st = db.get.Prepare("UPDATE `cli_maintaince` SET `cost`=? WHERE `id`=?");
        st.setString(1, cost); 
        st.setInt(2, id);
        st.execute();
        return true;
    }
}
