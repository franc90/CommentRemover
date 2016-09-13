package com.commentremover.processors;

import com.commentremover.exception.CommentRemoverException;
import com.commentremover.processors.conditions.RemoveCondition;
import com.commentremover.processors.file.content.ContentLoader;
import com.commentremover.processors.file.content.FileWriter;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractFileProcessor implements FileProcessor {

    private final FileWriter fileWriter = new FileWriter();

    private List<RemoveCondition> removeConditions = new LinkedList<>();

    private ContentLoader loader;

    protected abstract Pattern getPattern();

    @Override
    public void setLoader(ContentLoader loader) {
        this.loader = loader;
    }

    @Override
    public void addRemoveCondition(RemoveCondition removeCondition) {
        removeConditions.add(removeCondition);
    }

    @Override
    public void replaceCommentsWithBlanks(String currentFilePath) throws IOException, CommentRemoverException {
        File file = new File(currentFilePath);
        checkFileSize(file);

        StringBuilder fileContent = loader.loadContent(file);
        Matcher matcher = getPattern().matcher(fileContent);
        StringBuilder newContent = doRemoveOperation(fileContent, matcher);
        fileWriter.setFileContent(file, newContent.toString());
    }

    private void checkFileSize(File file) throws CommentRemoverException {
        long fileSize = file.length();

        if (fileSize > Integer.MAX_VALUE) {
            throw new CommentRemoverException("File size so big for scanning ! -> " + file.getAbsolutePath());
        }
    }

    protected StringBuilder doRemoveOperation(StringBuilder fileContent, Matcher matcher) {
        String sFileContent = fileContent.toString();
        while (matcher.find()) {

            String foundToken = matcher.group();

            if (canBeRemoved(foundToken)) {
                sFileContent = sFileContent.replaceFirst(Pattern.quote(foundToken), "");
            }
        }

        fileContent = new StringBuilder(sFileContent);
        return fileContent;
    }

    protected boolean canBeRemoved(String token) {
        boolean canBeRemoved = true;
        for (RemoveCondition removeCondition : removeConditions) {
            canBeRemoved &= removeCondition.canBeRemoved(token);
        }
        return canBeRemoved;
    }

    protected boolean doesContainTodo(String foundToken) {
        return Pattern.compile(Pattern.quote("todo"), Pattern.CASE_INSENSITIVE).matcher(foundToken).find();
    }

}
