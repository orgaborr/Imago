package com.orgabor;

import java.util.Scanner;

public class Main {

	private static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		System.out.println("Üdv az Imago-ban!");

		//asking for a source, a destination dir and a common new file name
		System.out.println("Add meg a forrásmappa helyét: ");
		String sourceDir = sc.nextLine();
		System.out.println("Add meg a célmappát: ");
		String destDir = sc.nextLine();
		System.out.println("Mi legyen a fájlok közös neve? (pl. dátum-helyszín)");
		String commonName = sc.nextLine();
		
		//closing scanner, the program runs on it's own from here
		sc.close();
		System.out.println("A fájlok feldolgozása megkezdõdött");
		
		//initializing source and destination folders
		SourceFolder source = new SourceFolder(sourceDir);
		source.copyImgs(destDir, commonName);

	}
	

}
