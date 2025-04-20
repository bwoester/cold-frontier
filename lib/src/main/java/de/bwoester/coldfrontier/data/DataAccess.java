package de.bwoester.coldfrontier.data;

import java.util.List;

public interface DataAccess {

    List<ValueEntry<?>> getMostRecentEntries();

    List<ValueEntry<?>> getMostRecentEntries(int maxEntries);

}
