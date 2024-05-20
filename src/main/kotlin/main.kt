import java.net.ServerSocket

public fun main(args: Array<String>) {
        val clientPort = 12345
        val serverPort = 1307
        val servers = mutableListOf<Server>()


        try {
            val clientSocket = ServerSocket(clientPort)
            println("Client serving started on port $clientPort")

            val serverSocket = ServerSocket(serverPort)
            println("Server serving started on port $serverPort")

            while (true) {
                val clientSocket = clientSocket.accept()
                println("Client connected: ${clientSocket.inetAddress.hostAddress}")

                val serverSocket = serverSocket.accept()
                println("Server connected: ${serverSocket.inetAddress.hostAddress}")

                Thread {
                    handleClient(clientSocket)
                }.start()

                Thread {
                    handleServer(serverSocket)
                }.start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
}

fun handleClient(clientSocket: java.net.Socket) {
    val input = clientSocket.getInputStream().bufferedReader()
    val output = clientSocket.getOutputStream().bufferedWriter()

    try {
        var message = input.readLine()
        println("kuksi")
        while (message != null) {
            println("Received from client: $message")

            if (message == "exit") {
                break
            }

            val responseData = "Response from server"
            output.write(responseData)
            output.newLine()
            output.flush()

            message = input.readLine()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        clientSocket.close()
        println("Client disconnected: ${clientSocket.inetAddress.hostAddress}")
    }
}

fun handleServer(serverSocket: java.net.Socket) {
    val input = serverSocket.getInputStream().bufferedReader()
    val output = serverSocket.getOutputStream().bufferedWriter()

    try {
        var message = input.readLine()
        while (message != null) {
            println("Received from server: $message")

            if (message == "exit") {
                break
            }

            val responseData = "Response from client"
            output.write(responseData)
            output.newLine()
            output.flush()

            message = input.readLine()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        serverSocket.close()
        println("Server disconnected: ${serverSocket.inetAddress.hostAddress}")
    }
}