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
public class ClientsAccount {

    int id;
    int clientId;
    int sourceId;
    String sourceType;
    String amount;
    String date;

    public ClientsAccount() {
    }

    public ClientsAccount(int id, int sourceId, String sourceType, String amount, String date) {
        this.id = id;
        this.sourceId = sourceId;
        this.sourceType = sourceType;
        this.amount = amount;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
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

    public static ObservableList<ClientsAccount> getData(int clientId) throws Exception {
        ObservableList<ClientsAccount> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `id`, `source_id`, `source_type`, `amount`, `date` FROM `cli_client_account` WHERE  `client_id`='" + clientId + "'");
        while (rs.next()) {
            data.add(new ClientsAccount(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5)));
        }
        return data;
    }

    public static String getTotalAcc(int id) throws Exception {
        return db.get.getTableData("SELECT SUM(amount) FROM  "
                + "  (SELECT IFNULL(SUM(CAST(`amount` as UNSIGNED)),'0') as amount FROM `cli_client_account` WHERE `client_id`='" + id + "' "
                + "      UNION ALL  "
                + "   SELECT IFNULL(0 - SUM(CAST(`amount` as UNSIGNED)),'0') as amount FROM `cli_client_pays` WHERE `client_acc_id` in (SELECT id from cli_client_account where  `client_id`='" + id + "')"
                + "   )a").getValueAt(0, 0).toString();
    }

}
