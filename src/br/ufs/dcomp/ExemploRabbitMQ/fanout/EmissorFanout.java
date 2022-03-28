package br.ufs.dcomp.ExemploRabbitMQ.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmissorFanout {
	private static final String EXCHANGE_NAME = "mensagens";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1"); // Alterar
		factory.setUsername("guest"); // Alterar
		factory.setPassword("guest"); // Alterar
		factory.setVirtualHost("/");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

		String mensagem = "Olá!!!";

		// (exchange, routingKey, props, message-body );
		channel.basicPublish(EXCHANGE_NAME, "", null, mensagem.getBytes("UTF-8"));
		System.out.println(" [x] Mensagem enviada: '" + mensagem + "'");

		channel.close();
		connection.close();
	}
}