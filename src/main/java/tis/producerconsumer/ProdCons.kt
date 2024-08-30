package tis.producerconsumer


import java.util.Collections
import kotlin.concurrent.thread

fun main() {
    val producer = Producer()
    val consumer1 = Consumer("Consumer 1")

    producer.produce("hello1")
    producer.produce("world1")

    consumer1.consume(producer)

    producer.produce("hello2")
    producer.produce("world2")

    val consumer2 = Consumer("Consumer 2")
    consumer2.consume(producer)

    Thread.sleep(10000)

    producer.produce("hello3")
    producer.produce("world3")
}

class MessageQueue {
    private val queue = Collections.synchronizedList(mutableListOf<String>())

    fun add(message: String) = queue.add(message)

    fun getItem(index: Int) = if (index < queue.size) Item(queue[index], index + 1) else Item("", index)
}

data class Item(
    val message: String,
    val nextIndex: Int,
)

class Producer(
    private val queue: MessageQueue = MessageQueue(),
) {
    fun produce(message: String) = queue.add(message)
    fun deliver(index: Int) = queue.getItem(index)
}

class Consumer(
    private val name: String,
) {
    fun consume(producer: Producer) {
        var index = 0
        thread {
            while (true) {
                val item = producer.deliver(index)
                index = item.nextIndex
                println("$name received message: $item")
                Thread.sleep(500)
            }
        }
    }
}
