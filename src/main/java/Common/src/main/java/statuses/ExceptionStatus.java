package statuses;

import java.io.Serial;
import java.io.Serializable;

/**
 * Статус если команда завершилась с ошибкой
 */
public class ExceptionStatus extends Status implements Serializable {
    @Serial
    private static final long serialVersionUID = 100;
    private String response;

    public ExceptionStatus(String response) {
        super("Exception");
        this.response = response;
    }

    @Override
    public String getResponse() {
        return response;
    }
}
