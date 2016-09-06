package com.commentremover.pattern;

import java.util.regex.Pattern;

public class RegexPatterns {

    private static final Pattern COMMENT_REGEX_MULTI_AND_SINGLE_LINE = Pattern.compile("(/\\*([^*]|[\\r\\n]|(\\*+([^*/]|[\\r\\n])))*\\*+/|[\\t]*//.*)|\"(\\\\.|[^\\\\\"])*\"|'(\\\\[\\s\\S]|[^'])*'");

    private static final Pattern COMMENT_REGEX_CSS = Pattern.compile("(/\\*([^*]|[\\r\\n]|(\\*+([^*/]|[\\r\\n])))*\\*+/)|\"(\\\\.|[^\\\\\"])*\"|'(\\\\[\\s\\S]|[^'])*'");

    private static final Pattern COMMENT_REGEX_PROPERTIES = Pattern.compile("([\\t]*#.*)|(=.*)");

    private static final Pattern COMMENT_REGEX_HTML_XML = Pattern.compile("<!--(?!\\s*(?:\\[if [^\\]]+]|<!|>))(?:(?!-->)(.|\\n))*-->");

    private static final Pattern COMMENT_REGEX_JSP = Pattern.compile("<%--(?!\\s*(?:\\[if [^\\]]+]|<!|>))(?:(?!-->)(.|\\n))*--%>");

    public static Pattern getCommentRegexMultiAndSingleLine() {
        return COMMENT_REGEX_MULTI_AND_SINGLE_LINE;
    }

    public static Pattern getCommentRegexCss() {
        return COMMENT_REGEX_CSS;
    }

    public static Pattern getCommentRegexProperties() {
        return COMMENT_REGEX_PROPERTIES;
    }

    public static Pattern getCommentRegexHtmlXml() {
        return COMMENT_REGEX_HTML_XML;
    }

    public static Pattern getCommentRegexJsp() {
        return COMMENT_REGEX_JSP;
    }
}
