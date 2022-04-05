package br.ufs.dcomp.provaSD;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

import javax.imageio.ImageIO;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class ReceberImagem {
	private final static String QUEUE_NAME = "enviar-imagens";
	
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
		
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			try {
				BufferedImage imagem = byteArrayToImage(delivery.getBody());
				ConverterImagem.call(imagem, "imagem-cliente");
			} catch(Exception e) {
				e.printStackTrace();
			}
		};
		
		channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
		
	}
}
