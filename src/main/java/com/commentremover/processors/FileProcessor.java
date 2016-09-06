package com.commentremover.processors;

import com.commentremover.exception.CommentRemoverException;

import java.io.IOException;

public interface FileProcessor {

    void setCurrentFilePath(String currentFilePath);

    void replaceCommentsWithBlanks() throws IOException, CommentRemoverException;

}
