package com.techacademy.controller;

import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techacademy.entity.User;
import com.techacademy.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    /** 一覧画面を表示 */
    @GetMapping("/list")
    public String getList(Model model) {
        // 全件検索結果をModelに登録
        model.addAttribute("userlist", service.getUserList());
        // user/list.htmlに画面遷移
        return "user/list";
    }
    @GetMapping("/register")
    public String getRegister(@ModelAttribute User user) {
        return "user/register";
    }
    @PostMapping("/register")
    public String postRegister(@Validated User user,BindingResult res,Model model) {
        if(res.hasErrors()) {
            return getRegister(user);
        }
        service.saveUser(user);
        return "redirect:/user/list";
    }

    @GetMapping("/update/{id}/")
    public String getUser(@PathVariable("id") Integer id, Model model) {
        User user = service.getUser(id);
        model.addAttribute("user", user);
        return "user/update";
    }

    @PostMapping("/update/{id}/")
    public String postUser(@PathVariable("id") Integer id, @Validated @ModelAttribute User user, BindingResult result) {
        if (result.hasErrors()) {
            return "user/update";
        }
        service.saveUser(user);
        return "redirect:/user/list";
    }
    @PostMapping(path="list",params="deleteRun")
    public String deleteRun(@RequestParam(name="idck") Set<Integer>idck,Model model) {
        service.deleteUser(idck);

        return "redirect:/user/list";
    }
}