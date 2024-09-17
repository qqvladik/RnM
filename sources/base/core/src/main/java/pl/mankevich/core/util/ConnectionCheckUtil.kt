package pl.mankevich.core.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketAddress

suspend fun checkConnection(dispatcher: CoroutineDispatcher): Boolean {
    return withContext(dispatcher) {
        try {
            val timeoutMs = 1500
            val sock = Socket()
            val sockAddress: SocketAddress = InetSocketAddress("8.8.8.8", 53)

            sock.connect(sockAddress, timeoutMs)
            sock.close()

            true
        } catch (e: IOException) {
            false
        }
    }
}