package br.ufs.dcomp.provaSD;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class ArmazenarImagemCinza {
	
	public static String EXCHANGE_NAME = "armazenar-imagens";
	private static Random random = new Random();
	
	private static BufferedImage byteArrayToImage(byte[] bytes) throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		return ImageIO.read(bais);
	}
	
	public void main(String[] args) {
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
			String nomeFila = channel.queueDeclare().getQueue();
			channel.queueBind(nomeFila, EXCHANGE_NAME, "");
			
			DeliverCallback deliverCallback = (consumerTag, delivery) -> {
				try {
					BufferedImage imagem = byteArrayToImage(delivery.getBody());
					String nomeImagem = String.valueOf(random.nextInt(425678));
					
					System.out.println("Imagem convertida em escala de cinza");
				} catch(Exception e) {
					e.printStackTrace();
				}
			};
			
			channel.basicConsume(EXCHANGE_NAME, true, deliverCallback, consumerTag -> { });
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
