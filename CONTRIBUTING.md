# Submitting a Pull Request

Please point all pull request to the `dev` branch. Ensure your pull request adheres to the following guidelines:

## Organization of the green queries

Per-langage directories are compliant with the CodeQL plugin for Visual Studio, as `codeql-custom-queries-<lang>`. Then, the recommended sub-directories should be alike the following Python language case:

| Directory | Issue Type | Example CodeQL Query (non green here) |
| :--- | :--- | :--- |
| **`lang`** | Core language syntax | Use of `eval()`, unsafe string concatenation in SQL queries. |
| **`ai` / `ml`** | Specific libraries | `TensorFlow` misconfigurations, unsafe `pickle.load()` usage (RCE). |
| **`web` / `flask`** | Frameworks | Misconfigured CORS headers, Jinja2 template injection. |

## Unit testing of the green queries

Given a sustainability issue for Python data scientists, saying "unsafe pickle", put the query `unsafe-pickle.ql` and its sibling files (.py, .expected) for unit testing.
```
/codeql-custom-queries-python
  |-- ia/
       |-- codeql-pack.yml
       |-- unsafe-pickle.ql       <-- Your new querie
       |-- unsafe-pickle.py       <-- Your test code (with/without problems)
       |-- unsafe-pickle.expected <-- The results that CodeQL is supposed to find
```

# Merging the Pull Request

Your pull request is reviewed, and if it is accepted and merged, the rest of the process is automated. Our workflows check that the unit tests pass, and if they do, your new query is added to the relevant Green CodeQL pack, and its version number is incremented.


# Useful Documentation :

- [Official Github CodeQL Doc](https://codeql.github.com/docs/)
- [CodeQl CheatSheet - CodeQL Agent Project](https://codeql-agent-project.github.io/codeql-cheatsheet/)
