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

public class JavaFileProcessor extends AbstractFileProcessor {

    private static final String singleLineCommentSymbol;
    private static final String singleLineCommentEscapeToken;
    private static final String singleLineTodoCommentEscapePrefix;

    static {
        singleLineCommentSymbol = "//";
        singleLineCommentEscapeToken = "//" + UUID.randomUUID().toString();
        singleLineTodoCommentEscapePrefix = UUID.randomUUID().toString();
    }

    @Override
    public void replaceCommentsWithBlanks(String currentFilePath) throws IOException, CommentRemoverException {
        super.replaceCommentsWithBlanks(currentFilePath, RegexSelector.getRegexByFileType(FileExtension.JAVA));
    }

    @Override
    protected StringBuilder getFileContent(File file) throws IOException, CommentRemoverException {
        return isGoingToRemoveSingleComments() ? this.getContentForSingleLinesRemoving(file)
                : super.getPlainFileContent(file);
    }

    private boolean isGoingToRemoveSingleComments() {
        return configuration.getCommentTypes().contains(CommentType.SINGLE_LINE);
    }

    private StringBuilder getContentForSingleLinesRemoving(File file) throws IOException {

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
        boolean isBothCommentTypeNotSelected = isBothCommentTypeNotSelected();
        boolean isPreserveJavaClassHeader = !configuration.containsType(CommentType.CLASS_HEADER);
        boolean isPreserveCopyrightHeaders = !configuration.containsType(CommentType.COPYRIGHT);
        while (matcher.find()) {

            String foundToken = matcher.group();

            if (isDoubleOrSingleQuoteToken(foundToken)) {
                continue;
            }

            if (isPreserveJavaClassHeader) {
                if (isClassHeader(foundToken)) {
                    continue;
                }
            }
            if (isPreserveCopyrightHeaders) {
                if (isCopyRightHeader(foundToken)) {
                    continue;
                }
            }

            if (isBothCommentTypeNotSelected) {

                if (isOnlyMultiLineCommentSelected() && isSingleCommentToken(foundToken)) {
                    continue;
                }

                if (isOnlySingleCommentSelected() && isMultiLineCommentToken(foundToken)) {
                    continue;
                }
            }

            if (isTodosRemoving) {
                sFileContent = sFileContent.replaceFirst(Pattern.quote(foundToken), "");
            } else {

                if (isSingleLineTodoToken(foundToken)) {
                    sFileContent = sFileContent.replaceFirst(Pattern.quote(foundToken),
                            foundToken.replace("//", singleLineTodoCommentEscapePrefix));
                }

                if (!doesContainTodo(foundToken)) {
                    sFileContent = sFileContent.replaceFirst(Pattern.quote(foundToken), "");
                }
            }
        }

        if (!isTodosRemoving) {
            sFileContent = sFileContent.replace(singleLineTodoCommentEscapePrefix, "//");
        }

        fileContent = new StringBuilder(sFileContent);

        return fileContent;
    }

    private boolean isBothCommentTypeNotSelected() {
        return !(configuration.containsType(CommentType.SINGLE_LINE) && configuration.containsType(CommentType.MULTI_LINE));
    }

    private boolean isDoubleOrSingleQuoteToken(String foundToken) {
        return foundToken.startsWith("\"") || foundToken.startsWith("\'");
    }

    private boolean isOnlyMultiLineCommentSelected() {
        return !configuration.containsType(CommentType.SINGLE_LINE) && configuration.containsType(CommentType.MULTI_LINE);
    }

    private boolean isSingleCommentToken(String foundToken) {
        return foundToken.startsWith("//");
    }

    private boolean isOnlySingleCommentSelected() {
        return configuration.containsType(CommentType.SINGLE_LINE) && !configuration.containsType(CommentType.MULTI_LINE);
    }

    private boolean isMultiLineCommentToken(String foundToken) {
        return foundToken.startsWith("/*");
    }

    private boolean isSingleLineTodoToken(String foundToken) {
        return isSingleCommentToken(foundToken) && doesContainTodo(foundToken);
    }

    private boolean isCopyRightHeader(String foundToken) {
        return Pattern.compile(Pattern.quote("copyright"), Pattern.CASE_INSENSITIVE).matcher(foundToken).find();
    }

    private boolean isClassHeader(String foundToken) {
        return Pattern.compile(Pattern.quote("author"), Pattern.CASE_INSENSITIVE).matcher(foundToken).find();
    }
}