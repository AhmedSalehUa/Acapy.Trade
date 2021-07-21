/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.sales.assets;

import javafx.collections.ObservableList;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import screens.store.assets.Products;

/**
 *
 * @author AHMED
 */
public class OffersDetails {

    int id;
    ComboBox products;
    TextField amount;
    TextField cost;
    TextField totalCost;

    String product;
    String amountString;
    String costString;
    String totalcostString;
    int productID;

    public OffersDetails(int id, String product, String amountString, String costString, String totalcostString) {
        this.id = id;
        this.product = product;
        this.amountString = amountString;
        this.costString = costString;
        this.totalcostString = totalcostString;
    }

    public OffersDetails(int id, ObservableList<Products> data, String selectedpro, String amount, String cost, String totalCost) {
        this.id = id;
        this.products = new ComboBox(data);
        products.setConverter(new StringConverter<Products>() {
            @Override
            public String toString(Products contract) {
                return contract.getName();
            }

            @Override
            public Products fromString(String string) {
                return null;
            }
        });
        products.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        for (Products a : data) {
            if (a.getName().equals(selectedpro)) {
                products.getSelectionModel().select(a);

            }
        }
        products.setCellFactory(cell -> new ListCell<Products>() {

            GridPane gridPane = new GridPane();
            Label lblid = new Label();
            Label lblName = new Label();
            Label lblQuali = new Label();

            {

                gridPane.getColumnConstraints().addAll(
                        new ColumnConstraints(100, 100, 100), new ColumnConstraints(100, 100, 100),
                        new ColumnConstraints(100, 100, 100)
                );

                gridPane.add(lblid, 0, 1);
                gridPane.add(lblName, 1, 1);
                gridPane.add(lblQuali, 2, 1);

            }

            @Override
            protected void updateItem(Products person, boolean empty) {
                super.updateItem(person, empty);

                if (!empty && person != null) {

                    // Update our Labels
                    lblid.setText("م: " + Integer.toString(person.getId()));
                    lblName.setText("الاسم: " + person.getName());
                    lblQuali.setText("الموديل: " + person.getModel());
                    // Set this ListCell's graphicProperty to display our GridPane
                    setGraphic(gridPane);
                } else {
                    // Nothing to display here
                    setGraphic(null);
                }
            }
        });
        this.amount = new TextField(amount);
        this.cost = new TextField(cost);
        this.totalCost = new TextField(totalCost);
    }
}
