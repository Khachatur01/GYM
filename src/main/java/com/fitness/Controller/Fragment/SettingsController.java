package com.fitness.Controller.Fragment;

import com.fitness.Constant.Status;
import com.fitness.Constant.Week;
import com.fitness.Controller.Controller;
import com.fitness.DataSource.DB;
import com.fitness.DataSource.Memory.DBMemory;
import com.fitness.Model.Configuration.WeekDay;
import com.fitness.Model.Configuration.WorkingDay;
import com.fitness.Model.Database.FileConnection;
import com.fitness.Model.Database.LocalConnection;
import com.fitness.Model.Database.RemoteConnection;
import com.fitness.Service.Clear;
import com.fitness.Service.Configuration.SettingsService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SettingsController extends GridPane implements Controller {
    @FXML
    private GridPane workingDaysGridPane;
    @FXML
    private ProgressIndicator connectionProgressIndicator;
    @FXML
    private Label connectionStatusLabel;

    @FXML
    private TabPane databaseTabPane;
    @FXML
    private Tab localTab;
    @FXML
    private Tab remoteTab;
    @FXML
    private Tab fileTab;

    @FXML
    private Button localConnectButton;
    @FXML
    private TextField localPortTextField;
    @FXML
    private TextField localDatabaseTextField;
    @FXML
    private TextField localUsernameTextField;
    @FXML
    private PasswordField localPasswordField;

    @FXML
    private Button remoteConnectButton;
    @FXML
    private TextField remoteURITextField;
    @FXML
    private TextField remoteDatabaseTextField;
    @FXML
    private TextField remoteUsernameTextField;
    @FXML
    private PasswordField remotePasswordField;

    @FXML
    private Button fileConnectButton;
    @FXML
    private TextField fileTextField;
    @FXML
    private Button browseFileButton;

    @FXML
    private Button disconnectButton;

    @FXML
    private Button confirmChangesButton;

    private final SettingsService settingsService = new SettingsService();
    private List<WorkingDay> workingDays;

    public SettingsController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fitness/fragment/settings.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }

    private void initWorkingDaysHeader() {
        Label employeeHeaderLabel = new Label("Աշխատող");
        workingDaysGridPane.add(employeeHeaderLabel, 0, 0);
        employeeHeaderLabel.setStyle("-fx-font-weight: bold");
        int weekNumber = 1;

        for(Week week: Week.values()) {
            Label weekHeaderLabel = new Label(week.getArmName());
            weekHeaderLabel.setStyle("-fx-font-weight: bold");
            workingDaysGridPane.add(weekHeaderLabel, weekNumber++, 0);
        }
    }

    private void initWorkingDaysGridPane() throws SQLException {
        initWorkingDaysHeader();
        this.workingDays = settingsService.getWorkingDays();
        int row = 1;
        for(WorkingDay workingDay: this.workingDays) {
            Label employeeNameLabel = new Label(workingDay.getEmployee().getName().toString());
            workingDaysGridPane.add(employeeNameLabel, 0, row);
            employeeNameLabel.setStyle("-fx-padding: 10");

            int col = 1;
            for(WeekDay weekDay: workingDay.getWorkingDays()) {
                CheckBox dayCheckBox = new CheckBox();
                dayCheckBox.setStyle("-fx-font-size: 30");
                dayCheckBox.setSelected(weekDay.isWorkingDay());

                dayCheckBox.selectedProperty().addListener((observable, oldValue, checked) ->
                    weekDay.setWorkingDay(checked)
                );

                workingDaysGridPane.add(dayCheckBox, col, row);
                col++;
            }
            row++;
        }

    }

    private void initListeners() {
        localConnectButton.setOnAction(event -> {
            try {
                int port = Integer.parseInt(localPortTextField.getText());
                String database = localDatabaseTextField.getText();
                String username = localUsernameTextField.getText();
                String password = localPasswordField.getText();

                LocalConnection localConnection = new LocalConnection(port, database, username, password);
                DBMemory.storeConnection(localConnection);

                this.checkConnectionAsync();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        });
        remoteConnectButton.setOnAction(event -> {
            try {
                String URI = remoteURITextField.getText();
                String database = remoteDatabaseTextField.getText();
                String username = remoteUsernameTextField.getText();
                String password = remotePasswordField.getText();

                RemoteConnection remoteConnection = new RemoteConnection(URI, database, username, password);
                DBMemory.storeConnection(remoteConnection);

                this.checkConnectionAsync();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        });
        browseFileButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Ընտրել ֆայլ");
            File sqliteFile = fileChooser.showOpenDialog(this.getScene().getWindow());
            if(sqliteFile != null) {
                fileTextField.setText(sqliteFile.getPath());
            }
        });
        fileConnectButton.setOnAction(event -> {
            String URL = fileTextField.getText();
            FileConnection fileConnection = new FileConnection(URL);
            DBMemory.storeConnection(fileConnection);

            this.checkConnectionAsync();
        });
        disconnectButton.setOnAction(event -> {
            DBMemory.deleteConnection();
            this.stop();
            this.start();
        });
        confirmChangesButton.setOnAction(event -> {
            try {
                settingsService.setWorkingDays(this.workingDays);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void initStatus() {
        Object fetchObject = DBMemory.fetchObject();
        if(fetchObject instanceof LocalConnection) {
            LocalConnection localConnection = (LocalConnection)fetchObject;
            connectionStatusLabel.setText(Status.LOCAL.toString());

            localPortTextField.setText(localConnection.getPort() + "");
            localDatabaseTextField.setText(localConnection.getDatabase());
            localUsernameTextField.setText(localConnection.getUsername());
            localPasswordField.setText(localConnection.getPassword());

            databaseTabPane.getSelectionModel().select(localTab);
        } else if(fetchObject instanceof RemoteConnection) {
            RemoteConnection remoteConnection = (RemoteConnection)fetchObject;
            connectionStatusLabel.setText(Status.REMOTE.toString());

            remoteURITextField.setText(remoteConnection.getURI());
            remoteDatabaseTextField.setText(remoteConnection.getDatabase());
            remoteUsernameTextField.setText(remoteConnection.getUsername());
            remotePasswordField.setText(remoteConnection.getPassword());

            databaseTabPane.getSelectionModel().select(remoteTab);
        } else if(fetchObject instanceof FileConnection) {
            FileConnection fileConnection = (FileConnection)fetchObject;
            connectionStatusLabel.setText(Status.LOCAL.toString());

            fileTextField.setText(fileConnection.getFile());
            databaseTabPane.getSelectionModel().select(fileTab);
        } else {
            connectionStatusLabel.setText(Status.NOT_CONNECTED.toString());
        }
    }

    private void checkConnectionAsync() {
        connectionProgressIndicator.setVisible(true);
        new Thread(() -> {
            try {
                DB.connect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                connectionProgressIndicator.setVisible(false);
                this.stop();
                this.start();
            });
        }).start();
    }

    @Override
    public void start() {
        makeActive();
        initListeners();
        initStatus();
        try {
            initWorkingDaysGridPane();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        workingDaysGridPane.setGridLinesVisible(false);
        workingDaysGridPane.getChildren().clear();
        workingDaysGridPane.setGridLinesVisible(true);

        Clear.textField(
                localPortTextField,
                localDatabaseTextField,
                localUsernameTextField,
                localPasswordField,

                remoteURITextField,
                remoteDatabaseTextField,
                remoteUsernameTextField,
                remotePasswordField,

                fileTextField
        );
    }
}