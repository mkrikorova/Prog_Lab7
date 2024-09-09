package validators;

import exchange.Request;

public interface IValidator {
    Request validate(String command, String args);
}
