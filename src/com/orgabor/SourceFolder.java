package com.orgabor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SourceFolder extends File {

	public SourceFolder(String pathname) {
		super(pathname);
	}
	
	//copies all files from source to destination folder
	public void copyImgs(String destFolderPathname, String newFileName) {
		checkDestFolder(destFolderPathname);
		int serialNumber = 1;
		
		SourceFolder sourceFolder = new SourceFolder(this.getPath());
		for(File fileEntry : sourceFolder.listFiles()) {
			BufferedImage bImg = readImg(fileEntry);
			File copy = new File(destFolderPathname + "\\" + nameFile(newFileName, serialNumber));
			try {
				ImageIO.write(bImg, getExtension(fileEntry), copy);
			} catch (IOException e) {
				e.printStackTrace();
			}
			serialNumber++;
		}
	}
	
	//reads img from SourceFolder
	private BufferedImage readImg(File file) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return img;
	}
	
	//checks if destination folder exists and creates it if not
	private void checkDestFolder(String path) {
		File destFolder = new File(path);
		if(!destFolder.exists()) {
			destFolder.mkdirs();
			System.out.println("Célmappa létrehozva: " + path);
		}
	}

	//names the new file with numbering and extension
	private String nameFile(String fileName, int serialNumber) {
		String number = "_" + String.format("%05d", serialNumber);
		String name = fileName + number;
		return name;
	}

	//gets file extension
	private String getExtension(File file) {
		String fileName = file.getName();
		String extension = fileName.substring((fileName.length()-4), (fileName.length()-1));
		return extension;
	}

}
