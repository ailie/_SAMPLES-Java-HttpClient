package ipc.http.message.req;

import java.net.URISyntaxException;

public class HttpTRACE extends HttpRequest {

    public HttpTRACE(String url) throws URISyntaxException {
        super(HttpRequest.Method.TRACE, url);
    }
}
