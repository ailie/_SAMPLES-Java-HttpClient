package ipc.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ipc.http.message.HTTPEntity;
import ipc.http.message.req.HttpRequest;
import ipc.http.message.rsp.HttpResponse;

/**
 * You retrieve and send data via this HTTP client. It is not a browser - it lacks the
 * UI, HTML renderer, JS engine and tolerance for HTTP-standard violations. It is an
 * HTTP communication library and as such it provides only a subset of functions
 * expected from a common browser application.
 * <p>
 * SESSION - a series of requests from a single source to a server. The server can keep
 * session data, and needs to recognize the session to which each incoming request
 * belongs. For example, if you execute a web search, the server will only return one
 * page of search results. But it keeps track of the other results and makes them
 * available when you click on the link to the "next" page. The server needs to know
 * from the request that it is you and your session for which more results are
 * requested, and not me and my session. That's because I searched for something else.
 * <p>
 * COOKIES - the preferred way for servers to track sessions. The server supplies a
 * piece of data, called a cookie, in response to a request. The server expects the
 * client to send that piece of data in a header field with each following request of
 * the same session. The cookie is different for each session, so the server can
 * identify to which session a request belongs by looking at the cookie. If the cookie
 * is missing from a request, the server will not respond as expected.
 */
public class HTTPClient {

    public static enum StreamingMode {
        NONE, CHUNKED, FIXED_LENGTH,
    }

    private HttpURLConnection fConn;
    private StreamingMode     fStreamingMode = StreamingMode.NONE;
    private int               fStreamingModeParam;

    /**
     * @param mode
     * @param param
     *            value for {@link HttpURLConnection#setFixedLengthStreamingMode(int)}
     *            or {@link HttpURLConnection#setChunkedStreamingMode(int)}
     */
    public HTTPClient setStreamingMode(StreamingMode mode, int param) {
        fStreamingMode = mode;
        fStreamingModeParam = param;
        return this;
    }

    public HttpResponse execute(HttpRequest req) throws IOException {
        /* compare conn.getURL().getHost() before/after getHeaderFields() and
         * getInputStream() to detect when you've been redirected ! */
        fConn = sendRequest(req);
        return readResponse(fConn);
    }

    private HttpURLConnection sendRequest(HttpRequest req)
            throws MalformedURLException, IOException {

        HttpURLConnection conn = (HttpURLConnection) req.getURI().toURL()
                                                        .openConnection();

        /* HttpURLConnection uses GET by default. It will use POST if setDoOutput(true)
         * has been called. Other HTTP methods (OPTIONS, HEAD, PUT, DELETE and TRACE)
         * can be used with setRequestMethod(String). */
        conn.setRequestMethod(req.getMethod().toString());
        addHeaderFields(conn, req.getHeaderFields());

        if (req.getEntity() != null && req.getEntity().getContent() != null) {
            OutputStream os = setWritable(conn);
            conn.connect();
            copy(req.getEntity().getContent(), os);
        } else
            conn.connect();

        return conn;
    }

    private long copy(InputStream src, OutputStream dst) throws IOException {
        byte[] buffer = new byte[1024 * 64];
        int count = 0;
        long countTotal = 0;

        while ((count = src.read(buffer)) != /* EOF */ -1) {
            dst.write(buffer, 0, count);
            countTotal += count;
        }
        return countTotal;
    }

    private HTTPClient addHeaderFields(HttpURLConnection conn, Map<String, List<String>> map) {
        for (Entry<String, List<String>> i : map.entrySet()) {
            String[] val = i.getValue().toArray(new String[i.getValue().size()]);
            conn.setRequestProperty(i.getKey(), String.join(",", val));
        }
        return this;
    }

    private OutputStream setWritable(HttpURLConnection conn) throws IOException {

        // we'll be using this connection for output

        conn.setDoOutput(true);

        /**
         * By default, HttpURLConnection will be forced to buffer the complete request
         * body in memory before it is transmitted, wasting (and possibly exhausting)
         * heap and increasing latency. For best performance, you should call either
         * setFixedLengthStreamingMode(contentLen) when the body length is known in
         * advance, or setChunkedStreamingMode(chunkLen) when it is not.
         */

        switch (fStreamingMode) {
        case CHUNKED:
            conn.setChunkedStreamingMode(fStreamingModeParam);
            break;
        case FIXED_LENGTH:
            conn.setFixedLengthStreamingMode(fStreamingModeParam);
            break;
        case NONE:
            // nothing to do
            break;
        }
        return conn.getOutputStream();
    }

    private HttpResponse readResponse(HttpURLConnection conn) throws IOException {

        String statusLine = conn.getHeaderField(0); // aka getHeaderField(null)

        HttpResponse result = new HttpResponse(statusLine.split("\\s+")[0],
                                               conn.getResponseCode(),
                                               conn.getResponseMessage());
        return (HttpResponse) result.setEntity(getResponseEntityFrom(conn));
    }

    private HTTPEntity getResponseEntityFrom(HttpURLConnection conn) {
        return new HTTPEntity().setContent(getInputStreamFrom(conn))
                               .setContentLength(conn.getContentLengthLong())
                               .setContentType(conn.getContentType())
                               .setContentEncoding(conn.getContentEncoding());
    }

    private InputStream getInputStreamFrom(HttpURLConnection conn) {

        InputStream is;

        try {
            is = conn.getInputStream();
        } catch (IOException e) {
            is = null;
        }

        if (is == null) // conn.getInputStream() == null or it threw some Exception
            is = conn.getErrorStream();

        return is;
    }
}
