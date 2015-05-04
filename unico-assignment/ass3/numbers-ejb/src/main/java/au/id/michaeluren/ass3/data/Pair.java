package au.id.michaeluren.ass3.data;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Pair implements Serializable {
	
	private Long id;
	private int first, second;
	
	public Pair() {} // to appease JPA
	
	public Pair(int first, int second) {
		this.first = first;
		this.second = second;
	}
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public int getFirst() {
		return first;
	}
	public void setFirst(int i) {
		this.first = i;
	}
	
	public int getSecond() {
		return second;
	}
	public void setSecond(int i) {
		this.second = i;
	}
}
