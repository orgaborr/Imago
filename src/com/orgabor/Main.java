package com.orgabor;

import java.io.File;
import java.util.Scanner;

public class Main {

	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("Üdv az Imago-ban!");

		while(true) {
			String sourceDir = null;
			String destDir = null;
			
			//asking for and checking source folder
			boolean isValid = false;
			while(!isValid) {
				System.out.println("Add meg a forrásmappa helyét: ");
				sourceDir = sc.nextLine();
				if(new File(sourceDir).exists()) {
					isValid = true;
				} else {
					System.out.println("Érvénytelen forrásmappa. Ellenõrizd a forrás útvonalát!");
				}
			}
			
			//asking for and checking destination folder
			isValid = false;
			while(!isValid) {
				System.out.println("Add meg a célmappát: ");
				destDir = sc.nextLine();
				if(checkDestFolder(destDir)) {
					isValid = true;
				}
			}

			//asking for a common new file name
			System.out.println("Mi legyen a fájlok közös neve? (pl. dátum-helyszín)");
			String commonName = sc.nextLine();

			System.out.println("A fájlok feldolgozása megkezdõdött");

			//initializing source folder
			SourceFolder source = new SourceFolder(sourceDir);
			if(source.copyImgs(destDir, commonName)) {
				break;
			}
		}

		sc.close();
	}
	
	//checks if destination folder exists and creates it if not
	private static boolean checkDestFolder(String path) {
		File destFolder = new File(path);
		if(!destFolder.exists()) {
			destFolder.mkdirs();
			if(destFolder.exists()) {
				System.out.println("Célmappa létrehozva: " + path);
				return true;
			}
			System.out.println("Célmappa létrehozása sikertelen. Ellenõrizd a megadott útvonalat!");
			return false;
		}
		return true;
	}

}
