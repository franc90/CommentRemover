package com.commentremover.app;

import com.commentremover.handling.CommentType;
import com.commentremover.pattern.FileExtension;
import com.commentremover.utility.CollectionsUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class CommentRemoverConfiguration {

    private final Set<FileExtension> extensionsToClear;

    private final Set<CommentType> commentTypes;

    private final String startPath;
    private final boolean isInternal;
    private final Set<String> packagesToExclude;

    private CommentRemoverConfiguration(Builder builder) {
        extensionsToClear = builder.extensionsToClear;
        commentTypes = builder.commentTypes;
        startPath = builder.startPath;
        isInternal = builder.isInternal;
        packagesToExclude = builder.packagesToExclude;
    }

    public Set<FileExtension> getExtensionsToClear() {
        return extensionsToClear;
    }

    public Set<CommentType> getCommentTypes() {
        return commentTypes;
    }

    public String getStartPath() {
        return startPath;
    }

    public boolean isInternal() {
        return isInternal;
    }

    public Set<String> getPackagesToExclude() {
        return packagesToExclude;
    }

    public boolean containsType(CommentType commentType) {
        return commentTypes.contains(commentType);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private Set<FileExtension> extensionsToClear = new HashSet<>();
        private Set<CommentType> commentTypes = new HashSet<>();
        private String startPath;
        private boolean isInternal;
        private Set<String> packagesToExclude = new HashSet<>();

        private Builder() {
        }

        public Builder withExtensionsToClear(Set<FileExtension> extensionsToClear) {
            this.extensionsToClear = Objects.requireNonNull(extensionsToClear, "extensions to clear cannot be null");
            return this;
        }

        public Builder addExtensionsToClear(Collection<FileExtension> extensionsToClear) {
            this.extensionsToClear.addAll(extensionsToClear);
            return this;
        }

        public Builder addExtensionsToClear(FileExtension... extensionsToClear) {
            this.extensionsToClear.addAll(Arrays.asList(extensionsToClear));
            return this;
        }

        public Builder addExtensionToClear(FileExtension extensionToClear) {
            this.extensionsToClear.add(extensionToClear);
            return this;
        }

        public Builder withCommentTypes(Set<CommentType> commentTypes) {
            this.commentTypes = Objects.requireNonNull(commentTypes, "comment types cannot be null");
            return this;
        }

        public Builder addCommentTypes(Collection<CommentType> commentTypes) {
            this.commentTypes.addAll(commentTypes);
            return this;
        }

        public Builder addCommentTypes(CommentType... commentTypes) {
            this.commentTypes.addAll(Arrays.asList(commentTypes));
            return this;
        }

        public Builder addCommentType(CommentType commentType) {
            this.commentTypes.add(commentType);
            return this;
        }

        public Builder removeTodos() {
            this.commentTypes.add(CommentType.TODO);
            return this;
        }

        public Builder removeMultiLines() {
            this.commentTypes.add(CommentType.MULTI_LINE);
            return this;
        }

        public Builder removeSingleLines() {
            this.commentTypes.add(CommentType.SINGLE_LINE);
            return this;
        }

        public Builder removeClassHeaders() {
            this.commentTypes.add(CommentType.CLASS_HEADER);
            return this;
        }

        public Builder removeCopyrights() {
            this.commentTypes.add(CommentType.COPYRIGHT);
            return this;
        }

        public Builder removeDocumentation() {
            this.commentTypes.add(CommentType.DOCUMENTATION);
            return this;
        }

        public Builder withStartPath(String startPath, boolean isInternal) {
            this.startPath = startPath;
            this.isInternal = isInternal;
            return this;
        }

        public Builder withPackagesToExclude(Set<String> packagesToExclude) {
            this.packagesToExclude = Objects.requireNonNull(packagesToExclude, "packages to exclude cannot be null");
            return this;
        }

        public Builder addPackagesToExclude(Collection<String> packagesToExclude) {
            this.packagesToExclude.addAll(packagesToExclude);
            return this;
        }

        public Builder addPackagesToExclude(String... packagesToExclude) {
            this.packagesToExclude.addAll(Arrays.asList(packagesToExclude));
            return this;
        }

        public Builder addPackageToExclude(String packageToExclude) {
            this.packagesToExclude.add(packageToExclude);
            return this;
        }

        public CommentRemoverConfiguration build() {
            return new CommentRemoverConfiguration(this);
        }
    }


    @Override
    public String toString() {
        return "CommentRemoverConfiguration{" +
                "extensionsToClear=" + CollectionsUtils.concatenate(extensionsToClear, ",") +
                ", commentTypes=" + CollectionsUtils.concatenate(commentTypes, ",") +
                ", startPath='" + startPath + '\'' +
                ", isInternal=" + isInternal +
                ", packagesToExclude=" + CollectionsUtils.concatenate(packagesToExclude, ",") +
                '}';
    }
}
