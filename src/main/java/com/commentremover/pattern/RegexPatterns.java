package com.commentremover.pattern;

import java.util.regex.Pattern;

public interface RegexPatterns {

    Pattern COMMENT_REGEX_MULTI_AND_SINGLE_LINE = Pattern.compile("(/\\*([^*]|[\\r\\n]|(\\*+([^*/]|[\\r\\n])))*\\*+/|[\\t]*//.*)|\"(\\\\.|[^\\\\\"])*\"|'(\\\\[\\s\\S]|[^'])*'");

    Pattern COMMENT_REGEX_CSS = Pattern.compile("(/\\*([^*]|[\\r\\n]|(\\*+([^*/]|[\\r\\n])))*\\*+/)|\"(\\\\.|[^\\\\\"])*\"|'(\\\\[\\s\\S]|[^'])*'");

    Pattern COMMENT_REGEX_PROPERTIES = Pattern.compile("([\\t]*#.*)|(=.*)");

    Pattern COMMENT_REGEX_HTML_XML = Pattern.compile("<!--(?!\\s*(?:\\[if [^\\]]+]|<!|>))(?:(?!-->)(.|\\n))*-->");

    Pattern COMMENT_REGEX_JSP = Pattern.compile("<%--(?!\\s*(?:\\[if [^\\]]+]|<!|>))(?:(?!-->)(.|\\n))*--%>");

}
