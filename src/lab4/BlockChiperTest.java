package lab4;

import java.util.ArrayList;
import java.util.List;

enum TYPE {
	ENCRYPTION,
	DECRYPTION,
	ENCRYPTION_CBC,
	DECRYPTION_CBC
}

class Block {
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


class BlockChiper {
	private String message;
	private List<String> blocks;
	private long iv;
	
	public BlockChiper(String m, long iv) {
		this.message=m;
		this.iv=iv;
		blocks = new ArrayList<String>();
		this.messagePartition();
	}
	
	public void messagePartition() {
		StringBuilder sb = new StringBuilder();
		for (int i=0;i<message.length();i++){
			if (i%8==0 && i!=0){
				blocks.add(sb.toString());
				sb = new StringBuilder();
				sb.append(message.charAt(i));
			}
			else {
				sb.append(message.charAt(i));
			}
			
			if (i==message.length()-1){
				int number = 8-(i%8)-1;
				
				while (number>0){
					sb.append(' ');	
					number--;
				}
				blocks.add(sb.toString());
			}
		}
		
	}
	
	public String transform (int key, TYPE t){
		List<String> ret = new ArrayList<>();
		
		blocks.stream().forEach(x -> {
			Block b = new Block(x);
			switch(t){
			case ENCRYPTION: 
				ret.add(b.encrypt(key));
				break;
			case DECRYPTION:
				ret.add(b.decrypt(key));
				break;
			case ENCRYPTION_CBC:
				ret.add(b.encryptCBC(key,iv));
				iv = Block.StrToLong(b.encryptCBC(key,iv));
				break;
			case DECRYPTION_CBC:
				ret.add(b.decryptCBC(key,iv));
				iv = Block.StrToLong(b.toString());
				break;
			default:
				break;
			}
		});
		
		StringBuilder sb = new StringBuilder();
		ret.stream().forEach(x-> sb.append(x));
		return sb.toString();
	}
	
	public String encrypt(int key) {
		return transform(key,TYPE.ENCRYPTION);
	}
	
	public String decrypt(int key) {
		return transform(key,TYPE.DECRYPTION);
	}
	
	public String encryptCBC(int key) {
		return transform(key,TYPE.ENCRYPTION_CBC);
	}
	
	public String decryptCBC(int key){
		return transform(key,TYPE.DECRYPTION_CBC);
	}
	
	
	public String cutBlock(int numBlock) {		
		return blocks.get(numBlock-1);		
	}
	
	public void swapBlocks(int b1, int b2) {
		List<String> newBlocks = new ArrayList<>();	
		int pom;
		if (b1>b2){
			pom=b2;
			b2=b1;
			b1=pom;
		}		
		String block1 = cutBlock(b1);
		String block2 = cutBlock(b2);		
		newBlocks.addAll(blocks.subList(0, b1-1));
		newBlocks.add(block2);
		newBlocks.addAll(blocks.subList(b1, b2-1));
		newBlocks.add(block1);
		newBlocks.addAll(blocks.subList(b2, blocks.size()));	
		
		blocks=newBlocks;
	}
	
	public void deleteBlock(int bNumber){
		blocks.remove(bNumber-1);
	}
	
	public String toString() {
		return blocks.toString();
	}
	
	
}

public class BlockChiperTest {

	public static void main(String[] args) {
		/*Block b = new Block("StafanAn");
		System.out.println(b.encryptCBC(1, 0));
		Block b1 = new Block(b.encryptCBC(1,0));
		System.out.println(b1.decryptCBC(1,0));*/
		
		/*String m = "Stefan Andonov sa$..";
		BlockChiper bc = new BlockChiper(m,0xffff);
		String c = bc.encryptCBC(10);
		System.out.println(c);
		BlockChiper bc1 = new BlockChiper(c,0xffff);
		System.out.println(bc1.decryptCBC(10));	*/
		
		String message = "Money for Alice is $100 Money for Trudy is $2";
		BlockChiper bc = new BlockChiper(message,0xabcfff1211045fffL);
		System.out.println(bc.toString());
		String c = bc.encryptCBC(10);
		System.out.println("Enkriptiranata poraka e: "+c);
		BlockChiper bc1 = new BlockChiper(c,0xabcfff1211045fffL);
		bc1.deleteBlock(2);
		bc1.deleteBlock(2);
		bc1.deleteBlock(2);
		System.out.println(bc1.toString());
		String m = bc1.decryptCBC(10);
		System.out.println("Dekriptirata poraka posle napad cut-and-paste: "+m);
		
		
		
	}

}
