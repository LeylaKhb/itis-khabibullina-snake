package com.khabibullina.snake;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class SnakeApplication extends Application  {
    private int breakTime = 1000;
    private boolean isDone = false;
    private final Pane root = new Pane();
    private int dir = 3;
    private final Random r = new Random();
    private Rectangle food;
    private Rectangle poison;
    private Rectangle rect;
    Label timerLabel;
    private int[] foodloc, headloc, poisonloc;
    private int size = 20;
    private final int foodAndPoisonSize = 20;
    BorderPane border;



    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(root, 520, 520);
        scene.setOnKeyPressed(event -> {
            KeyCode k = event.getCode();
            switch (k) {
                case RIGHT:
                    if (dir != 1 && ((dir == 2 || dir == 0))) dir = 3;
                    break;
                case LEFT:
                    if (dir != 3 && ((dir == 2 || dir == 0))) dir = 1;
                    break;
                case DOWN:
                    if (dir != 2 && ((dir == 3 || dir == 1))) dir = 0;
                    break;
                case UP:
                    if (dir != 0 && ((dir == 3 || dir == 1))) dir = 2;
                    break;
            }
        });

        createGameOverBorder();
        createSnakeRect();
        createPoisonRect();
        createFoodRect();
        createTimerLabel();

        root.getChildren().addAll(border, rect, poison, food, timerLabel);

        createThreads();

        stage.setTitle("Snake");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> isDone = true);
        stage.show();
    }

    private void createGameOverBorder() {
        border = new BorderPane();
        Rectangle gameOver = new Rectangle();
        gameOver.setWidth(520);
        gameOver.setHeight(520);
        gameOver.setFill(Color.RED);
        border.setLeft(gameOver);
        border.setVisible(false);
    }

    private void createSnakeRect() {
        rect = new Rectangle(size, size);
        rect.setFill(Color.BURLYWOOD);
        rect.setStroke(Color.BLACK);
        rect.setTranslateX(0);
        rect.setTranslateY(0);
        headloc = new int[]{0, 0};
    }

    private void createPoisonRect() {
        poison = new Rectangle(foodAndPoisonSize, foodAndPoisonSize);
        poison.setStroke(Color.RED);
        poison.setFill(Color.RED);
        setCoordsToPoison();
    }

    private void createFoodRect() {
        food = new Rectangle(foodAndPoisonSize, foodAndPoisonSize);
        food.setStroke(Color.GREEN);
        food.setFill(Color.GREEN);
        setCoordsToFood();
    }

    private void createTimerLabel() {
        timerLabel = new Label("00:00");
        timerLabel.setStyle("-fx-font-size: 20");
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int i = 0;
            public void run() {
                Platform.runLater(() -> {
                    int minutes = i / 60;
                    int seconds = i % 60;
                    timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
                    i++;
                });
            }
        }, 0, 1000);
    }

    private void createThreads() {
        Thread game;
        Thread breakTimer;
        game = new Thread(() -> {
            while (!isDone) {
                move();
                try {
                    Thread.sleep(breakTime);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
            border.setVisible(true);
        });
        game.start();

        breakTimer = new Thread(() -> {
            while (breakTime > 100) {
                breakTime -= 100;
                try {
                    Thread.sleep(60000);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        breakTimer.start();
    }

    private void move() {
        switch (dir) {
            case 0:
                if (headloc[1] + 20 <= 520) headloc[1] += 20;
                else headloc[1] = size;
                break;
            case 1:
                if (headloc[0] - 20 >= 0) headloc[0] -= 20;
                else headloc[0] = 520 - size;
                break;
            case 2:
                if (headloc[1] - 20 >= 0) headloc[1] -= 20;
                else headloc[1] = 520 - size;
                break;
            case 3:
                if (headloc[0] + 20 <= 520) headloc[0] += 20;
                else headloc[0] = size;
                break;
        }

        rect.setX(headloc[0]);
        rect.setY(headloc[1]);

        checkPoisonWasEaten();
        checkFoodWasEaten();
    }

    private void checkFoodWasEaten() {
        if ((foodloc[0] >= headloc[0] && foodloc[0] <= (headloc[0] + size)) ||
                ((foodloc[0] + foodAndPoisonSize) >= headloc[0] && (foodloc[0] + foodAndPoisonSize) <= (headloc[0] + size))) {
            if ((foodloc[1] >= headloc[1] && foodloc[1] <= (headloc[1] + size)) ||
                    ((foodloc[1] + foodAndPoisonSize) >= headloc[1] && (foodloc[1] + foodAndPoisonSize) <= (headloc[1] + size))) {
                size += 5;
                rect.setHeight(size);
                rect.setWidth(size);

                setCoordsToPoison();
                setCoordsToFood();
            }
        }
    }
    private void checkPoisonWasEaten() {
        if ((poisonloc[0] >= headloc[0] && poisonloc[0] <= (headloc[0] + size)) ||
                ((poisonloc[0] + foodAndPoisonSize) >= headloc[0] && (poisonloc[0] + foodAndPoisonSize) <= (headloc[0] + size))) {
            if ((poisonloc[1] >= headloc[1] && poisonloc[1] <= (headloc[1] + size)) ||
                    ((poisonloc[1] + foodAndPoisonSize) >= headloc[1] && (poisonloc[1] + foodAndPoisonSize) <= (headloc[1] + size))) {
                isDone = true;
            }
        }
    }

    private void setCoordsToPoison() {
        int xPoison = 0;
        int yPoison = 0;
        boolean correctCoords = false;
        while (!correctCoords) {
            xPoison = r.nextInt(26) * foodAndPoisonSize;
            yPoison = r.nextInt(26) * foodAndPoisonSize;
            if (!((xPoison >= headloc[0] && xPoison <= (headloc[0] + size)) ||
                    ((xPoison + foodAndPoisonSize) >= headloc[0] && (xPoison + foodAndPoisonSize) <= (headloc[0] + size)))) {
                if (!((yPoison >= headloc[1] && yPoison <= (headloc[1] + size)) ||
                        ((yPoison + foodAndPoisonSize) >= headloc[1] && (yPoison + foodAndPoisonSize) <= (headloc[1] + size)))) {
                    correctCoords = true;
                }
            }
        }
        poison.setTranslateX(xPoison);
        poison.setTranslateY(yPoison);
        poisonloc = new int[]{xPoison, yPoison};
    }

    private void setCoordsToFood() {
        int xFood = 0;
        int yFood = 0;
        boolean corectCoords = false;
        while (!corectCoords) {
            xFood = r.nextInt(26) * foodAndPoisonSize;
            yFood = r.nextInt(26) * foodAndPoisonSize;
            if (((poisonloc[0] + foodAndPoisonSize) < xFood) ||
                    (poisonloc[0] > (xFood + foodAndPoisonSize))) {
                if (((poisonloc[1] + foodAndPoisonSize) < yFood) ||
                        (poisonloc[1] > (yFood + foodAndPoisonSize))) {
                    corectCoords = true;
                }
            }
        }
        food.setTranslateX(xFood);
        food.setTranslateY(yFood);
        foodloc = new int[]{xFood, yFood};
    }
}
