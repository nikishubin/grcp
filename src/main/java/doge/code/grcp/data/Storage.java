package doge.code.grcp.data;

import com.google.protobuf.BoolValue;
import com.google.protobuf.Empty;
import io.grcp.proto.job.Job;
import io.grcp.proto.job.StorageGrpc;
import io.grpc.stub.StreamObserver;

import java.util.LinkedList;

public class Storage extends StorageGrpc.StorageImplBase {

    private LinkedList<Job> storage = new LinkedList<>();

    @Override
    public void pull(Empty request, StreamObserver<Job> responseObserver) {
        Job poll = storage.poll();
        responseObserver.onNext(poll);
        responseObserver.onCompleted();
    }

    @Override
    public void offer(Job request, StreamObserver<BoolValue> responseObserver) {
        storage.offer(request);
        responseObserver.onNext(BoolValue.of(true));
        responseObserver.onCompleted();
    }

    @Override
    public void defineTestData(Empty request, StreamObserver<Empty> responseObserver) {
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }
}
