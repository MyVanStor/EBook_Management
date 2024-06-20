package com.example.EBook_Management_BE.repositories.book;

import com.example.EBook_Management_BE.dtos.book.SearchBookDTO;
import com.example.EBook_Management_BE.entity.Book;
import com.example.EBook_Management_BE.entity.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepositoryCustomImpl implements BookRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Book> searchBook(SearchBookDTO searchBookDTO, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> book = query.from(Book.class);
        Join<Book, Category> bookCategoryJoin = book.join("categories", JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();

        if (searchBookDTO.getTypeOfBook() != null) {
            predicates.add(cb.equal(book.get("typeOfBook"), searchBookDTO.getTypeOfBook()));
        }

        if (searchBookDTO.getStatus() != null) {
            predicates.add(cb.equal(book.get("status"), searchBookDTO.getStatus()));
        }

        if (searchBookDTO.getKeyword() != null && !searchBookDTO.getKeyword().isEmpty()) {
            String pattern = "%" + searchBookDTO.getKeyword().toLowerCase() + "%";
            predicates.add(cb.or(
                    cb.like(cb.lower(book.get("title")), pattern),
                    cb.like(cb.lower(book.get("author")), pattern),
                    cb.like(cb.lower(book.get("painter")), pattern)
            ));
        }

        if (searchBookDTO.getCategoryIds() != null && !searchBookDTO.getCategoryIds().isEmpty()) {
            predicates.add(bookCategoryJoin.get("id").in(searchBookDTO.getCategoryIds()));
        }

        predicates.add(cb.notEqual(book.get("typeOfBook"), "private"));

        query.where(cb.and(predicates.toArray(new Predicate[0])));
        query.distinct(true);  // Ensure distinct results when using many-to-many relationships
        query.orderBy(cb.desc(book.get("updatedAt")));

        List<Book> books = entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        Long count = (long) entityManager.createQuery(query)
                .getResultList().size();

        return new PageImpl<>(books, pageable, count);
    }

}
