package kg.attractor.controlwork9.exceptions;

public class AlreadyExistsException extends IllegalArgumentException{
    public AlreadyExistsException() {
    }

    public AlreadyExistsException(String s) {
        super(s);
    }
}
