package com.commentremover.processors.impl;

import com.commentremover.exception.CommentRemoverException;
import com.commentremover.handling.CommentType;
import com.commentremover.handling.RegexSelector;
import com.commentremover.pattern.FileExtension;
import com.commentremover.processors.AbstractFileProcessor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertyFileProcessor extends AbstractFileProcessor {

    private static final String singleLineCommentSymbol;
    private static final String singleLineCommentEscapeToken;

    static {
        singleLineCommentSymbol = "#";
        singleLineCommentEscapeToken = "#" + UUID.randomUUID().toString();
    }

    @Override
    public void replaceCommentsWithBlanks(String currentFilePath) throws IOException, CommentRemoverException {
        super.replaceCommentsWithBlanks(currentFilePath, RegexSelector.getRegexByFileType(FileExtension.PROPERTIES));
    }

    @Override
    protected StringBuilder getFileContent(File file) throws IOException, CommentRemoverException {

        StringBuilder content = new StringBuilder((int) file.length());

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
            String temp;
            while ((temp = br.readLine()) != null) {
                String trimmedTemp = temp.trim();
                if (trimmedTemp.startsWith(singleLineCommentSymbol) && !doesContainTodo(trimmedTemp)) {
                    content.append(singleLineCommentEscapeToken).append("\n");
                } else {
                    content.append(temp).append("\n");
                }
            }
        }

        return content;
    }

    @Override
    protected StringBuilder doRemoveOperation(StringBuilder fileContent, Matcher matcher) throws StackOverflowError {

        String sFileContent = fileContent.toString();
        boolean isTodosRemoving = configuration.containsType(CommentType.TODO);
        while (matcher.find()) {

            String foundToken = matcher.group();

            if (isEqualsToken(foundToken)) {
                continue;
            }

            if (isTodosRemoving || !doesContainTodo(foundToken)) {
                sFileContent = sFileContent.replaceFirst(Pattern.quote(foundToken), "");
            }
        }

        fileContent = new StringBuilder(sFileContent);


        return fileContent;
    }

    private boolean isEqualsToken(String foundToken) {
        return foundToken.startsWith("=");
    }
}