/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.sales.assets;

/**
 *
 * @author AHMED
 */
public class OffersConditions {

    int id;
    int invoice_id;
    String attribute;
    String value;

    public OffersConditions() {
    }

    public OffersConditions(int id, int invoice_id, String attribute, String value) {
        this.id = id;
        this.invoice_id = invoice_id;
        this.attribute = attribute;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(int invoice_id) {
        this.invoice_id = invoice_id;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
     public static String getAutoNum() throws Exception {
     return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `sl_offers_condition`").getValueAt(0, 0).toString();
    }
}
