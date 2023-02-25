package com.gx.service;

import org.springframework.transaction.annotation.Transactional;

public interface BookService {
    @Transactional
    void save();

    public boolean openURL(String url, String pw);
}
