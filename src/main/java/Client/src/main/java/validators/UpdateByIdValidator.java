package validators;

import exceptions.ExitProgramException;
import exceptions.WrongArgumentsException;
import exchange.Request;

public class UpdateByIdValidator extends ReadValidator {
    @Override
    public Request validate(String command, String args, boolean parse) throws ExitProgramException {
        try {
            checkIfOneArgument(command, args);
            return super.validate(command, args, parse);
        } catch (WrongArgumentsException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (NumberFormatException e) {
            System.out.println("ID должен быть целым числом");
            return null;
        }
    }
}
