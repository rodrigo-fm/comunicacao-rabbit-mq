package br.ufs.dcomp.ExemploRabbitMQ.direct;

import java.io.IOException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class ReceptorDirect {

	private final static String QUEUE_NAME = "minha-fila";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1"); // Alterar
		factory.setUsername("guest"); // Alterar
		factory.setPassword("guest"); // Alterar
		factory.setVirtualHost("/");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		// (queue-name, durable, exclusive, auto-delete, params);
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		System.out.println(" [*] Esperando recebimento de mensagens...");

		Consumer consumer = new DefaultConsumer(channel) {
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {

				String message = new String(body, "UTF-8");
				System.out.println(" [x] Mensagem recebida: '" + message + "'");

				// (deliveryTag, multiple);
				// channel.basicAck(envelope.getDeliveryTag(), false);
			}
		};
		// (queue-name, autoAck, consumer);
		channel.basicConsume(QUEUE_NAME, true, consumer);
	}
}