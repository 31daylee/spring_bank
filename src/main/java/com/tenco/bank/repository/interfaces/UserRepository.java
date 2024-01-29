package com.tenco.bank.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tenco.bank.dto.SignUpFormDto;
import com.tenco.bank.repository.entity.User;

// interface + xml 연결
@Mapper
public interface UserRepository {
	public int insert(User user); 
	public int updateById(User user);
	public int deleteById(Integer id);
	
	// 단일 행으로 나오기에 return 타입은 User
	public User findById(Integer id);
	public List<User> findAll();
	
	
}
