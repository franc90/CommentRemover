package com.commentremover.handling;

import com.commentremover.pattern.FileExtension;
import com.commentremover.pattern.RegexPatterns;

import java.util.regex.Pattern;

public class RegexSelector {

    public static Pattern getRegexByFileType(FileExtension fileExtension) {

        switch (fileExtension) {

            case JS:
            case JAVA:
                return RegexPatterns.getCommentRegexMultiAndSingleLine();

            case PROPERTIES:
                return RegexPatterns.getCommentRegexProperties();

            case CSS:
                return RegexPatterns.getCommentRegexCss();

            case JSP:
                return RegexPatterns.getCommentRegexJsp();

            case XML:
            case HTML:
                return RegexPatterns.getCommentRegexHtmlXml();
            default:
                return null;
        }
    }
}
