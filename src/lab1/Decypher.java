package lab1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Scanner;

public class Decypher {
	
	public static void LetterDecription () throws FileNotFoundException, UnsupportedEncodingException {
		File output = new File("C:\\Users\\Stefan\\Desktop\\Kriptografija\\izlez.txt");
		PrintWriter pw = new PrintWriter(output,"UTF-8");
		Scanner sc = new Scanner(System.in);
		HashMap<Character,Character> permutation = new HashMap<>();
		String text = sc.nextLine();
		for (int i=0;i<31;i++){
			String s = sc.nextLine();
			String [] letters = s.split("\\s+");
			permutation.put(letters[0].charAt(0), letters[1].charAt(0));
		}
		
		StringBuilder sb = new StringBuilder();
		
		for (char c : text.toCharArray()) {
			char t = permutation.get(c);
			sb.append(t);
		}
		
		pw.print(sb.toString());		
		pw.flush();	
		
		//System.out.println(text);
		sc.close();		
	}

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		
		LetterDecription();
	}

}
