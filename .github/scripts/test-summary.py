"""Render Gradle's JUnit XML reports in the GitHub Actions run summary."""

import sys
from collections import Counter
from html import escape
from pathlib import Path
from xml.etree import ElementTree


def results(directory):
    for report in sorted(directory.glob("TEST-*.xml")):
        suite = ElementTree.parse(report).getroot()
        for test in suite.iter("testcase"):
            name = test.attrib["name"]
            group = test.get("classname") or suite.get("name")
            if group and group != "Examples" and not name.startswith(group):
                name = f"{group} · {name}"

            failure = test.find("failure")
            if failure is None:
                failure = test.find("error")
            if failure is not None:
                yield name, "Failed", " ".join(failure.get("message", "").split())[:400]
            elif test.find("skipped") is not None:
                yield name, "Skipped", ""
            else:
                yield name, "Passed", ""


def main(root):
    print("## Test results\n")
    for task, label in (
        ("test", "Unit and characterization tests"),
        ("acceptanceTest", "Acceptance tests"),
        ("integrationTest", "Integration tests"),
    ):
        cases = list(results(root / task))
        if not cases:
            print(f"**{label}** · No test results available.\n")
            continue

        counts = Counter(status for _, status, _ in cases)
        summary = (
            f"{label} · {counts['Passed']} passed, "
            f"{counts['Failed']} failed, {counts['Skipped']} skipped"
        )
        details = "<details open>" if counts["Failed"] else "<details>"
        print(f"{details}\n<summary>{summary}</summary>\n")
        print("<table><thead><tr><th>Status</th><th>Test</th><th>Failure</th></tr></thead><tbody>")
        order = {"Failed": 0, "Skipped": 1, "Passed": 2}
        for name, status, message in sorted(cases, key=lambda case: (order[case[1]], case[0])):
            print(f"<tr><td>{status}</td><td>{escape(name)}</td><td>{escape(message)}</td></tr>")
        print("</tbody></table>\n</details>\n")


if __name__ == "__main__":
    main(Path(sys.argv[1]))
