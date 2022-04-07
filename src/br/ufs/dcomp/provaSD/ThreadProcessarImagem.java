package br.ufs.dcomp.provaSD;

import java.awt.image.BufferedImage;
import java.util.Random;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import br.ufs.dcomp.provaSD.utilitarios.ConverterImagem;
import br.ufs.dcomp.provaSD.utilitarios.ImageHelper;
import br.ufs.dcomp.provaSD.utilitarios.Imagem;

public class ThreadProcessarImagem implements Runnable {
	private final static String QUEUE_NAME = "enviar-imagens";
	public static String EXCHANGE_NAME = "armazenar-imagens";
	private static Random random = new Random();
	
	private String nome;
	
	public ThreadProcessarImagem(String nome) {
		this.nome = nome;
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
			// Fila para receber as imagens
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			
			// Fanout para enviar as imagens
			channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
			
			System.out.println("Aguardando recebimento de imagem");
			
			while(true) {
				DeliverCallback deliverCallback = (consumerTag, delivery) -> {
					try {
						
						// Receber a imagem
						Imagem imagem = Imagem.toImagem(delivery.getBody());
						
						// Converter Imagem para escala de cinza
						Imagem imagemCinza = ConverterImagem.call(imagem);
						System.out.println(this.nome + ": Imagem convertida em escala de cinza");
						
						// Enviar imagem da forma fanout para os servidores (consumidores) responsáveis por armazenar as imagens
						channel.basicPublish(EXCHANGE_NAME, "", null, Imagem.toByteArray(imagemCinza));
						
					} catch(Exception e) {
						e.printStackTrace();
					}
				};
				
				channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
