package lab4;

import java.util.ArrayList;
import java.util.List;



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
		
		/*String message = "Money for Alice is $100 Money for Trudy is $2";
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
		System.out.println("Dekriptirata poraka posle napad cut-and-paste: "+m);*/
		
		//lab 5 zadaca
		
		String recievedMessage = "Krw\"a\"lknwgw\"kz\"Erwjw\"zk\"Opabwj.\"vw\"zw\"oa\"lnkrane\"gkiqjegwyefwpw\"zwhe\"bqjgyekjenw\"\"\"\"\"\"\"";
		BlockChiper bc = new BlockChiper(recievedMessage,0);
		System.out.println(bc.decrypt(224709026));
		
		String replyMessage = "ej zdravo ivana, se e okej funkcionira gi dobivam porakite, sto znaci deka uspeshno sme go vospostavile dhke protokolot. pozdrav se gledame";
		BlockChiper bc1 = new BlockChiper(replyMessage,0);
		System.out.println(bc1.encrypt(224709026));
		
		
		
	}

}
