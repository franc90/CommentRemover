package com.commentremover.processors.configurer.impl;

import com.commentremover.app.CommentRemoverConfiguration;
import com.commentremover.handling.CommentType;
import com.commentremover.pattern.FileExtension;
import com.commentremover.processors.FileProcessor;
import com.commentremover.processors.conditions.RemoveCondition;
import com.commentremover.processors.conditions.impl.NoClassHeaderCondition;
import com.commentremover.processors.conditions.impl.NoCopyrightsHeaderCondition;
import com.commentremover.processors.conditions.impl.NoMultiLineCommentCondition;
import com.commentremover.processors.conditions.impl.NoSingleLineCommentCondition;
import com.commentremover.processors.conditions.impl.NoSingleOrDoubleQuoteCondition;
import com.commentremover.processors.conditions.impl.NoTodoCondition;
import com.commentremover.processors.configurer.FileProcessorConfigurer;
import com.commentremover.processors.file.content.ContentLoaderWithSingleLineSanitize;
import com.commentremover.processors.file.content.PlainContentLoader;
import com.commentremover.processors.impl.JavaProcessor;

import java.util.HashSet;
import java.util.Set;

public class JavaProcessorConfigurer implements FileProcessorConfigurer {

    private static final FileProcessor fileProcessor = FileExtension.JAVA.getFileProcessor();

    protected FileProcessor getFileProcessor() {
        return fileProcessor;
    }

    @Override
    public void configure(CommentRemoverConfiguration config) {
        getFileProcessor().addRemoveCondition(new NoSingleOrDoubleQuoteCondition());

        Set<CommentType> commentTypes = config.getCommentTypes();

        if (!commentTypes.contains(CommentType.CLASS_HEADER)) {
            getFileProcessor().addRemoveCondition(new NoClassHeaderCondition());
        }

        if (!commentTypes.contains(CommentType.COPYRIGHT)) {
            getFileProcessor().addRemoveCondition(new NoCopyrightsHeaderCondition());
        }

        if (!commentTypes.contains(CommentType.MULTI_LINE)) {
            getFileProcessor().addRemoveCondition(new NoMultiLineCommentCondition());
        }

        if (!commentTypes.contains(CommentType.SINGLE_LINE)) {
            getFileProcessor().addRemoveCondition(new NoSingleLineCommentCondition());
        }

        if (!commentTypes.contains(CommentType.TODO)) {
            getFileProcessor().addRemoveCondition(new NoTodoCondition());
        }

        if (commentTypes.contains(CommentType.SINGLE_LINE)) {
            Set<RemoveCondition> conditions = new HashSet<>();
            conditions.add(new NoTodoCondition());
            ContentLoaderWithSingleLineSanitize loader = new ContentLoaderWithSingleLineSanitize(JavaProcessor.SINGLE_LINE_COMMENT, JavaProcessor.SINGLE_LINE_COMMENT_ESCAPE_TOKEN, conditions);
            getFileProcessor().setLoader(loader);
        } else {
            getFileProcessor().setLoader(new PlainContentLoader());
        }
    }

}
