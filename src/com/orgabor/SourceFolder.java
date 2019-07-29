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
	
}
