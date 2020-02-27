package doge.code.grcp;

import com.google.protobuf.Any;
import com.google.protobuf.Empty;
import com.google.protobuf.Int64Value;
import io.grcp.proto.job.Job;
import io.grcp.proto.mail.Mail;
import io.grcp.proto.storage.StorageGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.io.IOException;

public class RouteClient {

    public static void main(String[] args) throws IOException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9000)
                .usePlaintext()
                .build();

        StorageGrpc.StorageBlockingStub stub = StorageGrpc.newBlockingStub(channel);

        Int64Value offerResponse = stub.offer(Job.newBuilder()
                .setId(0)
                .setBody(Any.pack(constructMail()))
                .build());

        System.out.println(offerResponse.getValue());

        Job pullResponse = stub.pull(Empty.getDefaultInstance());
        Mail unpackedMail = pullResponse.getBody().unpack(Mail.class);
        System.out.println(unpackedMail);

        channel.shutdown();
    }

    private static Mail constructMail() {
        return Mail.newBuilder()
                .setSendFrom("doge")
                .addSendTo("doge")
                .setSubject("Subject")
                .setBody("Body")
                .build();
    }
}
