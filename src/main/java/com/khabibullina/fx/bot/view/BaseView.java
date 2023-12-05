package com.khabibullina.fx.bot.view;

import com.khabibullina.fx.bot.BotApplication;
import javafx.scene.Parent;

public abstract class BaseView {

    private static BotApplication botApplication;

    public static BotApplication getBotApplication() {
        if (botApplication != null) {
            return botApplication;
        }
        throw new RuntimeException("No app in base view");
    }

    public static void setBotApplication(BotApplication botApplication) {
        BaseView.botApplication = botApplication;
    }

    public abstract Parent getView();
}
