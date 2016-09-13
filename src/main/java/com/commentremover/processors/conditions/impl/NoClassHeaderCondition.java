package com.commentremover.processors.conditions.impl;

import com.commentremover.processors.conditions.RemoveCondition;

import java.util.regex.Pattern;

public class NoClassHeaderCondition implements RemoveCondition {

    private static final Pattern pattern = Pattern.compile(Pattern.quote("author"), Pattern.CASE_INSENSITIVE);

    @Override
    public boolean canBeRemoved(String token) {
        return !pattern
                .matcher(token)
                .find();
    }
}
