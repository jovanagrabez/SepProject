package com.sep.nc.common;

import java.io.*;

public class PasswordChecker {


	public static boolean check10000FrequentlyUsed(String pass) {
		/*
		 * Provjera da li je password medju 10000 najkoriscenijih sifara
		 */
		File file = new File("C:\\Users\\Laptop\\Nina\\Fakultet\\Master\\SEP\\Projekat\\SEP\\SEP\\NC\\src\\main\\resources\\passwords.txt");

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String st;
			while ((st = br.readLine()) != null) {
				System.out.println(st);
				if(st.equals(pass)) {
					return false;
				}
			}

			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}


}
