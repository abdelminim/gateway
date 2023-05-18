/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.services.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 *
 * @author Saeed Fathi
 */
public class BufferedServletRequestWrapper extends HttpServletRequestWrapper {

    private byte[] buffer;

    public BufferedServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);

        //Get Stream from Request Body
        InputStream is = request.getInputStream();

        //Convert Stream to byte array and keep it in instance variable
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte buff[] = new byte[1024];
        int read;
        while ((read = is.read(buff)) > 0) {
            baos.write(buff, 0, read);
        }

        this.buffer = baos.toByteArray();
    }

    //Replace the body acquisition source with this method
    @Override
    public ServletInputStream getInputStream() throws IOException {
        //Initialize Stream class and return
        return new BufferedServletInputStream(this.buffer);
    }
}
