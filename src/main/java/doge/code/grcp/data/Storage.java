package doge.code.grcp.data;

import com.google.protobuf.BoolValue;
import com.google.protobuf.Empty;
import com.google.protobuf.Int64Value;
import com.google.protobuf.StringValue;
import io.grcp.proto.job.Job;
import io.grcp.proto.storage.StorageGrpc;
import io.grpc.stub.StreamObserver;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Storage extends StorageGrpc.StorageImplBase {

    private Map<String, ConcurrentLinkedQueue<Job>> storage = new HashMap<>();

    @Override
    public void use(StringValue request, StreamObserver<BoolValue> responseObserver) {
        super.use(request, responseObserver);
    }

    @Override
    public void pull(Empty request, StreamObserver<Job> responseObserver) {
//        Job poll = storage.poll();
//        responseObserver.onNext(poll);
        responseObserver.onCompleted();
    }

    @Override
    public void offer(Job request, StreamObserver<Int64Value> responseObserver) {
        // storage.offer(request);
        responseObserver.onNext(Int64Value.of(12));
        responseObserver.onCompleted();
    }

    @Override
    public void bury(Job request, StreamObserver<Int64Value> responseObserver) {
        super.bury(request, responseObserver);
    }

    @Override
    public void delete(Job request, StreamObserver<BoolValue> responseObserver) {
        super.delete(request, responseObserver);
    }
}
