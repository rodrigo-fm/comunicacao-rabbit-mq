package br.ufs.dcomp.provaSD;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Main {
	
	private static int numeroImagens = 25;
	
	private static BufferedImage lerImagem(String diretorio) throws Exception {
		return ImageIO.read(new File(diretorio));
	}
	
	public static void main(String[] args) throws Exception {
		// Enviar imagem do cliente (produtor) para o servidor (consumidor)
		EnviarImagem enviarImagem = new EnviarImagem(numeroImagens);
		
		enviarImagem.call();
		
//		// Converter imagens para preto e branco
//		for(int i = 1; i <= 25; i++) {
//			BufferedImage imagem = lerImagem("C:\\imagens-sd\\cliente-1\\imagem (" + i + ").jpg");
//			ConverterImagem.call(imagem, "imagem (" + i + ")");
//		}
	}
}
