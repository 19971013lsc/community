package life.lsc.community.community.controller;

import life.lsc.community.community.mapper.UserMapper;
import life.lsc.community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapperl;

    @RequestMapping("/")
    public String index(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
           if ( cookie.getName().equals("token")){
               String token = cookie.getValue();
               User user = userMapperl.findByToken(token);
               if (user != null){
                    request.getSession().setAttribute("user",user);
               }
               break;
           }
        }

        return "index";
    }
}