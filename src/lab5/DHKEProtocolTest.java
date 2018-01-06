package lab5;

import java.math.BigInteger;

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
	
}

public class DHKEProtocolTest {
	
	public static void main(String [] args){
		
		DHKEProtocolPublic dhkepub = new DHKEProtocolPublic(29,2);
		DHKEProtocolA alice = new DHKEProtocolA(dhkepub,5);
		alice.recieveBKey(7);
		System.out.println(alice.getSecretKey());
		
		DHKEProtocolB bob = new DHKEProtocolB(dhkepub,12);
		bob.recieveAKey(3);
		System.out.println(bob.getSecretKey());
		
	}
	
}
