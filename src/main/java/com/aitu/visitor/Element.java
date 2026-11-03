package com.aitu.visitor;

import java.util.List;

public interface Element {

    String getName();

    DirectoryNode getParent();

    void setParent(DirectoryNode parent);

    default List<Element> getChildren() {
        return List.of();
    }

    void accept(Visitor visitor);
}