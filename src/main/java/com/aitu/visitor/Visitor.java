package com.aitu.visitor;

public interface Visitor {

    void begin();

    void setContext(String path, int depth);

    void visit(FileNode file);

    void visit(DirectoryNode directory);
}