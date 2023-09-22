package com.phitruong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.phitruong.entity.Account;
import com.phitruong.exception.PasswordMismatchException;
import com.phitruong.exception.UsernameAlreadyTakenException;
import com.phitruong.service.AccountService;


@Controller
public class RegisterController {
	
	private final AccountService accountService;

    @Autowired
    public RegisterController(AccountService accountService) {
        this.accountService = accountService;
    }
    
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("account", new Account());
        return "user/page-register";
    }
    
    @PostMapping("/register")
    public String registerAccount(@ModelAttribute("account") Account account,
            @RequestParam("confirmPassword") String confirmPassword, RedirectAttributes redirectAttributes) {
        try {
            // Kiểm tra xem mật khẩu và xác nhận mật khẩu có khớp hay không
            if (!account.getPassword().equals(confirmPassword)) {
                throw new PasswordMismatchException();
            }

            // Mật khẩu và xác nhận mật khẩu khớp nhau, thực hiện đăng ký tài khoản
            accountService.registerAccount(account);
            return "redirect:/login";
        } catch (UsernameAlreadyTakenException e) {
        	redirectAttributes.addFlashAttribute("error", "username");
            return "redirect:/register";
        } catch (PasswordMismatchException e) {
        	redirectAttributes.addFlashAttribute("error", "mismatch");
            return "redirect:/register";
        }
    }
    
}
