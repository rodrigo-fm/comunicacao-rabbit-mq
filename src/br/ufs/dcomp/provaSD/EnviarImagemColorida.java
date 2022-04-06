package br.ufs.dcomp.provaSD;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.imageio.ImageIO;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EnviarImagemColorida {
	
	private final static String QUEUE_NAME = "enviar-imagens";
	
	public static void call(BufferedImage imagem) {
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
			channel.basicPublish("", QUEUE_NAME, null, ImageHelper.imageToByteArray(imagem));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
