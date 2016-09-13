package com.commentremover.processors;

import com.commentremover.exception.CommentRemoverException;
import com.commentremover.processors.conditions.RemoveCondition;
import com.commentremover.processors.file.content.ContentLoader;

import java.io.IOException;

public interface FileProcessor {

    void setLoader(ContentLoader loader);

    void addRemoveCondition(RemoveCondition removeCondition);

    void replaceCommentsWithBlanks(String currentFilePath) throws IOException, CommentRemoverException;
}
