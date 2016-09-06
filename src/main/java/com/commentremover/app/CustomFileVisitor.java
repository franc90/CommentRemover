package com.commentremover.app;

import com.commentremover.exception.CommentRemoverException;
import com.commentremover.pattern.FileExtension;
import com.commentremover.utility.CommentUtility;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.List;

public class CustomFileVisitor extends SimpleFileVisitor<Path> {

    private final List<String> excludePackagesPaths;

    private final FileProcessRouter fileProcessRouter;

    private final CommentRemover commentRemover;

    public CustomFileVisitor(CommentRemover commentRemover, Path startingPath) {
        this.commentRemover = commentRemover;
        this.fileProcessRouter = new FileProcessRouter(commentRemover);
        excludePackagesPaths = getExcludePackagesPathsInValidForm(commentRemover.getExcludePackages(), startingPath.toString());
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

        Path parentPath = dir.getParent();
        if (parentPath == null) {
            return FileVisitResult.CONTINUE;
        }

        for (String excludePackagePath : excludePackagesPaths) {
            String currentDirectoryPath = dir.toString();
            if (currentDirectoryPath.equals(excludePackagePath)) {
                return FileVisitResult.SKIP_SUBTREE;
            }
        }

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException, StackOverflowError {

        String fileName = file.getFileName().toString();
        String fileExtension = CommentUtility.getExtension(fileName);
        if (!FileExtension.isExtensionSupported(fileExtension)) {
            return FileVisitResult.CONTINUE;
        }

        try {
            String filePath = file.toString();
            fileProcessRouter.setCurrentFilePath(filePath);
            fileProcessRouter.removeComments();
        } catch (CommentRemoverException e) {
            System.err.println(e.getMessage());
        } catch (StackOverflowError e) {
            System.err.println("StackOverflowError:Please increase your stack size! VM option command is: -Xss40m if you need to increase more -Xss{size}m");
            throw e;
        }

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {

        System.err.println(exc.getMessage());
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        return super.postVisitDirectory(dir, exc);
    }

    private List<String> getExcludePackagesPathsInValidForm(List<String> excludePackagesPaths, String startingPath) {

        if (excludePackagesPaths.isEmpty()) {
            return Collections.emptyList();
        }

        return isStartInternalPathSelected() ?
                CommentUtility.getExcludePackagesInValidFormForInternalStarting(excludePackagesPaths) :
                CommentUtility.getExcludePackagesInValidFormForExternalStarting(startingPath, excludePackagesPaths);
    }

    private boolean isStartInternalPathSelected() {
        return commentRemover.getStartInternalPath() != null;
    }

}
