package lab4;

import java.util.ArrayList;
import java.util.List;

public class BlockChiper {
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
		
		//return blocks.toString(); dolnata promena e poradi lab6
		StringBuilder sb = new StringBuilder();
		blocks.stream().forEach(x -> sb.append(x));
		return sb.toString();
	}
	
	
}