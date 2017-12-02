package lab3;
import java.util.*;
import java.math.*;
import java.io.*;
import java.nio.ByteBuffer;

public class DESTester {

	public static long messageToLong (String M){
		/** 
		 * Converts a message @param M to a 64 bit representation as a long variable.
		 * */		
		long ret = 0;		
		for (int i=0;i<M.length();i++){
			long bitRepresentation = Byte.toUnsignedLong((byte) M.toCharArray()[M.length()-i-1]);
			bitRepresentation=bitRepresentation<<(i*8);
			ret = ret ^ bitRepresentation;			
		}		
		return ret;		
	}
	
	public static String longToMessage (long l){
		/**
		 * Converts a long variable that represents a 64bit block into the String message that corresponds to it.
		 */
		StringBuilder sb = new StringBuilder();		
		long mask = 0x00000000000000FF;
		
		for (int i=0;i<8;i++){
			long ll = l & mask;
			ll = ll>>(i*8);
			char c = (char) ll;
			sb.append(c);
			mask = mask<<8;		
		}
		
		return sb.reverse().toString();		
	}

	public static void main(String[] args) {
		
		
		DES des = new DES();
		
		DES.NUM_OF_ROUNDS=1;
		//String M = "KRIPTOGR";
		
		//1. 
		/*long Mencrypted = des.encrypt(messageToLong(M),1234568999);
		String C = longToMessage(Mencrypted);		
		String Cbits = Long.toBinaryString(Mencrypted);
		System.out.println(C);
		System.out.println(Cbits);
		
		long Cdecrypted = des.decrypt(messageToLong(C),1234568999);
		String M1 = longToMessage(Cdecrypted);
		System.out.println(M1);
		System.out.println(Long.toBinaryString(Cdecrypted));*/
		
		//2
		/*DES.NUM_OF_ROUNDS=1;
		long M = 0;
		long K = 0;
		System.out.println(Long.toBinaryString(des.encrypt(M, K)));
		*/
		//3
		/*long M = Long.MAX_VALUE;
		long K = Long.MAX_VALUE;
		System.out.println(Long.toBinaryString(des.encrypt(M, K)));*/
		
		//4
		/*long M = 1;
		long K = 0;
		System.out.println(String.format("%64s",Long.toBinaryString(M) ));
		System.out.println(Long.toBinaryString(des.encrypt(M, K)));*/
		
		//d)

		/*DES.NUM_OF_ROUNDS=2;
		long M=0;
		long M1=1;
		long K=0;
		System.out.println(Long.toBinaryString(des.encrypt(M, K)));
		System.out.println(Long.toBinaryString(des.encrypt(M1, K)));*/
		
		//gj)
		/*DES.NUM_OF_ROUNDS=16;
		long M=0;
		long M1=1;
		long K=0;
		System.out.println(Long.toBinaryString(des.encrypt(M, K)));
		System.out.println(Long.toBinaryString(des.encrypt(M1, K)));*/
		
		//zadaca 2
		DES.NUM_OF_ROUNDS=16;
		long M=0xFFFABCD;
		long K=0;
		System.out.println(Long.toBinaryString(des.encrypt(M, K)));
		
	}

}
