package br.ufs.dcomp.provaSD;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

import javax.imageio.ImageIO;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import br.ufs.dcomp.provaSD.utilitarios.ImageHelper;
import br.ufs.dcomp.provaSD.utilitarios.Imagem;

public class ThreadServidorArmazenamento implements Runnable {

	public final String EXCHANGE_NAME = "armazenar-imagens";
	private Random random = new Random();
	private String nomeDiretorio;
	
	public ThreadServidorArmazenamento(String nomeDiretorio) {
		this.nomeDiretorio = nomeDiretorio;
	}
	
	@Override
	public void run() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		factory.setUsername("guest");
		factory.setPassword("guest");
		factory.setVirtualHost("/");
		
		try (
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
		) {
			channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
			String nomeFila = channel.queueDeclare().getQueue();
			channel.queueBind(nomeFila, EXCHANGE_NAME, "");
			
			System.out.println("Inicializando o servidor para armazenar imagens");
			
			while(true) {
				DeliverCallback deliverCallback = (consumerTag, delivery) -> {
					try {
						Imagem imagem = Imagem.toImagem(delivery.getBody());

						File saida = new File("C:\\imagens-sd\\"+ this.nomeDiretorio +"\\" + imagem.getNome() + ".jpg");
						ImageIO.write(ImageHelper.byteArrayToImage(imagem.getConteudo()), "jpg", saida);
						System.out.println(this.nomeDiretorio + ": Imagem grayscale armazenada");
					} catch (Exception e) {
						e.printStackTrace();
					}
				};
				
				channel.basicConsume(nomeFila, true, deliverCallback, consumerTag -> { });
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
