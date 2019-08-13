package com.orgabor.imago;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.File;

public class Controller {
    @FXML
    private TextField sourceTextField;
    @FXML
    private TextField destTextField;
    @FXML
    private TextField nameTextField;

    public boolean startProcessing() {
        String sourceDir = sourceTextField.getText();
        String destDir = destTextField.getText();
        String name = nameTextField.getText();
        
        if(new File(sourceDir).exists()) {
            if(checkDestFolder(destDir)) {
                if (new SourceFolder(sourceDir).copyImgs(destDir, name)) {
                    return true;
                }
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
