package com.commentremover.app;

import com.commentremover.pattern.FileExtension;
import com.commentremover.utility.FileUtils;

import java.util.HashSet;
import java.util.Set;

public class VisitorConfig {

    private final Set<FileExtension> extensionsToClear;
    private final Set<String> excludePackagePaths;

    public VisitorConfig(CommentRemoverConfiguration config) {
        this.extensionsToClear = new HashSet<>(config.getExtensionsToClear());
        this.excludePackagePaths = getExcludePackages(config);
    }

    private Set<String> getExcludePackages(CommentRemoverConfiguration config) {
        if (config.isInternal()) {
            return FileUtils.getExcludePackagesInValidFormForInternalStarting(config.getPackagesToExclude());
        }
        return FileUtils.getExcludePackagesInValidFormForExternalStarting(getStartPath(config), config.getPackagesToExclude());
    }

    private String getStartPath(CommentRemoverConfiguration config) {
        if (config.isInternal()) {
            return FileUtils.getStartInternalPathInValidForm(config.getStartPath());
        }
        return FileUtils.getStartExternalPathInValidForm(config.getStartPath());
    }

    public Set<FileExtension> getExtensionsToClear() {
        return extensionsToClear;
    }

    public Set<String> getExcludePackagePaths() {
        return excludePackagePaths;
    }
}
