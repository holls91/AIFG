package util;

import java.util.Optional;

public interface Optionize<T> {

	/**
	 * @return un Optional con l'oggetto di invocazione come parametro
	 */
	public default Optional<T> asOptional() {
		@SuppressWarnings("unchecked")
		Optional<T> out = Optional.of((T)this);
		return out;
	}
	
}
