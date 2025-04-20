package de.bwoester.coldfrontier.data;

import java.util.List;

public class ValueEntryPrinter {

    public static String prettyPrint(List<ValueEntry<?>> valueEntries) {
        if (valueEntries.isEmpty()) {
            return "(no entries)";
        }

        int maxBucketLength = valueEntries.stream()
                .map(e -> e.bucketAndKey().bucket().length())
                .max(Integer::compareTo)
                .orElse(10);
        int maxKeyLength = valueEntries.stream()
                .map(e -> e.bucketAndKey().key().length())
                .max(Integer::compareTo)
                .orElse(10);

        StringBuilder sb = new StringBuilder();
        for (ValueEntry<?> i : valueEntries) {
            sb.append(prettyPrintValueEntry(i, maxBucketLength, maxKeyLength)).append(System.lineSeparator());
        }
        return sb.toString();
    }

    private static String prettyPrintValueEntry(ValueEntry<?> valueEntry, int bucketWidth, int keyWidth) {
        return String.format(
                "Tick: %d | Bucket: %-" +bucketWidth+ "s | Key: %-" + keyWidth + "s | Payload: %s",
                valueEntry.event().tick(),
                valueEntry.bucketAndKey().bucket(),
                valueEntry.bucketAndKey().key(),
                payloadToString(valueEntry.event().payload())
        );
    }

    private static String payloadToString(Object payload) {
        // Customize per type if needed
//        if (payload instanceof BuildingCreatedEvent e) {
//            return String.format("BuildingCreated(%s, %s)", e.planetName(), e.buildingType());
//        }
        // fallback to toString()
        return payload.toString();
    }
}
