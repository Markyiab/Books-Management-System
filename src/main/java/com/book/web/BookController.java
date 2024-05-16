package com.book.web;

import com.book.domain.Book;
import com.book.service.BookService;
import com.book.service.ClassInfoService;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private ClassInfoService classInfoService;

    private final List<String> languages = ImmutableList.of("中文", "英文", "俄文", "德文", "日文");
    private final Map<String, String> orderBy =
        ImmutableMap.<String, String>builder()
            .put("name", "书名")
            .put("author", "作者")
            .put("publish", "出版社")
            .put("price", "价格")
            .put("classId", "分类")
            .build();

    @RequestMapping("/querybook.html")
    public ModelAndView queryBookDo(HttpServletRequest request, String searchWord) {
        if (bookService.matchBook(searchWord)) {
            List<Book> books = bookService.queryBook(searchWord);
            ModelAndView modelAndView = new ModelAndView("admin_books");
            modelAndView.addObject("books", books);
            return modelAndView;
        } else {
            return new ModelAndView("admin_books", "error", "没有匹配的图书");
        }
    }

    @RequestMapping("/reader_querybook.html")
    public ModelAndView readerQueryBook() {
        final ModelAndView modelAndView = new ModelAndView("reader_book_query");
        modelAndView.addObject("classInfos", classInfoService.getAllClassInfo());
        modelAndView.addObject("languages", languages);
        modelAndView.addObject("orderBy", orderBy);
        return modelAndView;
    }

    @RequestMapping("/reader_querybook_do.html")
    public String readerQueryBookDo(HttpServletRequest request, Book book, RedirectAttributes redirectAttributes) {
        final List<Book> books = bookService.queryBook(book);
        if (!books.isEmpty()) {
            redirectAttributes.addFlashAttribute("books", books);
        } else {
            redirectAttributes.addFlashAttribute("error", "没有匹配的图书！");
        }
        redirectAttributes.addFlashAttribute("queryBook", book);
        redirectAttributes.addFlashAttribute("classInfos", classInfoService.getAllClassInfo());
        redirectAttributes.addFlashAttribute("languages", languages);
        redirectAttributes.addFlashAttribute("orderBy", orderBy);
        return "redirect:/reader_querybook.html";
    }

    @RequestMapping("/allbooks.html")
    public ModelAndView allBook() {
        List<Book> books = bookService.getAllBooks();
        ModelAndView modelAndView = new ModelAndView("admin_books");
        modelAndView.addObject("books", books);
        return modelAndView;
    }

    @RequestMapping("/deletebook.html")
    public String deleteBook(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        long bookId = Integer.parseInt(request.getParameter("bookId"));
        if (bookService.deleteBook(bookId) == 1) {
            redirectAttributes.addFlashAttribute("succ", "图书删除成功！");
        } else {
            redirectAttributes.addFlashAttribute("error", "图书删除失败！");
        }
        return "redirect:/allbooks.html";
    }

    @RequestMapping("/book_add.html")
    public ModelAndView addBook(HttpServletRequest request) {
        return new ModelAndView("admin_book_add");
    }

    @RequestMapping("/book_add_do.html")
    public String addBookDo(BookAddCommand bookAddCommand, RedirectAttributes redirectAttributes) {
        Book book = new Book();
        book.setBookId(0);
        book.setPrice(bookAddCommand.getPrice());
        book.setState(bookAddCommand.getState());
        book.setPublish(bookAddCommand.getPublish());
        book.setPubdate(bookAddCommand.getPubdate());
        book.setName(bookAddCommand.getName());
        book.setIsbn(bookAddCommand.getIsbn());
        book.setClassId(bookAddCommand.getClassId());
        book.setAuthor(bookAddCommand.getAuthor());
        book.setIntroduction(bookAddCommand.getIntroduction());
        book.setPressmark(bookAddCommand.getPressmark());
        book.setLanguage(bookAddCommand.getLanguage());

        if (bookService.addBook(book)) {
            redirectAttributes.addFlashAttribute("succ", "图书添加成功！");
        } else {
            redirectAttributes.addFlashAttribute("error", "图书添加失败！");
        }
        return "redirect:/allbooks.html";
    }

    @RequestMapping("/updatebook.html")
    public ModelAndView bookEdit(HttpServletRequest request) {
        long bookId = Integer.parseInt(request.getParameter("bookId"));
        ModelAndView modelAndView = new ModelAndView("admin_book_edit");
        modelAndView.addObject("detail", bookService.getBook(bookId));
        return modelAndView;
    }

    @RequestMapping("/book_edit_do.html")
    public String bookEditDo(HttpServletRequest request, BookAddCommand bookAddCommand, RedirectAttributes redirectAttributes) {
        long bookId = Integer.parseInt(request.getParameter("id"));
        Book book = new Book();
        book.setBookId(bookId);
        book.setPrice(bookAddCommand.getPrice());
        book.setState(bookAddCommand.getState());
        book.setPublish(bookAddCommand.getPublish());
        book.setPubdate(bookAddCommand.getPubdate());
        book.setName(bookAddCommand.getName());
        book.setIsbn(bookAddCommand.getIsbn());
        book.setClassId(bookAddCommand.getClassId());
        book.setAuthor(bookAddCommand.getAuthor());
        book.setIntroduction(bookAddCommand.getIntroduction());
        book.setPressmark(bookAddCommand.getPressmark());
        book.setLanguage(bookAddCommand.getLanguage());

        if (bookService.editBook(book)) {
            redirectAttributes.addFlashAttribute("succ", "图书修改成功！");
        } else {
            redirectAttributes.addFlashAttribute("error", "图书修改失败！");
        }
        return "redirect:/allbooks.html";
    }

    @RequestMapping("/bookdetail.html")
    public ModelAndView bookDetail(HttpServletRequest request) {
        long bookId = Integer.parseInt(request.getParameter("bookId"));
        ModelAndView modelAndView = new ModelAndView("admin_book_detail");
        modelAndView.addObject("detail", bookService.getBook(bookId));
        return modelAndView;
    }

    @RequestMapping("/readerbookdetail.html")
    public ModelAndView readerBookDetail(HttpServletRequest request) {
        long bookId = Integer.parseInt(request.getParameter("bookId"));
        ModelAndView modelAndView = new ModelAndView("reader_book_detail");
        modelAndView.addObject("detail", bookService.getBook(bookId));
        return modelAndView;
    }

}
