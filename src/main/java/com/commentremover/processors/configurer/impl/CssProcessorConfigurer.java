package com.commentremover.processors.configurer.impl;

import com.commentremover.app.CommentRemoverConfiguration;
import com.commentremover.handling.CommentType;
import com.commentremover.pattern.FileExtension;
import com.commentremover.processors.FileProcessor;
import com.commentremover.processors.conditions.impl.NoSingleOrDoubleQuoteCondition;
import com.commentremover.processors.conditions.impl.NoTodoCondition;
import com.commentremover.processors.configurer.FileProcessorConfigurer;
import com.commentremover.processors.file.content.PlainContentLoader;

import java.util.Set;

public class CssProcessorConfigurer implements FileProcessorConfigurer {

    private static final FileProcessor fileProcessor = FileExtension.CSS.getFileProcessor();

    @Override
    public void configure(CommentRemoverConfiguration config) {
        fileProcessor.addRemoveCondition(new NoSingleOrDoubleQuoteCondition());

        Set<CommentType> commentTypes = config.getCommentTypes();
        if (!commentTypes.contains(CommentType.TODO)) {
            fileProcessor.addRemoveCondition(new NoTodoCondition());
        }

        fileProcessor.setLoader(new PlainContentLoader());
    }

}
