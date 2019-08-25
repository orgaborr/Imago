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
        printMessage("�dv az Imago-ban! :) T�ltsd ki a mez�ket �s katt a 'Mehet' gombra.");
    }

    //checks the content of TextFields and calls copyImgs @SourceFolder, prints IOExceptions
    @FXML
    private void processFields() {
        new Thread() {
            public void run() {
                goButton.setDisable(true);
                if(new File(sourceField.getText()).exists()) {
                    if(checkDestFolder(destField.getText())) {
                        printMessage("A feldolgoz�s megkezd�d�tt\n...");
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
                            printMessage(sourceFolder.getSerialNumber() + " k�p m�sol�sa befejez�d�tt.");
                        } else {
                            printMessage("A megadott forr�smapp�ban nincsenek k�pf�jlok.");
                        }
                    }
                } else {
                    printMessage("�rv�nytelen forr�smappa. Ellen�rizd a forr�s �tvonal�t!");
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
                printMessage("C�lmappa l�trehozva: " + path);
                return true;
            }
            printMessage("C�lmappa l�trehoz�sa sikertelen. Ellen�rizd a megadott �tvonalat!");
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
