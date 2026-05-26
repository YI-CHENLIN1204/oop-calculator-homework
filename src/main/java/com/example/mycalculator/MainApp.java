package com.example.mycalculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // 讀取我們剛剛在 Scene Builder 畫好的設計圖
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("calculator.fxml"));

        // 把設計圖放進 Scene 裡面，並設定好視窗大小
        Scene scene = new Scene(fxmlLoader.load(), 300, 450);

        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        primaryStage.setTitle("我的專屬計算機");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}