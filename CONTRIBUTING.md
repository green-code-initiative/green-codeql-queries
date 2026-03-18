# Submitting a Pull Request

Please point all pull request to the `dev` branch. Ensure your contribution adheres to the following quality guidelines:

## Organization of the green queries

Per-language directories `codeql-custom-queries-<lang>` are compliant with the CodeQL extension for Visual Studio Code. For better clarity, we recommend to place your query into a sub-directory, as the following example targeting the Python language:

| Directory | Scope | Sustainability issue the query could detect |
| :--- | :--- | :--- |
| **`lang`** | Core language syntax | “Busy-wait loop”, “Repeated regex compilation inside loop”, ... |
| **`ai`** | Specific domains | “Unnecessary retraining”, “No batching for inference”, ... |
| **`web` / `flask`** | Frameworks | “No pagination”, “No caching headers (ETag/Cache-Control)”, ... |

> [!TIP]
> Your query metadata must be chosen carefully (`@name`/`@description`/`@tags`) and even your alert message, so that *Copilot Autofix* can infer a patch. Optional file `.qhelp` is also useful.

## Unit testing of the green queries

Given a sustainability general issue for Python, saying "Busy-Wait Loop", put the query `busy-wait-loop.ql` and its sibling files (.py, .expected) for unit testing.
```
/codeql-custom-queries-python
  |-- lang/
       |-- busy-wait-loop.ql        <-- Your new query
       |-- busy-wait-loop.py        <-- Your test code (with/without problems)
       |-- busy-wait-loop.expected  <-- The issues that CodeQL is supposed to detect
```

# Merging the Pull Request

Your pull request will be reviewed, and if it is accepted and merged, the rest of the process is automated. Our workflows check that the unit tests pass (please run tests locally before), and if they do, your new query is added to the relevant Green CodeQL pack, and its version number is incremented.


# Useful Documentation :

- [Official Github CodeQL Doc](https://codeql.github.com/docs/)
- [CodeQl CheatSheet - CodeQL Agent Project](https://codeql-agent-project.github.io/codeql-cheatsheet/)
