package life.lsc.community.community.controller;

import life.lsc.community.community.dto.AccesstokenDTO;
import life.lsc.community.community.dto.GithubUser;
import life.lsc.community.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.Client_id}")
    private String clientId;
    @Value("${github.Client_secret}")
    private String clientSecret;
    @Value("${github.Client_uri}")
    private String redirectUri;

    @GetMapping("/callback")
        public String callback(@RequestParam(name="code")String code,
                                @RequestParam(name="state")String state        ){
        AccesstokenDTO accesstokenDTO = new AccesstokenDTO();
        accesstokenDTO.setClient_id(clientId);
        accesstokenDTO.setClient_secret(clientSecret);
        accesstokenDTO.setCode(code);
        accesstokenDTO.setRedirect_uri(redirectUri);
        accesstokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accesstokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}
