package com.commentremover.app;

import com.commentremover.handling.UserInputHandler;
import com.commentremover.pattern.FileExtension;

public class CommentRemoverInitializer {

    private final UserInputHandler userInputHandler = new UserInputHandler();

    public CustomFileVisitor initialize(CommentRemoverConfiguration configuration) {
        userInputHandler.checkAllStates(configuration);
        initializeFileProcessors(configuration);
        return new CustomFileVisitor(new VisitorConfig(configuration));
    }

    private void initializeFileProcessors(CommentRemoverConfiguration configuration) {
        for (FileExtension fileExtension : FileExtension.getAllExtensions()) {
            fileExtension.getFileProcessor().initialize(configuration);
        }
    }

}
