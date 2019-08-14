package com.orgabor.imago;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.io.File;

public class Controller {
    @FXML
    private TextField sourceField;
    @FXML
    private TextField destField;
    @FXML
    private TextField nameField;

    //checks the content of TextFields and calls copyImgs @SourceFolder
    public boolean startProcessing() {
        if(new File(sourceField.getText()).exists()) {
            if(checkDestFolder(destField.getText())) {
                if(!nameField.getText().equals("")) {
                    if (new SourceFolder(sourceField.getText())
                            .copyImgs(destField.getText(), nameField.getText())) {
                        return true;
                    }
                    return false;
                }
                System.out.println("Adj meg nevet az �j k�pf�jloknak!");
                return false;
            }
            return false;
        }
        System.out.println("�rv�nytelen forr�smappa. Ellen�rizd a forr�s �tvonal�t!");
        return false;
    }

    //checks if destination folder exists and creates it if not
    private static boolean checkDestFolder(String path) {
        File destFolder = new File(path);
        if (!destFolder.exists()) {
            destFolder.mkdirs();
            if (destFolder.exists()) {
                System.out.println("C�lmappa l�trehozva: " + path);
                return true;
            }
            System.out.println("C�lmappa l�trehoz�sa sikertelen. Ellen�rizd a megadott �tvonalat!");
            return false;
        }
        return true;
    }
}
