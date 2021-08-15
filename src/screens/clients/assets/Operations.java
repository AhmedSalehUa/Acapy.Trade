package screens.clients.assets;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javax.swing.JTable;

public class Operations {

    int id;
    int client_id;
    String client_name;
    int sales_id;
    String sales_name;
    String date;
    int total_cost;
    String pay_type;
    InputStream doc;
    String doc_ext;

    public Operations() {
    }

    public Operations(int id, String client_name, String sales_name, String date, int total_cost, String pay_type) {
        this.id = id;
        this.client_name = client_name;
        this.sales_name = sales_name;
        this.date = date;
        this.total_cost = total_cost;
        this.pay_type = pay_type;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public int getSales_id() {
        return sales_id;
    }

    public void setSales_id(int sales_id) {
        this.sales_id = sales_id;
    }

    public String getSales_name() {
        return sales_name;
    }

    public void setSales_name(String sales_name) {
        this.sales_name = sales_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(int total_cost) {
        this.total_cost = total_cost;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public InputStream getDoc() {
        return doc;
    }

    public void setDoc(InputStream doc) {
        this.doc = doc;
    }

    public String getDoc_ext() {
        return doc_ext;
    }

    public void setDoc_ext(String doc_ext) {
        this.doc_ext = doc_ext;
    }

    public boolean Add() throws Exception {
        PreparedStatement st = db.get.Prepare("INSERT INTO `cli_operation`(`id`, `client_id`,`sales_id`, `date` ,`total_cost`, `pay_type`,`doc`,`doc_ext`) VALUES (?,?,?,?,?,?,?,?)");
        st.setInt(1, id);
        st.setInt(2, client_id);
        st.setInt(3, sales_id);
        st.setString(4, date);
        st.setInt(5, total_cost);
        st.setString(6, pay_type);
        st.setBlob(7, doc);
        st.setString(8, doc_ext);

        st.execute();
        return true;
    }

    public boolean AddWithouPhoto() throws Exception {
        PreparedStatement st = db.get.Prepare("INSERT INTO `cli_operation`(`id`, `client_id`,`sales_id`, `date` ,`total_cost`, `pay_type`) VALUES (?,?,?,?,?,?)");
        st.setInt(1, id);
        st.setInt(2, client_id);
        st.setInt(3, sales_id);
        st.setString(4, date);
        st.setInt(5, total_cost);
        st.setString(6, pay_type);

        st.execute();
        return true;
    }

    public boolean Edit() throws Exception {
        PreparedStatement st = db.get.Prepare("UPDATE `cli_operation` SET `client_id`=?,`sales_id`=? ,`date`=? ,`total_cost`=?,`pay_type`=?,`doc`=?, `doc_ext`=? WHERE `id`=?");

        st.setInt(1, client_id);
        st.setInt(2, sales_id);
        st.setString(3, date);
        st.setInt(4, total_cost);
        st.setString(5, pay_type);
        st.setBlob(6, doc);
        st.setString(7, doc_ext);
        st.setInt(8, id);
        st.execute();
        return true;
    }

    public boolean EditeWithouPhoto() throws Exception {
        PreparedStatement st = db.get.Prepare("UPDATE `cli_operation` SET `client_id`=?,`sales_id`=? ,`date`=? ,`total_cost`=?,`pay_type`=? WHERE `id`=?");

        st.setInt(1, client_id);
        st.setInt(2, sales_id);
        st.setString(3, date);
        st.setInt(4, total_cost);
        st.setString(5, pay_type);
        st.setInt(6, id);
        st.execute();
        return true;
    }

    public static boolean updateCost(int id , String cost) throws Exception {
        PreparedStatement st = db.get.Prepare("UPDATE `cli_operation` SET `total_cost`=? WHERE `id`=?");
 
        st.setString(1, cost); 
        st.setInt(2, id);
        st.execute();
        return true;
    }

     

    public boolean Delete() throws Exception {
        PreparedStatement st = db.get.Prepare("DELETE FROM `cli_operation` WHERE `id`=?");
        st.setInt(1, id);
        st.execute();
        return true;
    }

    public static ObservableList<Operations> getData() throws Exception {

        ObservableList<Operations> data = FXCollections.observableArrayList();

        String SQL = "SELECT `cli_operation`.`id`,`cli_clients`.`organization`, `sl_sales_members`.`name`,`cli_operation`.`date` ,`cli_operation`.`total_cost`, `cli_operation`.`pay_type` FROM `cli_operation`,`cli_clients`,`sl_sales_members`where `cli_operation`.`client_id`=`cli_clients`.`id`AND`cli_operation`.`sales_id`= `sl_sales_members`.`id`";
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery(SQL);

        while (rs.next()) {
            data.add(new Operations(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(max(`id`) + 1,'1') FROM `cli_operation`").getValueAt(0, 0).toString();
    }

    public void getDocdown() throws Exception {

        File file = null;
        String selectSQL = "SELECT `doc` FROM `cli_operation` WHERE `id`='" + id + "'";
        JTable tab = db.get.getTableData("SELECT `doc_ext` FROM `cli_operation` WHERE `id`='" + id + "'");
        if (tab.getRowCount() <= 0 || tab.getValueAt(0, 0) == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("لا يوجد صورة متوفرة");
            alert.show();
        } else {
            String ext = tab.getValueAt(0, 0).toString();
            ResultSet rs = null;

            try {
                PreparedStatement pstmt = db.get.Prepare(selectSQL);

                rs = pstmt.executeQuery();

                File directory = new File(System.getProperty("user.home") + "\\Desktop\\Acapy Trade\\documentation");
                directory.mkdirs();
                String dateFromSql = db.get.getTableData("SELECT  `date` FROM `cli_operation` WHERE `id`='" + id + "'").getValueAt(0, 0).toString();

                file = new File(directory + "\\" + id + "-" + dateFromSql + "." + ext);

                FileOutputStream output = new FileOutputStream(file);

                String payPath = file.getAbsolutePath();
                while (rs.next()) {
                    InputStream input = rs.getBinaryStream("doc");
                    byte[] buffer = new byte[1024];
                    while (input.read(buffer) > 0) {
                        output.write(buffer);
                    }
                }
                Desktop d = Desktop.getDesktop();
                d.open(file);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
