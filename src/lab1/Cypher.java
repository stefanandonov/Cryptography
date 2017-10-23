package lab1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

class CMessage {
	public HashMap<Character, Character> permutation;
	public String original;
	public String ciphered;
	
	public CMessage (String o){
		original = o;		
		permutation = new HashMap<Character, Character>();		
		Character [] alphabet = {'А','Б','В','Г','Д','Ѓ','Е','Ж','З','Ѕ','И','Ј','К','Л','Љ','М','Н','Њ','О','П','Р','С','Т','Ќ','У','Ф','Х','Ц','Ч','Џ','Ш'};
		Character [] newAlphabet = {'Ш','Р','С','У','Ќ','Х','Џ','А','В','Д','Ѓ','Б','Г','И','Е','З','Ѕ','К','Ј','Ц','Ч','Ф','Л','Љ','Ж','Н','Њ','М','Т','О','П'};
		
		for (int i=0;i<31;i++){
			permutation.put(alphabet[i], newAlphabet[i]);
			
		}	
	}
	
	public void encryption (){
		
		StringBuilder sb = new StringBuilder();		
		for (char c : original.toCharArray()){
			sb.append(permutation.get(c));
			//System.out.println(c+" "+permutation.get(c));
		}		
		ciphered = sb.toString();
	}
	
	public String getChipered() {
		return ciphered;
	}	
}

public class Cypher {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		String s = sc.next();		
		CMessage cmessage = new CMessage(s);		
		cmessage.encryption();		
		System.out.println(cmessage.getChipered());	

	}

}
