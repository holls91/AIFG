package util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WeightedObject<T> {
	private T data;
	private double weight;
}