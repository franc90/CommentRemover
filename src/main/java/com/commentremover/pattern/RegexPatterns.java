package com.commentremover.pattern;

import java.util.regex.Pattern;

public interface RegexPatterns {

    Pattern MULTI_AND_SINGLE_LINE = Pattern.compile("(/\\*([^*]|[\\r\\n]|(\\*+([^*/]|[\\r\\n])))*\\*+/|[\\t]*//.*)|\"(\\\\.|[^\\\\\"])*\"|'(\\\\[\\s\\S]|[^'])*'");

    Pattern CSS = Pattern.compile("(/\\*([^*]|[\\r\\n]|(\\*+([^*/]|[\\r\\n])))*\\*+/)|\"(\\\\.|[^\\\\\"])*\"|'(\\\\[\\s\\S]|[^'])*'");

    Pattern PROPERTIES = Pattern.compile("([\\t]*#.*)|(=.*)");

    Pattern HTML_XML = Pattern.compile("<!--(?!\\s*(?:\\[if [^\\]]+]|<!|>))(?:(?!-->)(.|\\n))*-->");

    Pattern JSP = Pattern.compile("<%--(?!\\s*(?:\\[if [^\\]]+]|<!|>))(?:(?!-->)(.|\\n))*--%>");

}
