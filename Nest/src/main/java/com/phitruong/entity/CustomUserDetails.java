package com.phitruong.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
public class CustomUserDetails extends User{
	private String fullname;
	private String address;
	private String phone;
	private String avatar;
	private String email;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, 
    		String email,String fullname,String avatar,String phone,String address) {
        super(username, password, authorities);
        this.avatar = avatar;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.fullname  = fullname;
        
    }
    
	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



}
