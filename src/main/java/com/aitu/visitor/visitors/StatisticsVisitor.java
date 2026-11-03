package com.aitu.visitor.visitors;

import com.aitu.visitor.DirectoryNode;
import com.aitu.visitor.FileNode;
import com.aitu.visitor.Visitor;

public class StatisticsVisitor implements Visitor {

    private int fileCount;
    private int directoryCount;
    private int maxDepth;

    private String currentPath = "";
    private int currentDepth;

    @Override
    public void begin() {
        fileCount = 0;
        directoryCount = 0;
        maxDepth = 0;
    }

    @Override
    public void setContext(String path, int depth) {
        currentPath = path;
        currentDepth = depth;
    }

    @Override
    public void visit(FileNode file) {
        fileCount++;
        maxDepth = Math.max(maxDepth, currentDepth);
    }

    @Override
    public void visit(DirectoryNode directory) {
        directoryCount++;
        maxDepth = Math.max(maxDepth, currentDepth);
    }

    public int getFileCount() {
        return fileCount;
    }

    public int getDirectoryCount() {
        return directoryCount;
    }

    public int getMaxDepth() {
        return maxDepth;
    }
}