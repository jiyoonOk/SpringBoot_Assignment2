package kr.ac.hansung.cse.hellospringdatajpa.controller;
import kr.ac.hansung.cse.hellospringdatajpa.entity.Role;
import kr.ac.hansung.cse.hellospringdatajpa.entity.User;
import kr.ac.hansung.cse.hellospringdatajpa.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {

        User user = new User();
        model.addAttribute("user", user);

        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signupPost(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {

        // 유효성 검사 실패 시
        if (bindingResult.hasErrors()) {
            return "signup";
        }

        if (registrationService.checkEmailExists(user.getEmail())) {
            model.addAttribute("emailExists", true);
            return "signup";
        }
        else {
            List<Role> userRoles = new ArrayList<>();

            Role role = registrationService.findByRolename("ROLE_USER");
            userRoles.add(role);

            // 특정 이메일 주소인 경우 ADMIN 역할 추가
            if ("admin@hansung.ac.kr".equals(user.getEmail()) || "ojoj7ojo@icloud.com".equals(user.getEmail())) {
                Role roleAdmin = registrationService.findByRolename("ROLE_ADMIN");
                userRoles.add(roleAdmin);
            }

            registrationService.createUser(user, userRoles);

            // 회원가입 성공 시 success 파라미터와 함께 로그인 페이지로 리다이렉트
            return "redirect:/login?success";
        }
    }
}
