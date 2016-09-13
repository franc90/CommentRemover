package com.commentremover.processors.configurer.impl;

import com.commentremover.app.CommentRemoverConfiguration;
import com.commentremover.handling.CommentType;
import com.commentremover.pattern.FileExtension;
import com.commentremover.processors.FileProcessor;
import com.commentremover.processors.conditions.impl.NoTodoCondition;
import com.commentremover.processors.configurer.FileProcessorConfigurer;
import com.commentremover.processors.file.content.PlainContentLoader;

import java.util.Set;

public class XMLProcessorConfigurer implements FileProcessorConfigurer {

    private static final FileProcessor fileProcessor = FileExtension.XML.getFileProcessor();

    protected FileProcessor getFileProcessor() {
        return fileProcessor;
    }

    @Override
    public void configure(CommentRemoverConfiguration config) {
        Set<CommentType> commentTypes = config.getCommentTypes();
        if (!commentTypes.contains(CommentType.TODO)) {
            getFileProcessor().addRemoveCondition(new NoTodoCondition());
        }

        getFileProcessor().setLoader(new PlainContentLoader());
    }
}
