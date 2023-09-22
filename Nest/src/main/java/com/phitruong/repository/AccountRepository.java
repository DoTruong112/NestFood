package com.phitruong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.phitruong.entity.Account;

public interface AccountRepository extends JpaRepository<Account, String>{
	Account findByUsername(String username);
	
	@Query("SELECT DISTINCT a FROM Account a LEFT JOIN FETCH a.authorities WHERE a.username = :username")
    Account findByUsernameWithAuthorities(String username);
	
	@Query("SELECT COUNT(a) FROM Account a WHERE EXISTS (SELECT 1 FROM Authority auth WHERE auth.account = a AND auth.roles.name = 'Customers')")
	Long countAccountsByRoleCustomers();
	
	@Modifying
	@Query("UPDATE Account a SET a.email = :email, a.fullname = :fullname, a.address = :address, "
			+ "a.avatar = :avatar, a.phone = :phone WHERE a.username = :username")
	void updateAccountInfo(@Param("username") String username, @Param("email") String email,
			@Param("fullname") String fullname, @Param("address") String address, @Param("avatar") String avatar,
			@Param("phone") String phone);
}
