package com.aitu.visitor;

public interface Element {
    void accept(Visitor visitor);
}
