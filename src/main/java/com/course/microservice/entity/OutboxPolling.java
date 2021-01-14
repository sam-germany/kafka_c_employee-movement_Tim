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
		if (this == obj)  return true;         // if already inserted object is same to the coming to the new object
		if (obj == null)  return false;      // if the coming  (Object obj)  is null then it return false

		if (getClass() != obj.getClass())  return false; //

		OutboxPolling other = (OutboxPolling) obj;

		if (id !=  other.id) return false;

		return true;                                //   >>>       understand this operator see this url and read below the explanation
	}                                              //   https://stackoverflow.com/questions/19058859/what-does-mean-in-java/19058871

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (32  >>> id));        // ^   XOR         see below the explanation
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
/*
(1)
    int high = 2100000000;
    int low = 2000000000;
         System.out.println("mid using >>> 1 = " + ((low + high) >>> 1));
         System.out.println("mid using / 2   = " + ((low + high) / 2));

output
-------
      mid using >>> 1 = 2050000000
     mid using / 2   = -97483648

main point is when integer goes to its top limit then it jump from +  to -
e,g   top limit is   (Integer.MAX_VALUE = +2147483647  + 1   then it makes   -2147483648
so by >>> operator  we are stopping the int value to goes in minus

 (2)

   ^     XOR   operator, see this url   https://www.baeldung.com/java-xor-operator



 */