package com.fitness.Controller.Fragment.Archive;

import com.fitness.Controller.Controller;
import com.fitness.Model.Archive.Archive;
import com.fitness.Model.Person.Employee;
import com.fitness.Model.Report.Report;
import com.fitness.Model.Report.Salary;
import com.fitness.Model.Work.DateTime;
import com.fitness.Model.Work.Employment;
import com.fitness.Model.Work.Subscription;
import com.fitness.Service.Archive.ArchiveService;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
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
    private TableView<Archive> historyTableView;
    @FXML
    private TableColumn<Archive, DateTime> dateColumn;
    @FXML
    private TableColumn<Archive, String> cardColumn;
    @FXML
    private TableColumn<Archive, String> nameColumn;
    @FXML
    private TableColumn<Archive, String> employmentColumn;
    @FXML
    private TableColumn<Archive, Employee> employeeColumn;
    @FXML
    private TableColumn<Archive, String> subscriptionColumn;
    @FXML
    private TableColumn<Archive, String> priceColumn;
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;
    @FXML
    private Label visitsQuantityLabel;
    @FXML
    private Label totalPriceLabel;

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
            this.fillReport();
        });

        deleteButton.setOnAction(event -> {
            Archive archive = this.historyTableView.getSelectionModel().getSelectedItem();
            try {
                archiveService.remove(archive);
                this.fillReport();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

    private void fillReport() {
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
                progressIndicator.setVisible(false);
                return;
            }
            Platform.runLater(() -> {
                List<Report> reports = archiveService.getReports(archives);
                clearSalary();
                fillSalary(reports);
                clearHistory();
                fillHistory(archives);
                progressIndicator.setVisible(false);
            });
        }).start();
    }

    private void initDatePickers() {
        StringConverter<LocalDate> converter = new StringConverter<>() {
            final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

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

            Employment employment = reports.get(row).getEmployment();
            Label employmentLabel = new Label(employment == null ? "" : employment.getName());
            employmentLabel.setStyle("-fx-font-weight: bold");
            Separator separator = new Separator();
            separator.setOrientation(Orientation.HORIZONTAL);
            GridPane.setColumnSpan(separator, REMAINING);
            int rowIndex = row * 3 + 1;

            int price = reports.get(row).getTotalPrice();
            int quantity = reports.get(row).getTotalQuantity();
            totalQuantity += quantity;
            totalPrice += price;

            salaryGridPane.add(new Label(quantity + ""), 1, rowIndex + 1);
            salaryGridPane.add(new Label(price + ""), 2, rowIndex + 1);

            salaryGridPane.add(separator, 0, rowIndex);
            salaryGridPane.add(employmentLabel, 0, rowIndex + 1);
            salaryGridPane.add(salaryTableView, 0, rowIndex + 2);
        }

        visitsQuantityLabel.setText(totalQuantity + "");
        totalPriceLabel.setText(totalPrice + "");
    }

    private void clearHistory() {
        historyTableView.getItems().clear();
    }

    private void fillHistory(List<Archive> archives) {
        historyTableView.setItems(FXCollections.observableArrayList(archives));
    }

    private void initHistoryTable() {
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        cardColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getCard()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getFullName()));
        employmentColumn.setCellValueFactory(new PropertyValueFactory<>("employment"));
        employmentColumn.setCellValueFactory(cellData -> {
            Employment employment = cellData.getValue().getEmployment();
            return new SimpleStringProperty(employment == null ? "Գրանցում" : employment.getName());
        });
        employeeColumn.setCellValueFactory(new PropertyValueFactory<>("employee"));
        subscriptionColumn.setCellValueFactory(cellData -> {
            Subscription subscription = cellData.getValue().getCustomer().getSubscription();
            return new SimpleStringProperty(subscription == null ? "" : subscription.getName());
        });
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    @Override
    public void start() {
        makeActive();
        initListeners();
        initDatePickers();
        initHistoryTable();
    }

    @Override
    public void stop() {

    }
}