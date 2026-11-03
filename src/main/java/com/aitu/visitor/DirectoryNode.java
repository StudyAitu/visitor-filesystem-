package com.aitu.visitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DirectoryNode implements Element {

    private final String name;
    private final List<Element> children = new ArrayList<>();

    public DirectoryNode(String name) {
        // TODO: Validate name.
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Element> getChildren() {
        return Collections.unmodifiableList(children);
    }

    public void add(Element element) {
        // TODO:
        // Reject duplicate sibling names.
        // Reject multiple parent ownership.
        // Reject cycles.
        // Add valid child.
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
