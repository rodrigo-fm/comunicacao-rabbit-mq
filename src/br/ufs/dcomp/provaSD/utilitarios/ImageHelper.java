package br.ufs.dcomp.provaSD.utilitarios;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

public class ImageHelper {
	public static byte[] imageToByteArray(BufferedImage imagem) throws Exception {
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		ImageIO.write(imagem, "jpg", byteArray);
		return byteArray.toByteArray();
	}
	
	public static BufferedImage byteArrayToImage(byte[] bytes) throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		return ImageIO.read(bais);
	}
}
