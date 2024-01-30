package com.tenco.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenco.bank.dto.AccountSaveFormDto;
import com.tenco.bank.handler.exception.CustomRestfulException;
import com.tenco.bank.repository.entity.Account;
import com.tenco.bank.repository.interfaces.AccountRepository;
import com.tenco.bank.utils.Define;

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
		// 계좌 번호 중복 확인
		// 예외 처리 
		if(readAccount(dto.getNumber()) != null) {
			throw new CustomRestfulException(Define.EXIST_ACCOUNT, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		Account account = new Account();
		account.setNumber(dto.getNumber());
		account.setPassword(dto.getPassword());
		account.setBalance(dto.getBalance());
		account.setUserId(principalId);
		int resultRowCount = accountRepository.insert(account);

		if(resultRowCount != 1) {
			throw new CustomRestfulException(Define.FAIL_TO_CREATE_ACCOUNT, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	

	}
	
	// 단일 계좌 검색 기능 - account 객체로 리턴함으로써 타 기능에도 재사용 가능 (int로 하면 한정적)
	public Account readAccount(String number) {
		return accountRepository.findByNumber(number);
	}
}
