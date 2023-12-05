package com.khabibullina.fx.bot;

import com.khabibullina.fx.bot.view.BotView;
import com.khabibullina.fx.bot.view.BaseView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class BotApplication extends Application {
    private BotView botView;
    private BorderPane root;
    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        primaryStage.setTitle("Bot");
        primaryStage.setOnCloseRequest(e -> System.exit(0));
        BaseView.setBotApplication(this);
        botView = new BotView();

        root = new BorderPane();
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
        setView(botView);
    }

    public Stage getStage() {
        return stage;
    }


    public void setView(BaseView view) {
        root.setCenter(view.getView());
    }
}
