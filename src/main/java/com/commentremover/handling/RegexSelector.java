package com.commentremover.handling;

import com.commentremover.pattern.FileExtension;
import com.commentremover.pattern.RegexPatterns;

import java.util.regex.Pattern;

public class RegexSelector {

    public static Pattern getRegexByFileType(FileExtension fileExtension) {

        switch (fileExtension) {

            case JS:
            case JAVA:
                return RegexPatterns.COMMENT_REGEX_MULTI_AND_SINGLE_LINE;

            case PROPERTIES:
                return RegexPatterns.COMMENT_REGEX_PROPERTIES;

            case CSS:
                return RegexPatterns.COMMENT_REGEX_CSS;

            case JSP:
                return RegexPatterns.COMMENT_REGEX_JSP;

            case XML:
            case HTML:
                return RegexPatterns.COMMENT_REGEX_HTML_XML;
            default:
                return null;
        }
    }
}
