package doge.code.grcp;

import doge.code.grcp.services.Storage;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class RouteServer {

    private static final Logger LOG = Logger.getLogger(RouteServer.class.getName());

    private Server server;

    public static void main(String[] args) throws IOException, InterruptedException {
        final RouteServer application = new RouteServer();
        application.start();
        application.blockUntilShutdown();
    }

    private void start() throws IOException {
        server = ServerBuilder.forPort(50051)
                .addService(new Storage())
                .build().start();
        LOG.info("Server started, listening on " + 50051);
        addShutdownHook();
    }

    private void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    private void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                System.err.println("Shutting down gRPC server since JVM is shutting down!");
                RouteServer.this.stop();
                System.err.println("gRPC server shut down!");
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace(System.err);
            }
        }));
    }
}

