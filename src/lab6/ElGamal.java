package lab6;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ElGamal {
	
	public static BigInteger p;
	public static BigInteger a;
	public static BigInteger d;
	public static BigInteger b;
	
	public ElGamal (BigInteger p, BigInteger a, BigInteger d){
		/**
		 * A constructor for generating the public parameters and for the person who 
		 * signs the message
		 */
		ElGamal.p=p;
		ElGamal.a=a;
		ElGamal.d=d;
		generateB();
	}
	
	private void generateB () {
		b = a.modPow(d, p);
	}
	
	public static List<BigInteger> publishPublicParametars() {
		/**
		 * Publishes the public parameters p,a,b as a List of three BigInteger-s
		 */
		List<BigInteger> publicParameters = new ArrayList<>();
		publicParameters.add(p);
		publicParameters.add(a);
		publicParameters.add(b);
		return publicParameters;
	}
	
	public ElGamal (BigInteger p, BigInteger a, BigInteger b, boolean validator){
		/**
		 * Constructor for the person who validates the signed message.
		 */
		this.p=p;
		this.a=a;
		this.b=b;
		d = null;
	}
	
	public static String getSHA1 (String m) throws NoSuchAlgorithmException{
		MessageDigest mDigest = MessageDigest.getInstance("SHA1");
		byte [] res = mDigest.digest(m.getBytes());
		StringBuffer sb = new StringBuffer();
		for (int i=0;i<res.length;i++){
			sb.append(Integer.toString((res[i] & 0xff)+0x100,16));
		}
		
		return sb.toString();
	}
	
	public static BigInteger generateKencryption(){
		List<BigInteger> possibleValues = new ArrayList<>();
		for (int i=1;i<p.intValue();i++){
			if (BigInteger.valueOf(i).gcd(p.subtract(BigInteger.valueOf(1))).equals(BigInteger.valueOf(1))){
				possibleValues.add(BigInteger.valueOf(i));
			}
		}	
		
		Random rdm = new Random();
		return possibleValues.get(rdm.nextInt(possibleValues.size()));
	}
	
	public static List<String> signTheMessage (String m) throws NoSuchAlgorithmException {
		/**
		 * Return a List of three BigInteger-s SHA1(M), R, S
		 */
		List<String> result = new ArrayList<>();
		BigInteger kE = generateKencryption();
		BigInteger r = a.modPow(kE, p);
		BigInteger s;
	    String shaM = getSHA1(m);
		BigInteger shaInteger = new BigInteger(shaM,16);
		s = shaInteger.subtract(d.multiply(r));
		s = s.multiply(kE.modInverse(p.subtract(BigInteger.valueOf(1))))
				.mod(p.subtract(BigInteger.valueOf(1)));
		
		result.add(m);
		result.add(r.toString());
		result.add(s.toString());
		
		
		return result;
	}
	
	public static boolean verifySignature (String x, String r, String s){
		BigInteger X = new BigInteger(x,16);
		BigInteger R = new BigInteger(r);
		BigInteger S = new BigInteger(s);
		
		BigInteger br = b.modPow(R, p);
		BigInteger rx = R.modPow(X, p);
		BigInteger t = br.multiply(rx).mod(p);
		BigInteger ax = a.modPow(X, p);
		
		System.out.println(t.toString() + " " + ax.toString());
		if (t==ax)
			return true;
		else 
			return false;
	}
}