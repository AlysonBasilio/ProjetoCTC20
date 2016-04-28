import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Grafo {
	private static String nos[];
	private static int MAX=100;
	private static int total;
	
	Grafo(){
		total=0;
	}
	
	public static void leitor(String path) throws IOException {
		nos = new String[MAX];
		boolean sinal;
		BufferedReader buffRead = new BufferedReader(new FileReader(path));
		String linha = ""; 
		linha = buffRead.readLine();
		for(int i=0; i<MAX && linha != null; i++){
			System.out.println("Linha "+i+": "+linha);
			sinal=false;
			for(int j=0; j<total; j++){
				if(linha.equals(nos[j])){
					sinal=true;
					//System.out.println("Essa porra deu igual:"+linha);
				}
			}
			if(!sinal){
				if(!linha.equals("") && linha.charAt(0)=='1')
					nos[total++]=linha;
			}
			linha = buffRead.readLine();
		}
		buffRead.close(); 
	}
	
	public static void imprimeSubgrupos(){
		for(int i=0; i<total;i++)
			System.out.println(nos[i]);
	}
}
