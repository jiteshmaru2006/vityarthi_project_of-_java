# AI Based Resume Screening and Skill Gap Analyzer

Java 17 academic project for Programming in Java (CSE2006). The application compares a candidate resume with a selected entry-level job profile using skill extraction, TF-IDF cosine similarity and transparent weighted scoring. It reports matched and missing skills, classifies the match, recommends next actions, appends a CSV history row and writes a text report.

## Run

From the project root, compile with JDK 17:

```text
java -m jdk.compiler/com.sun.tools.javac.Main -d target/classes <all main .java files>
java -cp target/classes com.vityarthi.resumescreener.app.ConsoleApplication
```

Run tests after compiling main and test sources:

```text
java -cp target/classes:target/test-classes com.vityarthi.resumescreener.ProjectTests
```

The included profiles and sample resume are demonstration data. The score supports self-improvement and must not be used as an automated hiring decision.

## Repository Contents

- `src/main/java` - complete application source code
- `src/test/java` - executable project tests
- `data` - role profiles and sample screening history
- `samples` - sample resume used for the verified run
- `reports` - generated candidate report
- `docs/assets` - architecture diagrams and verified execution screenshot
- `docs` - final DOCX and PDF project reports
- `pom.xml` - Maven project configuration
- `statement.md` - student and course declaration

## GitHub Upload

Create an empty GitHub repository, extract the project ZIP, and upload everything inside the extracted `AI_Resume_Screening_Project_Jitesh_Maru` folder. Keep the same folder structure so the application can locate the files in `data` and `samples`.
