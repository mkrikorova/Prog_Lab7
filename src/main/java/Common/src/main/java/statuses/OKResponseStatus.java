package statuses;

import java.io.Serial;
import java.io.Serializable;

/**
 * Статус если команда выполнилась успешно и передала ответ
 */
public class OKResponseStatus extends Status implements Serializable {
    @Serial
    private static final long serialVersionUID = 101;
    private String response;

    public OKResponseStatus(String response) {
        super("OK");
        this.response = response;
    }

    @Override
    public String getResponse() {
        return response;
    }
}
