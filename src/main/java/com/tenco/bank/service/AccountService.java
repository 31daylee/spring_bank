package com.tenco.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenco.bank.dto.AccountSaveFormDto;
import com.tenco.bank.handler.exception.CustomRestfulException;
import com.tenco.bank.repository.entity.Account;
import com.tenco.bank.repository.interfaces.AccountRepository;

@Service
public class AccountService {

	// SOLID : OCP 오픈 폐쇄의 원칙에 해당
	// 생성자 의존 주입을 받는 것은 인터페이스로 구현하는 것이 OCP를 만족함 
	@Autowired
	private AccountRepository accountRepository;
	
	// 계좌 생성
	// 1. 사용자 정보 
	// TODO: 계좌 번호 중복 확인 예정
	@Transactional
	public void createAccount(AccountSaveFormDto dto, Integer principalId) {

		System.out.println("----------");
		Account account = new Account();
		account.setNumber(dto.getNumber());
		account.setPassword(dto.getPassword());
		account.setBalance(dto.getBalance());
		account.setUserId(principalId);
		int resultRowCount = accountRepository.insert(account);
		System.out.println("2222222");
		if(resultRowCount != 1) {
			throw new CustomRestfulException("계좌 생성 실패", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
