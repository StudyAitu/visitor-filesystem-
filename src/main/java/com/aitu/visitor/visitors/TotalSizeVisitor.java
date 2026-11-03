package com.aitu.visitor.visitors;

import com.aitu.visitor.DirectoryNode;
import com.aitu.visitor.FileNode;
import com.aitu.visitor.Visitor;

public class TotalSizeVisitor implements Visitor {

    private long totalSize;
    private String currentPath = "";
    private int currentDepth;

    @Override
    public void begin() {
        totalSize = 0;
    }

    @Override
    public void setContext(String path, int depth) {
        currentPath = path;
        currentDepth = depth;
    }

    @Override
    public void visit(FileNode file) {
        totalSize = Math.addExact(totalSize, file.getSize());
    }

    @Override
    public void visit(DirectoryNode directory) {
        // Directories do not contribute to total size.
    }

    public long getTotalSize() {
        return totalSize;
    }
}