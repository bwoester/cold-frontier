package de.bwoester.coldfrontier.acceptance.reporting;

import io.cucumber.gherkin.GherkinParser;
import io.cucumber.messages.types.Envelope;
import io.cucumber.messages.types.Pickle;
import io.cucumber.messages.types.PickleTag;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public final class PendingScenarioReport {
    private static final Pattern ISSUE_TAG = Pattern.compile("@issue-([1-9][0-9]*)");

    public static void main(String[] args) throws IOException {
        Path root = Path.of(args[0]);
        Path report = Path.of(args[1]);
        var parser = GherkinParser.builder().includeSource(false).includeGherkinDocument(false).build();
        List<String> entries = new ArrayList<>();
        try (var files = Files.walk(root)) {
            for (Path feature : files.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".feature")).sorted().toList()) {
                for (Envelope message : parser.parse(feature).toList()) {
                    if (message.getParseError().isPresent()) {
                        throw new IllegalArgumentException(feature + ": " + message.getParseError().orElseThrow().getMessage());
                    }
                    message.getPickle().flatMap(pickle -> pendingEntry(root.relativize(feature), pickle))
                            .ifPresent(entries::add);
                }
            }
        }

        var summary = new StringBuilder("## Pending game behavior scenarios\n\n");
        if (entries.isEmpty()) {
            summary.append("No deferred cases.\n");
        } else {
            summary.append("<details><summary>").append(entries.size())
                    .append(" deferred cases</summary>\n\n");
            entries.forEach(entry -> summary.append(entry).append('\n'));
            summary.append("\n</details>\n");
        }
        Files.createDirectories(report.getParent());
        Files.writeString(report, summary);
    }

    private static Optional<String> pendingEntry(Path feature, Pickle pickle) {
        List<String> tags = pickle.getTags().stream().map(PickleTag::getName).toList();
        if (!tags.contains("@pending")) {
            return Optional.empty();
        }
        List<String> issues = tags.stream().map(ISSUE_TAG::matcher).filter(matcher -> matcher.matches())
                .map(matcher -> issueLink(matcher.group(1))).distinct().toList();
        if (issues.isEmpty()) {
            throw new IllegalArgumentException(feature + ": pending case '" + pickle.getName() + "' needs an @issue-<number> tag");
        }
        return Optional.of("- " + code(pickle.getName()) + " · " + code(feature.toString()) + " · " + String.join(", ", issues));
    }

    private static String issueLink(String number) {
        String server = System.getenv("GITHUB_SERVER_URL");
        String repository = System.getenv("GITHUB_REPOSITORY");
        if (server == null || repository == null) {
            return "#" + number;
        }
        return "[#" + number + "](" + server + "/" + repository + "/issues/" + number + ")";
    }

    private static String code(String value) {
        return "<code>" + value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;") + "</code>";
    }
}
