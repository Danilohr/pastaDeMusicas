package Pacote1;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Pasta {
	private File pasta;
	private String raiz;
	private int mRepetidas;
	private int mCopiadas;
	private int indice;
	private File fileRaiz;
	
	public Pasta(File pasta, String raiz, int mCopiadas, int mRepetidas, int indice) {
		this.pasta = pasta;
		this.raiz = raiz;
		this.mRepetidas = mRepetidas;
		this.mCopiadas = mCopiadas;
		this.indice = indice;
		fileRaiz = new File(raiz);
	}
	
	public void copiaMusicas(FilenameFilter filter) throws IOException { //Copia todas as músicas para a destino
	    
		for(File sub:pasta.listFiles(filter)) {
			boolean repetida=false;
			String novoNome = removeNumero(sub.getName());
			for(File subRaiz:fileRaiz.listFiles(filter)) {
				if(novoNome.equals(subRaiz.getName().substring(subRaiz.getName().indexOf('-')+2))) {
					this.mRepetidas++;
					repetida = true;
				}
			}
			novoNome = adicionaIndice(novoNome);
			if(repetida == false) {
				Path newFilePath = Paths.get(raiz+"/"+novoNome);
				try {
					Files.copy(sub.toPath(), newFilePath); //alterado
					this.mCopiadas++;
					this.indice++;
				}
				catch(FileAlreadyExistsException e) {
					this.mRepetidas++;
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
			
		}
		for(File sub : pasta.listFiles()) {
			if(sub.isDirectory()) {
				Pasta arquivo = new Pasta(sub,raiz, this.mCopiadas, this.mRepetidas, this.indice);
				arquivo.copiaMusicas(filter);
				this.mCopiadas = arquivo.mCopiadas;
				this.mRepetidas = arquivo.mRepetidas;
				this.indice = arquivo.indice;
			}
		}
	}
	
	public String removeNumero(String nome) throws IOException{
		String novoNome = null;
		Scanner scan = null;
		
		String inicial = "vol ";
		if(nome.toLowerCase().startsWith(inicial)) {
			novoNome = nome.substring(4, nome.length());
			scan = new Scanner(novoNome);
		}
		else 
			scan = new Scanner(nome);
		
		try {
			scan.nextInt();
			String palavra = scan.next();
			if(palavra.equals("-")) {
				palavra = scan.next();
				novoNome = nome.substring(nome.indexOf(palavra));
			}else novoNome = nome;
			
		}catch(InputMismatchException e) {
			String[] palavras = nome.split(" ");
			if(palavras[0].endsWith("-")) {
				novoNome = nome.substring(nome.indexOf('-')+2);
			}
			else novoNome = nome;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		scan.close();
		return novoNome;
	}

	public String adicionaIndice(String nome) throws IOException{
		String novoNome = this.indice+" - "+nome;
		return novoNome;
	}
	
	public void preparaRaiz(FilenameFilter filter) throws IOException {
	    for(File subRaiz:fileRaiz.listFiles(filter)) {
	    	String novoNome = indice+" - "+removeNumero(subRaiz.getName());
	    	String newFilePath = raiz+"/"+novoNome;
	    	File novoFile = new File(newFilePath);
	    	subRaiz.renameTo(novoFile);
	    	this.indice++;
	    }
	}

	
	public int getmRepetidas() {
		return mRepetidas;
	}

	public void setmRepetidas(int mRepetidas) {
		this.mRepetidas = mRepetidas;
	}

	public int getmCopiadas() {
		return mCopiadas;
	}

	public void setmCopiadas(int mCopiadas) {
		this.mCopiadas = mCopiadas;
	}

	public int getIndice() {
		return indice;
	}

	public void setIndice(int indice) {
		this.indice = indice;
	}
	
	
	
}
