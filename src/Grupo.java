import java.io.*;
import java.util.Scanner;


public class Grupo {
	private static char tabela[][];
	private static int tabeladeposicoes[][];
	private static int tamanho;
	
	Grupo(String path){
		try {
			//Faz a leitura do arquivo de texto
			leitor(path);
			//Cria uma tabela que no lugar dos caracteres, tem a posicao de cada um na primeira linha da tabela
			char c;
			for(int i=0; i<tamanho; i++){
				for(int j=0; j<tamanho; j++){
					c=tabela[i][j];
					for(int k=0;k<tamanho;k++)
						if(c==tabela[0][k])
							tabeladeposicoes[i][j]=k;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Erro na abertura do arquivo.");
			e.printStackTrace();
		}
	}
	
	public static void leitor(String path) throws IOException {
		BufferedReader buffRead = new BufferedReader(new FileReader(path));
		String linha = ""; 
		linha = buffRead.readLine();
		tamanho = linha.length();
		tabela = new char[tamanho][tamanho];
		tabeladeposicoes = new int[tamanho][tamanho]; 
		for(int i=0; i<tamanho; i++){
			for(int j=0; j<tamanho; j++){
				tabela[i][j]=linha.charAt(j);
				tabeladeposicoes[i][j]=0;
			}
			linha = buffRead.readLine();
			if(linha == null) break;
		} 
		buffRead.close(); 
	} 
	
	public static void escritor(String path, String add) throws IOException {
		BufferedWriter buffWrite = new BufferedWriter(new FileWriter(path,true));
		buffWrite.append(add);
		buffWrite.newLine();
		buffWrite.close(); 
	} 
	
	public static void imprimeTabela(char tabela[][], int tamanho){
		for(int i=0; i<tamanho; i++){
			for(int j=0; j<tamanho; j++){
				System.out.print(tabela[i][j]+" ");
			}
			System.out.println("");
		}
	}
	
	public static void imprimeTabelaDePosicoes(int tabeladeposicoes1[][], int tam){
		for(int i=0; i<tam; i++){
			for(int j=0; j<tam; j++){
				System.out.print(tabeladeposicoes1[i][j]+" ");
			}
			System.out.println("");
		}
	}
	
	public static boolean testaFechamento(char tabela1[][],int tam){
		char c;
		boolean sinal;
		for(int i=0; i<tam; i++){
			for(int j=0; j<tam; j++){
				c=tabela1[i][j];
				sinal=false;
				for(int k=0; k<tam; k++)
					if(c==tabela1[0][k]) sinal=true;
				if(!sinal)
					return false;
			}
		}
		return true;
	}

	public static boolean testaAssociativa(char tabela1[][],int tabeladeposicoes1[][],int tam){
		for(int i=1; i<tam; i++)
			for(int j=1; j<tam; j++)
				for(int k=1; k<tam; k++)
					if(tabela1[tabeladeposicoes1[i][j]][k]!=tabela1[i][tabeladeposicoes1[j][k]])
						return false;
		return true;
	}
	
	public static boolean testaInversa(char tabela1[][], int tam){
		boolean sinal;
		for(int i=0; i<tam; i++){
			sinal=false;
			for(int j=0; j<tam; j++)
				if(tabela1[i][j]=='1') sinal = true;
			if (!sinal) return false;
		}
		return true;
	} 
	
	public static boolean testaGrupo(char tabela1[][],int tabeladeposicoes1[][],int tam){
		/*imprimeTabela(tabela1, tam);
		imprimeTabelaDePosicoes(tabeladeposicoes1, tam);		
		if(testaFechamento(tabela1, tam)) System.out.println("Grupo fechado");
		else System.out.println("Grupo Não fechado");
		if(testaAssociativa(tabela1, tabeladeposicoes1, tam)) System.out.println("Grupo Associado");
		else System.out.println("Grupo não associado");
		if(testaInversa(tabela1, tam)) System.out.println("Todos os elementos possuem inversa");
		else System.out.println("Nem todos os elementos possuem inversa");
		*/
		if(testaFechamento(tabela1, tam) && testaAssociativa(tabela1, tabeladeposicoes1, tam) && testaInversa(tabela1, tam)) return true;
		else return false;
	}
	
	public static void subgrupos(){
		String n ="";
		int i=0, j=0, cont=0;
		char subtabela[][];
		int subtabeladeposicoes[][];
		//pegando todas as combinações possíveis
		for(int x = 0; x < Math.pow(2,tamanho); x++){
			n = Integer.toBinaryString(x);
			for(i=n.length(); i<tamanho; i++)
				n="0"+n;
			char c;
			//tamanho do subgrupo
			cont=0;
			for(int y = 0; y < n.length(); y++){
				if(n.charAt(y) == '1') 
					cont++;
			}
			subtabela = new char[cont][cont];
			subtabeladeposicoes = new int[cont][cont];
			i=0;
			//preenchendo a tabela do subgrupo
			for(int a = 0; a < tamanho; a++){
				j=0;
				for(int b = 0; b < tamanho; b++){
					if(n.charAt(a) == '1' && n.charAt(b) == '1'){
						subtabela[i][j] = tabela[a][b];
						c=subtabela[i][j];
						j++;
					}
				}
				if(j>0) i++;
			}
			//Cria uma tabela que no lugar dos caracteres, tem a posicao de cada um na primeira linha da tabela
			for(int w=0; w<cont; w++){
				for(int s=0; s<cont; s++){
					c=subtabela[w][s];
					for(int k=0;k<cont;k++)
						if(c==subtabela[0][k])
							subtabeladeposicoes[w][s]=k;
				}
			}
			if(testaGrupo(subtabela, subtabeladeposicoes, cont)){
				String add="";
				for(int i1=0; i1<cont; i1++)
					add+=subtabela[0][i1];
				try {
					escritor("Subgrupos.txt", add);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		Grupo g = new Grupo("table 8.txt");
		File file = new File("Subgrupos.txt");
		if ( file.exists()) {
		   file.delete();
		}
		g.imprimeTabela(g.tabela,g.tamanho);
		//g.imprimeTabelaDePosicoes();
		if(g.testaGrupo(g.tabela,g.tabeladeposicoes,g.tamanho)) System.out.println("Eh grupo");
		else System.out.println("Nao eh grupo");
		g.subgrupos();
		Grafo f = new Grafo();
		f.leitor("Subgrupos.txt");
		System.out.println("Aqui comeca a imprimir");
		f.imprimeSubgrupos();
		f.digrafo();
	}
}
