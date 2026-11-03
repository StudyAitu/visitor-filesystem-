package com.aitu.visitor;

import java.util.Objects;

public class FileSystemWalker {

    public void walk(Element root, Visitor visitor) {

        Objects.requireNonNull(root, "Root cannot be null");
        Objects.requireNonNull(visitor, "Visitor cannot be null");

        visitor.begin();

        traverse(root, "", 0, visitor);
    }

    private void traverse(
            Element element,
            String path,
            int depth,
            Visitor visitor
    ) {

        visitor.setContext(path, depth);

        element.accept(visitor);

        for (Element child : element.getChildren()) {

            String childPath = path.isEmpty()
                    ? child.getName()
                    : path + "/" + child.getName();

            traverse(child, childPath, depth + 1, visitor);
        }
    }
}