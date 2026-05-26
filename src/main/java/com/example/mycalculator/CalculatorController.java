package com.example.mycalculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CalculatorController {

    @FXML
    private TextField displayField;

    private double firstNumber = 0;
    private String currentOperator = "";
    private boolean startNewInput = true;

    @FXML
    private void handleButtonClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonText = clickedButton.getText();

        // 避免在 Error 狀態下繼續輸入
        if (displayField.getText().equals("Error") && !buttonText.equals("C")) {
            return;
        }

        switch (buttonText) {
            case "C":
                displayField.setText("");
                firstNumber = 0;
                currentOperator = "";
                startNewInput = true;
                break;

            case ".":
                // 【防呆 1：小數點邏輯】
                if (startNewInput || displayField.getText().isEmpty()) {
                    displayField.setText("0.");
                    startNewInput = false;
                } else if (!displayField.getText().contains(".")) {
                    // 確保螢幕上沒有小數點時，才能加上小數點
                    displayField.setText(displayField.getText() + ".");
                }
                break;

            case "+": case "-": case "*": case "/":
                // 【防呆 2：連續輸入符號的切換】
                if (startNewInput && !currentOperator.isEmpty() && !displayField.getText().isEmpty()) {
                    currentOperator = buttonText; // 使用者改變心意，只更新符號
                    return;
                }

                if (!displayField.getText().isEmpty()) {
                    firstNumber = Double.parseDouble(displayField.getText());
                }
                currentOperator = buttonText;
                startNewInput = true;
                break;

            case "=":
                if (currentOperator.isEmpty() || displayField.getText().isEmpty() || startNewInput) return;

                double secondNumber = Double.parseDouble(displayField.getText());
                double result = 0;

                switch (currentOperator) {
                    case "+": result = firstNumber + secondNumber; break;
                    case "-": result = firstNumber - secondNumber; break;
                    case "*": result = firstNumber * secondNumber; break;
                    case "/":
                        // 【防呆 3：攔截除以零的數學災難】
                        if (secondNumber == 0) {
                            displayField.setText("Error");
                            startNewInput = true;
                            currentOperator = "";
                            return;
                        }
                        result = firstNumber / secondNumber;
                        break;
                }

                // 為了美觀，如果結果是整數 (如 15.0)，就把後面的 .0 拿掉
                if (result == Math.floor(result) && !Double.isInfinite(result)) {
                    displayField.setText(String.format("%.0f", result));
                } else {
                    displayField.setText(String.valueOf(result));
                }

                startNewInput = true;
                currentOperator = ""; // 算完清空符號狀態
                break;

            default: // 處理數字輸入
                if (startNewInput) {
                    displayField.setText(buttonText);
                    startNewInput = false;
                } else {
                    displayField.setText(displayField.getText() + buttonText);
                }
                break;
        }
    }
}