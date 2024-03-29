package com.tenco.bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenco.bank.dto.AccountSaveFormDto;
import com.tenco.bank.dto.DepositFormDto;
import com.tenco.bank.dto.TransferFormDto;
import com.tenco.bank.dto.WithdrawFormDto;
import com.tenco.bank.handler.exception.CustomRestfulException;
import com.tenco.bank.repository.entity.Account;
import com.tenco.bank.repository.entity.CustomHistoryEntity;
import com.tenco.bank.repository.entity.History;
import com.tenco.bank.repository.interfaces.AccountRepository;
import com.tenco.bank.repository.interfaces.HistoryRepository;
import com.tenco.bank.utils.Define;

@Service
public class AccountService {

	// SOLID : OCP 오픈 폐쇄의 원칙에 해당
	// 생성자 의존 주입을 받는 것은 인터페이스로 구현하는 것이 OCP를 만족함 
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private HistoryRepository historyRepository;

	
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
	
	// 계좌 목록 보기 가능
	public List<Account> readAccountListByUserId(Integer principalId){

		return accountRepository.findAllByUserId(principalId);
	}

	// 출금 기능 만들기
	// 1. 계좌 존재 여부 확인 --select
	// 2. 본인 계좌 여부 확인 --select
	// 3. 계좌 비번 확인
	// 4. 잔액 여부 확인
	// 5. 출금 처리 --update
	// 6. 거래 내역 등록 --insert(history)
	// 7. 트랜잭션 처리
	
	@Transactional
	public void updateAccountWithdraw(WithdrawFormDto dto, Integer principalId) {

		// 1.
		Account accountEntity = accountRepository.findByNumber(dto.getWAccountNumber());
		if(accountEntity == null) {
			throw new CustomRestfulException(Define.NOT_EXIST_ACCOUNT, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// 2.
		accountEntity.checkOwner(principalId);
		// 3. 
		accountEntity.checkPassword(dto.getWAccountPassword());
		// 4.
		accountEntity.checkBalance(dto.getAmount());
		// 5.
		accountEntity.withdraw(dto.getAmount()); // 출금기능 
		accountRepository.updateById(accountEntity); // 객체 상태값 변경

		// 6.
		History history = new History();
		history.setAmount(dto.getAmount());
		history.setWAccountId(accountEntity.getId());
		history.setDAccountId(null);
		history.setWBalance(accountEntity.getBalance());
		history.setDBalance(null);
		
		int rowResultCount = historyRepository.insert(history);
		if(rowResultCount != 1) {
			throw new CustomRestfulException("정상 처리 되지 않았습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	// 입금 기능 만들기
	// 1. 계좌 존재 여부 확인 --select
	// 2. 본인 계좌 여부 확인 --select
	// 3. 잔액 여부 확인
	// 4. 입금 처리 --update
	// 5. 거래 내역 등록 --insert(history)
	// 6. 트랜잭션 처리
	@Transactional
	public void updateAccountDeposit(DepositFormDto dto, Integer principalId) {
		// 1.
		Account accountEntity = accountRepository.findByNumber(dto.getDAccountNumber());
		if(accountEntity == null) {
			throw new CustomRestfulException(Define.NOT_EXIST_ACCOUNT, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// 2.
		accountEntity.checkOwner(principalId);
		// 3.
		accountEntity.checkAmount(dto.getAmount());
		// 4.
		accountEntity.deposit(dto.getAmount()); 
		accountRepository.updateById(accountEntity); 
		// 5.
		History history = new History();
		history.setAmount(dto.getAmount());
		history.setWAccountId(null);
		history.setDAccountId(accountEntity.getId());
		history.setWBalance(null);
		history.setDBalance(accountEntity.getBalance());
		
		int rowResultCount = historyRepository.insert(history);
		if(rowResultCount != 1) {
			throw new CustomRestfulException("정상 처리 되지 않았습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}


	// 이체 기능 만들기
	// 1. 출금 계좌 존재 확인 --select
	// 2. 입금 계좌 존재 확인 --select
	// 3. 출금 계좌 본인 소유 확인
	// 4. 출금 계좌 비번 확인
	// 5. 출금 계좌 잔액 확인
	// 6. 출금 계좌 잔액 수정 -update
	// 7. 입금 계좌 잔액 수정 -update
	// 8. 거래 내역 등록 처리(이체 내역 쿼리 테스트)
	// 9. 트랜잭션 처리 
	@Transactional
	public void updateAccountTransfer(TransferFormDto dto, Integer principalId) {
		Account accountWEntity = accountRepository.findByNumber(dto.getWAccountNumber());
		Account accountDEntity = accountRepository.findByNumber(dto.getDAccountNumber());
		if(accountWEntity == null || accountDEntity == null ) {
			throw new CustomRestfulException(Define.NOT_EXIST_ACCOUNT, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		accountWEntity.checkOwner(principalId);
		accountWEntity.checkPassword(dto.getPassword());
		accountWEntity.checkBalance(dto.getAmount());
		accountWEntity.withdraw(dto.getAmount());
		accountDEntity.deposit(dto.getAmount());
		
		int resultRowCountWithdraw = accountRepository.updateById(accountWEntity);
		int resultRowCountDeposit = accountRepository.updateById(accountDEntity);
		if(resultRowCountWithdraw != 1 && resultRowCountDeposit != 1) {
			throw new CustomRestfulException("정상 처리 되지 않았습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		History history = History.builder()
				.amount(dto.getAmount())					// 이체 금액
				.wAccountId(accountWEntity.getId())	 		// 출금 계좌
				.dAccountId(accountDEntity.getId())			// 입금 계좌
				.wBalance(accountWEntity.getBalance())		// 출금 계좌 남은 잔액
				.dBalance(accountDEntity.getBalance())		// 입금 계좌 남은 잔액
				.build();
		int resultRowCountHistory = historyRepository.insert(history);
		if(resultRowCountHistory != 1) {
			throw new CustomRestfulException("정상 처리 되지 않았습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	/**
	 * 단일 계좌 거래 내역 검색(전체, 입금, 출금)
	 * @param type [all, deposit, withdraw]
	 * @param id (account_id)
	 * @return 동적 쿼리 - List 
	 */
	public List<CustomHistoryEntity> readHistoryListByAccount(String type, Integer id) {
		
		return historyRepository.findByIdHistoryType(type, id);
	}
	
	// 단일 계좌 조회 - AccountById
	public Account readByAccountId(Integer id) {
		return accountRepository.findByAccountId(id);
	}
	
	
}
