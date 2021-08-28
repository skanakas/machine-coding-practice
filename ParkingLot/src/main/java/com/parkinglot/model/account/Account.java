/**
 * 
 */
package com.parkinglot.model.account;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

/**
 * @author arun
 *
 */
@Getter
@Setter
public class Account {

	private String id;
	private String email;
	private String username;
	private String password;
	private LocalDateTime lastAccessed;
	private Contact contact;
}
