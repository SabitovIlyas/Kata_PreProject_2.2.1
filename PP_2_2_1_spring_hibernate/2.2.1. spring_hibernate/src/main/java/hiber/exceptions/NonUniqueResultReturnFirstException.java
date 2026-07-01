package hiber.exceptions;

import org.hibernate.NonUniqueResultException;

public class NonUniqueResultReturnFirstException extends NonUniqueResultException {
    /**
     * Constructs a NonUniqueResultReturnFirstException.
     *
     * @param resultCount The number of actual results.
     * @param firstElement The first element in results.
     */
    public final Object firstElement;
    public final int resultCount;

    public NonUniqueResultReturnFirstException(int resultCount, Object firstElement) {
        super(resultCount);
        this.firstElement = firstElement;
        this.resultCount = resultCount;
    }
}
