package com.phitruong.service;


import java.util.List;	
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.phitruong.entity.Account;
import com.phitruong.entity.Authority;
import com.phitruong.entity.CustomUserDetails;
import com.phitruong.repository.AccountRepository;

@Service
public class AccountDetailService implements UserDetailsService {

    private final AccountRepository accountRepository;

    public AccountDetailService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException("Username not found: " + username);
        }

        // Lấy danh sách các Authorities (quyền) liên kết với tài khoản
        List<Authority> authoritiesList = account.getAuthorities();
        

        // Tạo danh sách các SimpleGrantedAuthority từ danh sách Authorities (quyền)
        List<SimpleGrantedAuthority> authorities = authoritiesList.stream()
                .map(authority -> new SimpleGrantedAuthority("ROLE_" + authority.getRoles().getName()))
                .collect(Collectors.toList());

        // Trả về UserDetails với tên đăng nhập, mật khẩu và danh sách quyền tương ứng
        return new CustomUserDetails(
        		account.getUsername(),
        	    account.getPassword(),
        	    authorities,
        	    account.getEmail(),
        	    account.getFullname(),
        	    account.getAvatar(),
        	    account.getPhone(),
        	    account.getAddress()
        	    
        ); 
    }
}
