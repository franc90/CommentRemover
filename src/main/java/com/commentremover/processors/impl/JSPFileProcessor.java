package com.commentremover.processors.impl;

import com.commentremover.exception.CommentRemoverException;
import com.commentremover.handling.RegexSelector;
import com.commentremover.pattern.FileExtension;

import java.io.IOException;

public class JSPFileProcessor extends XMLFileProcessor {

    @Override
    public void replaceCommentsWithBlanks(String currentFilePath) throws IOException, CommentRemoverException {
        super.replaceCommentsWithBlanks(currentFilePath, RegexSelector.getRegexByFileType(FileExtension.JSP));
    }
}