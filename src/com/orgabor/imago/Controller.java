package com.orgabor.imago;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Controller {
    @FXML
    private TextField sourceField;
    @FXML
    private TextField destField;
    @FXML
    private TextField nameField;
    @FXML
    private TextArea messageTextArea;
    @FXML
    private Button goButton;
    @FXML
    private BorderPane mainBorderPane;

    //sets initial message in TextArea and sets goButton disabled
    @FXML
    public void initialize() {
        goButton.setDisable(true);
        printMessage("Üdv az Imago-ban! :) Töltsd ki a mezõket és katt a 'Mehet' gombra.");
    }

    //checks the content of TextFields and calls copyImgs @SourceFolder, prints IOExceptions
    @FXML
    private void processFields() {
        goButton.setDisable(true);
        new CopyService().start();
        checkFields();
    }

    //checks if destination folder exists and creates it if not
    private boolean checkDestFolder(String path) {
        File destFolder = new File(path);
        if (!destFolder.exists()) {
            if (destFolder.mkdirs()) {
                printMessage("Célmappa létrehozva: " + path);
                return true;
            }
            printMessage("Célmappa létrehozása sikertelen. Ellenõrizd a megadott útvonalat!");
            return false;
        }
        return true;
    }

    //prints messages to messageTextArea
    @FXML
    private void printMessage(String message) {
        DateFormat df = new SimpleDateFormat("hh:mm:ss");
        messageTextArea.appendText("[" + df.format(new Date()) + "] " + message + "\n");
    }

    //clears the messageTextArea
    @FXML
    private void clearText() {
        messageTextArea.setText(null);
    }

    //enables or disables goButton depending on if a field is empty
    @FXML
    private void checkFields() {
        boolean disabled = sourceField.getText().isEmpty() || sourceField.getText().trim().isEmpty() ||
                destField.getText().isEmpty() || destField.getText().trim().isEmpty() ||
                nameField.getText().isEmpty() || nameField.getText().trim().isEmpty();
        goButton.setDisable(disabled);
    }

    @FXML
    private void showProgressDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        try {
            Parent root = FXMLLoader.load(getClass().getResource("progressDialog.fxml"));
            dialog.getDialogPane().setContent(root);
        } catch(IOException e) {
            printMessage(e.toString());
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
    }

    private class CopyService extends Service<Integer> {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    int count = 0;
                    if (new File(sourceField.getText()).exists()) {
                        if (checkDestFolder(destField.getText())) {
                            printMessage("A feldolgozás megkezdõdött\n...");
                            SourceFolder sourceFolder = new SourceFolder(sourceField.getText());
                            showProgressDialog();
                            try {
                                if ((count = sourceFolder.copyImgs(destField.getText(), nameField.getText())) > 0) {
                                    printMessage(count + " kép másolása befejezõdött.");
                                } else if (sourceFolder.imgCounter() > 0) {
                                    printMessage("A fájlok már szerepelnek a mappában.");
                                } else {
                                    printMessage("A megadott forrásmappában nincsenek képfájlok.");
                                }
                            } catch (IOException e) {
                                printMessage(e.toString());
                            }
                        }
                    } else {
                        printMessage("Érvénytelen forrásmappa. Ellenõrizd a forrás útvonalát!");
                    }
                    return count;
                }
            };
        }
    }
}
