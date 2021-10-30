package com.fitness.Service;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Control;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class Grid {
    public static void addColumns(GridPane gridPane, int col){
        for(int i = 0; i < col; ++i){
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setHalignment(HPos.CENTER);
            columnConstraints.setHgrow(Priority.ALWAYS);
            gridPane.getColumnConstraints().add(columnConstraints);
        }
    }

    public static void addRows(GridPane gridPane, int row){
        for(int i = 0; i < row; ++i){
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setValignment(VPos.CENTER);
            rowConstraints.setVgrow(Priority.ALWAYS);

            gridPane.getRowConstraints().add(rowConstraints);
        }
    }
}
