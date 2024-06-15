package com.example.EBook_Management_BE.repositories.book;

import com.example.EBook_Management_BE.dtos.book.SearchBookDTO;
import com.example.EBook_Management_BE.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookRepositoryCustom {
    Page<Book> searchBook(SearchBookDTO searchBookDTO, Pageable pageable);
}
