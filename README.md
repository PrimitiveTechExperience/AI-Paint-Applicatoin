# AI Paint Application

A JavaFX paint program extended with AI-assisted drawing. On top of the usual
paint-app basics (draw shapes, save/load files) it can talk to a local
[Ollama](https://ollama.com/) `llama3` server to generate or modify drawings
from plain-English prompts.

## Features

- **Shape drawing**: rectangles, circles, polylines, and freehand squiggles
- **Select & manipulate**: move/resize existing shapes via per-shape
  manipulator strategies
- **Save/load**: a custom, human-readable "Paint Save File" text format
- **AI-assisted drawing** (via `OllamaPaint`):
  - Generate a new drawing from a text prompt (`newFile`)
  - Modify an existing drawing from a text prompt (`modifyFile`)
  - Built-in demo generators: a solar system, a tree-lined path, and a face
  - Built-in demo modifiers: recolor shapes, turn a rectangle into a circle,
    or add a new shape

## Project layout

- [a3/](a3/) — the Maven/JavaFX project
  - `src/main/java/ca/utoronto/utm/paint/` — application source
    - `Paint.java` — JavaFX application entry point
    - `PaintModel.java`, `View.java` — MVC model/view+controller
    - `*Command.java`, `*ManipulatorStrategy.java` — shape drawing/editing
    - `PaintFileParser.java`, `PaintSaveFileSavable.java` — save file format
    - `Ollama.java`, `OllamaPaint.java` — LLM integration for AI drawing
  - `src/main/resources/` — save-file format specs and example prompts/files
    used to prime the AI
  - `src/test/` — JUnit tests and sample files for the parser

## Requirements

- JDK 22
- Maven (or the bundled `mvnw`/`mvnw.cmd` wrapper)
- (Optional, for AI features) access to an Ollama server running `llama3`

## Running

From the `a3` directory:

```bash
./mvnw clean javafx:run
```

## AI setup

`OllamaPaint` points at a specific Ollama host by default
(see `OllamaPaint.main`). To use the AI features, update the host to point at
your own Ollama server (running the `llama3` model on port `11434`) before
running the AI demo methods.
