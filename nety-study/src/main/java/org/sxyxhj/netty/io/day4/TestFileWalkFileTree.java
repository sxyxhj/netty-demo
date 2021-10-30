package org.sxyxhj.netty.io.day4;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: netty-demo
 * @description:
 * @author: @sxyxhj
 * @create: 2021-10-30 15:34
 **/
public class TestFileWalkFileTree {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("/Users/hanjie/Desktop/github/netty-demo/nety-study");

        AtomicInteger dirCount = new AtomicInteger();
        AtomicInteger fileCount = new AtomicInteger();

        Files.walkFileTree(path,new SimpleFileVisitor<Path>(){

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

                dirCount.getAndIncrement();
                return super.preVisitDirectory(dir, attrs);
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                fileCount.getAndIncrement();
                return super.visitFile(file, attrs);
            }
        });
        System.out.println(dirCount);
        System.out.println(fileCount);

    }
}

    
