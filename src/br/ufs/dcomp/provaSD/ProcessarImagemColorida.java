package br.ufs.dcomp.provaSD;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class ProcessarImagemColorida {
	private final static String QUEUE_NAME = "enviar-imagens";
	public static String EXCHANGE_NAME = "armazenar-imagens";
	private static Random random = new Random();
	
	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		factory.setUsername("guest");
		factory.setPassword("guest");
		factory.setVirtualHost("/");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		// Fila para receber as imagens
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		// Fanout para enviar as imagens
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		
		System.out.println("Aguardando recebimento de imagem");
		
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			try {
				
				// Receber a imagem
				BufferedImage imagem = ImageHelper.byteArrayToImage(delivery.getBody());
				System.out.println("Imagem recebida! Convertendo imagem para a escala de cinza");
				
				// Converter Imagem para escala de cinza
				String nomeImagem = String.valueOf(random.nextInt(425678));
				BufferedImage imagemCinza = ConverterImagem.call(imagem, nomeImagem);
				System.out.println("Imagem convertida em escala de cinza");
				
				// Enviar imagem da forma fanout para os servidores (consumidores) responsáveis por armazenar as imagens
				channel.basicPublish(EXCHANGE_NAME, "", null, ImageHelper.imageToByteArray(imagemCinza));
				System.out.println("Imagem enviada para os servidores de armazenamento");
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		};
		
		channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
		
	}
}
