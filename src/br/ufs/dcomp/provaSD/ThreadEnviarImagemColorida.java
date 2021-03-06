package br.ufs.dcomp.provaSD;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import br.ufs.dcomp.provaSD.utilitarios.ImageHelper;
import br.ufs.dcomp.provaSD.utilitarios.Imagem;

public class ThreadEnviarImagemColorida implements Runnable {

	private final static String QUEUE_NAME = "enviar-imagens";
	private File[] arquivos;
	
	public ThreadEnviarImagemColorida(File[] arquivos) {
		this.arquivos = arquivos;
	}
	
	@Override
	public void run() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		factory.setUsername("guest");
		factory.setPassword("guest");
		factory.setVirtualHost("/");
		
		try(
				Connection connection = factory.newConnection();
				Channel channel = connection.createChannel();
			) {
				channel.queueDeclare(QUEUE_NAME, false, false, false, null);
				
				for(File arquivo : this.arquivos) {
					String nomeArquivo = arquivo.getName();
					BufferedImage imagemArquivo = ImageIO.read(arquivo);
					Imagem imagem = new Imagem(nomeArquivo, ImageHelper.imageToByteArray(imagemArquivo));
					channel.basicPublish("", QUEUE_NAME, null, Imagem.toByteArray(imagem));
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		
	}

}
