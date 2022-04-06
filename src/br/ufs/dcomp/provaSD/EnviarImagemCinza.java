package br.ufs.dcomp.provaSD;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EnviarImagemCinza {
	
	public static String EXCHANGE_NAME = "armazenar-imagens";
	
	private static byte[] imageToByteArray(BufferedImage imagem) throws Exception {
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		ImageIO.write(imagem, "jpg", byteArray);
		return byteArray.toByteArray();
	}
	
	public static void call(BufferedImage imagemCinza) {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		factory.setUsername("guest");
		factory.setPassword("guest");
		factory.setVirtualHost("/");
		try(
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
		) {
			channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
			channel.basicPublish(EXCHANGE_NAME, "", null, imageToByteArray(imagemCinza));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
