package br.ufs.dcomp.provaSD;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Main {
	
	private static int quantidadeImagensCinza = 0;
	
	public static void main(String[] args) throws Exception {
		File diretorio = new File("C:\\imagens-sd\\coloridas\\");
		
		File[] directoryListing = diretorio.listFiles();
		
		if(directoryListing == null) {
			System.out.println("Nenhuma imagem encontrada!!");
		} else {
			System.out.println("Quantidade de fotos: " + directoryListing.length);
			for(File arquivo : directoryListing) {
				BufferedImage imagem = ImageIO.read(arquivo);				
				EnviarImagemColorida.call(imagem);
			}
			System.out.println("Saiu do for");
			
			// Loop para aguardar o processamento de todas as imagens para a escala de cinza
			do {
				Thread.sleep(1000);
				File diretorioImagensCinza = new File("C:\\imagens-sd\\cinzas");
				File[] directoryListingGrayscale = diretorioImagensCinza.listFiles();
				quantidadeImagensCinza = directoryListingGrayscale.length;
				System.out.println("Quantidade de imagens cinza: " + quantidadeImagensCinza);
			} while(directoryListing.length > quantidadeImagensCinza);

			System.out.println("Saiu do do / while");
			System.out.println("Hora de salvar as imagens no servidor");
			
			// do while quantidade de fotos do singleton for menor que a quantidade de fotos coloridas
			// dica: use um Thread.sleep(1000) no do while pra não fazer tanta checagem assim
			
		}
	}
}
