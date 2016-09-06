package com.commentremover.processors.impl;

import com.commentremover.app.CommentRemover;
import com.commentremover.exception.CommentRemoverException;
import com.commentremover.handling.RegexSelector;
import com.commentremover.pattern.FileExtension;

import java.io.IOException;

public class JSPFileProcessor extends HTMLFileProcessor {

    public JSPFileProcessor(CommentRemover commentRemover) {
        super(commentRemover);
    }

    @Override
    public void replaceCommentsWithBlanks() throws IOException, CommentRemoverException {
        super.replaceCommentsWithBlanks(RegexSelector.getRegexByFileType(FileExtension.JSP));
    }
}