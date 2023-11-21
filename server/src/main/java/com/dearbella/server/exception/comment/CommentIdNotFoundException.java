package com.dearbella.server.exception.comment;

public class CommentIdNotFoundException extends RuntimeException {
    private String message;

    public CommentIdNotFoundException(final Long commentId) {
        super(commentId.toString());

        message = commentId.toString();
    }
}
