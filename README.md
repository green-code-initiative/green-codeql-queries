> [!WARNING]  
> Edit Licence to follow the GSF way of doing things.
> Edit Code of conduct to add the mean of contact in case of problem.

# Green Code Scan Rules

---
Java/Kotlin :
[![Java - Run Tests and Publish CodeQL Pack](https://github.com/green-code-initiative/green-codeql-queries/actions/workflows/PublishJavaPack.yml/badge.svg)](https://github.com/green-code-initiative/green-codeql-queries/actions/workflows/PublishJavaPack.yml)

Python:
[![Python - Run Tests and Publish CodeQL Pack](https://github.com/green-code-initiative/green-codeql-queries/actions/workflows/PublishPythonPack.yml/badge.svg)](https://github.com/green-code-initiative/green-codeql-queries/actions/workflows/PublishPythonPack.yml)

C/C++ :
[![C++ - Run Tests and Publish CodeQL Pack](https://github.com/green-code-initiative/green-codeql-queries/actions/workflows/PublishCppPack.yml/badge.svg)](https://github.com/green-code-initiative/green-codeql-queries/actions/workflows/PublishCppPack.yml)

Javascript/TypeScript :
[![JS - Run Tests and Publish CodeQL Pack](https://github.com/green-code-initiative/green-codeql-queries/actions/workflows/PublishJavascriptPack.yml/badge.svg)](https://github.com/green-code-initiative/green-codeql-queries/actions/workflows/PublishJavascriptPack.yml)

Actions/YAML :
[![Actions - Run Tests and Publish CodeQL Pack](https://github.com/green-code-initiative/green-codeql-queries/actions/workflows/PublishActionsPack.yml/badge.svg)](https://github.com/green-code-initiative/green-codeql-queries/actions/workflows/PublishActionsPack.yml)

C# :
[![C# - Run Tests and Publish CodeQL Pack](https://github.com/green-code-initiative/green-codeql-queries/actions/workflows/PublishCSharpPack.yml/badge.svg)](https://github.com/green-code-initiative/green-codeql-queries/actions/workflows/PublishCSharpPack.yml)

---

This repository have rules which check if your code is eco-friendly (or not). It use CodeQL to find parts of your code that are not good for environment (useless process, memory leak,...).

## Project Map

The rules are put in different folders for each language. Each folder have special queries to detect environnemental unfriendly behavior.

* **Java/Kotlin**
* **C/C++**
* **Javascript/Typescript**
* **Python**
* **Action/Yml**
* **C#**

---

## Usage

### TUI for local projects
You can run inside your project's folder
```
curl --fail --remote-name --location https://raw.githubusercontent.com/TitouanCharrier/cql-green-queries/main/run_green_queries.sh
chmod +x run_green_queries.sh
```
and

```./run_green_queries.sh```


### Manual run for local projects
#### 1) Download test pack
Add desired pack in your codeql installation.
```codeql pack download titouancharrier/cql-green-queries-java```

#### 2) Build your Database
**Case 1** 
You don't want to use the compilation
```codeql database create db --language=java --build-mode=none```

**Case 2**
You are using Maven / Gradle 
```codeql database create db --language=java ```

**Case 3** 
You are compiling using javac
```codeql database create db --language=java --command="javac [your-java-file]"```

#### 3) Run the queries / queries suites
**Case 1** 
You want to run all the queries in the pack.
```codeql database analyze db titouancharrier/cql-green-queries-java --format=csv --output=resultats.csv```

**Case 2** 
You want to run only the queries in a querie suite (.qls)
```codeql database analyze db titouancharrier/cql-green-queries-java:queries-suites/android.qls --format=csv  --output=resultats.csv```

## How to use in your Github

To use copy this code into a Github Action. Precise which language (java by default).

```yaml
name: "Green CodeQL Analysis"
env:
  TARGET_LANGUAGE: "java"
on:
  workflow_dispatch: 
jobs:
  analyze:
    name: Analyze
    runs-on: ubuntu-latest
    permissions:
      actions: read
      contents: read
      security-events: write
    steps:
    - name: Checkout repository
      uses: actions/checkout@v4
    - name: Initialize CodeQL
      uses: github/codeql-action/init@v4
      with:
        languages: ${{ env.TARGET_LANGUAGE }}
        build-mode: none
        packs: green-code-initiative/${{ env.TARGET_LANGUAGE }}-queries
    - name: Perform CodeQL Analysis
      uses: github/codeql-action/analyze@v4
      with:
        category: "Sustainability"

```

