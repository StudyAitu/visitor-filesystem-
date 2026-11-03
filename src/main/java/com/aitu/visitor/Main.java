package com.aitu.visitor;

import com.aitu.visitor.visitors.*;

public class Main {

    public static void main(String[] args) {

        DirectoryNode root = new DirectoryNode("root");

        DirectoryNode documents =
                new DirectoryNode("documents");

        DirectoryNode projects =
                new DirectoryNode("projects");

        DirectoryNode images =
                new DirectoryNode("images");

        root.add(documents);
        root.add(images);

        documents.add(projects);

        root.add(new FileNode("readme.txt", 500));

        documents.add(new FileNode("notes.txt", 1200));

        projects.add(new FileNode("report.pdf", 5000));
        projects.add(new FileNode("data.txt", 3000));

        images.add(new FileNode("photo.jpg", 7000));
        images.add(new FileNode("logo.png", 2000));

        FileSystemWalker walker = new FileSystemWalker();

        // 1. Total size
        TotalSizeVisitor sizeVisitor =
                new TotalSizeVisitor();

        walker.walk(root, sizeVisitor);

        System.out.println("=== TOTAL SIZE ===");
        System.out.println(
                "Total size: " + sizeVisitor.getTotalSize() + " bytes"
        );

        // 2. Search
        SearchVisitor searchVisitor =
                new SearchVisitor("report", 1000);

        walker.walk(root, searchVisitor);

        System.out.println("\n=== SEARCH RESULTS ===");

        for (String path : searchVisitor.getResults()) {
            System.out.println(path);
        }

        // 3. Statistics
        StatisticsVisitor statisticsVisitor =
                new StatisticsVisitor();

        walker.walk(root, statisticsVisitor);

        System.out.println("\n=== FILE SYSTEM STATISTICS ===");

        System.out.println(
                "Files: " + statisticsVisitor.getFileCount()
        );

        System.out.println(
                "Directories: " + statisticsVisitor.getDirectoryCount()
        );

        System.out.println(
                "Maximum depth: " + statisticsVisitor.getMaxDepth()
        );
    }
}