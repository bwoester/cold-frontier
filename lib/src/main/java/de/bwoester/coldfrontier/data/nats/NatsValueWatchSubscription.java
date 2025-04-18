package de.bwoester.coldfrontier.data.nats;

import de.bwoester.coldfrontier.data.ValueWatchSubscription;
import io.nats.client.impl.NatsKeyValueWatchSubscription;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NatsValueWatchSubscription extends ValueWatchSubscription {

    private final NatsKeyValueWatchSubscription natsKeyValueWatchSubscription;

    @Override
    public void unsubscribe() {
        natsKeyValueWatchSubscription.unsubscribe();
    }
}
