package lab6;

import lab5.*;
import lab4.*;
import java.util.*;
import java.math.BigInteger;
import java.security.*;

public class ElGamalTest {
	
	/**
	 * Helps to parse a integer from the block chiper
	 * @param x
	 * @return
	 */
	public static String removeSpaces (String x){
		StringBuilder sb = new StringBuilder();
		
		for (int i=0;i<x.length();i++){
			if (x.charAt(i)!=' ')
				sb.append(x.charAt(i));
		}
		
		return sb.toString();
	}
	
	public static void main (String [] args) throws NoSuchAlgorithmException{
				
		//1.Vospostavuvanje na DHKE javnite parametri
		DHKEProtocolPublic dhke = new DHKEProtocolPublic(167, 25);
		DHKEProtocolA alice = new DHKEProtocolA(dhke,5);
		//Alice ja isprakja vrednosta g^x
		Long gx = alice.publishPublicKey();
		String gX = gx.toString();
		
		DHKEProtocolB bob = new DHKEProtocolB(dhke,10);
		bob.recieveAKey(gx);
		System.out.println("1. Alice -> Bob: "+gx);
		
		//2.Bob gi kreira i isprakja g^y i Ek(Sb(g^x,g^y)); 
		Long gy = bob.publishPublicKey();
		String gY = gy.toString(); //g^y
		
		ElGamal elgamalBob = new ElGamal(BigInteger.valueOf(197),
				BigInteger.valueOf(50),BigInteger.valueOf(10));
		
		String gxgy = gX+","+gY;
		List<String> signatureBob = elgamalBob.signTheMessage(gxgy);
		
		System.out.println("2. Bob -> Alice: "+ gy + ", ");
		List<String> encryptedSignature = new ArrayList<String>(); //Ek(Sb(gx,gy))
		signatureBob.stream().forEach(part -> {
			BlockChiper bc = new BlockChiper(part,0);
			String enc = bc.encrypt((int) bob.getSecretKey());
			encryptedSignature.add(enc);
			System.out.println(enc);
		});
		
		
		
		
		//2.1 Alice ja prima porakata i pravi prvo dekripcija pa potoa i validiranje
		//na potpisot na Bob
		ElGamal elgamalAlice = new ElGamal(BigInteger.valueOf(197),
				BigInteger.valueOf(50),elgamalBob.b,true);
		List<String> decryptedSignature = new ArrayList<>();
		encryptedSignature.stream().forEach(part -> {
			BlockChiper bc = new BlockChiper(part,0);
			String dec = bc.transform((int)bob.getSecretKey(), TYPE.DECRYPTION);
			decryptedSignature.add(dec);
		});
		
		//validation 
		System.out.println("Validation of Bob's message: ");
		String gxgy1 = removeSpaces(decryptedSignature.get(0));
		String r = removeSpaces(decryptedSignature.get(1));
		String s = removeSpaces(decryptedSignature.get(2));
		
		if (gxgy1.equals(gxgy) || elgamalAlice.verifySignature(gxgy1, r, s)){
			System.out.println("The signature is verified");
			String [] parts = gxgy1.split(",");
			alice.recieveBKey(Long.parseLong(parts[1]));
		}
		else 
			System.out.println("The signature is not veriied");
		
		
		//3. Alice mu prakja na bob Ek(Sa(g^x,g^y))
		String gxgyAlice = alice.publishPublicKey()+","+bob.publishPublicKey();
		ElGamal elgamalAliceSignature = new ElGamal(BigInteger.valueOf(197),
				BigInteger.valueOf(50),BigInteger.valueOf(10));
		List<String> aliceSignature = elgamalAliceSignature.signTheMessage(gxgyAlice);
		
		System.out.println("3. Alice -> Bob: ");
		List<String> encryptedAliceSignature = new ArrayList<>();
		aliceSignature.stream().forEach(x -> {
			BlockChiper bc = new BlockChiper(x,0);
			String enc = bc.encrypt((int) alice.getSecretKey());
			encryptedAliceSignature.add(enc);
			System.out.println(enc);
		});
		
		ElGamal elgamalBobValidation = new ElGamal(BigInteger.valueOf(197),
				BigInteger.valueOf(50), ElGamal.b,true);
		List<String> decryptedAliceSignature = new ArrayList<>();
		encryptedAliceSignature.stream().forEach(part -> {
			BlockChiper bc = new BlockChiper(part,0);
			String dec = bc.transform((int)bob.getSecretKey(), TYPE.DECRYPTION);
			decryptedAliceSignature.add(dec);
		});
		
		System.out.println("Validation of Alice's message: ");
		String gxgy2 = removeSpaces(decryptedAliceSignature.get(0));
		String rBob = removeSpaces(decryptedAliceSignature.get(1));
		String sBob = removeSpaces(decryptedAliceSignature.get(2));
		
		if (gxgy2.equals(gxgy) || elgamalBobValidation.verifySignature(gxgy2, rBob, sBob)){
			System.out.println("The signature is verified");
			
		}
		else 
			System.out.println("The signature is not veriied");
		
		BlockChiper bc = new BlockChiper("12,14",0);
		bc.encrypt(20);
		System.out.println(bc.encrypt(115));
		
	}

}
