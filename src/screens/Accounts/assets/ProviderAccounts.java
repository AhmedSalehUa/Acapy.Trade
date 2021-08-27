/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.Accounts.assets;

import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author AHMED
 */
public class ProviderAccounts {
    int id;
    int providerID;
    int invoiceId; 
    String amount;
    String date;

    public ProviderAccounts() {
    }

    public ProviderAccounts(int id, int providerID, int invoiceId, String amount, String date) {
        this.id = id;
        this.providerID = providerID;
        this.invoiceId = invoiceId;
        this.amount = amount;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProviderID() {
        return providerID;
    }

    public void setProviderID(int providerID) {
        this.providerID = providerID;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
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
     public static ObservableList<ProviderAccounts> getData(int id) throws Exception {
        ObservableList<ProviderAccounts> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `id`, `provider_id`, `invoice_id`, `date`, `amount` FROM `st_provider_accounts` WHERE `provider_id`='"+id+"'");
        while (rs.next()) {
            data.add(new ProviderAccounts(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5)));
        }
        return data;
    }
}
