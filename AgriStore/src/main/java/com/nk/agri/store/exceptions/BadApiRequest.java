package com.nk.agri.store.exceptions;

public class BadApiRequest extends RuntimeException {
    public BadApiRequest(String msg) {
        super(msg);
    }

    public BadApiRequest() {
        super("Bad Req !!! ");
    }


}
