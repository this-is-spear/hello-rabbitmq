package tis.publishersubscriber


import java.util.Collections
import kotlin.concurrent.thread

fun main() {
    val pubSub = PubSub()
    pubSub.publish("hello1")
    pubSub.publish("world1")

    val subscriber1 = Subscriber()
    pubSub.subscribe(subscriber1)

    pubSub.publish("hello2")
    pubSub.publish("world2")

    val subscriber2 = Subscriber()
    pubSub.subscribe(subscriber2)

    pubSub.publish("hello3")
    pubSub.publish("world3")

    Thread.sleep(1000)

    println("Done")

    pubSub.exit()
}

class PubSub {
    private val queue = Collections.synchronizedList(mutableListOf<String>())
    private val subscribers = mutableListOf<Subscriber>()
    private var flag = true

    init {
        thread {
            while (flag) {
                if (queue.isNotEmpty()) {
                    val message = queue.removeAt(0)
                    subscribers.forEach { it.receive(message) }
                }
            }
        }
    }

    fun publish(message: String) {
        queue.add(message)
    }

    fun subscribe(subscriber: Subscriber) {
        subscribers.add(subscriber)
    }

    fun exit() {
        flag = false
    }
}

class Subscriber() {
    fun receive(message: String) {
        println("Subscriber $message received message: $message")
    }
}
