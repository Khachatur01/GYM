package com.fitness.Controller.Fragment.Archive;

import com.fitness.Controller.Controller;
import com.fitness.Model.Archive.Archive;
import com.fitness.Model.Person.Employee;
import com.fitness.Model.Report.Report;
import com.fitness.Model.Report.Salary;
import com.fitness.Service.Archive.ArchiveService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class ArchiveController extends GridPane implements Controller {
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private Button getReportButton;
    @FXML
    private GridPane salaryGridPane;
    @FXML
    private Label visitsQuantityLabel;
    @FXML
    private Label totalPriceLabel;
    @FXML
    private Button detailButton;

    private final ArchiveService archiveService = new ArchiveService();

    public ArchiveController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fitness/fragment/report/report.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }

    private void initListeners() {
        startDatePicker.valueProperty().addListener((ov, oldValue, newValue) -> {
            endDatePicker.setValue(newValue);
        });

        getReportButton.setOnAction(event -> {
            progressIndicator.setVisible(true);

            new Thread(() -> {
                LocalDate startDate = startDatePicker.getValue();
                LocalDate endDate = endDatePicker.getValue();
                List<Archive> archives;

                if(startDate == null || endDate == null || endDate.isBefore(startDate)) {
                    progressIndicator.setVisible(false);
                    return;
                }

                try {
                    archives = archiveService.getByDateRange(startDate, endDate);
                } catch (SQLException e) {
                    System.err.println("Archives fetching error");
                    return;
                }
                Platform.runLater(() -> {

                    List<Report> reports = archiveService.getReports(archives);
                    clearSalary();
                    fillSalary(reports);
                    progressIndicator.setVisible(false);
                });
            }).start();


        });
    }

    private void initDatePickers() {
        StringConverter<LocalDate> converter = new StringConverter<>() {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            @Override
            public String toString(LocalDate localDate) {
                if (localDate != null)
                    return dateTimeFormatter.format(localDate);
                else
                    return "";
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty())
                    return LocalDate.parse(string, dateTimeFormatter);
                else
                    return null;
            }
        };

        LocalDate localDate = LocalDate.now();
        startDatePicker.setConverter(converter);
        startDatePicker.setValue(localDate);

        endDatePicker.setConverter(converter);
        endDatePicker.setValue(localDate);
    }

    private void clearSalary() {
        Node employmentHeader = salaryGridPane.getChildren().get(0);
        Node totalHeader = salaryGridPane.getChildren().get(1);

        salaryGridPane.getChildren().clear();
        salaryGridPane.add(employmentHeader, 0, 0);
        salaryGridPane.add(totalHeader, 1, 0);

        visitsQuantityLabel.setText("0");
        totalPriceLabel.setText("0");
    }

    private void fillSalary(List<Report> reports) {
        int totalPrice = 0;
        int totalQuantity = 0;
        for(int row = 0; row < reports.size(); row++) {
            TableView<Salary> salaryTableView = new TableView<>();
            salaryTableView.setMaxHeight(200);
            salaryTableView.setMinWidth(700);
            salaryTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            TableColumn<Salary, Employee> employeeTableColumn = new TableColumn<>("Աշխատող");
            employeeTableColumn.setCellValueFactory(new PropertyValueFactory<>("employee"));
            employeeTableColumn.setMinWidth(300);
            employeeTableColumn.setEditable(false);

            TableColumn<Salary, Integer> quantityTableColumn = new TableColumn<>("Քանակ");
            quantityTableColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            quantityTableColumn.setEditable(false);

            TableColumn<Salary, Integer> priceTableColumn = new TableColumn<>("Գումար");
            priceTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            priceTableColumn.setEditable(false);

            salaryTableView.getColumns().addAll(employeeTableColumn, quantityTableColumn, priceTableColumn);
            salaryTableView.setItems(FXCollections.observableArrayList(reports.get(row).getSalaries()));

            Label employmentLabel = new Label(reports.get(row).getEmployment().toString());
            Separator separator = new Separator();
            separator.setOrientation(Orientation.HORIZONTAL);
            GridPane.setColumnSpan(separator, REMAINING);
            int rowIndex = row * 3 + 1;

            int price = reports.get(row).getTotalPrice();
            int quantity = reports.get(row).getTotalQuantity();
            totalQuantity += quantity;
            totalPrice += price;

            salaryGridPane.add(new Label(quantity + " այց"), 1, rowIndex + 1);
            salaryGridPane.add(new Label(price + " դրամ"), 2, rowIndex + 1);

            salaryGridPane.add(separator, 0, rowIndex);
            salaryGridPane.add(employmentLabel, 0, rowIndex + 1);
            salaryGridPane.add(salaryTableView, 0, rowIndex + 2);
        }

        visitsQuantityLabel.setText(totalQuantity + " այց");
        totalPriceLabel.setText(totalPrice + " դրամ");
    }

    @Override
    public void start() {
        makeActive();
        initListeners();
        initDatePickers();
    }

    @Override
    public void stop() {

    }
}