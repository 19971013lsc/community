package life.lsc.community.community.service;

import life.lsc.community.community.dto.QuestionDTO;
import life.lsc.community.community.mapper.QuestionMapper;
import life.lsc.community.community.mapper.UserMapper;
import life.lsc.community.community.model.Question;
import life.lsc.community.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public List<QuestionDTO> list() {
        List<Question> questions = questionMapper.list();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions){
          User user = userMapper.findById(question.getCreator());
          QuestionDTO questionDTO = new QuestionDTO();
          //把question上的对象拷贝到questionDTO
          BeanUtils.copyProperties(question,questionDTO);
          questionDTO.setUser(user);
          questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
}
