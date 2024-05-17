package com.book.web;

import com.book.service.ClassInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ClassnInfoController {

    @Autowired
    private ClassInfoService classInfoService;

    @RequestMapping("class_list")
    public ModelAndView classList(ModelAndView modelAndView) {
        modelAndView.setViewName("admin_class_list");
        modelAndView.addObject("list", classInfoService.getAllClassInfo());
        return modelAndView;
    }
}
