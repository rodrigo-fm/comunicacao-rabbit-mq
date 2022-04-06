package br.ufs.dcomp.provaSD;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import com.rabbitmq.client.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Envelope;

public class ArmazenarImagemCinza {
	
	public static final String EXCHANGE_NAME = "armazenar-imagens";
	private static Random random = new Random();
	
	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		factory.setUsername("guest");
		factory.setPassword("guest");
		factory.setVirtualHost("/");
		
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		String nomeFila = channel.queueDeclare().getQueue();
		channel.queueBind(nomeFila, EXCHANGE_NAME, "");
		
		System.out.println("Inicializado o servidor para armazenar imagens");
		
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			BufferedImage imagem;
			try {
				imagem = ImageHelper.byteArrayToImage(delivery.getBody());
				String nomeImagem = String.valueOf(random.nextInt(425678));
				File saida = new File("C:\\imagens-sd\\cinzas\\" + nomeImagem + ".jpg");
				ImageIO.write(imagem, "jpg", saida);
				System.out.println("Imagem grayscale armazenada");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		};
		
		channel.basicConsume(nomeFila, true, deliverCallback, consumerTag -> { });
		
//		Consumer consumer = new DefaultConsumer(channel) {
//			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws Exception {
//				BufferedImage imagem = ImageHelper.byteArrayToImage(body);
//				String nomeImagem = String.valueOf(random.nextInt(425678));
//				
//				File saida = new File("C:\\imagens-sd\\cinzas\\" + nomeImagem + ".jpg");
//				ImageIO.write(imagem, "jpg", saida);
//				System.out.println("Imagem grayscale armazenada");
//			}
//		};
	}
}
