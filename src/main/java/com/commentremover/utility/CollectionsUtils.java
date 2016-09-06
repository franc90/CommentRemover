package com.commentremover.utility;

import java.util.Collection;
import java.util.Iterator;

public class CollectionsUtils {

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static String concatenate(Collection<?> collection, String separator) {
        if (isEmpty(collection)) {
            return "[]";
        }

        StringBuilder builder = new StringBuilder("[");
        Iterator<?> iter = collection.iterator();
        if (iter.hasNext()) {
            builder.append(iter.next());
            while (iter.hasNext()) {
                builder.append(separator)
                        .append(iter.next());
            }
        }
        return builder.append("]").toString();
    }


}
