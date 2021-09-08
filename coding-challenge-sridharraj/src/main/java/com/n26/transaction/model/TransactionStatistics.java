/**
 * 
 */
package com.n26.transaction.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import lombok.Getter;
import lombok.ToString;

/**
 * @author SridharRaj
 *
 */
@Getter
@ToString
public class TransactionStatistics implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String ZERO = "0.00";
	
	private String sum = ZERO;
	private String avg = ZERO;
	private String max = ZERO;
	private String min = ZERO;
	private Long count = 0l;
	
	public void setSum(BigDecimal sum) {
		this.sum = sum.toString();
	}
	public void setAvg(BigDecimal avg) {
		this.avg = avg.toString();
	}
	public void setMax(BigDecimal max) {
		this.max = max.toString();
	}
	public void setMin(BigDecimal min) {
		this.min = min.toString();
	}
	public void setCount(Long count) {
		this.count = count;
	}
	@Override
	public int hashCode() {
		return Objects.hash(avg, count, max, min, sum);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransactionStatistics other = (TransactionStatistics) obj;
		
		return (this.min.equals(other.min) 
				&& this.max.equals(other.max)
				&& this.avg.equals(other.avg)
				&& this.sum.equals(other.sum)
				&& this.count.longValue() == other.count.longValue());
	}
	
	
	
}
