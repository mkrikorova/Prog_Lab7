package validators;

import exceptions.WrongArgumentsException;
import exchange.Request;

public class NoArgumentsValidator extends Validator {
    public Request validate(String command, String args) {
        try {
            checkIfNoArguments(command, args);
            return super.validate(command, args);
        } catch (WrongArgumentsException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
