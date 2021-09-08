package com.n26.transaction.domain;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.stream.Collector;

import lombok.Getter;

public class TransactionRollUp
{
	private final Comparator<BigDecimal> comparator;

	@Getter private long count = 0;
	@Getter private BigDecimal min;
	@Getter private BigDecimal max;
	@Getter private BigDecimal sum = BigDecimal.ZERO;

	public TransactionRollUp(Comparator<BigDecimal> comparator)
	{
		this.comparator = comparator;
	}

	public void accept(BigDecimal val)
	{
		if(count==0)
			min = max = val;
		else if(comparator.compare(val, min)<0)
			min = val;
		else if(comparator.compare(val, max)>0)
			max = val;

		sum = sum.add(val);
		count++;
	}

	public TransactionRollUp combine(TransactionRollUp other)
	{
		if(this.count==0) return other;
		if(other.count==0) return this;

		this.count += other.count;
		this.sum = this.sum.add(other.sum);
		
		if(comparator.compare(other.min, this.min)<0)
			this.min = other.min;
		if(comparator.compare(other.max, this.max)>0)
			this.max = other.max;

		return this;
	}

	public static <T extends Comparable<? super T>> Collector<BigDecimal, TransactionRollUp, TransactionRollUp> collector()
	{
		return Collector.of(
				()->new TransactionRollUp(Comparator.naturalOrder()),
				TransactionRollUp::accept,
				TransactionRollUp::combine,
				Collector.Characteristics.UNORDERED
				);

	}
}
