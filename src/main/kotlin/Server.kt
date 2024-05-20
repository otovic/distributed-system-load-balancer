class Server(public val clientPort: Int, public val serverPort: Int) {
    lateinit var ip: String;
    lateinit var port: String;
    var clients: Int = 0;
}