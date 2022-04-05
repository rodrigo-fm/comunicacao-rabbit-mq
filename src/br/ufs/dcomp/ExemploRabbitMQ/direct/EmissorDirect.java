package br.ufs.dcomp.ExemploRabbitMQ.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmissorDirect {

	private final static String QUEUE_NAME = "minha-fila";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		factory.setUsername("guest");
		factory.setPassword("guest");
		factory.setVirtualHost("/");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		// (queue-name, durable, exclusive, auto-delete, params);
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		String mensagem = "Olá!!!";

		// (exchange, routingKey, props, message-body );
		channel.basicPublish("", QUEUE_NAME, null, mensagem.getBytes("UTF-8"));
		System.out.println(" [x] Mensagem enviada: '" + mensagem + "'");

		channel.close();
		connection.close();
	}
}