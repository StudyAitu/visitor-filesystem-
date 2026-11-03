package com.aitu.visitor;

public class FileNode implements Element {

    private final String name;
    private final long size;
    public FileNode(String name, long size) {
        // TODO:
        // 1. Validate name.
        // 2. Reject negative size.
        // 3. Initialize fields.
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
