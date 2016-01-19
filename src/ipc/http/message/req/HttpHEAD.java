package ipc.http.message.req;

import java.net.URISyntaxException;

public class HttpHEAD extends HttpRequest {

    public HttpHEAD(String url) throws URISyntaxException {
        super(HttpRequest.Method.HEAD, url);
    }
}
