package com.book.web;

import com.book.service.NationInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NationInfoController {

    @Autowired
    private NationInfoService nationInfoService;

    @RequestMapping("nation_list")
    public ModelAndView nationList(ModelAndView modelAndView) {
        modelAndView.setViewName("admin_nation_list");
        modelAndView.addObject("list", nationInfoService.getAllNationInfo());
        return modelAndView;
    }
}
