package tis.rabbitmq

import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.Delivery
import java.nio.charset.StandardCharsets
import kotlin.random.Random

fun main() {
    val connectionFactory = ConnectionFactory().apply {
        username = "myuser"
        password = "secret"
    }

    // connection 의 auto closable 을 활성화하는 경우 더이상 데이터받을 게 없으면 종료된다.
    val connection = connectionFactory.newConnection()
    val channel = connection.createChannel()
    channel.queueDeclare(QUEUE_NAME, true, false, false, null)
    channel.basicQos(1)

    println(" [*] Waiting for messages. To exit press CTRL+C");

    val deliverCallback = { _: String, delivery: Delivery ->
        val message = String(delivery.body, StandardCharsets.UTF_8)
        println(" [x] Received $message")
        try {
            taskWithMessage(message)
        } finally {
            println(" [x] Send ACK")
            channel.basicAck(delivery.envelope.deliveryTag, false)
        }
    }

    channel.basicConsume(QUEUE_NAME, false, deliverCallback) { _ -> }
}

private fun taskWithMessage(message: String) {
    for (c in message.toCharArray()) {
        if (c == '.') Thread.sleep(Random.nextLong(100, 10000))
    }
}
