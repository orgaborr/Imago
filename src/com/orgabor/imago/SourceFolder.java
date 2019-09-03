package com.orgabor.imago;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;

class SourceFolder extends File {

    SourceFolder(String pathname) {
        super(pathname);
    }

    //copies all files from source to destination folder, exception handled in processFields @Controller
    int copyImgs(String destFolderPathname, String newFileName) throws IOException {
        int serialNumber = 1;
        int filesCopied = 0;
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
            try {
                Files.copy(fileEntry.toPath(), copy.toPath());
                filesCopied++;
            } catch(FileAlreadyExistsException e) {
                continue;
            } finally {
                serialNumber++;
            }

        }
        return filesCopied;
    }

    int imgCounter() throws IOException {
        int count = 0;
        for(File entry : this.listFiles()) {
            if (entry.isFile()) {
                if (ImageIO.read(entry) == null) {
                    continue;
                }
            } else {
                continue;
            }
            count++;
        }
        return count;
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
}
