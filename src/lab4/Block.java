package lab4;

public class Block {
	/**
	 * A class that represents one block for the Block chiper.
	 */
	
	private String block; 
	//private long iv;
	
	public Block(String block){
		this.block=block;
		//this.iv = iv;
	}
	
	

	public String ceasarEncryption (String m, int key){
		StringBuilder sb = new StringBuilder();
		
		for (int i=0;i<m.length();i++){
			char c = m.charAt(i);
			
			if ('a'<=c && c<='z'){
				sb.append((char)((c-'a'+key)%26+'a'));
			}
			
			else if ('A'<=c && c<='Z'){
				sb.append((char)((c-'A'+ key)%26+'A'));
				
			}
			else if ('0' <= c && c<='9'){
				sb.append((char) ((c-'0'+key)%10+'0'));
			}
			else if (' '<=c && c<='/'){
				sb.append((char) ((c-' '+key)%16+' '));
			}
			else 
				sb.append(c);
		}		
		return sb.toString();	
	}
	
	public String ceasarDecryption(String m, int key){
		StringBuilder sb = new StringBuilder();
		for (int i=0;i<m.length();i++){
			char c = m.charAt(i);
			
			if ('a'<=c && c<='z'){
				char c1 = (char)((c-'a'-key)%26+'a');
				if (c1<'a')
					c1=(char) (c1+'z'-'a'+1);
				sb.append(c1);
			}
			
			else if ('A'<=c && c<='Z'){
				char c1 = (char)((c-'A'-key)%26+'A');
				if (c1<'A')
					c1=(char) (c1+'Z'-'A'+1);
				sb.append(c1);
				
			}
			else if ('0' <= c && c<='9'){
				char c1 = (char)((c-'0'-key)%10+'0');
				if (c1<'0')
					c1=(char) (c1+'9'-'0'+1);
				sb.append(c1);
			}
			else if (' '<=c && c<='/'){
				char c1 = (char)((c-' '-key)%16+' ');
				if (c1<' ')
					c1=(char) (c1+'/'-' '+1);
				sb.append(c1);
			}
			else 
				sb.append(c);
		}		
		return sb.toString();
	}
	
	public String encrypt (int key){
			return ceasarEncryption(block,key);
	}
	
	public String encryptCBC (int key, long iv){
		long input = iv ^ Block.StrToLong(block);
		return ceasarEncryption(LongToStr(input),key);		
	}
	
	public String decrypt(int key) {
			return ceasarDecryption(block,key);	
	}
	
	public String decryptCBC(int key, long iv){
		long ret = iv ^ StrToLong(ceasarDecryption(block,key));
		return LongToStr(ret);
	}
	
	public static long StrToLong (String s){
		/** 
		 * Converts a message @param M to a 64 bit representation as a long variable.
		 * */		
		long ret = 0;		
		for (int i=0;i<s.length();i++){
			long bitRepresentation = Byte.toUnsignedLong((byte) s.toCharArray()[s.length()-i-1]);
			bitRepresentation=bitRepresentation<<(i*8);
			ret = ret ^ bitRepresentation;			
		}		
		return ret;	
	}
	
	public static String LongToStr(long l){
		/**
		 * Converts a 64 bit representation as a long variable @param l to a 8 characters long string
		 * 
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
	
	public String toString() {
		return block;
	}
}


