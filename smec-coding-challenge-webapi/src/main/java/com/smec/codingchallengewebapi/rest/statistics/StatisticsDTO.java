package com.smec.codingchallengewebapi.rest.statistics;

import java.time.LocalDate;

public class StatisticsDTO {

	private LocalDate day;
	private String type;
	private long count;

	protected StatisticsDTO() {
	}

	public StatisticsDTO(LocalDate day, String type, long count) {
		this.day = day;
		this.type = type;
		this.count = count;
	}

	public LocalDate getDay() {
		return day;
	}

	public void setDay(LocalDate day) {
		this.day = day;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (count ^ (count >>> 32));
		result = prime * result + ((day == null) ? 0 : day.hashCode());
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
		StatisticsDTO other = (StatisticsDTO) obj;
		if (count != other.count)
			return false;
		if (day == null) {
			if (other.day != null)
				return false;
		} else if (!day.equals(other.day))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getDay() + "_" + getType() + "_" + getCount();
	}
}
