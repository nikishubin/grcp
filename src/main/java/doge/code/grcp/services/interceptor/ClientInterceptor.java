package doge.code.grcp.services.interceptor;

import io.grpc.*;

public class ClientInterceptor implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
//        ForwardingServerCall.SimpleForwardingServerCall<ReqT, RespT> responseInterceptingServerCall =
//                new ForwardingServerCall.SimpleForwardingServerCall<>(serverCall) {
//                    @Override
//                    public void sendMessage(RespT message) {
//                        super.sendMessage(message);
//                        System.out.println("resp message: " + message);
//                    }
//                };
//
//        ServerCall.Listener<ReqT> nextListener = serverCallHandler.startCall(responseInterceptingServerCall, metadata);
//        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<>(nextListener) {
//            @Override
//            public void onMessage(ReqT message) {
//                super.onMessage(message);
//                System.out.println("req message: " + message);
//            }
//        };
        headers.get(Metadata.Key.of("User-Agent", Metadata.ASCII_STRING_MARSHALLER));
        return Contexts.interceptCall(Context.current(), call, headers, next);
    }
}
