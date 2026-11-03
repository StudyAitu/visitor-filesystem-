# Advanced File System — Visitor Design Pattern

## Astana IT University

| Information | Details |
|---|---|
| University | Astana IT University |
| Course | ShP-2216 — Software Design Patterns |
| Assignment | Assignment 06 |
| Project | Visitor Pattern — Advanced File System |
| Student | Muratbek Ibrai |
| Programme | 6B06102 — Software Engineering |
| Programming Language | Java 17 |
| Build Tool | Apache Maven |
| Testing Framework | JUnit 5 |

---

# 1. Project Overview

This project implements an in-memory hierarchical file system using the Visitor behavioral design pattern.

The system represents files and directories in a tree structure. It supports arbitrary nesting and allows multiple independent operations to be performed on the same file system without placing operation-specific logic inside the element classes.

The implementation includes three concrete visitors:

1. **TotalSizeVisitor** — calculates the total size of all files in a selected subtree.
2. **SearchVisitor** — searches for files using a filename criterion and a minimum file-size threshold.
3. **StatisticsVisitor** — calculates the number of files, directories, and maximum tree depth.

The main objective is to demonstrate double dispatch, separation of responsibilities, extensibility, and reliable traversal of a hierarchical object structure.

# 2. Project Objectives

The main objectives are:

- Implement the Visitor design pattern in Java.
- Create a common Element interface.
- Implement FileNode and DirectoryNode.
- Demonstrate double dispatch through overloaded visitor methods.
- Support nested directories and empty directories.
- Calculate total file sizes.
- Search files using multiple conditions.
- Collect file system statistics.
- Prevent invalid tree modifications.
- Implement automated tests using JUnit 5.
- Demonstrate extensibility by adding a third visitor.

# 3. Technologies Used

| Technology | Purpose |
|---|---|
| Java 17 | Main programming language and target compatibility |
| Maven | Project build and dependency management |
| JUnit 5 | Automated testing |
| IntelliJ IDEA | Development environment |
| Mermaid | UML class diagram |
| Git and GitHub | Version control and project submission |

# 4. Project Structure

```text
visitor-filesystem/
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   └── java/
    │       └── com/aitu/visitor/
    │           ├── Main.java
    │           ├── Element.java
    │           ├── Visitor.java
    │           ├── FileNode.java
    │           ├── DirectoryNode.java
    │           ├── FileSystemWalker.java
    │           └── visitors/
    │               ├── TotalSizeVisitor.java
    │               ├── SearchVisitor.java
    │               └── StatisticsVisitor.java
    └── test/
        └── java/
            └── com/aitu/visitor/
                └── FileSystemTest.java
```

# 5. System Architecture

## 5.1 Element Interface

Element is the common interface implemented by FileNode and DirectoryNode.

It defines the accept(Visitor) operation and provides common tree-related methods.

This abstraction allows the walker to process different element types through one common interface.

## 5.2 FileNode

FileNode represents an individual file.

It contains:

- File name.
- File size in bytes.
- Parent directory reference.

The constructor validates the file name and rejects negative file sizes.

The accept() method delegates processing to the appropriate overloaded visitor method.

## 5.3 DirectoryNode

DirectoryNode represents a directory containing other elements.

Its responsibilities include:

- Maintaining a collection of child elements.
- Supporting nested directories.
- Providing read-only access to its children.
- Rejecting duplicate sibling names.
- Preventing multiple parent ownership.
- Preventing cycles.
- Supporting removal and valid reattachment of elements.

## 5.4 Visitor Interface

Visitor defines the operations available for both concrete element types:

- visit(FileNode)
- visit(DirectoryNode)

It also defines begin() for resetting visitor state and setContext() for receiving the current path and depth from the walker.

## 5.5 FileSystemWalker

FileSystemWalker is responsible for tree traversal.

It recursively processes the root and its children. Before processing each element, it provides the current relative path and depth to the visitor.

Each reachable node is visited exactly once during one traversal.

The traversal logic is centralized rather than duplicated inside individual visitors.

# 6. Visitor Implementations

## 6.1 TotalSizeVisitor

TotalSizeVisitor calculates the total size of files in the selected subtree.

Every file contributes its size exactly once.

Directories do not contribute their own size.

The visitor uses a long variable to accumulate the total number of bytes.

Example:

```text
readme.txt = 500 bytes
notes.txt  = 1200 bytes
report.pdf = 5000 bytes
data.txt   = 3000 bytes
photo.jpg  = 7000 bytes
logo.png   = 2000 bytes

Total = 18700 bytes
```

## 6.2 SearchVisitor

SearchVisitor finds files using two conditions:

1. The filename must match the configured criterion.
2. The file size must be greater than or equal to the minimum size.

Both conditions must be satisfied.

Search results use root-relative paths and forward slashes as separators.

The resulting paths are sorted lexicographically to guarantee deterministic output.

