package exceptions;

public class DivideByZeroException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  
  DivideByZeroException(String errorMessage){
    super(errorMessage);
  }
}
