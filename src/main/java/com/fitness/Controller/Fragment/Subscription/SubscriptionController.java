package com.fitness.Controller.Fragment.Subscription;

import com.fitness.Constant.Fragment;
import com.fitness.Controller.Controller;
import com.fitness.DataSource.Log.Log;
import com.fitness.Element.SubscriptionButton;
import com.fitness.Model.Work.Subscription;
import com.fitness.Service.Grid;
import com.fitness.Service.Work.SubscriptionService;
import com.fitness.Window;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionController extends GridPane implements Controller {
    @FXML
    private ScrollPane subscriptionsScrollPane;
    @FXML
    private Button editButton;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;

    private GridPane subscriptionsGridPane = null;

    private int row = 0, col = 0;
    private final byte SUBSCRIPTION_PER_ROW = 3;
    private SubscriptionService subscriptionService = new SubscriptionService();
    private List<SubscriptionButton> subscriptionButtons = new ArrayList<>();

    public SubscriptionController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fitness/fragment/subscription/subscription.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();

        initListeners();
    }

    private void initGridPane() throws SQLException {
        row = 0; col = 0;
        subscriptionsGridPane = new GridPane();
        subscriptionsGridPane.setMaxWidth(800);
        subscriptionsGridPane.setMinWidth(800);
        subscriptionsGridPane.setPrefWidth(800);
        subscriptionsGridPane.setVgap(20);
        subscriptionsGridPane.setPadding(new Insets(20, 20, 20, 20));
        subscriptionsScrollPane.setContent(subscriptionsGridPane);

        List<Subscription> subscriptions = subscriptionService.getAll();
        Grid.addColumns(subscriptionsGridPane, SUBSCRIPTION_PER_ROW);
        Grid.addRows(subscriptionsGridPane, (int)Math.ceil(subscriptions.size() / SUBSCRIPTION_PER_ROW));

        for(Subscription subscription: subscriptions){
            addSubscriptionToGrid(subscription);
        }
    }

    private void initListeners() {
        editButton.setOnAction(event -> {
            if(SubscriptionButton.getSelected() != null){
                Window.getFragment(Fragment.EDIT_SUBSCRIPTION).start();
            }
        });
        deleteButton.setOnAction(event -> {
            SubscriptionButton subscription = SubscriptionButton.getSelected();
            if(subscription == null) return;
            try {
                subscriptionService.remove(subscription.getSubscription());
            } catch (SQLException e) {
                Log.error("Can't delete subscription", e);
            }
            SubscriptionButton.removeSelected();
            try {
                initGridPane();
            } catch (SQLException e) {
                Log.warning("Can't fetch subscriptions");
            }
        });
        addButton.setOnAction(event -> {
            Window.getFragment(Fragment.ADD_SUBSCRIPTION).start();
        });
    }

    private void addSubscriptionToGrid(Subscription subscription) {
        SubscriptionButton subscriptionButton = new SubscriptionButton(subscription);
        subscriptionButton.setOnAction();
        subscriptionButtons.add(subscriptionButton);

        if(col == SUBSCRIPTION_PER_ROW){
            col = 0;
            row++;
        }
        subscriptionsGridPane.add(subscriptionButton.getButton(), col, row);
        col++;
    }

    @Override
    public void start() {
        makeActive();
        try {
            initGridPane();
        } catch (SQLException e) {
            Log.warning("Can't fetch subscriptions");
        }
    }

    @Override
    public void stop() {

    }
}
