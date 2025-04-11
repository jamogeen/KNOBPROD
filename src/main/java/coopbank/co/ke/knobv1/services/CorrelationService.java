package coopbank.co.ke.knobv1.services;

import com.solab.iso8583.IsoMessage;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
/**
 * Author   :jamesgichiri
 * Package  :coopbank.co.ke.knobv1.services
 * GitHub   :jamogeen
 * Date     :05-Apr-25
 * Project  :Knob
 */


@Service
public class CorrelationService {
    private ConcurrentMap<String, CompletableFuture<IsoMessage>> pendingRequests;

    @PostConstruct
    public void init() {
        this.pendingRequests = new ConcurrentHashMap<>();
    }

    public CompletableFuture<IsoMessage> registerRequest(String stan) {
        if (stan == null || stan.isBlank()) {
            throw new IllegalArgumentException("STAN cannot be null or empty");
        }
        CompletableFuture<IsoMessage> future = new CompletableFuture<>();
        pendingRequests.put(stan, future);
        return future;
    }

    public void completeRequest(String stan, IsoMessage response) {
        if (stan == null) return;
        CompletableFuture<IsoMessage> future = pendingRequests.remove(stan);
        if (future != null) {
            future.complete(response);
        }
    }

    public void failRequest(String stan, Throwable ex) {
        if (stan == null) return;
        CompletableFuture<IsoMessage> future = pendingRequests.remove(stan);
        if (future != null) {
            future.completeExceptionally(ex);
        }
    }
}