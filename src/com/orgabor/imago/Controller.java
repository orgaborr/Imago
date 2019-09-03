package com.orgabor.imago;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
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

    //sets initial message in TextArea and sets goButton disabled
    @FXML
    public void initialize() {
        goButton.setDisable(true);
        printMessage("�dv az Imago-ban! :) T�ltsd ki a mez�ket �s katt a 'Mehet' gombra.");
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
                printMessage("C�lmappa l�trehozva: " + path);
                return true;
            }
            printMessage("C�lmappa l�trehoz�sa sikertelen. Ellen�rizd a megadott �tvonalat!");
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

    private class CopyService extends Service<Integer> {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    int count = 0;
                    if (new File(sourceField.getText()).exists()) {
                        if (checkDestFolder(destField.getText())) {
                            printMessage("A feldolgoz�s megkezd�d�tt\n...");
                            SourceFolder sourceFolder = new SourceFolder(sourceField.getText());
                            try {
                                if ((count = sourceFolder.copyImgs(destField.getText(), nameField.getText())) > 0) {
                                    printMessage(count + " k�p m�sol�sa befejez�d�tt.");
                                } else if (sourceFolder.imgCounter() > 0) {
                                    printMessage("A f�jlok m�r szerepelnek a mapp�ban.");
                                } else {
                                    printMessage("A megadott forr�smapp�ban nincsenek k�pf�jlok.");
                                }
                            } catch (IOException e) {
                                printMessage(e.toString());
                            }
                        }
                    } else {
                        printMessage("�rv�nytelen forr�smappa. Ellen�rizd a forr�s �tvonal�t!");
                    }
                    return count;
                }
            };
        }
    }
}
