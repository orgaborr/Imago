package com.orgabor.imago;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
    private Button clearButton;

    //sets initial message in TextArea and sets goButton disabled
    @FXML
    public void initialize() {
        goButton.setDisable(true);
        printMessage("Üdv az Imago-ban! :) Töltsd ki a mezõket és katt a 'Mehet' gombra.");
    }

    //checks the content of TextFields and calls copyImgs @SourceFolder, prints IOExceptions
    @FXML
    private void processFields() {
        new Thread() {
            public void run() {
                goButton.setDisable(true);
                if(new File(sourceField.getText()).exists()) {
                    if(checkDestFolder(destField.getText())) {
                        printMessage("A feldolgozás megkezdõdött\n...");
                        SourceFolder sourceFolder = new SourceFolder(sourceField.getText());

                        Thread copyThread = new Thread() {
                            public void run() {
                                try {
                                    sourceFolder.copyImgs(destField.getText(), nameField.getText());
                                } catch(IOException e) {
                                    printMessage(e.getMessage());
                                }
                            }
                        };
                        copyThread.start();

                        try {
                            copyThread.join();
                        } catch(InterruptedException e) {
                            printMessage(e.getMessage());
                        }

                        if(sourceFolder.getSerialNumber() > 0) {
                            printMessage(sourceFolder.getSerialNumber() + " kép másolása befejezõdött.");
                        } else {
                            printMessage("A megadott forrásmappában nincsenek képfájlok.");
                        }
                    }
                } else {
                    printMessage("Érvénytelen forrásmappa. Ellenõrizd a forrás útvonalát!");
                }
                checkFields();
            }
        }.start();
    }

    //checks if destination folder exists and creates it if not
    private boolean checkDestFolder(String path) {
        File destFolder = new File(path);
        if (!destFolder.exists()) {
            destFolder.mkdirs();
            if (destFolder.exists()) {
                printMessage("Célmappa létrehozva: " + path);
                return true;
            }
            printMessage("Célmappa létrehozása sikertelen. Ellenõrizd a megadott útvonalat!");
            return false;
        }
        return true;
    }

    //prints messages to TextArea
    @FXML
    private void printMessage(String message) {
        DateFormat df = new SimpleDateFormat("hh:mm:ss");
        messageTextArea.appendText("[" + df.format(new Date())+ "] " + message + "\n");
    }

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
}
