package com.smec.codingchallengewebapi.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Event {

	@Id
	@GeneratedValue
	private long id;

	@Column(nullable = false)
	private LocalDateTime happenedAt;

	@Column(nullable = false)
	private String type;

	@ManyToOne(fetch = FetchType.LAZY)
	private Account account;

	public Event() {
	}

	public Event(LocalDateTime happenedAt, String type, Account account) {
		this.happenedAt = happenedAt;
		this.type = type;
		this.account = account;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDateTime getHappenedAt() {
		return happenedAt;
	}

	public void setHappenedAt(LocalDateTime happenedAt) {
		this.happenedAt = happenedAt;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		result = prime * result + ((happenedAt == null) ? 0 : happenedAt.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		if (happenedAt == null) {
			if (other.happenedAt != null)
				return false;
		} else if (!happenedAt.equals(other.happenedAt))
			return false;
		if (id != other.id)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

}
