package spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.DAO.QuestionsDAO;
import spring.DAO.UserDAO;
import spring.models.Question;
import spring.models.Username;
import java.util.Random;

@Controller
@RequestMapping("/")
public class MainController {
    private final UserDAO userDAO;
    private final QuestionsDAO questionsDAO;
    final Random random = new Random();
    private int score;

    @Autowired
    public MainController(UserDAO userDAO, QuestionsDAO questionsDAO) {
        this.userDAO = userDAO;
        this.questionsDAO = questionsDAO;
    }

    @GetMapping()
    public String main(Model model) {
        Username username = new Username("");
        model.addAttribute("username", username);
        return "index";
    }

    @PostMapping("/questions")
    public String test(@ModelAttribute("username") Username username) {

        if (!userDAO.checkUser(username.getUsername())) {
            userDAO.addUser(username.getUsername());
        }

        System.out.println(username.getUsername());
        return "redirect:/questions";
    }

    @GetMapping("/questions")
    public String question(Model model) {
        Question question = new Question();
        question.setQuestion(questionsDAO.getQuestion(random.nextInt(5)).getQuestion());
        question.setId(questionsDAO.getQuestion(random.nextInt(5)).getId());
        model.addAttribute("question", question);
        return "question";
    }

    @GetMapping("/questions/{id}")
    public String questionCheck(@ModelAttribute("question") Question question, @PathVariable("id") int id) {
        if (questionsDAO.getQuestion(id).getAnswer().equals(question.getAnswer())) {
            score += questionsDAO.getQuestion(id).getPoints();
        }
        return "redirect:/questions";
    }

    @GetMapping("/finish")
    public String finish(Model model) {
        model.addAttribute("result", score);
        return "result";
    }
}
