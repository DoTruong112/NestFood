package com.phitruong.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.phitruong.entity.Account;
import com.phitruong.entity.CustomUserDetails;
import com.phitruong.repository.AccountRepository;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class AccountController {
	@Autowired
	private AccountRepository accountDao;
	
	@GetMapping("myaccount")
	public String myAccount(Model model) {

        
     // Lấy thông tin tên người đăng nhập từ Spring Security
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            // Lấy thông tin tên người đăng nhập và danh sách quyền
            String username = userDetails.getUsername();

            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            model.addAttribute("roles", roles);
            model.addAttribute("username", username);
            // Lấy thông tin ảnh từ đối tượng item và truyền nó vào model
            String photoUrl = userDetails.getAvatar();
            model.addAttribute("photoUrl", photoUrl);
            String address = userDetails.getAddress();
            model.addAttribute("address",address);
            String phone = userDetails.getPhone();
            model.addAttribute("phone",phone);
            String email = userDetails.getEmail();
            model.addAttribute("email",email);
            String fullname = userDetails.getFullname();
            model.addAttribute("fullname",fullname);
            
        }
        
        
		return "user/page-account";
	}
	
	@GetMapping("/login")
	public String login() {
		return "user/page-login";
	}

	@GetMapping("/logout")
	public String logout() {
		// Đăng xuất người dùng bằng cách xóa thông tin xác thực trong
		// SecurityContextHolder

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			SecurityContextHolder.getContext().setAuthentication(null);
		}

		// Chuyển hướng người dùng về trang đăng nhập sau khi đăng xuất thành công
		return "user/index";
	}
	
	@RequestMapping("/myaccount/change")
	public String changeMyAccount(@ModelAttribute("account") Account account,HttpServletResponse response) {
		Account existingAccount = accountDao.findByUsername(account.getUsername());
		existingAccount.setEmail(account.getEmail());
        existingAccount.setFullname(account.getFullname());
        existingAccount.setAddress(account.getAddress());
        existingAccount.setAvatar(account.getAvatar());
        existingAccount.setPhone(account.getPhone());

        // Lưu tài khoản đã cập nhật
        accountDao.save(existingAccount);
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			SecurityContextHolder.getContext().setAuthentication(null);
		}

		return "redirect:/login";
	}
	
}
