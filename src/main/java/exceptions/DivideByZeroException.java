package exceptions;

public class DivideByZeroException extends Exception {

  private static final long serialVersionUID = 1L;
  
  DivideByZeroException(String errorMessage){
    super(errorMessage);
  }
}
