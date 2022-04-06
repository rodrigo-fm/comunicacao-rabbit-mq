package br.ufs.dcomp.provaSD;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ImagensSingleton {
	// TODO: criar singleton.
	private static ImagensSingleton singleton = null;
	
	public ArrayList<BufferedImage> imagensCinza = new ArrayList<BufferedImage>();
	
	public static ImagensSingleton getInstance() {
		if(singleton == null) {
			singleton = new ImagensSingleton();
		}
		return singleton;
	}
}