Example:

```text
Search criterion: report
Minimum size: 1000 bytes

Result:
documents/projects/report.pdf
```

## 6.3 StatisticsVisitor

StatisticsVisitor collects three measurements:

- File count.
- Directory count.
- Maximum tree depth.

The root directory starts at depth zero.

Files and directories are included when determining the maximum depth.

Example:

```text
Files: 6
Directories: 4
Maximum depth: 3
```

# 7. Double Dispatch

The Visitor pattern uses double dispatch to select the correct operation.

First, an element calls accept(visitor).

Second, the concrete element delegates to its matching overloaded visit() method.

For example:

```java
file.accept(visitor);
```

Inside FileNode:

```java
visitor.visit(this);
```

Since this refers to FileNode, the compiler selects:

```java
visit(FileNode file)
```

For DirectoryNode, the corresponding method is:

```java
visit(DirectoryNode directory)
```

This allows the visitor to perform different operations depending on the concrete element type without using instanceof-based visitor selection.

# 8. Tree Integrity and Validation

The implementation includes validation to protect the hierarchical structure.

The following invalid operations are rejected:

- Creating a file with a negative size.
- Creating an element with an invalid name.
- Adding two siblings with the same name.
- Assigning an element to multiple parents.
- Adding a directory to itself.
- Creating a cycle in the directory hierarchy.
- Removing an element that is not a child.

Valid nesting, removal, and reattachment are supported.

# 9. Testing

The project uses JUnit 5 for automated verification.

Test class:

```text
FileSystemTest.java
```

The test suite includes 13 test methods covering:

1. Correct double dispatch.
2. Total size across nested directories.
3. Search with combined conditions.
4. Sorted root-relative search paths.
5. File and directory statistics.
6. Empty directory handling.
7. Search with no matching files.
8. Repeated visitor execution.
9. Search state reset.
10. Negative file size rejection.
11. Duplicate sibling name rejection.
12. Multiple parent and cycle rejection.
13. Valid removal and reattachment.

Run the tests using Maven:

```bash
mvn clean test
```

Record the actual test result here after execution:

```text
Tests run: [actual number]
Failures: [actual number]
Errors: [actual number]
Skipped: [actual number]
Build: [actual result]
```

# 10. Running the Application

Compile the project:

```bash
mvn clean compile
```

Run the application:

```bash
java -cp target/classes com.aitu.visitor.Main
```

Alternatively, run Main.java directly through IntelliJ IDEA.

# 11. Sample Application Output

The application creates a sample hierarchical file system and executes all three visitors.

Expected demonstration output:

```text
=== TOTAL SIZE ===
Total size: 18700 bytes

=== SEARCH RESULTS ===
documents/projects/report.pdf

=== FILE SYSTEM STATISTICS ===
Files: 6
Directories: 4
Maximum depth: 3
```

# 12. UML Class Diagram

The UML diagram represents the relationships between:

- Element.
- FileNode.
- DirectoryNode.
- Visitor.
- TotalSizeVisitor.
- SearchVisitor.
- StatisticsVisitor.
- FileSystemWalker.
- Main.

UML Diagram: <img width="1279" height="1280" alt="IMAGE 2026-11-03 19:20:00" src="https://github.com/user-attachments/assets/168017b9-2231-4a16-acc2-c8aa2e18c0f7" />


# 13. Clean Code Principles

The implementation follows several Clean Code principles.

**Meaningful Naming:** Classes and methods use descriptive names that communicate their responsibilities.

**Single Responsibility Principle:** Each visitor performs one independent operation, while FileSystemWalker manages traversal.

**Encapsulation:** DirectoryNode protects its child collection by exposing a read-only view.

**Separation of Concerns:** The object structure and operations are implemented in separate classes.

**Avoiding Type-Checking Chains:** Double dispatch selects visitor operations through overloaded methods instead of instanceof-based visitor selection.

# 14. Extensibility

The architecture supports adding new operations without modifying FileNode, DirectoryNode, or their accept() methods.

For example, StatisticsVisitor can be added as a new implementation of the existing Visitor interface.

However, adding a new element type requires extending the Visitor interface and updating all concrete visitors.

This is an important trade-off of the Visitor design pattern.

# 15. Conclusion

This project demonstrates the practical application of the Visitor behavioral design pattern in an in-memory hierarchical file system.

The implementation supports multiple independent operations over a shared object structure and demonstrates double dispatch, centralized traversal, tree integrity validation, and automated testing.

The three visitors provide file size calculation, configurable file search, and file system statistics.

The project demonstrates how the Visitor pattern separates operations from the underlying object structure and makes it easier to introduce new operations without modifying existing element classes.

# 16. Repository

GitHub Repository: https://github.com/StudyAitu/visitor-filesystem-

Student: Muratbek Ibrai

Astana IT University  
Software Engineering  
ShP-2216 — Software Design Patterns
