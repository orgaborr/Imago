package com.orgabor.imago;

import javax.imageio.ImageIO;
import java.io.*;
import java.nio.file.Files;

class SourceFolder extends File {
    //numbering of images
    private int serialNumber = 1;

    SourceFolder(String pathname) {
        super(pathname);
    }

    //copies all files from source to destination folder, exception handled in processFields @Controller
    void copyImgs(String destFolderPathname, String newFileName) throws IOException {
        for (File fileEntry : this.listFiles()) {
            //checks if entry is image file and skips if not
            if (fileEntry.isFile()) {
                if (ImageIO.read(fileEntry) == null) {
                    continue;
                }
            } else {
                continue;
            }
            File copy = new File(destFolderPathname,
                    nameFile(fileEntry, newFileName, serialNumber));
            Files.copy(fileEntry.toPath(), copy.toPath());
            serialNumber++;
        }
        serialNumber -= 1;
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
