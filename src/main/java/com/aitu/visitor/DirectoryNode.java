package com.aitu.visitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class DirectoryNode implements Element {

    private final String name;
    private final List<Element> children = new ArrayList<>();

    private DirectoryNode parent;

    public DirectoryNode(String name) {
        if (name == null || name.isBlank() || name.contains("/")) {
            throw new IllegalArgumentException(
                    "Invalid directory name"
            );
        }

        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public DirectoryNode getParent() {
        return parent;
    }

    @Override
    public void setParent(DirectoryNode parent) {
        this.parent = parent;
    }

    @Override
    public List<Element> getChildren() {
        return Collections.unmodifiableList(children);
    }

    public void add(Element element) {
        Objects.requireNonNull(element);

        if (element.getParent() != null) {
            throw new IllegalArgumentException(
                    "Element already has a parent"
            );
        }

        DirectoryNode current = this;

        while (current != null) {
            if (current == element) {
                throw new IllegalArgumentException(
                        "Cycle detected"
                );
            }

            current = current.getParent();
        }

        for (Element child : children) {
            if (child.getName().equals(element.getName())) {
                throw new IllegalArgumentException(
                        "Duplicate sibling name: " + element.getName()
                );
            }
        }

        children.add(element);
        element.setParent(this);
    }

    public void remove(Element element) {
        Objects.requireNonNull(element);

        if (!children.remove(element)) {
            throw new IllegalArgumentException(
                    "Element is not a child of this directory"
            );
        }

        element.setParent(null);
    }

    @Override
    public void accept(Visitor visitor) {
        Objects.requireNonNull(visitor);
        visitor.visit(this);
    }
}
