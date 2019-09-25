package com.chengsij.jasypt.encryptor;

import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class StringEncryptor {

    @Autowired
    private static Environment env;

    static private BasicTextEncryptor textEncryptor;

    public static BasicTextEncryptor getEncryptor(){
        if (textEncryptor == null){
            textEncryptor = new BasicTextEncryptor();
            textEncryptor.setPasswordCharArray(System.getenv().get("JASYPT_PASSWORD").toCharArray());
        }
        return textEncryptor;
    }
}
