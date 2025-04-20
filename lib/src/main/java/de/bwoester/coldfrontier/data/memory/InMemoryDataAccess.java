package de.bwoester.coldfrontier.data.memory;

import de.bwoester.coldfrontier.data.DataAccess;
import de.bwoester.coldfrontier.data.ValueEntry;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class InMemoryDataAccess implements DataAccess {

    private static final int BATCH_SIZE = 100;

    @Getter
    private final List<ValueEntry<?>> data = new ArrayList<>();

    @Override
    public List<ValueEntry<?>> getMostRecentEntries() {
        return getMostRecentEntries(BATCH_SIZE);
    }

    @Override
    public List<ValueEntry<?>> getMostRecentEntries(int maxEntries) {
        int startSeq = Math.max(0, data.size() - maxEntries);
        return List.copyOf(data.subList(startSeq, data.size()));
    }

}
