package lab3;
import java.util.*;
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
		String M = "KRIPTOGR";
		
		//1. 
		long Mencrypted = des.encrypt(messageToLong(M),1234568999);
		String C = longToMessage(Mencrypted);		
		String Cbits = Long.toBinaryString(Mencrypted);
		System.out.println(C);
		System.out.println(Cbits);
		
		long Cdecrypted = des.decrypt(messageToLong(C),1234568999);
		String M1 = longToMessage(Cdecrypted);
		System.out.println(M1);
		System.out.println(Long.toBinaryString(Cdecrypted));
		
		
	}

}
