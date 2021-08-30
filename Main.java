package Pacote1;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException{ 
		Scanner scan = new Scanner(System.in);
		System.out.println("Digite o caminho da pasta de destino para a cópia. Ex:C:/Users/danilo/Desktop");
		final String cRaiz = scan.nextLine();
		System.out.println("Digite o caminho da pasta de músicas para ser explorada. Ex:C:/Users/danilo/Desktop/pasta");
		String cPasta = scan.nextLine();
		scan.close();
		File raiz = new File(cRaiz);
		File pastaPrincipal =  new File(cPasta);
		
		// Entra na pasta desejada
		
		if(!raiz.canWrite()) {
			System.out.println("Não foi possível acessar a pasta \""+cRaiz+"\"");
			System.exit(0);
		}
		if(!pastaPrincipal.canWrite()) {
			System.out.println("Não foi possível acessar a pasta \""+cPasta+"\"");
			System.exit(0);
		}
		
		Pasta pasta = new Pasta(pastaPrincipal, cRaiz, 0, 0, 1);
		
		// Copia arquivo para a raiz
		FilenameFilter filter = new FilenameFilter() {
	        @Override
	        public boolean accept(File f, String name) {
	            name = name.toLowerCase();
				if(name.endsWith(".mp3")||name.endsWith(".txt")) {
					return true;
				}
				return false;
	        }
	    };
		
		pasta.preparaRaiz(filter);
		pasta.copiaMusicas(filter);
		System.out.println(pasta.getmCopiadas()+" músicas copiadas e "+pasta.getmRepetidas()+" músicas repetidas");
			
		// Renomeia com os numeros
		
		
	}
	
}
