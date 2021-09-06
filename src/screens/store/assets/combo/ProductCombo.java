/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.store.assets.combo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;
import javafx.util.StringConverter;
import screens.store.assets.Products;

/**
 *
 * @author AHMED
 */
public class ProductCombo {

    private ComboBox<Products> cmb;
    String filter = "";
    private ObservableList<Products> originalItems;

    public ProductCombo(ComboBox<Products> cmb) {
        this.cmb = cmb;
        this.cmb.setConverter(new StringConverter<Products>() {
            @Override
            public String toString(Products patient) {
                return patient.getName();
            }

            @Override
            public Products fromString(String string) {
                return null;
            }
        });
        this.cmb.setCellFactory(cell -> new ListCell<Products>() {

            // Create our layout here to be reused for each ListCell
            GridPane gridPane = new GridPane();
            Label lblid = new Label();
            Label lblName = new Label();
            Label lblAge = new Label();
            Label lblTele = new Label();

            {
                // Ensure all our column widths are constant
                gridPane.getColumnConstraints().addAll(
                        new ColumnConstraints(100, 100, 100),
                        new ColumnConstraints(100, 100, 100),
                        new ColumnConstraints(100, 100, 100),
                        new ColumnConstraints(100, 100, 100)
                );

                gridPane.add(lblid, 0, 1);
                gridPane.add(lblName, 1, 1);
                gridPane.add(lblAge, 2, 1);
                gridPane.add(lblTele, 3, 1);

            }
 
            @Override
            protected void updateItem(Products person, boolean empty) {
                super.updateItem(person, empty);

                if (!empty && person != null) {
 
                    lblid.setText("م: " + Integer.toString(person.getId()));
                    lblName.setText("الاسم: " + person.getName());
                    lblTele.setText("الموديل: " + person.getModel());
                    lblAge.setText("التفاصيل: " + person.getDetails()); 
                    setGraphic(gridPane);
                } else { 
                    setGraphic(null);
                }
            }
        });
        originalItems = FXCollections.observableArrayList(cmb.getItems());
        cmb.setTooltip(new Tooltip());
        cmb.setOnKeyPressed(this::handleOnKeyPressed);
        cmb.setOnHidden(this::handleOnHiding);
    }

    public void handleOnKeyPressed(KeyEvent e) {
        ObservableList<Products> filteredList = FXCollections.observableArrayList();
        KeyCode code = e.getCode();

        filter += e.getText();

        if (code == KeyCode.BACK_SPACE && filter.length() > 0) {
            filter = filter.substring(0, filter.length() - 1);
            cmb.getItems().setAll(originalItems);
        }
        if (code == KeyCode.ESCAPE) {
            filter = "";
        }
        if (filter.length() == 0) {
            filteredList = originalItems;
            cmb.getTooltip().hide();
        } else {
            String txtUsr = filter; 
            ObservableList<Products> items1 = cmb.getItems();
            for (Products a : items1) {
                if (a.getName().contains(txtUsr)) {
                    filteredList.add(a);
                }
                if (a.getModel().contains(txtUsr)) {
                    filteredList.add(a);
                } 
            }

            cmb.getTooltip().setText(txtUsr);
            Window stage = cmb.getScene().getWindow();
            double posX = stage.getX() + cmb.getBoundsInParent().getMinX();
            double posY = stage.getY() + cmb.getBoundsInParent().getMinY();
            cmb.getTooltip().show(stage, posX, posY);
            cmb.show();
        }
        cmb.getItems().setAll(filteredList);
    }

    public void handleOnHiding(Event e) {
        filter = "";
        cmb.getTooltip().hide();
        Products s = cmb.getSelectionModel().getSelectedItem();
        cmb.getItems().setAll(originalItems);
        cmb.getSelectionModel().select(s);
    }
}
