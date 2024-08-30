package tis

import org.junit.jupiter.api.Test
import tis.rabbitmq.producerconsumer.send

class ConsumerKtTest {
    @Test
    fun run100() {
        for (i in 0..20) {
            send(i)
        }
    }
}
