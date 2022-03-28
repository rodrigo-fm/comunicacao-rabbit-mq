package br.ufs.dcomp.ExemploRabbitMQ.fanout;

import java.io.IOException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class ReceptorFanout {
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
		String nomeFila = channel.queueDeclare().getQueue();
		channel.queueBind(nomeFila, EXCHANGE_NAME, "");

		System.out.println(" [*] Esperando recebimento de mensagens...");

		Consumer consumer = new DefaultConsumer(channel) {
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {

				String message = new String(body, "UTF-8");
				System.out.println(" [x] Mensagem recebida: '" + message + "'");
			}
		};

		channel.basicConsume(nomeFila, true, consumer);
	}
}