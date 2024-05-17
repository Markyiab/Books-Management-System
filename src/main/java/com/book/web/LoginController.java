package com.book.web;

import com.book.domain.Admin;
import com.book.domain.ReaderCard;
import com.book.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

//标注为一个Spring mvc的Controller
@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    //负责处理login.html请求
    @RequestMapping(value = {"/", "/login.html"})
    public String toLogin(HttpSession session) {
        session.invalidate();
        return "index";
    }

    @RequestMapping("/logout.html")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login.html";
    }

    //负责处理loginCheck.html请求
    //请求参数会根据参数名称默认契约自动绑定到相应方法的入参中
    @PostMapping(value = "/api/loginCheck")
    public @ResponseBody Object loginCheck(int id, String passwd, HttpSession session) {
        boolean isReader = loginService.hasMatchReader(id, passwd);
        boolean isAdmin = loginService.hasMatchAdmin(id, passwd);
        HashMap<String, String> res = new HashMap<>();
        if (!isAdmin && !isReader) {
            res.put("stateCode", "0");
            res.put("msg", "账号或密码错误！");
        } else if (isAdmin) {
            Admin admin = new Admin();
            admin.setAdminId(id);
            admin.setPassword(passwd);
            session.setAttribute("admin", admin);
            res.put("stateCode", "1");
            res.put("msg", "管理员登陆成功！");
        } else {
            ReaderCard readerCard = loginService.findReaderCardByUserId(id);
            session.setAttribute("readercard", readerCard);
            res.put("stateCode", "2");
            res.put("msg", "读者登陆成功！");
        }
        return res;
    }

    @RequestMapping("/admin_main.html")
    public ModelAndView toAdminMain(HttpServletResponse response) {
        return new ModelAndView("admin_main");
    }

    @RequestMapping("/reader_main.html")
    public ModelAndView toReaderMain(HttpServletResponse response) {
        return new ModelAndView("reader_main");
    }

    @RequestMapping("/admin_repasswd.html")
    public ModelAndView reAdminPasswd() {
        return new ModelAndView("admin_repasswd");
    }

    @RequestMapping("/admin_repasswd_do")
    public String reAdminPasswdDo(HttpSession session, String oldPasswd, String newPasswd, RedirectAttributes redirectAttributes) {
        Admin admin = (Admin) session.getAttribute("admin");
        int id = admin.getAdminId();

        if (loginService.getAdminPasswd(id).equals(oldPasswd)) {
            if (loginService.adminRePasswd(id, newPasswd)) {
                redirectAttributes.addFlashAttribute("succ", "密码修改成功！");
            } else {
                redirectAttributes.addFlashAttribute("error", "密码修改失败！");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "旧密码错误！");
        }
        return "redirect:/admin_repasswd.html";
    }

    //配置404页面
    @RequestMapping("*")
    public String notFind() {
        return "404";
    }

}