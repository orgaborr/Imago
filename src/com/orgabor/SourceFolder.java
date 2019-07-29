package com.orgabor;

import java.io.File;

public class SourceFolder extends File {

	public SourceFolder(String pathname) {
		super(pathname);
	}
	
	//copies all files from source to destination folder
	public void copyImgs(String destFolderPathname, String newFileName) {
		
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
	private String nameFile(File file, String fileName, int serialNumber) {
		
	}
	
	//gets file extension
	private String getExtension(File file) {
		String fileName = file.getName();
		String extension = fileName.substring((fileName.length()-4), (fileName.length()-1));
		return "." + extension;
	}
}
