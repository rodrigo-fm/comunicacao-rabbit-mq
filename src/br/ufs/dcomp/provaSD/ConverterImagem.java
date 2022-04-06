package br.ufs.dcomp.provaSD;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ConverterImagem {
	// TODO: retornar a imagem em escala de cinza neste método
	public static BufferedImage call(BufferedImage imagem, String output) throws Exception {
		int largura = imagem.getWidth();
		int altura = imagem.getHeight();
		
		for(int i = 0; i < altura; i++) {
			for(int j = 0; j < largura; j++) {
				Color cor = new Color(imagem.getRGB(j, i));
				int red = new Double(cor.getRed() * 0.299).intValue();
				int green = new Double(cor.getGreen() * 0.587).intValue();
				int blue = new Double(cor.getBlue() * 0.114).intValue();
				
				int cinza = red + green + blue;
				Color tomDeCinza = new Color(cinza, cinza, cinza);
				
				imagem.setRGB(j, i, tomDeCinza.getRGB());
			}
		}
		
		File saida = new File("C:\\imagens-sd\\cinzas\\" + output + ".jpg");
		ImageIO.write(imagem, "jpg", saida);
		return imagem;
	}
}