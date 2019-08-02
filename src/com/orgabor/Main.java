package com.orgabor;

import java.util.Scanner;

public class Main {

	private static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		System.out.println("�dv az Imago-ban!");

		//asking for a source, a destination dir and a common new file name
		System.out.println("Add meg a forr�smappa hely�t: ");
		String sourceDir = sc.nextLine();
		System.out.println("Add meg a c�lmapp�t: ");
		String destDir = sc.nextLine();
		System.out.println("Mi legyen a f�jlok k�z�s neve? (pl. d�tum-helysz�n)");
		String commonName = sc.nextLine();
		
		//closing scanner, the program runs on it's own from here
		sc.close();
		System.out.println("A f�jlok feldolgoz�sa megkezd�d�tt");
		
		//initializing source and destination folders
		SourceFolder source = new SourceFolder(sourceDir);
		source.copyImgs(destDir, commonName);

	}
	

}
