package org.ksushka.group;

public class HttpStatusThrowable extends Throwable {
    private int status;
    public HttpStatusThrowable(int status) {
        this.status = status;
    }

    public int getStatus() { return status; }
}
