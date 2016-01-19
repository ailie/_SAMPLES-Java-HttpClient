package ipc.http.message.req;

import java.net.URISyntaxException;

public class HttpDELETE extends HttpRequest {

    public HttpDELETE(String url) throws URISyntaxException {
        super(HttpRequest.Method.DELETE, url);
    }
}
