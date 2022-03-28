package br.ufs.dcomp.ExemploRabbitMQ.dual;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class ReceptorDual {
	private static final String EXCHANGE_NAME = "mensagens";
	private final static String QUEUE_NAME = "minha-fila";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1"); // Alterar
		factory.setUsername("guest"); // Alterar
		factory.setPassword("guest"); // Alterar
		factory.setVirtualHost("/");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		// fanout
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		String nomeFila = channel.queueDeclare().getQueue();
		channel.queueBind(nomeFila, EXCHANGE_NAME, "");

		// direct
//		Channel channelDirect = connection.createChannel();
		// (queue-name, durable, exclusive, auto-delete, params);
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		System.out.println(" [*] Esperando recebimento de mensagens...");

		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), "UTF-8");
			System.out.println(" [x] Mensagem recebida: '" + message + "'");
		};

		// fanout
		channel.basicConsume(nomeFila, true, deliverCallback, consumerTag -> {
		});

		// direct
		channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
		});
	}
}