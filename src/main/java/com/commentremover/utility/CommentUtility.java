package com.commentremover.utility;

import java.util.HashSet;
import java.util.Set;

public class CommentUtility {

    private static final char EXTENSION_SEPARATOR = '.';

    private static final char UNIX_SEPARATOR = '/';

    private static final char WINDOWS_SEPARATOR = '\\';

    private CommentUtility() {
    }

    private static String replaceDotWithSlash(String path) {

        String pathSeparator = getFileSeparator();
        return path.replaceAll("\\.", pathSeparator);
    }

    private static String getFileSeparator() {
        return System.getProperty("file.separator");
    }

    private static String getCurrentPath() {
        return System.getProperty("user.dir");
    }

    public static String getStartInternalPathInValidForm(String path) {
        return getCurrentPath() + getFileSeparator() + replaceDotWithSlash(path).trim();
    }

    public static String getStartExternalPathInValidForm(String path) {
        return path.trim();
    }

    public static Set<String> getExcludePackagesInValidFormForInternalStarting(Set<String> paths) {

        Set<String> pathsList = new HashSet<>(paths.size());
        for (String path : paths) {
            String fullPath = getCurrentPath() + getFileSeparator() + replaceDotWithSlash(path).trim();
            pathsList.add(fullPath);
        }

        return pathsList;
    }

    public static Set<String> getExcludePackagesInValidFormForExternalStarting(String path, Set<String> paths) {

        Set<String> pathList = new HashSet<>(paths.size());
        for (String excludePath : paths) {
            String fullPath = path + getFileSeparator() + replaceDotWithSlash(excludePath).trim();
            pathList.add(fullPath);
        }

        return pathList;
    }

    public static String getExtension(String filename) {

        if (filename == null) {
            return null;
        }

        int index = indexOfExtension(filename);
        if (index == -1) {
            return "";
        }

        return filename.substring(index + 1);
    }

    private static int indexOfExtension(String filename) {

        if (filename == null) {
            return -1;
        }

        int extensionPos = filename.lastIndexOf(EXTENSION_SEPARATOR);
        int lastSeparator = indexOfLastSeparator(filename);

        return (lastSeparator > extensionPos ? -1 : extensionPos);
    }

    private static int indexOfLastSeparator(String filename) {

        if (filename == null) {
            return -1;
        }

        int lastUnixPos = filename.lastIndexOf(UNIX_SEPARATOR);
        int lastWindowsPos = filename.lastIndexOf(WINDOWS_SEPARATOR);

        return Math.max(lastUnixPos, lastWindowsPos);
    }
}
