import java.util.Arrays;
import java.util.Collections;

/**
 * Ilustracija na shifruvach baziran na kvazigrupna kriptografija, bazirana na binarna kvazigrupa od red 4.
 * Shifruvachot ima zadaca da shifrira i deshifrira poraki so elementi od mnozestvoto (azbukata) Q={1,2,3,4}.
 * Izraboteno vo ramkite na predmetot Kriptografija na FINKI vo akademskata 2017/18 godina.
 * @author StefanAndonov
 *
 */

class QuasigroupChiper{
	/**
	 * Promenliva - binarna kvazigrupa sto se koristi za enkripcija
	 */
	private static final int [][] quasigroupLookUpTable = {
			{1,2,3,4},
			{2,3,4,1},
			{3,4,1,2},
			{4,1,2,3}
	};
	
	/**
	 * Promenliva - parastrof na prethodno definiranata kvazigrupa, shto ke se koristi za dekripcija
	 */
	private static final int [][] parastropheLookUpTable = {
			{1,2,3,4},
			{4,1,2,3},
			{3,4,1,2},
			{2,3,4,1}
			
	};
	
	private static void printArray (int [] array){
		Arrays.stream(array).forEach(x -> System.out.print(x + " "));
		System.out.println();
	}
	
	/**
	 * 
	 * @param message originalnata poraka sostavena so elementi od 1 do 4 oddeleni so prazno mesto
	 * @param leaders niza od nosachi(lideri) koi sluzat za enkripcija
	 * @return shifrirana poraka kako string, sekoj element e oddelen so prazno mesto
	 */
	public String encryption (String message, String leaders) {
		String [] m = message.split("\\s+");
		String [] l = leaders.split("\\s+");		
		int [] m1 = new int [m.length];
		for (int i=0;i<m.length;i++)
			m1[i]=Integer.parseInt(m[i]);
		int [] l1 = new int [l.length];
		for (int i=0;i<l.length;i++)
			l1[i]=Integer.parseInt(l[i]);
		int [] c1 = new int [m.length];
		
		for (int i=0;i<l.length;i++){			
			for (int j=0;j<c1.length;j++){				
				if (j==0)
					c1[j]=quasigroupLookUpTable[l1[i]-1][m1[j]-1];
				else 
					c1[j]=quasigroupLookUpTable[c1[j-1]-1][m1[j]-1];
			}			
			QuasigroupChiper.printArray(c1);
			m1=c1;
			c1 = new int [m1.length];
		}		
		c1=m1;
		StringBuilder sb = new StringBuilder();
		Arrays.stream(c1).forEach(x -> sb.append(x+" "));
		return sb.toString();		
	}
	
	/**
	 * 
	 * @param chipertext shifriran tekst so elementi od 1 do 4 oddeleni so prazno mesto
	 * @param leaders niza od nosachi oddeleni so prazno mesto koi sluzele za enkripcija na podatocite (kluch)
	 * @return string reprezentacija na originalnata poraka (sekoj element oddelen so prazno mesto)
	 */
	
	public String decryption (String chipertext, String leaders){
		String [] c = chipertext.split("\\s+");
		String [] l = leaders.split("\\s+");		
		int [] c1 = new int [c.length];
		for (int i=0;i<c.length;i++)
			c1[i]=Integer.parseInt(c[i]);		
		int [] l1 = new int [l.length];
		for (int i=0;i<l.length;i++)
			l1[i]=Integer.parseInt(l[i]);		
		int [] m1 = new int [c.length];		
		int [] pom = new int [l1.length];		
		for (int i = 0;i<pom.length;i++){
			pom[i]=l1[pom.length-1-i];
		}		
		l1=pom;
		
		for (int i=0;i<l1.length;i++){
			for (int j=0;j<c1.length;j++){				
				if (j==0)
					m1[j]=parastropheLookUpTable[l1[i]-1][c1[j]-1];
				else 
					m1[j]=parastropheLookUpTable[c1[j-1]-1][c1[j]-1];
			}	
			QuasigroupChiper.printArray(m1);
			c1=m1;
			m1=new int [c1.length];
		}		
		m1=c1;
		StringBuilder sb = new StringBuilder();
		Arrays.stream(m1).forEach(x -> sb.append(x + " "));
		return sb.toString();		
	}
	
	
}
public class QuasigroupChiperTest {

	public static void main(String[] args) {
		
		QuasigroupChiper qgc = new QuasigroupChiper();
		
		
		//System.out.println(qgc.encryption("3 2 4 1 2 4 3 2 2 4 1 3", "1 2"));
		System.out.println(qgc.encryption("3 2 4 1 2 4 3 2 2 4 1 3", "1 2 3 4 4 3 3 2 1 1 2 3 4 3 2 1 1 2 3 4 1 2 3 4 4 3 2 1 3 2 4 1 3 4 3 2 1 3 2 3 4 1 2 3 4"));
		//dava ramnomerna raspredelba
		
		//System.out.println(qgc.decryption("4 3 1 3 2 4 4 1 3 4 1 4 ", "1 2"));		
	}

}
