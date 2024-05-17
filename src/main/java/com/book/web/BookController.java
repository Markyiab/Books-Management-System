package com.book.web;

import com.book.domain.Book;
import com.book.domain.ReaderCard;
import com.book.service.BookService;
import com.book.service.ClassInfoService;
import com.book.service.LendService;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class BookController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }

    @Autowired
    private BookService bookService;

    @Autowired
    private ClassInfoService classInfoService;

    @Autowired
    private LendService lendService;

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
    public ModelAndView queryBookDo(Book book, ModelAndView modelAndView) {
        modelAndView.setViewName("admin_books");
        modelAndView.addObject("books", bookService.queryBook(book));
        modelAndView.addObject("queryBook", book);
        addQueryOptions(modelAndView);
        return modelAndView;
    }

    private void addQueryOptions(final ModelAndView modelAndView) {
        modelAndView.addObject("classMap", classInfoService.getAllClassInfo());
        modelAndView.addObject("languages", languages);
        modelAndView.addObject("orderBy", orderBy);
    }

    @RequestMapping("/reader_querybook.html")
    public ModelAndView readerQueryBook(Book book, ModelAndView modelAndView, HttpSession session) {
        ReaderCard readerCard = (ReaderCard) session.getAttribute("readercard");
        final int readerId = readerCard.getReaderId();
        modelAndView.setViewName("reader_book_query");
        final List<Book> books = bookService.queryBook(book);
        books.stream()
             .filter(b -> b.getState() != 1)
             .forEach(b->b.setSelfLend(readerId == lendService.lendReaderOut(b.getBookId())));
        modelAndView.addObject("books", books);
        modelAndView.addObject("queryBook", book);
        addQueryOptions(modelAndView);
        return modelAndView;
    }

    @RequestMapping("/reader_querybook_do.html")
    public String readerQueryBookDo(Book book, RedirectAttributes redirectAttributes) {
        final List<Book> books = bookService.queryBook(book);
        redirectAttributes.addFlashAttribute("books", books);
        redirectAttributes.addFlashAttribute("queryBook", book);
        redirectAttributes.addFlashAttribute("classMap", classInfoService.getAllClassInfo());
        redirectAttributes.addFlashAttribute("languages", languages);
        redirectAttributes.addFlashAttribute("orderBy", orderBy);
        return "redirect:/reader_querybook.html";
    }

    @RequestMapping("/allbooks.html")
    public ModelAndView allBook(Book book, ModelAndView modelAndView) {
        modelAndView.setViewName("admin_books");
        modelAndView.addObject("books", bookService.queryBook(book));
        modelAndView.addObject("queryBook", book);
        addQueryOptions(modelAndView);
        return modelAndView;
    }

    @RequestMapping("/deletebook.html")
    public String deleteBook(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        long bookId = Integer.parseInt(request.getParameter("bookId"));
        if (bookService.deleteBook(bookId) > 0) {
            redirectAttributes.addFlashAttribute("succ", "图书删除成功！");
        } else {
            redirectAttributes.addFlashAttribute("error", "图书删除失败！");
        }
        return "redirect:/allbooks.html";
    }

    @RequestMapping("/book_add.html")
    public ModelAndView addBook(HttpServletRequest request) {
        final ModelAndView modelAndView = new ModelAndView("admin_book_add");
        modelAndView.addObject("classMap", classInfoService.getAllClassInfo());
        return modelAndView;
    }

    @RequestMapping("/book_add_do.html")
    public String addBookDo(Book book, RedirectAttributes redirectAttributes) {
        if (bookService.addBook(book)) {
            redirectAttributes.addFlashAttribute("succ", "图书添加成功！");
        } else {
            redirectAttributes.addFlashAttribute("error", "图书添加失败！");
        }
        return "redirect:/allbooks.html";
    }

    @RequestMapping("/updatebook.html")
    public ModelAndView bookEdit(long bookId) {
        ModelAndView modelAndView = new ModelAndView("admin_book_edit");
        modelAndView.addObject("detail", bookService.getBook(bookId));
        modelAndView.addObject("classMap", classInfoService.getAllClassInfo());
        return modelAndView;
    }

    @RequestMapping("/book_edit_do.html")
    public String bookEditDo(Book book, RedirectAttributes redirectAttributes) {
        if (bookService.editBook(book)) {
            redirectAttributes.addFlashAttribute("succ", "图书修改成功！");
        } else {
            redirectAttributes.addFlashAttribute("error", "图书修改失败！");
        }
        return "redirect:/allbooks.html";
    }

    @RequestMapping("/bookdetail.html")
    public ModelAndView bookDetail(long bookId) {
        ModelAndView modelAndView = new ModelAndView("admin_book_detail");
        final Book book = bookService.getBook(bookId);
        modelAndView.addObject("detail", book);
        modelAndView.addObject("className", classInfoService.getByClassId(book.getClassId()));
        return modelAndView;
    }

    @RequestMapping("/readerbookdetail.html")
    public ModelAndView readerBookDetail(long bookId) {
        ModelAndView modelAndView = new ModelAndView("reader_book_detail");
        final Book book = bookService.getBook(bookId);
        modelAndView.addObject("detail", book);
        modelAndView.addObject("className", classInfoService.getByClassId(book.getClassId()));
        return modelAndView;
    }

}
