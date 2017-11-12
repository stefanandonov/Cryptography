package lab2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class RC4 {
	
	int[] S;
	int j;
	
	public RC4 () {
		S = new int[256];		
		j=0;
	}	
	
	public void KSA (int [] key) {
		IntStream.range(0, 256).forEach(i -> S[i]=i);
		j=0;
		IntStream.range(0, 256).forEach(i -> {
			j = (j+S[i]+key[i%(key.length)])%256;
			int pom = S[i];
			S[i]=S[j];
			S[j]=pom;
		});		
	}
	
	public List<Integer> PRGA (int [] key, int offset) {
		KSA(key);
		List<Integer> keystream = new ArrayList<>();
		int i=0;
		j=0;
		int [] array = new int[16+offset];
		IntStream.range(0, 16+offset).forEach(x -> array[x]=x);
		for (int x : array){
			i = (i+1)%256;
			j = (j+S[i]) % 256;
			int pom = S[i];
			S[i]=S[j];
			S[j]=pom;
			
			if (x >= offset){
				keystream.add(S[(S[i]+S[j])%256]);
			}
		}		
		return keystream;		
	}
	
	public String encryption (int [] key, String original){
		List<Integer> keystream = this.PRGA(key, original.length());
		List<Integer> encryptedM = new ArrayList<>();
		IntStream.range(0, original.length()).forEach(i -> encryptedM.add(keystream.get(i)^original.charAt(i)));
		StringBuilder sb = new StringBuilder();
		encryptedM.stream().forEach(letter -> sb.append((char)letter.intValue()));
		return sb.toString();
	}
	
	public String decryption (int [] key, String encrypted) {
		List<Integer> keystream = this.PRGA(key, encrypted.length());
		List<Integer> originalM = new ArrayList<>();
		IntStream.range(0, encrypted.length()).forEach(i -> originalM.add(keystream.get(i)^encrypted.charAt(i)));
		StringBuilder sb = new StringBuilder();
		originalM.stream().forEach(letter -> sb.append((char)letter.intValue()));
		return sb.toString();
	}	
	
}

public class RC4ChiperTester {

	public static void main(String[] args) {
		
		int [][] keys = {{0x01,0x02,0x03,0x04,0x05},
				{0x01,0x02,0x03,0x04,0x05,0x06,0x07},
				{0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08},
				{0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x09,0x0a},
				{0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x09,0x0a,0x0b,0x0c,0x0d,0x0e,0x0f,0x10},
				{0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x09,0x0a,0x0b,0x0c,0x0d,0x0e,0x0f,0x10,0x11,0x12,0x13,0x14,0x15,0x16,0x17,0x18},
				{0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x09,0x0a,0x0b,0x0c,0x0d,0x0e,0x0f,0x10,0x11,0x12,0x13,0x14,0x15,0x16,0x17,0x18,0x19,0x1a,0x1b,0x1c,0x1d,0x1e,0x1f,0x20},
				
		 };
		int [] offsets = {0, 16, 240, 256, 496, 512, 752, 768, 1008, 1024, 1520, 1536, 2032, 2048, 3056, 3072, 4080, 4096};
		RC4 rc4 = new RC4();
		
		for (int [] key : keys){
			System.out.print("Key: ");
			for (int k : key) {
				System.out.print(k+" ");
			}
			System.out.println();
			for (int offset : offsets){
				List<Integer> stream = rc4.PRGA(key, offset);
				System.out.print(offset+" : ");
				for (int s : stream){
					System.out.print(Integer.toHexString(s)+" ");
				}
				System.out.println();
			}
			System.out.println("\n");
		}
		
		String message = "Stefan";
		System.out.println("Original message: "+message);
		String encrypted,decrypted;
		encrypted = rc4.encryption(keys[0], message);
		decrypted = rc4.decryption(keys[0], encrypted);
		System.out.println(encrypted + " " + decrypted);
	}

}
