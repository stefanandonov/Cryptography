package lab5;

import java.math.BigInteger;

public class DHKEProtocolA {
	
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