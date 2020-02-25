package doge.code.grcp.services;

import doge.code.grcp.configuration.HubProperties;
import doge.code.grcp.data.Storage;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Service(value = "routeServer")
public class RouteServer implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(RouteServer.class);
    private Server server;

    @Resource(name = "hubProps")
    private HubProperties hubProperties;

    @Override
    public void run(String... args) throws IOException, InterruptedException {
        start();
        blockUntilShutdown();
    }

    private void start() throws IOException {
        server = ServerBuilder.forPort(hubProperties.getServer().getPort())
                .addService(new Storage())
                .build().start();
        LOG.info("Server started, listening on: " + hubProperties.getServer().getPort());
        addShutdownHook();
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    private void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                LOG.info("Shutting down gRPC server since JVM is shutting down!");
                stop();
                LOG.info("gRPC server shut down!");
            } catch (InterruptedException interruptedException) {
                LOG.error("gRPC shutdown interrupted! Trace: {}", Arrays.toString(interruptedException.getStackTrace()));
            }
        }));
    }

    @PreDestroy
    private void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }
}

