package com.tenco.bank.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomHistoryEntity {

	private Integer id;
	private Long amount;
	private Long balance;
	private String sender;
	private String receiver;
}
