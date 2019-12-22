package com.smec.codingchallengewebapi.rest.event;

import java.time.LocalDateTime;

public class EventDTO {

	private LocalDateTime happenedAt;
	private String type;

	public EventDTO(LocalDateTime happenedAt, String type) {
		this.happenedAt = happenedAt;
		this.type = type;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((happenedAt == null) ? 0 : happenedAt.hashCode());
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
		EventDTO other = (EventDTO) obj;
		if (happenedAt == null) {
			if (other.happenedAt != null)
				return false;
		} else if (!happenedAt.equals(other.happenedAt))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

}
