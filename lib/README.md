# Library build and dependency maintenance

The library targets Java 21 LTS. Run its checks from the repository root:

```sh
./gradlew :lib:build :lib:integrationTest --warning-mode=all
```

`test` runs unit tests and the Cucumber characterization suite without Docker.
`integrationTest` runs only JUnit Jupiter tests tagged `integration` and requires
Docker for the NATS Testcontainer. Both tasks deliberately share `src/test`;
their inputs are explicit and lazily wired for Gradle 9. `build` alone does not
run the Docker tests. CI invokes both tasks.

## Versions and ownership

- Gradle 9.9.1, with regenerated Wrapper files and a pinned distribution SHA-256.
- FreeFair Lombok 9.8.2 for the Gradle 9 build.
- Jackson 2.21 LTS, currently BOM 2.21.8, is selected by **lib**. It is not
  inherited from Quarkus. Individual Jackson modules use the BOM's versions
  (annotations and databind need not have identical patch numbers).
- Jackson databind is an `api` dependency because public constructors expose
  `ObjectMapper`. A regular `platform`, not an `enforcedPlatform`, aligns the
  Jackson modules without forcing a framework platform on consumers.
- JUnit's own BOM aligns Jupiter, Platform Suite and the explicit runtime
  launcher; this change does not intentionally upgrade the JUnit major version.
- Logback is updated to 1.6.7. The project has no Janino-based conditional
  logging configuration to migrate.

Jackson 3 is not required by current library functionality. Its package/API
migration is deferred; this is not a claim that Jackson 3 is unstable.
Serializer tests cover reading the existing event JSON shape and a round trip
with generic record payloads, precise decimals and nanosecond timestamps.

## Shared-build prerequisite

Gradle configures `backend` even when invoking `:lib:test`. With Gradle 9.9.1,
Quarkus 3.22.1 fails during configuration with
`Detached configuration cannot extend other configurations`.
The Quarkus plugin and BOM are therefore updated together to 3.40.1.2 LTS.
This prerequisite does not establish Quarkus as the library's dependency owner
or as the project's permanent framework choice.

For future updates, check resolved dependencies in both contexts:

```sh
./gradlew :lib:dependencyInsight --dependency jackson-databind --configuration runtimeClasspath
./gradlew :backend:dependencyInsight --dependency jackson-databind --configuration runtimeClasspath
```

The backend's enforced platform may select different versions in the application.
A successful library build alone is not evidence of backend runtime compatibility.

## References

- [Gradle 9 migration guide](https://docs.gradle.org/9.9.1/userguide/upgrading_major_version_9.html)
- [Jackson release and support policy](https://github.com/FasterXML/jackson/wiki/Jackson-Releases)
- [Quarkus 3.26: Gradle 9 support](https://quarkus.io/blog/quarkus-3-26-released/)
- [Quarkus release support](https://quarkus.io/releases/)
