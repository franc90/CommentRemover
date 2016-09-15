package com.commentremover.app;

import com.commentremover.exception.CommentRemoverException;
import com.commentremover.pattern.FileExtension;
import com.commentremover.processors.FileProcessor;
import com.commentremover.utility.FileUtils;

import java.io.IOException;
import java.util.Set;

public class FileProcessRouter {

    private final Set<FileExtension> fileExtensions;

    public FileProcessRouter(Set<FileExtension> fileExtensions) {
        this.fileExtensions = fileExtensions;
    }

    public void removeComments(String currentFilePath) throws IOException, CommentRemoverException {
        String fileExtension = FileUtils.getExtension(currentFilePath);
        FileExtension extension = FileExtension.getForExtensionName(fileExtension);
        if (extension == null) {
            return;
        }

        if (fileExtensions.contains(extension)) {
            FileProcessor fileProcessor = extension.getFileProcessor();
            fileProcessor.replaceCommentsWithBlanks(currentFilePath);
        }
    }

}
