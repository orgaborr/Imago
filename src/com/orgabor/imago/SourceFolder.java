package com.orgabor.imago;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class SourceFolder extends File {
    private int serialNumber = 1;

    SourceFolder(String pathname) {
        super(pathname);
    }

    //copies all files from source to destination folder, exception handled in processFields @Controller
    int copyImgs(String destFolderPathname, String newFileName) throws IOException {
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
            ImageIO.write(bImg, getExtension(fileEntry), copy);
            serialNumber++;
        }
        serialNumber -= 1;
        return serialNumber;
    }

    //reads image file and returns BufferedImage, called in copyImgs,
    // exception handled in processFields @Controller
    private BufferedImage readImg(File file) throws IOException {
        BufferedImage img = null;
        img = ImageIO.read(file);
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
}
