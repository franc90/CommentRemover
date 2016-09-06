package com.commentremover.processors.impl;

import com.commentremover.app.CommentRemover;
import com.commentremover.exception.CommentRemoverException;
import com.commentremover.handling.RegexSelector;
import com.commentremover.pattern.FileExtension;
import com.commentremover.processors.AbstractFileProcessor;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLFileProcessor extends AbstractFileProcessor {

    public XMLFileProcessor(CommentRemover commentRemover) {
        super(commentRemover);
    }

    @Override
    public void replaceCommentsWithBlanks() throws IOException, CommentRemoverException {
        super.replaceCommentsWithBlanks(RegexSelector.getRegexByFileType(FileExtension.XML));
    }

    @Override
    protected StringBuilder getFileContent(File file) throws IOException, CommentRemoverException {
        return super.getPlainFileContent(file);
    }

    @Override
    protected StringBuilder doRemoveOperation(StringBuilder fileContent, Matcher matcher) throws StackOverflowError {
        String sFileContent = fileContent.toString();
        boolean isTodosRemoving = commentRemover.isRemoveTodos();

        while (matcher.find()) {

            String foundToken = matcher.group();

            if (isTodosRemoving || !doesContainTodo(foundToken)) {
                sFileContent = sFileContent.replaceFirst(Pattern.quote(foundToken), "");
            }
        }
        fileContent = new StringBuilder(sFileContent);

        return fileContent;
    }
}
