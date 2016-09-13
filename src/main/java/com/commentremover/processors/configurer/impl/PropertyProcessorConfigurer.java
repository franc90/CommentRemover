package com.commentremover.processors.configurer.impl;

import com.commentremover.app.CommentRemoverConfiguration;
import com.commentremover.handling.CommentType;
import com.commentremover.pattern.FileExtension;
import com.commentremover.processors.FileProcessor;
import com.commentremover.processors.conditions.RemoveCondition;
import com.commentremover.processors.conditions.impl.NoEqualsSignCondition;
import com.commentremover.processors.conditions.impl.NoTodoCondition;
import com.commentremover.processors.configurer.FileProcessorConfigurer;
import com.commentremover.processors.file.content.ContentLoaderWithSingleLineSanitize;
import com.commentremover.processors.file.content.PlainContentLoader;
import com.commentremover.processors.impl.JavaProcessor;
import com.commentremover.processors.impl.PropertyProcessor;

import java.util.HashSet;
import java.util.PropertyResourceBundle;
import java.util.Set;

public class PropertyProcessorConfigurer implements FileProcessorConfigurer {

    private static final FileProcessor fileProcessor = FileExtension.PROPERTIES.getFileProcessor();

    @Override
    public void configure(CommentRemoverConfiguration config) {
        fileProcessor.addRemoveCondition(new NoEqualsSignCondition());

        Set<CommentType> commentTypes = config.getCommentTypes();
        if (!commentTypes.contains(CommentType.TODO)) {
            fileProcessor.addRemoveCondition(new NoTodoCondition());
        }

        if (commentTypes.contains(CommentType.SINGLE_LINE)) {
            Set<RemoveCondition> conditions = new HashSet<>();
            conditions.add(new NoTodoCondition());
            ContentLoaderWithSingleLineSanitize loader = new ContentLoaderWithSingleLineSanitize(PropertyProcessor.SINGLE_LINE_COMMENT, PropertyProcessor.SINGLE_LINE_COMMENT_ESCAPE_TOKEN, conditions);
            fileProcessor.setLoader(loader);
        } else {
            fileProcessor.setLoader(new PlainContentLoader());
        }
    }
}
