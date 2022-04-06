package br.ufs.dcomp.provaSD;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class ReceberImagem {
	private final static String QUEUE_NAME = "enviar-imagens";
	private static Random random = new Random();
	
	private static BufferedImage byteArrayToImage(byte[] bytes) throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		return ImageIO.read(bais);
	}
	
	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println("Aguardando recebimento de imagem");
		
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			
			try {
				ImagensSingleton imagensSingleton = ImagensSingleton.getInstance();
				BufferedImage imagem = byteArrayToImage(delivery.getBody());
				System.out.println("Imagem recebida! Convertendo imagem para a escala de cinza");
				String nomeImagem = String.valueOf(random.nextInt(425678));
				BufferedImage imagemCinza = ConverterImagem.call(imagem, nomeImagem);
				imagensSingleton.imagensCinza.add(imagemCinza);
				System.out.println("Imagem convertida em escala de cinza");
			} catch(Exception e) {
				e.printStackTrace();
			}
		};
		
		channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
		
	}
}
