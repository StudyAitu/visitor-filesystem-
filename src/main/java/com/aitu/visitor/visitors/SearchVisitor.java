package com.aitu.visitor.visitors;

import com.aitu.visitor.DirectoryNode;
import com.aitu.visitor.FileNode;
import com.aitu.visitor.Visitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class SearchVisitor implements Visitor {

    private final Predicate<String> filenameCriterion;
    private final long minimumSize;

    private final List<String> results = new ArrayList<>();

    private String currentPath = "";
    private int currentDepth;

    public SearchVisitor(
            Predicate<String> filenameCriterion,
            long minimumSize
    ) {

        this.filenameCriterion = Objects.requireNonNull(
                filenameCriterion
        );

        if (minimumSize < 0) {
            throw new IllegalArgumentException(
                    "Minimum size cannot be negative"
            );
        }

        this.minimumSize = minimumSize;
    }

    public SearchVisitor(String filename, long minimumSize) {
        this(name -> name.contains(filename), minimumSize);

        if (filename == null || filename.isBlank()) {
            throw new IllegalArgumentException(
                    "Filename criterion cannot be empty"
            );
        }
    }

    @Override
    public void begin() {
        results.clear();
    }

    @Override
    public void setContext(String path, int depth) {
        currentPath = path;
        currentDepth = depth;
    }

    @Override
    public void visit(FileNode file) {

        boolean nameMatches =
                filenameCriterion.test(file.getName());

        boolean sizeMatches =
                file.getSize() >= minimumSize;

        if (nameMatches && sizeMatches) {
            results.add(currentPath);
        }
    }

    @Override
    public void visit(DirectoryNode directory) {
        // Directory traversal is handled by FileSystemWalker.
    }

    public List<String> getResults() {

        List<String> sorted = new ArrayList<>(results);

        Collections.sort(sorted);

        return Collections.unmodifiableList(sorted);
    }
}