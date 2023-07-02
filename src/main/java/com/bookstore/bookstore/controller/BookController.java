package com.bookstore.bookstore.controller;

import com.bookstore.bookstore.dto.BookCreateRequestDTO;
import com.bookstore.bookstore.model.Book;
import com.bookstore.bookstore.openTelemetry.MetricOpenTelemetry;
import com.bookstore.bookstore.openTelemetry.TraceOpenTelemetry;
import com.bookstore.bookstore.repository.BookRepository;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.context.Scope;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.bookstore.bookstore.constants.BookConstants.*;

@RestController
@RequestMapping("/book")
public class BookController {

    private static final TraceOpenTelemetry trace = new TraceOpenTelemetry("tracer.book.controller");
    private static final MetricOpenTelemetry metric = new MetricOpenTelemetry(METRIC);

    @Autowired
    private BookRepository bookRepository;


    @PostConstruct
    private void createAllMetrics(){
        metric.createStaticMetrics(METRIC_NAME_NUMBER_OF_CREATED_BOOKS,NUMBER_OF_CREATED_BOOKS,NUMBER_OF_CREATED_BOOKS_DESCRIPTION,"long");
        metric.createStaticMetrics(METRIC_NAME_NUMBER_OF_BOOKS_NOTFOUND,NUMBER_OF_BOOKS_NOTFOUND,NUMBER_BOOKS_NOTFOUND_DESCRIPTION,"long");
    }

    @WithSpan
    @Transactional
    @PostMapping("/create")
    public ResponseEntity createBook(@RequestBody BookCreateRequestDTO bookCreateDTO){
        Book book = bookCreateDTO.convert();

        Span span = trace.startSpan("savingBook");
        try (Scope scope = span.makeCurrent()){
            bookRepository.save(book);
            metric.getStaticMetrics(METRIC_NAME_NUMBER_OF_CREATED_BOOKS).add(1);
        }finally {
            trace.endSpan(span);
        }
        return ResponseEntity.ok().build();
    }



    @GetMapping("/listAll")
    public ResponseEntity listAll(){
        List<Book> allBooksList = bookRepository.findAll();
        return ResponseEntity.ok().body(allBooksList);
    }


    @GetMapping("/getBook/{id}")
    public ResponseEntity getBook(@PathVariable Long id){

        Optional<Book> book = bookRepository.findById(id);
        if(!book.isPresent()) {
            metric.getStaticMetrics(METRIC_NAME_NUMBER_OF_BOOKS_NOTFOUND).add(1);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(book);
    }

}
