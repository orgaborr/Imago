package com.orgabor.imago;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.io.File;

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
        messageTextArea.setText("Imago: �dv az Imago-ban! :)");
    }

    //checks the content of TextFields and calls copyImgs @SourceFolder
    @FXML
    private boolean startProcessing() {
        if(new File(sourceField.getText()).exists()) {
            if(checkDestFolder(destField.getText())) {
                if(!nameField.getText().equals("")) {
                    if (new SourceFolder(sourceField.getText())
                            .copyImgs(destField.getText(), nameField.getText())) {
                        return true;
                    }
                    return false;
                }
                printMessage("Adj meg nevet az �j k�pf�jloknak!");
                return false;
            }
            return false;
        }
        printMessage("�rv�nytelen forr�smappa. Ellen�rizd a forr�s �tvonal�t!");
        return false;
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

    @FXML
    //prints messages to TextArea
    private void printMessage(String message) {
        messageTextArea.setText(messageTextArea.getText() + "\nImago: " + message);
    }
}
