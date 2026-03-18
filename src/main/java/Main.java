public class Main {
    public static void main(String[] args) throws Exception {
        int port = Integer.parseInt(System.getenv().getOrDefault("APP_PORT", "8080"));
        com.sun.net.httpserver.HttpServer server =
            com.sun.net.httpserver.HttpServer.create(new java.net.InetSocketAddress(port), 0);
        server.createContext("/", exchange -> {
            String response = "Boutique OK";
            exchange.sendResponseHeaders(200, response.length());
            exchange.getResponseBody().write(response.getBytes());
            exchange.getResponseBody().close();
        });
        server.start();
        System.out.println("Server started on port " + port);
    }
}
