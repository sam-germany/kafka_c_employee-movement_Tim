package com.course.microservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class OutboxPolling {

	@Column(length = 4000, nullable = false)
	private String changedData;

	@CreatedDate                       // when we create this object that Data-Time will be inserted to this property
	@Column
	private LocalDateTime createdDate;

	@Id
	@JsonIgnore               // <-- it means that just ignore this property while serialization and deserialization
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "outbox_polling_seq")
	private long id;

	@Column(length = 20, nullable = false)
	private String transactionType;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)  return true;
		if (obj == null)  return false;

		if (getClass() != obj.getClass())  return false;

		OutboxPolling other = (OutboxPolling) obj;

		if (id !=  other.id) return false;

		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (32  >>> id));
		return result;
	}

	public String getChangedData() {
		return changedData;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public long getId() {
		return id;
	}

	public String getTransactionType() {
		return transactionType;
	}



	public void setChangedData(String changedData) {
		this.changedData = changedData;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	@Override
	public String toString() {
		return "OutboxPolling [id=" + id + ", transactionType=" + transactionType + ", changedData=" + changedData
				+ "]";
	}

}
