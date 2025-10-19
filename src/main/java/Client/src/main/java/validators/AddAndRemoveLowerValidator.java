package validators;

import exceptions.ExitProgramException;
import exceptions.WrongArgumentsException;
import exchange.Request;
import utils.ColorOutput;

public class AddAndRemoveLowerValidator extends ReadValidator {
    @Override
    public Request validate(String command, String args, boolean parse, int user_id) throws ExitProgramException {
        try {
            checkIfNoArguments(command, args);
            return super.validate(command, args, parse, user_id);
        } catch (WrongArgumentsException e) {
            ColorOutput.printlnRed(e.getMessage());
            return null;
        }
    }
}
