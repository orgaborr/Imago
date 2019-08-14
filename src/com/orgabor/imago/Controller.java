package com.orgabor.imago;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.File;
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

    //sets initial message in TextArea
    public void initialize() {
        printMessage("Üdv az Imago-ban! :) Töltsd ki a mezõket és katt a 'Mehet' gombra.");
    }

    //checks the content of TextFields and calls copyImgs @SourceFolder
    @FXML
    private boolean startProcessing() {
        if(new File(sourceField.getText()).exists()) {
            if(checkDestFolder(destField.getText())) {
                if(!nameField.getText().equals("")) {
                    printMessage("A feldolgozás megkezdõdött\n...");
                    SourceFolder sourceFolder = new SourceFolder(sourceField.getText());
                    if (sourceFolder.copyImgs(destField.getText(), nameField.getText()) > 0) {
                        printMessage(sourceFolder.getSerialNumber() + " kép másolása befejezõdött");
                        return true;
                    }
                    if(sourceFolder.getExceptionMessage() != null) {
                        printMessage(sourceFolder.getExceptionMessage());
                    }
                    return false;
                }
                printMessage("Adj meg nevet az új képfájloknak!");
                return false;
            }
            return false;
        }
        printMessage("Érvénytelen forrásmappa. Ellenõrizd a forrás útvonalát!");
        return false;
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

    @FXML
    //prints messages to TextArea
    private void printMessage(String message) {
        DateFormat df = new SimpleDateFormat("hh:mm:ss");
        messageTextArea.setText(messageTextArea.getText() + "[" + df.format(new Date())+ "] " + message + "\n");
    }
}
