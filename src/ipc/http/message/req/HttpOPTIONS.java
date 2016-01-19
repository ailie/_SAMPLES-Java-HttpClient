package ipc.http.message.req;

import java.net.URISyntaxException;

public class HttpOPTIONS extends HttpRequest {

    public HttpOPTIONS(String url) throws URISyntaxException {
        super(HttpRequest.Method.OPTIONS, url);
    }
}
