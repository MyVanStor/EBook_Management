package com.example.EBook_Management_BE.utils;

public class MessageExceptionKeys {
    public static final String AUTHOR_DUPLICATE_AUTHOR = "exception.author.duplicate_author";
    public static final String AUTHOR_NOT_FOUND = "exception.author.not_found";
    public static final String AUTHOR_DELETE_HAVE_ASSOCIATED_BOOK = "exception.author.delete_have_associated_book";

    public static final String PAINTER_DUPLICATE_PAINTER = "exception.painter.duplicate_painter";
    public static final String PAINTER_NOT_FOUND = "exception.painter.not_found";
    public static final String PAINTER_DELETE_HAVE_ASSOCIATED_BOOK = "exception.painter.delete_have_associated_book";

    public static final String CATEGORY_DUPLICATE_CATEGORY = "exception.category.duplicate_category";
    public static final String CATEGORY_NOT_FOUND = "exception.category.not_found";
    public static final String CATEGORY_DELETE_HAVE_ASSOCIATED_BOOK = "exception.category.delete_have_associated_book";

    public static final String ROLE_DUPLICATE_NAME = "exception.role.duplicate_name";
    public static final String ROLE_NOT_FOUND = "exception.role.not_found";
    public static final String ROLE_DELETE_HAVE_ASSOCIATED_USER = "exception.role.delete_have_associated_user";

    public static final String USER_PASSWORD_DIFFERENT_RETYPE_PASSWORD = "exception.user.password_different_retype_password";
    public static final String USER_DOES_NOT_CREATE_ADMIN = "exception.user.does_not_create_admin";
    public static final String USER_DUPLICATE_USERNAME = "exception.user.duplicate_username";
    public static final String USER_NOT_FOUND = "exception.user.not_found";
    public static final String USER_INVALID_USERNAME_OR_PASSWORD = "exception.user.invalid_username_or_password";

    public static final String TOKEN_IS_EXPRIED = "exception.token.is_expried";

    public static final String COMMENT_NOT_FOUND = "exception.comment.not_found";

    public static final String CHAPTER_NOT_FOUND = "exception.chapter.not_found";
    
    public static final String USER_BOOK_NOT_FOUND = "exception.user_book.not_found";

    public static final String BOOK_NOT_FOUND = "exception.book.not_found";
    public static final String BOOK_DELETE_HAVE_USER_BUYING = "exception.book.delete_have_user_buying";
}   