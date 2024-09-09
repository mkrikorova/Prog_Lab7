package validators;

import exceptions.ExitProgramException;
import exceptions.WrongArgumentsException;
import exchange.Request;
import utils.ColorOutput;

public class AddAndRemoveLowerValidator extends ReadValidator {
    @Override
    public Request validate(String command, String args, boolean parse) throws ExitProgramException {
        try {
            checkIfNoArguments(command, args);
            return super.validate(command, args, parse);
        } catch (WrongArgumentsException e) {
            ColorOutput.printlnRed(e.getMessage());
            return null;
        }
    }
}
