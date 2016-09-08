package com.commentremover.processors;

import com.commentremover.app.CommentRemoverConfiguration;
import com.commentremover.exception.CommentRemoverException;

import java.io.IOException;

public interface FileProcessor {

    void initialize(CommentRemoverConfiguration configuration);

    void replaceCommentsWithBlanks(String currentFilePath) throws IOException, CommentRemoverException;

}
