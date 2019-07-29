package com.orgabor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	private static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		System.out.println("Üdv a RenameForAnyában! :)");

//		//asking for a source dir
//		System.out.println("Add meg a forrásmappa helyét: ");
//		String sourceDir = sc.nextLine();
//
//		//asking for destination dir
//		System.out.println("Add meg a célmappát: ");
//		String destDir = sc.nextLine();
//		
//		//asking for a common name for the renamed files
//		System.out.println("Mi legyen a fájlok közös neve? (pl. dátum-helyszín)");
//		String commonName = sc.nextLine();
		
		//closing scanner, the program runs on it's own from here
		sc.close();
		System.out.println("Köszi, a fájlok feldolgozása megkezdõdött!");
		
		//initializing source and destination folders
		File sourceFolder = new File("C:\\Users\\Gábor\\Desktop\\testRes");
		File destFolder = new File("C:\\Users\\Gábor\\Desktop\\testDest");
		if(!destFolder.exists()) {
			destFolder.mkdirs();
		}
		
//		for(File fileEntry : sourceFolder.listFiles()) {
//			System.out.println(fileEntry.getName());
//		}
	}
	
	public static void copyImgs(File source, File destination, String newFileName) {
		
	}

}
