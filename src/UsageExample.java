import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import ipc.http.HTTPClient;
import ipc.http.message.HTTPEntity;
import ipc.http.message.req.HttpGET;
import ipc.http.message.req.HttpPOST;
import ipc.http.message.rsp.HttpResponse;

public class UsageExample {

    public static void main(String[] args)
            throws FileNotFoundException, URISyntaxException, IOException {

        performGET();
        performPOST();
    }

    private static void performGET()
            throws URISyntaxException, FileNotFoundException, IOException {

        HttpGET httpGET = new HttpGET("http://httpbin.org");

        HttpResponse response = new HTTPClient()
                .execute(httpGET);

        System.out.println(response.fStatusCode);
    }

    private static void performPOST()
            throws URISyntaxException, FileNotFoundException, IOException {

        HttpPOST httpPOST = new HttpPOST("http://httpbin.org/post");

        httpPOST.setEntity(new HTTPEntity().setContent(new FileInputStream("someFile")));

        HttpResponse response = new HTTPClient()
                .setStreamingMode(HTTPClient.StreamingMode.CHUNKED, 1024 * 64)
                .execute(httpPOST);

        System.out.println(response.fStatusCode);
    }
}
