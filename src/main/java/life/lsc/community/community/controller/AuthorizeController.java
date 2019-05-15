package life.lsc.community.community.controller;

import life.lsc.community.community.dto.AccesstokenDTO;
import life.lsc.community.community.dto.GithubUser;
import life.lsc.community.community.mapper.UserMapper;
import life.lsc.community.community.model.User;
import life.lsc.community.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserMapper userMapper;

    @Value("${github.Client_id}")
    private String clientId;
    @Value("${github.Client_secret}")
    private String clientSecret;
    @Value("${github.Redirect_uri}")
    private String redirectUri;

    @GetMapping("/callback")
        public String callback(@RequestParam(name="code")String code,
                               @RequestParam(name="state")String state,
                               HttpServletResponse response){
        AccesstokenDTO accesstokenDTO = new AccesstokenDTO();
        accesstokenDTO.setClient_id(clientId);
        accesstokenDTO.setClient_secret(clientSecret);
        accesstokenDTO.setCode(code);
        accesstokenDTO.setRedirect_uri(redirectUri);
        accesstokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accesstokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser != null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            //强壮string类型
            user.setAccountId(String.valueOf(githubUser.getId()));
            //当前得行秒速
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            response.addCookie(new Cookie("token",token));
            //登陆成功，写cookie和session
//            request.getSession().setAttribute("user",githubUser);
            return "redirect:/";
        }else {
            //登陆失败，重新登陆
            return "redirect:/";
        }
    }
}
