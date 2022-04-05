package br.ufs.dcomp.provaSD;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.imageio.ImageIO;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EnviarImagem {
	
	private int numeroImagens;
	private final static String QUEUE_NAME = "enviar-imagens";
	
	public EnviarImagem(int numeroImagens) {
		this.numeroImagens = numeroImagens;
	}
	
	private BufferedImage escolherImagem() throws Exception {
		int numero = new Double(Math.random() * (numeroImagens + 1)).intValue();
		return ImageIO.read(new File("C:\\imagens-sd\\cliente-1\\imagem (" + numero + ").jpg"));
	}
	
	private byte[] imageToByteArray(BufferedImage imagem) throws Exception {
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		ImageIO.write(imagem, "jpg", byteArray);
		return byteArray.toByteArray();
	}
	
	public void call() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		factory.setUsername("guest");
		factory.setPassword("guest");
		factory.setVirtualHost("/");
		try(
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
		) {
			BufferedImage imagem = this.escolherImagem();
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			channel.basicPublish("", QUEUE_NAME, null, this.imageToByteArray(imagem));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
