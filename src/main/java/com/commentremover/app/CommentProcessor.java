package com.commentremover.app;

import com.commentremover.exception.CommentRemoverException;
import com.commentremover.utility.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CommentProcessor {

    private final CommentRemoverInitializer initializer = new CommentRemoverInitializer();
    private final ProgressPresenter progressPresenter = new ProgressPresenter();
    private final CommentRemoverConfiguration configuration;

    public CommentProcessor(CommentRemoverConfiguration configuration) {
        this.configuration = configuration;
    }

    public void start() throws CommentRemoverException, StackOverflowError {
        CustomFileVisitor customFileVisitor = initializer.initialize(configuration);
        progressPresenter.displayProgressByDots();
        doProcess(customFileVisitor);
        progressPresenter.stopDisplayingProgress();
    }

    private void doProcess(CustomFileVisitor customFileVisitor) {
        final Path startingPath = Paths.get(getSelectedStartingPath());

        try {
            Files.walkFileTree(startingPath, customFileVisitor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // todo remove - sth similar in com.commentremover.app.VisitorConfig
    private String getSelectedStartingPath() {
        if (configuration.isInternal()) {
            return FileUtils.getStartInternalPathInValidForm(configuration.getStartPath());
        }
        return FileUtils.getStartExternalPathInValidForm(configuration.getStartPath());
    }

}
