package lab5;

import java.math.BigInteger;

public class DHKEProtocolB {
	
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