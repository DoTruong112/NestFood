package com.phitruong.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.phitruong.entity.Account;
import com.phitruong.entity.Authority;
import com.phitruong.entity.Role;
import com.phitruong.exception.PasswordMismatchException;
import com.phitruong.exception.UsernameAlreadyTakenException;
import com.phitruong.repository.AccountRepository;
import com.phitruong.repository.AuthorityRepository;
import com.phitruong.repository.RoleRepository;


import jakarta.transaction.Transactional;

@Service
public class AccountService {
	
	@Autowired
    private AccountRepository accountRep;

    public Account getAccountByUsernames(String username) {
        return accountRepository.findByUsername(username);
    }
	

	private final AccountRepository accountRepository;
	private final AuthorityRepository  authoritiesRepository ;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

   

    public AccountService( AccountRepository accountRepository,
			AuthorityRepository authoritiesRepository, PasswordEncoder passwordEncoder,
			RoleRepository roleRepository) {
		this.accountRepository = accountRepository;
		this.authoritiesRepository = authoritiesRepository;
		this.passwordEncoder = passwordEncoder;
		this.roleRepository = roleRepository;
	}

	public void registerAccount(Account account) {
        if (!isUsernameAvailable(account.getUsername())) {
            throw new UsernameAlreadyTakenException();
        }
        String encodedPassword = passwordEncoder.encode(account.getPassword());
        account.setPassword(encodedPassword);
        accountRepository.save(account);
        
        createAuthorities(account, "Customers");
    }

    private boolean isUsernameAvailable(String username) {
        // Kiểm tra xem tên đăng nhập có sẵn không
        return accountRepository.findByUsername(username) == null;
    }
    
    public Account getAccountByUsername(String username) {
        return accountRepository.findByUsername(username);
    }
    
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
    private void createAuthorities(Account account, String roleName) {
        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            throw new RuntimeException("Role not found: " + roleName);
        }

        Authority authorities = new Authority();
        authorities.setAccount(account);
        authorities.setRoles(role);

        authoritiesRepository.save(authorities);
    }
    
    @Transactional
    public void createAccountWithAuthorities(Account account, Role selectedRole) {
        if (!isUsernameAvailable(account.getUsername())) {
            throw new UsernameAlreadyTakenException();
        }
        
        String encodedPassword = passwordEncoder.encode(account.getPassword());
        account.setPassword(encodedPassword);
        accountRepository.save(account);

        Authority authorities = new Authority();
        authorities.setAccount(account);
        authorities.setRoles(selectedRole);
        authoritiesRepository.save(authorities);
    }
    public void updateAccount(Account account) {
        accountRepository.save(account);
    }
    @Transactional
    public void updateAuthorities(Account account, Role selectedRole) {
        // Xóa bản ghi Authorities cũ của tài khoản
        authoritiesRepository.deleteByAccount(account);

        // Tạo mới bản ghi Authorities với vai trò mới
        Authority authorities = new Authority();
        authorities.setAccount(account);
        authorities.setRoles(selectedRole);
        authoritiesRepository.save(authorities);
    }
    
    @Transactional
    public void deleteAccountAndAuthorities(Account account) {
        authoritiesRepository.deleteByAccount(account); // Xóa bản ghi Authorities liên quan đến tài khoản
        accountRepository.delete(account); // Xóa tài khoản
    }
    
    
}
