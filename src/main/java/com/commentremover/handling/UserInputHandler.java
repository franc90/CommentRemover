package com.commentremover.handling;

import com.commentremover.app.CommentRemoverConfiguration;
import com.commentremover.exception.CommentRemoverException;
import com.commentremover.utility.FileUtils;
import com.commentremover.utility.StringUtils;

import java.io.File;
import java.util.Collections;
import java.util.Set;

import static com.commentremover.handling.CommentType.MULTI_LINE;
import static com.commentremover.handling.CommentType.SINGLE_LINE;

public class UserInputHandler {

    public void checkAllStates(CommentRemoverConfiguration configuration) throws CommentRemoverException {
        checkAtLeastOneFileTypeAssigned(configuration);
        checkAnyCommentTypeAssigned(configuration);
        checkStartPointExists(configuration);
        checkExcludePackagesExist(configuration);
    }

    private void checkAtLeastOneFileTypeAssigned(CommentRemoverConfiguration configuration) throws CommentRemoverException {
        if (configuration.getExtensionsToClear().isEmpty()) {
            throw new CommentRemoverException("Please select at least ONE file type to remove comments. (Java, HTML, Properties etc.)");
        }
    }

    private void checkAnyCommentTypeAssigned(CommentRemoverConfiguration configuration) throws CommentRemoverException {
        // TODO change to warn that no type assigned
        Set<CommentType> commentTypesToRemove = configuration.getCommentTypes();
        boolean isAnyCommentTypeAssigned = commentTypesToRemove.contains(SINGLE_LINE) || commentTypesToRemove.contains(MULTI_LINE);

        if (!isAnyCommentTypeAssigned) {
            throw new CommentRemoverException("Please select at least ONE comment type!(singleLine or/and multipleLine)");
        }
    }

    private void checkStartPointExists(CommentRemoverConfiguration configuration) throws CommentRemoverException {
        String startPath = configuration.getStartPath();

        if (StringUtils.isEmpty(startPath)) {
            throw new CommentRemoverException("Please select ONLY ONE starting path type (startInternalPath or startExternalPath)!");
        }

        String fullStartingPath = getFullStartingPath(configuration);
        checkIfExists(fullStartingPath);
    }

    private static String getFullStartingPath(CommentRemoverConfiguration configuration) {
        if (configuration.isInternal()) {
            return FileUtils.getStartInternalPathInValidForm(configuration.getStartPath());
        }
        return FileUtils.getStartExternalPathInValidForm(configuration.getStartPath());
    }

    private void checkIfExists(String fullStartingPath) throws CommentRemoverException {
        File file = new File(fullStartingPath);

        if (!(file.exists() && file.isDirectory())) {
            throw new CommentRemoverException("Please specify valid directory path! " + file.getAbsolutePath() + " is not a valid directory.");
        }
    }

    private void checkExcludePackagesExist(CommentRemoverConfiguration configuration) throws CommentRemoverException {
        Set<String> excludePackagesPaths = getExcludePackagesPaths(configuration);

        for (String path : excludePackagesPaths) {
            File file = new File(path);
            if (!(file.exists() && file.isDirectory())) {
                throw new CommentRemoverException(file.getAbsolutePath() + " is not valid directory path for exclude packages!");
            }
        }
    }

    private Set<String> getExcludePackagesPaths(CommentRemoverConfiguration configuration) {
        Set<String> excludePackagesPaths = configuration.getPackagesToExclude();
        if (excludePackagesPaths.isEmpty()) {
            return Collections.emptySet();
        }

        if (configuration.isInternal()) {
            return FileUtils.getExcludePackagesInValidFormForInternalStarting(excludePackagesPaths);
        }
        return FileUtils.getExcludePackagesInValidFormForExternalStarting(configuration.getStartPath(), excludePackagesPaths);
    }
}
