package screens.Accounts.assets;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author amran
 */
public class Accounts {

    int id;
    String name;
    String accountNumber;
    String bank;
    String balance;
    String type;

    public Accounts() {
    }

    public Accounts(int id, String name, String accountNumber, String bank, String balance, String type) {
        this.id = id;
        this.name = name;
        this.accountNumber = accountNumber;
        this.bank = bank;
        this.balance = balance;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `acc_accounts`(`id`, `name`, `acc_num`, `bank`, `credite`, `type`) VALUES (?,?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setString(3, accountNumber);
        ps.setString(4, bank);
        ps.setString(5, balance);
        ps.setString(6, type);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `acc_accounts` SET `name`=?,`acc_num`=?,`bank`=?,`credite`=?,`type`=? WHERE `id`=?");
        ps.setString(1, name);
        ps.setString(2, accountNumber);
        ps.setString(3, bank);
        ps.setString(4, balance);
        ps.setString(5, type);
        ps.setInt(6, id);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `acc_accounts` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<Accounts> getData() throws Exception {
        ObservableList<Accounts> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT * FROM `acc_accounts`");
        while (rs.next()) {
            data.add(new Accounts(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `acc_accounts`").getValueAt(0, 0).toString();
    }
}
