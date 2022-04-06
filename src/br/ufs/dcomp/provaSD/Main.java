package br.ufs.dcomp.provaSD;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Main {
	
	// private static int quantidadeImagensCinza = 0;
	
	public static void main(String[] args) throws Exception {
		File diretorio = new File("C:\\imagens-sd\\coloridas\\");
		
		File[] directoryListing = diretorio.listFiles();
		
		if(directoryListing == null) {
			System.out.println("Nenhuma imagem encontrada!!");
		} else {
			for(File arquivo : directoryListing) {
				BufferedImage imagem = ImageIO.read(arquivo);
				EnviarImagemColorida.call(imagem);
			}
			System.out.println("Fim");
	
		}
	}
}
