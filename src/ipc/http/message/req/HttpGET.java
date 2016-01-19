package ipc.http.message.req;

import java.net.URISyntaxException;

public class HttpGET extends HttpRequest {

    public HttpGET(String url) throws URISyntaxException {
        super(HttpRequest.Method.GET, url);
    }
}
