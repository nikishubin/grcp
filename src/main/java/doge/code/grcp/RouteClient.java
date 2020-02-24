package doge.code.grcp;

import com.google.protobuf.Any;
import com.google.protobuf.BoolValue;
import com.google.protobuf.Empty;
import io.grcp.proto.job.Job;
import io.grcp.proto.job.StorageGrpc;
import io.grcp.proto.mail.Mail;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.io.IOException;

public class RouteClient {

    public static void main(String[] args) throws IOException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        StorageGrpc.StorageBlockingStub stub = StorageGrpc.newBlockingStub(channel);

        BoolValue offerResponse = stub.offer(Job.newBuilder()
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
                .setFrom("doge")
                .addTo("doge")
                .setSubject("Subject")
                .setBody("Body")
                .build();
    }
}
