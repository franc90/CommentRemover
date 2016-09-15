package com.commentremover.processors.impl;

import com.commentremover.pattern.FileExtension;
import com.commentremover.processors.AbstractFileProcessor;

import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaProcessor extends AbstractFileProcessor {

    public static final String SINGLE_LINE_COMMENT = "//";
    public static final String SINGLE_LINE_COMMENT_ESCAPE_TOKEN = SINGLE_LINE_COMMENT + UUID.randomUUID().toString();
    private static final String SINGLE_LINE_TODO_COMMENT_ESCAPE_PREFIX = UUID.randomUUID().toString();

    @Override
    protected Set<Pattern> getPatterns() {
        return FileExtension.JAVA.getPatterns();
    }

    @Override
    protected StringBuilder doRemoveOperation(StringBuilder fileContent, Matcher matcher) throws StackOverflowError {

        String sFileContent = fileContent.toString();
        while (matcher.find()) {

            String foundToken = matcher.group();

            if (!canBeRemoved(foundToken)) {
                continue;
            }

            // special case for isSingleLineTodoToken
            if (isSingleLineTodoToken(foundToken)) {
                sFileContent = sFileContent.replaceFirst(Pattern.quote(foundToken),
                        foundToken.replace(SINGLE_LINE_COMMENT, SINGLE_LINE_TODO_COMMENT_ESCAPE_PREFIX));
            } else {
                sFileContent = sFileContent.replaceFirst(Pattern.quote(foundToken), "");
            }
        }

        if (sFileContent.contains(SINGLE_LINE_TODO_COMMENT_ESCAPE_PREFIX)) {
            sFileContent = sFileContent.replace(SINGLE_LINE_TODO_COMMENT_ESCAPE_PREFIX, SINGLE_LINE_COMMENT);
        }

        fileContent = new StringBuilder(sFileContent);

        return fileContent;
    }

    private boolean isSingleLineTodoToken(String foundToken) {
        return isSingleCommentToken(foundToken) && doesContainTodo(foundToken);
    }


    private boolean isSingleCommentToken(String foundToken) {
        return foundToken.startsWith(SINGLE_LINE_COMMENT);
    }
}