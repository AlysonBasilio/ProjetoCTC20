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
			for(int i=1; i<tamanho; i++){
				for(int j=1; j<tamanho; j++){
					c=tabela[i][j];
					for(int k=0;k<tamanho;k++)
						if(c==tabela[0][k])
							tabeladeposicoes[i][j]=k;
				}
				System.out.println("");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
	
	public static void escritor(String path) throws IOException {
		BufferedWriter buffWrite = new BufferedWriter(new FileWriter(path));
		String linha = ""; 
		Scanner in = new Scanner(System.in); 
		System.out.println("Escreva algo: "); 
		linha = in.nextLine(); 
		buffWrite.append(linha + "\n"); 
		buffWrite.close(); 
	} 
	
	public void imprimeTabela(){
		for(int i=0; i<tamanho; i++){
			for(int j=0; j<tamanho; j++){
				System.out.print(tabela[i][j]);
			}
			System.out.println("");
		}
	}
	
	public void imprimeTabelaDePosicoes(){
		for(int i=0; i<tamanho; i++){
			for(int j=0; j<tamanho; j++){
				System.out.print(tabeladeposicoes[i][j]);
			}
			System.out.println("");
		}
	}
	
	public boolean testaFechamento(){
		char c;
		boolean sinal;
		for(int i=0; i<tamanho; i++){
			for(int j=0; j<tamanho; j++){
				c=tabela[i][j];
				sinal=false;
				for(int k=0; k<tamanho; k++)
					if(c==tabela[0][k]) sinal=true;
				if(!sinal)
					return false;
			}
		}
		return true;
	}

	public boolean testaAssociativa(){
		for(int i=1; i<tamanho; i++)
			for(int j=1; j<tamanho; j++)
				for(int k=1; k<tamanho; k++)
					if(tabela[tabeladeposicoes[i][j]][k]!=tabela[i][tabeladeposicoes[j][k]])
						return false;
		return true;
	}
	
	public boolean testaInversa(){
		boolean sinal;
		for(int i=0; i<tamanho; i++){
			sinal=false;
			for(int j=0; j<tamanho; j++)
				if(tabela[i][j]=='1') sinal = true;
			if (!sinal) return false;
		}
		return true;
	} 
	
	public boolean testaGrupo(){
		if(testaFechamento()) System.out.println("Grupo fechado");
		else System.out.println("Grupo Não fechado");
		if(testaAssociativa()) System.out.println("Grupo Associado");
		else System.out.println("Grupo não associado");
		if(testaInversa()) System.out.println("Todos os elementos possuem inversa");
		else System.out.println("Nem todos os elementos possuem inversa");
		
		if(testaFechamento() && testaAssociativa() && testaInversa()) return true;
		else return false;
	}
	
	public static void main(String[] args) {
		Grupo g = new Grupo("table 1.txt");
		g.imprimeTabela();
		//g.imprimeTabelaDePosicoes();
		if(g.testaGrupo()) System.out.println("É grupo");
		else System.out.println("Não é grupo");
	}

}
