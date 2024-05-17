package com.book.web;

import com.book.domain.ReaderCard;
import com.book.domain.ReaderInfo;
import com.book.service.LoginService;
import com.book.service.NationInfoService;
import com.book.service.ReaderCardService;
import com.book.service.ReaderInfoService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ReaderController {
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }

    @Autowired
    private ReaderInfoService readerInfoService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private ReaderCardService readerCardService;

    @Autowired
    private NationInfoService nationInfoService;

    @RequestMapping("allreaders.html")
    public ModelAndView allBooks() {
        ModelAndView modelAndView = new ModelAndView("admin_readers");
        modelAndView.addObject("readers", readerInfoService.readerInfos());
        modelAndView.addObject("nationMap", nationInfoService.getAllNationInfo());
        return modelAndView;
    }

    @RequestMapping("reader_delete.html")
    public String readerDelete(int readerId, RedirectAttributes redirectAttributes) {

        if (readerInfoService.deleteReaderInfo(readerId)) {
            redirectAttributes.addFlashAttribute("succ", "删除成功！");
        } else {
            redirectAttributes.addFlashAttribute("error", "删除失败！");
        }
        return "redirect:/allreaders.html";
    }

    @RequestMapping("/reader_info.html")
    public ModelAndView toReaderInfo(HttpSession session) {
        ReaderCard readerCard = (ReaderCard) session.getAttribute("readercard");
        ReaderInfo readerInfo = readerInfoService.getReaderInfo(readerCard.getReaderId());
        ModelAndView modelAndView = new ModelAndView("reader_info");
        modelAndView.addObject("readerinfo", readerInfo);
        initNationMapToSession(session);
        return modelAndView;
    }

    @RequestMapping("reader_edit.html")
    public ModelAndView readerInfoEdit(int readerId) {
        ReaderInfo readerInfo = readerInfoService.getReaderInfo(readerId);
        ModelAndView modelAndView = new ModelAndView("admin_reader_edit");
        modelAndView.addObject("readerInfo", readerInfo);
        modelAndView.addObject("nationMap", nationInfoService.getAllNationInfo());
        return modelAndView;
    }

    @RequestMapping("reader_edit_do.html")
    public String readerInfoEditDo(ReaderInfo readerInfo, RedirectAttributes redirectAttributes) {
        ReaderCard readerCard = loginService.findReaderCardByUserId(readerInfo.getReaderId());
        if (readerInfoService.editReaderInfo(readerInfo)
            && equalsOrUpdateName(readerCard, readerInfo.getName())) {
            redirectAttributes.addFlashAttribute("succ", "读者信息修改成功！");
        } else {
            redirectAttributes.addFlashAttribute("error", "读者信息修改失败！");
        }
        return "redirect:/allreaders.html";
    }

    @RequestMapping("reader_add.html")
    public ModelAndView readerInfoAdd() {
        final ModelAndView modelAndView = new ModelAndView("admin_reader_add");
        modelAndView.addObject("nationMap", nationInfoService.getAllNationInfo());
        return modelAndView;
    }

    //用户功能--进入修改密码页面
    @RequestMapping("reader_repasswd.html")
    public ModelAndView readerRePasswd() {
        return new ModelAndView("reader_repasswd");
    }

    //用户功能--修改密码执行
    @RequestMapping("reader_repasswd_do.html")
    public String readerRePasswdDo(HttpSession session, String oldPasswd, String newPasswd, String reNewPasswd, RedirectAttributes redirectAttributes) {
        ReaderCard readerCard = (ReaderCard) session.getAttribute("readercard");
        int readerId = readerCard.getReaderId();
        String passwd = readerCard.getPasswd();

        if (newPasswd.equals(reNewPasswd)) {
            if (passwd.equals(oldPasswd)) {
                if (readerCardService.updatePasswd(readerId, newPasswd)) {
                    ReaderCard readerCardNew = loginService.findReaderCardByUserId(readerId);
                    session.setAttribute("readercard", readerCardNew);
                    redirectAttributes.addFlashAttribute("succ", "密码修改成功！");
                } else {
                    redirectAttributes.addFlashAttribute("error", "密码修改失败！");
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "修改失败,原密码错误");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "修改失败,两次输入的新密码不相同");
        }
        return "redirect:/reader_repasswd.html";
    }

    //管理员功能--读者信息添加
    @RequestMapping("reader_add_do.html")
    public String readerInfoAddDo(ReaderInfo readerInfo, RedirectAttributes redirectAttributes) {
        if (readerInfoService.addReaderInfo(readerInfo)
            && readerCardService.addReaderCard(readerInfo)) {
            redirectAttributes.addFlashAttribute("succ", "添加读者信息成功！");
        } else {
            redirectAttributes.addFlashAttribute("error", "添加读者信息失败！");
        }
        return "redirect:/allreaders.html";
    }

    @PostMapping(value = "/readerCheck")
    @ResponseBody
    public Map<String, Object> readerCheck(int readerId) {
        Map<String, Object> res = new HashMap<>();
        if (readerInfoService.readerExist(readerId)) {
            res.put("success", false);
            res.put("msg", "借书证号已被注册，请更换一个");
        }else {
            res.put("success", true);
        }
        return res;
    }
    //读者功能--读者信息修改
    @RequestMapping("reader_info_edit.html")
    public ModelAndView readerInfoEditReader(HttpSession session) {
        ReaderCard readerCard = (ReaderCard) session.getAttribute("readercard");
        ReaderInfo readerInfo = readerInfoService.getReaderInfo(readerCard.getReaderId());
        ModelAndView modelAndView = new ModelAndView("reader_info_edit");
        modelAndView.addObject("readerinfo", readerInfo);
        //modelAndView.addObject("nationMap", nationInfoService.getAllNationInfo());
        return modelAndView;
    }
    @RequestMapping("reader_edit_do_r.html")
    public String readerInfoEditDoReader(HttpSession session, ReaderInfo readerInfo, RedirectAttributes redirectAttributes) {
        ReaderCard readerCard = (ReaderCard) session.getAttribute("readercard");
        readerInfo.setReaderId(readerCard.getReaderId());
        final String name = readerInfo.getName();

        if (readerInfoService.editReaderInfo(readerInfo)
            && equalsOrUpdateName(readerCard, name)) {
            session.setAttribute("readercard", loginService.findReaderCardByUserId(readerCard.getReaderId()));
            redirectAttributes.addFlashAttribute("succ", "信息修改成功！");
        } else {
            redirectAttributes.addFlashAttribute("error", "信息修改失败！");
        }
        return "redirect:/reader_info.html";
    }

    @RequestMapping("/queryreaders.html")
    public ModelAndView queryBookDo(String readerId, String name, ModelAndView modelAndView) {
        modelAndView.addObject("readers", readerInfoService.queryReader(NumberUtils.toInt(readerId), name));
        modelAndView.addObject("readerId", readerId);
        modelAndView.addObject("name", name);
        modelAndView.addObject("nationMap", nationInfoService.getAllNationInfo());

        modelAndView.setViewName("admin_readers");
        return modelAndView;
    }

    private boolean equalsOrUpdateName(ReaderCard readerCard, String name) {
        return readerCard.getName().equals(name) || readerCardService.updateName(readerCard.getReaderId(), name);
    }

    private void initNationMapToSession(HttpSession session){
        final Map<Integer, String> nationMap = (Map<Integer, String>) session.getAttribute("nationMap");
        if(null == nationMap) {
            session.setAttribute("nationMap", nationInfoService.getAllNationInfo());
        }
    }
}
