# 🍃 Green CodeQL
A first-of-its-kind repository of custom queries to scan your codebase and detect environmental sustainability problems, notably energy-related issues.

## 🚀 How to use green queries on your GitHub

To run these custom green queries directly on your repository, copy this code into a GitHub Action (Java code analysis in the example below):
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
> [!NOTE]
> For lack of a better option, sustainability alerts are reported just as vulnerabilities in 🛡️ Security → Code scanning


## 📦 Status of green queries packs

[6 Green CodeQL packs](https://github.com/orgs/green-code-initiative/packages?repo_name=green-codeql-queries) are currently available:
- Java/Kotlin :
[![Java - Run Tests and Publish CodeQL Pack](https://github.com/green-code-initiative/green-codeql-queries/actions/workflows/PublishJavaPack.yml/badge.svg)](https://github.com/green-code-initiative/green-codeql-queries/actions/workflows/PublishJavaPack.yml)

- Python:
[![Python - Run Tests and Publish CodeQL Pack](https://github.com/green-code-initiative/green-codeql-queries/actions/workflows/PublishPythonPack.yml/badge.svg)](https://github.com/green-code-initiative/green-codeql-queries/actions/workflows/PublishPythonPack.yml)

- C/C++ :
[![C++ - Run Tests and Publish CodeQL Pack](https://github.com/green-code-initiative/green-codeql-queries/actions/workflows/PublishCppPack.yml/badge.svg)](https://github.com/green-code-initiative/green-codeql-queries/actions/workflows/PublishCppPack.yml)

- Javascript/TypeScript :
[![JS - Run Tests and Publish CodeQL Pack](https://github.com/green-code-initiative/green-codeql-queries/actions/workflows/PublishJavascriptPack.yml/badge.svg)](https://github.com/green-code-initiative/green-codeql-queries/actions/workflows/PublishJavascriptPack.yml)

- Actions/YAML :
[![Actions - Run Tests and Publish CodeQL Pack](https://github.com/green-code-initiative/green-codeql-queries/actions/workflows/PublishActionsPack.yml/badge.svg)](https://github.com/green-code-initiative/green-codeql-queries/actions/workflows/PublishActionsPack.yml)

- C# :
[![C# - Run Tests and Publish CodeQL Pack](https://github.com/green-code-initiative/green-codeql-queries/actions/workflows/PublishCSharpPack.yml/badge.svg)](https://github.com/green-code-initiative/green-codeql-queries/actions/workflows/PublishCSharpPack.yml)
