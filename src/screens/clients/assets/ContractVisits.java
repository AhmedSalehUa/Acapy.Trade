/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.clients.assets;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.InputStream;
import javafx.scene.control.Alert;
import javax.swing.JTable;
import screens.clients.ClientScreenContractVisitsController;

/**
 *
 * @author amran
 */
public class ContractVisits {
    int id;
    int contractID;
    int memID;
    String memName;
    String date;
    String report;
    InputStream doc;
    String doc_ext; 
    
    public ContractVisits() {
    }

    public ContractVisits(int id, String memName, String date, String report) {
        this.id=id;
        this.memName = memName;
        this.date = date;
        this.report = report;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id; 
    }

    public int getContractID() {
        return contractID;
    }

    public void setContractID(int contractID) {
        this.contractID = contractID;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
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
        PreparedStatement ps = db.get.Prepare("INSERT INTO `cli_contract_visits`(`id`, `contract_id`, `member_id`, `date`, `report`, `doc`, `doc_ext`) VALUES (?,?,?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setInt(2, contractID);
        ps.setInt(3, memID);
        ps.setString(4, date);
        ps.setString(5,report);
        ps.setBlob(6, doc);
        ps.setString(7, doc_ext);
        ps.execute();
        return true;
    }
     
     public boolean AddWithouPhoto() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `cli_contract_visits`(`id`, `contract_id`, `member_id`, `date`, `report`) VALUES (?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setInt(2, contractID);
        ps.setInt(3, memID);
        ps.setString(4, date);
        ps.setString(5,report);
        ps.execute();

        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `cli_contract_visits` SET `contract_id`=?,`member_id`=?,`date`=?,`report`=?,`doc`=?,`doc_ext`=? WHERE `id`=?");
        ps.setInt(1, contractID);
        ps.setInt(2, memID);
        ps.setString(3, date);
        ps.setString(4,report);
        ps.setBlob(5, doc);
        ps.setString(6, doc_ext);
        ps.setInt(7, id);
        ps.execute();
        return true;
    }
    
    public boolean EditeWithouPhoto() throws Exception {
         PreparedStatement ps = db.get.Prepare("UPDATE `cli_contract_visits` SET `contract_id`=?,`member_id`=?,`date`=?,`report`=? WHERE `id`=?");
        ps.setInt(1, contractID);
        ps.setInt(2, memID);
        ps.setString(3, date);
        ps.setString(4,report);
        ps.setInt(5, id);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `cli_contract_visits` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<ContractVisits> getData(int id) throws Exception {
        ObservableList<ContractVisits> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `cli_contract_visits`.`id`,mem_acapy_members.`name`, cli_contract_visits.`date`, cli_contract_visits.`report` FROM `mem_acapy_members`,`cli_contract_visits` WHERE cli_contract_visits.`id`='"+id+"' AND`cli_contract_visits`.`member_id`= `mem_acapy_members`.`id`");
        while (rs.next()) {
            data.add(new ContractVisits(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `cli_contract_visits`").getValueAt(0, 0).toString();
    }
    public void getDocdown() throws Exception {

        File file = null;
        String selectSQL = "SELECT `doc` FROM `cli_contract_visits` WHERE `id`='" + id + "'";
        JTable tab = db.get.getTableData("SELECT `doc_ext` FROM `cli_contract_visits` WHERE `id`='" + id + "'");
         if (tab.getRowCount() <= 0 || tab.getValueAt(0, 0)==null) {
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
                String dateFromSql = db.get.getTableData("SELECT  `date` FROM `cli_contract_visits` WHERE `id`='" + id + "'").getValueAt(0, 0).toString();

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
