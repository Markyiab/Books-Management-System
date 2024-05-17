package com.book.web;

import com.book.domain.Book;
import com.book.domain.ReaderCard;
import com.book.domain.ReaderInfo;
import com.book.service.BookService;
import com.book.service.LendService;
import com.book.service.ReaderInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LendController {

    private LendService lendService;

    @Autowired
    public void setLendService(LendService lendService) {
        this.lendService = lendService;
    }

    private BookService bookService;

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping("/lendbook.html")
    public ModelAndView bookLend(long bookId) {
        Book book = bookService.getBook(bookId);
        ModelAndView modelAndView = new ModelAndView("admin_book_lend");
        modelAndView.addObject("book", book);
        return modelAndView;
    }

    @Autowired
    private ReaderInfoService readerInfoService;

    @RequestMapping("/lendbookdo.html")
    public String bookLendDo(long bookId, RedirectAttributes redirectAttributes, int readerId) {
        if (lendService.bookLend(bookId, readerId)) {
            redirectAttributes.addFlashAttribute("succ", "图书借阅成功！");
        } else {
            redirectAttributes.addFlashAttribute("error", "图书借阅失败！");
        }
        return "redirect:/allbooks.html";
    }

    @RequestMapping("/returnbook.html")
    public String bookReturn(long bookId, RedirectAttributes redirectAttributes) {
        if (lendService.bookReturn(bookId)) {
            redirectAttributes.addFlashAttribute("succ", "图书归还成功！");
        } else {
            redirectAttributes.addFlashAttribute("error", "图书归还失败！");
        }
        return "redirect:/allbooks.html";
    }

    @RequestMapping("/losebook.html")
    public String bookLose(long bookId, RedirectAttributes redirectAttributes) {
        if (lendService.bookLose(bookId)) {
            redirectAttributes.addFlashAttribute("succ", "图书归还成功！");
        } else {
            redirectAttributes.addFlashAttribute("error", "图书归还失败！");
        }
        return "redirect:/allbooks.html";
    }

    @RequestMapping("/lendlist.html")
    public ModelAndView lendList() {
        ModelAndView modelAndView = new ModelAndView("admin_lend_list");
        modelAndView.addObject("list", lendService.lendList());
        return modelAndView;
    }

    @RequestMapping("/mylend.html")
    public ModelAndView myLend(HttpSession session) {
        ReaderCard readerCard = (ReaderCard) session.getAttribute("readercard");
        ModelAndView modelAndView = new ModelAndView("reader_lend_list");
        modelAndView.addObject("list", lendService.myLendList(readerCard.getReaderId()));
        return modelAndView;
    }
    @PostMapping(value = "/lendCheck")
    @ResponseBody
    public Map<String, Object> lendCheck(int readerId) {
        final ReaderInfo readerInfo = readerInfoService.getReaderInfo(readerId);
        Map<String, Object> res = new HashMap<>();
        if(StringUtils.isBlank(readerInfo.getName())){
            res.put("success", false);
            res.put("msg", "读者信息不存在，请检查借书证号");
        }else {
            res.put("success", true);
        }
        return res;
    }

    @RequestMapping("/readerlendbook.html")
    public String readerBookLend(long bookId, RedirectAttributes redirectAttributes, HttpSession session) {
        ReaderCard readerCard = (ReaderCard) session.getAttribute("readercard");
        if (lendService.bookLend(bookId, readerCard.getReaderId())) {
            redirectAttributes.addFlashAttribute("succ", "图书借阅成功！");
        } else {
            redirectAttributes.addFlashAttribute("error", "图书借阅失败！");
        }
        return "redirect:/reader_querybook.html";
    }


    @RequestMapping("/readerreturnbook.html")
    public String readerBookReturn(long bookId, RedirectAttributes redirectAttributes) {
        if (lendService.bookReturn(bookId)) {
            redirectAttributes.addFlashAttribute("succ", "图书归还成功！");
        } else {
            redirectAttributes.addFlashAttribute("error", "图书归还失败！");
        }
        return "redirect:/reader_querybook.html";
    }

    @RequestMapping("/readerlosebook.html")
    public String readerBookLose(long bookId, RedirectAttributes redirectAttributes) {
        if (lendService.bookLose(bookId)) {
            redirectAttributes.addFlashAttribute("succ", "图书申请遗失成功！");
        } else {
            redirectAttributes.addFlashAttribute("error", "图书申请遗失失败！");
        }
        return "redirect:/reader_querybook.html";
    }
}
