package com.commentremover.app;

import com.commentremover.exception.CommentRemoverException;
import com.commentremover.pattern.FileExtension;
import com.commentremover.processors.*;
import com.commentremover.processors.impl.CSSFileProcessor;
import com.commentremover.processors.impl.HTMLFileProcessor;
import com.commentremover.processors.impl.JSPFileProcessor;
import com.commentremover.processors.impl.JavaFileProcessor;
import com.commentremover.processors.impl.JavaScriptFileProcessor;
import com.commentremover.processors.impl.PropertyFileProcessor;
import com.commentremover.processors.impl.XMLFileProcessor;
import com.commentremover.utility.CommentUtility;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

final class FileProcessRouter {

    private final Map<FileExtension, FileProcessor> fileProcessors;

    private String currentFilePath;

    protected FileProcessRouter(CommentRemover commentRemover) {
        this.fileProcessors = new HashMap<>();
        initProcessors(commentRemover);
    }

    private void initProcessors(CommentRemover commentRemover) {

        if (commentRemover.isRemoveJava()) {
            fileProcessors.put(FileExtension.JAVA, new JavaFileProcessor(commentRemover));
        }

        if (commentRemover.isRemoveJavaScript()) {
            fileProcessors.put(FileExtension.JS, new JavaScriptFileProcessor(commentRemover));
        }

        if (commentRemover.isRemoveProperties()) {
            fileProcessors.put(FileExtension.PROPERTIES, new PropertyFileProcessor(commentRemover));
        }

        if (commentRemover.isRemoveJSP()) {
            fileProcessors.put(FileExtension.JSP, new JSPFileProcessor(commentRemover));
        }

        if (commentRemover.isRemoveCSS()) {
            fileProcessors.put(FileExtension.CSS, new CSSFileProcessor(commentRemover));
        }

        if (commentRemover.isRemoveHTML()) {
            fileProcessors.put(FileExtension.HTML, new HTMLFileProcessor(commentRemover));
        }

        if (commentRemover.isRemoveXML()) {
            fileProcessors.put(FileExtension.XML, new XMLFileProcessor(commentRemover));
        }
    }

    protected void setCurrentFilePath(String currentFilePath) {
        this.currentFilePath = currentFilePath;
    }

    protected void removeComments() throws IOException, CommentRemoverException {

        String fileExtension = CommentUtility.getExtension(currentFilePath);
        FileExtension extension = FileExtension.getForExtensionName(fileExtension);
        if (extension == null) {
            return;
        }

        FileProcessor fileProcessor = fileProcessors.get(extension);
        if (fileProcessor == null) {
            return;
        }

        fileProcessor.setCurrentFilePath(currentFilePath);
        fileProcessor.replaceCommentsWithBlanks();
    }
}