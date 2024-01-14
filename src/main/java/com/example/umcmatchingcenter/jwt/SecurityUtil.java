package com.example.umcmatchingcenter.jwt;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtil {

    public static String getCurrentMember() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();
        return username;
    }

}
