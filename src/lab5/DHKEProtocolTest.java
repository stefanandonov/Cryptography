package lab5;
import java.security.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

class DHKEProtocolPublic {
	public static BigInteger p;
	public static BigInteger g;
	
	public DHKEProtocolPublic (long p, long g) {
		DHKEProtocolPublic.p=BigInteger.valueOf(p);
		DHKEProtocolPublic.g=BigInteger.valueOf(g);		
	}	 
}

class DHKEProtocolA {
	
	public static DHKEProtocolPublic dhke;
	private static BigInteger a;
	public static BigInteger publicKey;
	private static BigInteger secretKey;
	
	public DHKEProtocolA(DHKEProtocolPublic dhkepub, long a){
		dhke = dhkepub;
		DHKEProtocolA.a=BigInteger.valueOf(a);
		publicKey = DHKEProtocolPublic.g.modPow(DHKEProtocolA.a, DHKEProtocolPublic.p);
		secretKey=BigInteger.valueOf(0);
	}
	
	public void recieveBKey (long Bkey){
		BigInteger bkey = BigInteger.valueOf(Bkey);
		secretKey = bkey.modPow(DHKEProtocolA.a, DHKEProtocolPublic.p);
	}
	
	public long getSecretKey() {
		return secretKey.longValue();
	}
	
	public long publishPublicKey() {
		return publicKey.longValue();
	}
	
	
}

class DHKEProtocolB {
	
	public static DHKEProtocolPublic dhke;
	private static BigInteger b;
	public static BigInteger publicKey;
	private static BigInteger secretKey;
	
	public DHKEProtocolB(DHKEProtocolPublic dhkepub, long b){
		dhke = dhkepub;
		DHKEProtocolB.b=BigInteger.valueOf(b);
		publicKey = DHKEProtocolPublic.g.modPow(DHKEProtocolB.b, DHKEProtocolPublic.p);
		secretKey=BigInteger.valueOf(0);
	}
	
	public void recieveAKey (long Akey){
		BigInteger akey = BigInteger.valueOf(Akey);
		secretKey = akey.modPow(DHKEProtocolB.b, DHKEProtocolPublic.p);
	}
	
	public long getSecretKey() {
		return secretKey.longValue();
	}
	
	public long publishPublicKey() {
		return publicKey.longValue();
	}
	
}

class BabyStepGiantStep {
	DHKEProtocolPublic dhke;
	BigInteger A;
	BigInteger ret;
	
	
	public BabyStepGiantStep (DHKEProtocolPublic dhke, BigInteger A, BigInteger B){
		this.dhke=dhke;
		this.A=A;
	}
	
	public static BigInteger sqrt(BigInteger m) {
		      //Uses the Newton method to find largest integer whose square does not exceed m
		      //We search for a zero of f(x)=x^2-p ==>  note that derivative f'(x)=2x
		      int diff=m.compareTo(BigInteger.ZERO);
		      //Throw an exception for negative arguments
		      if (diff<0) throw new IllegalArgumentException("Cannot compute square root of a negative integer!");
		      //Return 0 in case m is 0
		      if (diff==0) return BigInteger.valueOf(0);
		      BigDecimal two=new BigDecimal(2);
		      //Convert the parameter to a BigDecimal
		      BigDecimal n=new BigDecimal(m);
		      //Begin with an initial guess-the square root will be half the size of m
		      //Make a byte array at least that long, & set bit in the high order byte
		      byte[] barray=new byte[m.bitLength()/16+1];
		      barray[0]=(byte)255;
		      //This is the first guess-it will be too high
		      BigDecimal r=new BigDecimal(new BigInteger(1,barray));
		      //Next approximation is computed by taking r-f(r)/f'(r)
		      r=r.subtract(r.multiply(r).subtract(n).divide(r.multiply(two),BigDecimal.ROUND_UP));
		      //As long as our new approximation squared exceeds m, we continue to approximate
		      while (r.multiply(r).compareTo(n)>0) {
		         r=r.subtract(r.multiply(r).subtract(n).divide(r.multiply(two),BigDecimal.ROUND_UP));
		      }
		      return r.toBigInteger();
		   }
	
	public static BigInteger logBabyStepGiantStep(BigInteger base, BigInteger residue, BigInteger modulus) {
	      //This algorithm solves base^x = residue (mod modulus) for x using baby step giant step
	      BigInteger m=sqrt(modulus).add(BigInteger.ONE);
	      //Use a hash table to store the entries-use Java Hashtable class
	      Hashtable h=new Hashtable();
	      BigInteger basePow=BigInteger.valueOf(1);
	      //Build the hash table base^j is the key, index j is the value
	      for (BigInteger j=BigInteger.valueOf(0);j.compareTo(m)<0;j=j.add(BigInteger.ONE)) {
	         h.put(basePow,j);
	         basePow=basePow.multiply(base).mod(modulus);
	      }
	      //Compute an inverse of base^m modulo p
	      BigInteger basetotheminv=base.modPow(m,modulus).modInverse(modulus);
	      BigInteger y=new BigInteger(residue.toByteArray());
	      //Search the hashtable for a base^j such that y=base^j for some j
	      BigInteger target;
	      for (BigInteger i=BigInteger.valueOf(0);i.compareTo(m)<0;i=i.add(BigInteger.ONE)) {
	         target = (BigInteger)h.get(y);
	         if (target!=null) return i.multiply(m).add(target);
	         y=y.multiply(basetotheminv).mod(modulus);
	      }
	      throw new NoSuchElementException("No solution");
	   }	
}

public class DHKEProtocolTest {
	
	public static void main(String [] args){
		DHKEProtocolPublic dhkepub = new DHKEProtocolPublic(2038074743,1000);
			
		
		DHKEProtocolB bob;
		bob = new DHKEProtocolB(dhkepub,7);
		System.out.println(bob.publishPublicKey());
		bob.recieveAKey(2007280509);
		System.out.println(bob.getSecretKey());
		
		
		
		BabyStepGiantStep bsgs = new BabyStepGiantStep(dhkepub,BigInteger.valueOf(2007280509), BigInteger.valueOf(159047246));
		BigInteger a = null;
		try{
			System.out.println(a = BabyStepGiantStep.logBabyStepGiantStep(dhkepub.g, BigInteger.valueOf(2007280509), dhkepub.p));
		}
		catch (Exception e) {
			e.printStackTrace(System.out);
		}
		
		System.out.println(BigInteger.valueOf(159047246).modPow(a, dhkepub.p));
		
		
	}
	
}
