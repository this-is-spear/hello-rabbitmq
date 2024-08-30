package tis

import org.junit.jupiter.api.Test
import tis.rabbitmq.send

class SendKtTest {
    @Test
    fun run100() {
        for (i in 0..20) {
            send(i)
        }
    }
}
