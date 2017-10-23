package lab1;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;
import static java.util.stream.Collectors.*;

import java.util.Collections;
import java.util.Comparator;

import static java.util.Map.Entry.*;

public class FrequencyCounter {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner (System.in);
		Character [] alphabet = {'А','Б','В','Г','Д','Ѓ','Е','Ж','З','Ѕ','И','Ј','К','Л','Љ','М','Н','Њ','О','П','Р','С','Т','Ќ','У','Ф','Х','Ц','Ч','Џ','Ш'};
		HashMap<Character,Double> letterFr = new HashMap<>();
		HashMap<String,Double> twoLettersFr = new HashMap<>();
		HashMap<String,Double> twoLettersFr1 = new HashMap<>();
		HashMap<String,Double> couplesSorted = new HashMap<>();
		HashMap<Character,Double> lettersSorted = new HashMap<>();
		double numOfLetters = 0;
		double numOfCouples = 0;
		String cipheredMessage = sc.next();
		
		for (char c : alphabet){ //broenje na bukvite
			int counter = 0;
			
			for (char c1 : cipheredMessage.toCharArray())
				if (c==c1)
					++counter;
			
			letterFr.put(c, (double) counter);
		}
		
		for (int i = 0; i < cipheredMessage.length()-1;i++){ //broenje na parovi bukvi
			StringBuilder sb = new StringBuilder();
			String couple = sb.append(cipheredMessage.charAt(i)).append(cipheredMessage.charAt(i+1)).toString();

			Double count = twoLettersFr.get(couple);
			
			if (count == null){
				twoLettersFr.put(couple, 1.0);
			}
			else {
				twoLettersFr.remove(couple);
				twoLettersFr.put(couple, count+1);
			}			
		}
		
		numOfLetters = letterFr.values().stream().mapToDouble(x->x).sum();
		numOfCouples = twoLettersFr.values().stream().mapToDouble(x->x).sum();
		
		for (char c : alphabet){
			Double count = letterFr.get(c);
			
			if (count!=null){
				count/=numOfLetters;
				letterFr.remove(c);
				letterFr.put(c, count);
			}		
		}
		
		for (String s : twoLettersFr.keySet()){
			Double count = twoLettersFr.get(s);
			count/=numOfCouples;
			//twoLettersFr.remove(s);
			twoLettersFr1.put(s, count);				
		}
		
		lettersSorted = letterFr.entrySet()
				.stream()
				.sorted(Collections.reverseOrder(comparingByValue()))
				.collect(
						toMap(e->e.getKey(), e->e.getValue(), (e1,e2)->e2, LinkedHashMap::new));
		
		couplesSorted = twoLettersFr1.entrySet()
				.stream()
				.sorted(Collections.reverseOrder(comparingByValue()))
				.collect(
						toMap(e->e.getKey(), e->e.getValue(), (e1,e2)->e2, LinkedHashMap::new));
				
		System.out.println(lettersSorted.size()+" "+lettersSorted.toString());
		System.out.println(couplesSorted.size()+" "+couplesSorted.toString());
		
		
		
		
			
		
		
		
		
	}

}
