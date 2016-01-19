package ipc.http.message.req;

import java.net.URISyntaxException;

public class HttpPUT extends HttpRequest {

    public HttpPUT(String url) throws URISyntaxException {
        super(HttpRequest.Method.PUT, url);
    }
}
