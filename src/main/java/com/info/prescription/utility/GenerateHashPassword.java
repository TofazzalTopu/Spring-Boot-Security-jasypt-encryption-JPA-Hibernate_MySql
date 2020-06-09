package com.info.prescription.utility;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

public class GenerateHashPassword {

    private static final String SALT = "salt";
//    private static final String SALT = "exp-managert-salt";

    //    @Bean
    public static BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(11, new SecureRandom(SALT.getBytes()));
    }

    public static void main(String[] args){
        passwordEncoder().encode("tofazzal");
        System.out.println(passwordEncoder().encode("tofazzal"));
        System.out.println(passwordEncoder().encode("123456"));
        System.out.println(passwordEncoder().matches("123456", "$2a$12$DcuqccaIWeKWnknf.KVeq.M.kKHiDC4cwS4T6AgKVO3X0b26xW3kO"));
    }
}
