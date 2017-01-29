package com.ymonnier.restful.littleapp.utilities;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;

/**
 * Project restful.littleapp.
 * Package com.ymonnier.restful.littleapp.utilities.
 * File CORSFilter.java.
 * Created by Ysee on 28/01/2017 - 14:10.
 * www.yseemonnier.com
 * https://github.com/YMonnier
 */
public class CORSFilter implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        containerResponseContext.getHeaders().putSingle("Access-Control-Allow-Origin", "*");
        //containerResponseContext.getHeaders().putSingle("Access-Control-Allow-Credentials", "true");
        containerResponseContext.getHeaders().putSingle("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD");
        containerResponseContext.getHeaders().putSingle("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With");
    }
}
