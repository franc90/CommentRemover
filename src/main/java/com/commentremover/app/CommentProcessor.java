package com.commentremover.app;

import com.commentremover.exception.CommentRemoverException;
import com.commentremover.handling.UserInputHandler;
import com.commentremover.utility.CommentUtility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class CommentProcessor {

    private final CommentRemover commentRemover;
    private final UserInputHandler userInputHandler;
    private final ProgressDisplayer progressDisplayer = new ProgressDisplayer();

    public CommentProcessor(CommentRemover commentRemover) {
        this.commentRemover = commentRemover;
        this.userInputHandler = new UserInputHandler(commentRemover);
    }

    public void start() throws CommentRemoverException, StackOverflowError {
        userInputHandler.checkAllStates();
        progressDisplayer.displayProgressByDots();
        doProcess();
        progressDisplayer.stopDisplayingProgress();
    }

    private void doProcess() {

        final Path startingPath = Paths.get(getSelectedStartingPath());

        try {
            Files.walkFileTree(startingPath, new CustomFileVisitor(commentRemover, startingPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getSelectedStartingPath() {

        String startInternalPath = commentRemover.getStartInternalPath();
        String startExternalPath = commentRemover.getStartExternalPath();

        if (startInternalPath != null) {
            return CommentUtility.getStartInternalPathInValidForm(startInternalPath);
        } else {
            return CommentUtility.getStartExternalPathInValidForm(startExternalPath);
        }
    }

}
