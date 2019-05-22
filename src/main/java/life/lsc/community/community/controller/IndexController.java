package life.lsc.community.community.controller;

import life.lsc.community.community.dto.PaginationDTO;
import life.lsc.community.community.dto.QuestionDTO;
import life.lsc.community.community.mapper.UserMapper;
import life.lsc.community.community.model.User;
import life.lsc.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @RequestMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "1")Integer page,
                        @RequestParam(name = "size", defaultValue = "5")Integer size) {

        PaginationDTO pagination = questionService.list(page,size);
            model.addAttribute("pagination",pagination);
        return "index";
    }
}