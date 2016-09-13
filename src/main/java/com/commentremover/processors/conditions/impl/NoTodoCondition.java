package com.commentremover.processors.conditions.impl;

import com.commentremover.processors.conditions.RemoveCondition;

import java.util.regex.Pattern;

public class NoTodoCondition implements RemoveCondition {

    private static final Pattern pattern = Pattern.compile(Pattern.quote("todo"), Pattern.CASE_INSENSITIVE);

    @Override
    public boolean canBeRemoved(String token) {
        return !pattern
                .matcher(token)
                .find();
    }

}
