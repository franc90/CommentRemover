package com.commentremover.utility;

import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class FileUtils {

    private static final char EXTENSION_SEPARATOR = '.';
    private static final String CURRENT_DIR = ".";

    private FileUtils() {
    }

    public static String getStartInternalPathInValidForm(String path) {
        return Paths.get(CURRENT_DIR, splitByDot(path)).toString();
    }

    public static String getStartExternalPathInValidForm(String path) {
        return path.trim();
    }

    public static Set<String> getExcludePackagesInValidFormForInternalStarting(Set<String> paths) {
        return getExcludePackagesInValidFormForExternalStarting(CURRENT_DIR, paths);
    }

    public static Set<String> getExcludePackagesInValidFormForExternalStarting(String path, Set<String> paths) {
        Set<String> pathList = new HashSet<>(paths.size());

        for (String excludePath : paths) {
            String fullPath = Paths.get(path, splitByDot(excludePath)).toString();
            pathList.add(fullPath);
        }

        return pathList;
    }

    private static String[] splitByDot(String path) {
        return path.trim().split("\\.");
    }

    public static String getExtension(String filename) {
        if (StringUtils.isEmpty(filename)) {
            return null;
        }

        String lastFileOrDir = Paths
                .get(filename)
                .getFileName()
                .toString();

        int indexOfExtension = lastFileOrDir.lastIndexOf(EXTENSION_SEPARATOR);
        if (indexOfExtension == -1) {
            return "";
        }

        return lastFileOrDir.substring(indexOfExtension + 1);
    }

}
