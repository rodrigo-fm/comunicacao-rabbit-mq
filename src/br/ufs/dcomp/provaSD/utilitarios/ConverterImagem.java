package br.ufs.dcomp.provaSD.utilitarios;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ConverterImagem {
	public static Imagem call(Imagem imagem) throws Exception {
		BufferedImage imagemBuffered = ImageHelper.byteArrayToImage(imagem.getConteudo());
		int largura = imagemBuffered.getWidth();
		int altura = imagemBuffered.getHeight();
		
		for(int i = 0; i < altura; i++) {
			for(int j = 0; j < largura; j++) {
				Color cor = new Color(imagemBuffered.getRGB(j, i));
				int red = new Double(cor.getRed() * 0.299).intValue();
				int green = new Double(cor.getGreen() * 0.587).intValue();
				int blue = new Double(cor.getBlue() * 0.114).intValue();
				
				int cinza = red + green + blue;
				Color tomDeCinza = new Color(cinza, cinza, cinza);
				
				imagemBuffered.setRGB(j, i, tomDeCinza.getRGB());
			}
		}
		
		return new Imagem(imagem.getNome(), ImageHelper.imageToByteArray(imagemBuffered));
	}
}