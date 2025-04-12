package de.bwoester.coldfrontier.messaging;

import java.util.List;

public class GameEventPrinter {

    public static String prettyPrint(List<GameEvent<?>> events) {
        if (events.isEmpty()) {
            return "(no events)";
        }

        int maxSubjectLength = events.stream()
                .map(e -> e.subject().length())
                .max(Integer::compareTo)
                .orElse(10);

        StringBuilder sb = new StringBuilder();
        for (GameEvent<?> event : events) {
            sb.append(prettyPrintEvent(event, maxSubjectLength)).append(System.lineSeparator());
        }
        return sb.toString();
    }

    private static String prettyPrintEvent(GameEvent<?> event, int subjectWidth) {
        return String.format(
                "Seq: %d | Tick: %d | Subject: %-" + subjectWidth + "s | Payload: %s",
                event.seq(),
                event.tick(),
                event.subject(),
                payloadToString(event.payload())
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
