package com.sep.nc.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class PasswordChecker {


	public static boolean check10000FrequentlyUsed(String pass) throws IOException {
		/*
		 * Provjera da li je password medju 10000 najkoriscenijih sifara
		 */
		File file = new File("src/main/resources/passwords.txt");

		BufferedReader br = new BufferedReader(new FileReader(file));

		String st;
		while ((st = br.readLine()) != null) {
			System.out.println(st);
			if(st.equals(pass)) {
				return false;
			}
		}
		
		return true;
	}


}
