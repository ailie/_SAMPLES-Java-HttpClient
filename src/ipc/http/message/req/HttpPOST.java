package ipc.http.message.req;

import java.net.URISyntaxException;

public class HttpPOST extends HttpRequest {

    public HttpPOST(String url) throws URISyntaxException {
        super(HttpRequest.Method.POST, url);
    }
}
