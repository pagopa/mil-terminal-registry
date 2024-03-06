# terminal-registry

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Dependency pinning and checksum verification
### Dependency pinning
Gradle lock file's is used to pin the version number of all dependencies. In this way, 
when there are versions with wildcards (e.g. 1.3+) gradle is forced to use the 
specific version listed on the lock file at the time of generation. The lock file doesn't check
the checksum of dependency, cause gradle has a different mechanism to achieve this. To enable
dependency pinning verification this line need to be added to build.gradle file:
```groovy
dependencyLocking {
    lockMode = LockMode.STRICT
    lockAllConfigurations()
}
```

More details at [official doc](https://docs.gradle.org/8.5/userguide/dependency_locking.html)

### Dependency checksum verification
You can generate a file under the project's gradle folder called `verification-metadata.xml`. 
This file contains the dependency hashes and optionally also the pgp signatures (if enabled). 
When gradle runs a build or downloads dependencies it automatically checks if this file is present 
and returns error if a dependency is not present in the `verification-metadata.xml` file 
or if its signature or checksum has changed.

More details at [official doc](https://docs.gradle.org/current/userguide/dependency_verification.html#sub:enabling-verification)

### How to generate both?
To generate dependency pinning or the checksum file, gradle provides two flag to use when running a task.
`--write-locks` to generate lock file and `--write-verification-metadata` to generate checksum file. With
`-DskipTests` you can skip test without getting coverage errors.
The following commands generates both during build task:

`gradle --write-locks --write-verification-metadata sha256 -DskipTests quarkusBuild --no-build-cache --refresh-dependencies`
`gradle --write-locks --write-verification-metadata sha256 -DskipTests quarkusDev --no-build-cache --refresh-dependencies`

NOTE: Sometimes the local gradle cache can inhibit exploration of some dependencies 
so it is good to perform file generation without using cache with `--no-build-cache --refresh-dependencies`

### How to add new dependency?
Add the new dependency/plugin or something else to gradle file and then re-run the command
listed above.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./gradlew quarkusDev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

gradle --write-locks --write-verification-metadata  sha256 build --no-build-cache --refresh-dependencies
refers to https://docs.gradle.org/current/userguide/dependency_verification.html#sub:enabling-verification
and https://docs.gradle.org/8.5/userguide/dependency_locking.html

## Packaging and running the application

The application can be packaged using:
```shell script
./gradlew build
```
It produces the `quarkus-run.jar` file in the `build/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `build/quarkus-app/lib/` directory.

The application is now runnable using `java -jar build/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./gradlew build -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar build/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./gradlew build -Dquarkus.package.type=native
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./build/terminal-registry-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/gradle-tooling.

## Related Guides

- RESTEasy Reactive ([guide](https://quarkus.io/guides/resteasy-reactive)): A Jakarta REST implementation utilizing build time processing and Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it.
- Jacoco - Code Coverage ([guide](https://quarkus.io/guides/tests-with-coverage)): Jacoco test coverage support

## Provided Code

### RESTEasy Reactive

Easily start your Reactive RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
