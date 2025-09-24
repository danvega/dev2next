# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Spring Boot 3.5.6 application demonstrating integration of LLMs with Java using the Model Context Protocol (MCP). The project is set up for a conference presentation about building AI-enabled Java applications.

**Tech Stack:**
- Java 25
- Spring Boot 3.5.6 with Spring Web
- Spring AI 1.0.2 with MCP Server WebMVC starter
- Maven build system

## Architecture

The application follows a simple Spring Boot structure with:

- **Application.java**: Main Spring Boot application class
- **Conference.java**: Record representing conference data structure
- **Session.java**: Record representing individual conference sessions
- **SessionType.java**: Enum for valid session types (Workshop, Keynote, Talk, General Session)
- **ConferenceRepository.java**: Repository for loading and providing conference data
- **SessionTools.java**: MCP tools for session querying and analytics
- **TrackResource.java**: MCP resource for accessing conference tracks
- **ConferencePrompts.java**: MCP prompts for guided conference data analysis
- **Data Layer**: JSON files in `src/main/resources/data/` containing conference session data:
  - `sessions.json`: Main session data
  - `sessions-cyc.json`: Additional session data

The project uses Java records for immutable data structures and Jackson for JSON processing. The ConferenceRepository centralizes JSON loading logic, eliminating duplication between SessionTools and TrackResource.

## Common Commands

### Build and Run
```bash
# Build the project
./mvnw clean compile

# Run the application
./mvnw spring-boot:run

# Package the application
./mvnw clean package
```

### Testing
```bash
# Run all tests
./mvnw test

# Run a specific test class
./mvnw test -Dtest=ApplicationTests
```

### Development
```bash
# Clean and build
./mvnw clean install

# Skip tests during build
./mvnw clean install -DskipTests
```

## Model Context Protocol Integration

This application uses Spring AI's MCP Server WebMVC starter for integrating with LLM tools. The MCP implementation allows the application to serve as a context provider for AI models, enabling them to access conference data and functionality.

## Data Structure

Conference sessions are stored as JSON in the resources/data directory. The Session record includes:
- Basic info: day, time, duration, title, type, room
- Speaker details: speakers array
- Categorization: track list for session topics

The JSON structure follows a flat session-centric approach with all sessions in a single array under `conference.sessions`.