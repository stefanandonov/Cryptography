package lab5;

import java.math.BigInteger;

public class DHKEProtocolPublic {
	public static BigInteger p;
	public static BigInteger g;
	
	public DHKEProtocolPublic (long p, long g) {
		DHKEProtocolPublic.p=BigInteger.valueOf(p);
		DHKEProtocolPublic.g=BigInteger.valueOf(g);		
	}	 
}