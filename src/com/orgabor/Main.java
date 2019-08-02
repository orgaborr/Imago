package com.orgabor;

import java.io.File;
import java.util.Scanner;

public class Main {

	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("�dv az Imago-ban!");

		while(true) {
			String sourceDir = null;
			String destDir = null;
			
			//asking for and checking source folder
			boolean isValid = false;
			while(!isValid) {
				System.out.println("Add meg a forr�smappa hely�t: ");
				sourceDir = sc.nextLine();
				if(new File(sourceDir).exists()) {
					isValid = true;
				} else {
					System.out.println("�rv�nytelen forr�smappa. Ellen�rizd a forr�s �tvonal�t!");
				}
			}
			
			//asking for and checking destination folder
			isValid = false;
			while(!isValid) {
				System.out.println("Add meg a c�lmapp�t: ");
				destDir = sc.nextLine();
				if(checkDestFolder(destDir)) {
					isValid = true;
				}
			}

			//asking for a common new file name
			System.out.println("Mi legyen a f�jlok k�z�s neve? (pl. d�tum-helysz�n)");
			String commonName = sc.nextLine();

			System.out.println("A f�jlok feldolgoz�sa megkezd�d�tt");

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
				System.out.println("C�lmappa l�trehozva: " + path);
				return true;
			}
			System.out.println("C�lmappa l�trehoz�sa sikertelen. Ellen�rizd a megadott �tvonalat!");
			return false;
		}
		return true;
	}

}
