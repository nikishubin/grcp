package doge.code.grcp.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component(value = "hubProps")
@ConfigurationProperties(prefix = "hub")
public class HubProperties {

    private final Server server = new Server();

    public Server getServer() {
        return server;
    }

    public static class Server {
        private String host;
        private int port;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }
}
