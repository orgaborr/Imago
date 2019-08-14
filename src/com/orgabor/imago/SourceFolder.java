package com.orgabor.imago;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class SourceFolder extends File {
    private int serialNumber = 1;
    //exception messages will be saved here for printMessage in processFields @Controller
    private String exceptionMessage = null;

    SourceFolder(String pathname) {
        super(pathname);
    }

    //copies all files from source to destination folder
    int copyImgs(String destFolderPathname, String newFileName) {
        for (File fileEntry : this.listFiles()) {
            //checks if entry is image file and skips if not
            if (fileEntry.isFile()) {
                if (readImg(fileEntry) == null) {
                    continue;
                }
            } else {
                continue;
            }

            BufferedImage bImg = readImg(fileEntry);
            File copy = new File(destFolderPathname,
                    nameFile(fileEntry, newFileName, serialNumber));
            try {
                ImageIO.write(bImg, getExtension(fileEntry), copy);
            } catch (IOException e) {
                exceptionMessage = e.getMessage();
            }
            serialNumber++;
        }
        serialNumber -= 1;
        return serialNumber;
    }

    //reads image file and returns BufferedImage
    private BufferedImage readImg(File file) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(file);
        } catch (IOException e) {
            exceptionMessage = e.getMessage();
        }

        return img;
    }

    //names the new file with numbering and extension
    private String nameFile(File original, String fileName, int serialNumber) {
        String number = "_" + String.format("%05d", serialNumber);
        String name = fileName + number + "." + getExtension(original);
        return name;
    }

    //gets file extension
    private String getExtension(File file) {
        String fileName = file.getName();
        String extension = fileName.substring((fileName.length() - 3), fileName.length());
        return extension;
    }

    int getSerialNumber() {
        return serialNumber;
    }

    String getExceptionMessage() {
        return exceptionMessage;
    }
}
