package com.khabibullina.fx.bot.view;

import com.khabibullina.fx.bot.utils.MessageHandler;
import com.khabibullina.snake.SnakeApplication;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import static javafx.scene.input.KeyEvent.KEY_PRESSED;

public class BotView extends BaseView {

    private TextArea input;
    private TextArea conversation;
    private AnchorPane pane;
    MessageHandler messageHandler = new MessageHandler();

    private final EventHandler<KeyEvent> eventHandler = new EventHandler<>() {
        @Override
        public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.ENTER) {
                String message = input.getText();
                conversation.appendText("you: " + message  + "\n");

                String answer = messageHandler.handleMessage(message);
                if (answer.equals(messageHandler.getGameStartingPhrase())) {
                    SnakeApplication snakeApplication = new SnakeApplication();
                    try {
                        snakeApplication.start(getBotApplication().getStage());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    conversation.appendText("Bot: " + answer + "\n");
                }

                input.clear();
                event.consume();
            }
        }
    };

    @Override
    public Parent getView() {
        if (pane == null) {
            createView();
        }
        return pane;
    }

    private void createView() {
        pane = new AnchorPane();

        conversation = new TextArea();
        conversation.setEditable(false);
        conversation.setWrapText(true);

        AnchorPane.setTopAnchor(conversation, 10.0);
        AnchorPane.setLeftAnchor(conversation, 10.0);
        AnchorPane.setRightAnchor(conversation, 10.0);

        input = new TextArea();
        input.setMaxHeight(50);

        AnchorPane.setBottomAnchor(input, 10.0);
        AnchorPane.setLeftAnchor(input, 10.0);
        AnchorPane.setRightAnchor(input, 10.0);

        input.addEventHandler(KEY_PRESSED, eventHandler);
        pane.getChildren().addAll(input, conversation);
    }
}
