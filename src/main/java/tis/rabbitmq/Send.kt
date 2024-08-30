package tis.rabbitmq

import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.MessageProperties

const val QUEUE_NAME = "durable-queue"

fun main() {
    send(0)
}

fun send(int: Int) {
    val factory = ConnectionFactory().apply {
        username = "myuser"
        password = "secret"
    }

    factory.newConnection().use {
        val channel = it.createChannel()
        channel.queueDeclare(QUEUE_NAME, true, false, false, null)
        val message = "message number ${int}..."
        channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.toByteArray())
        println(" [x] Sent '$message'")
    }
}
