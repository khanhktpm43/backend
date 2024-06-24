package com.example.DoAnAngular.Service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BlackList {
    private List<String> blackList = Collections.synchronizedList(new ArrayList<>());

    // Phương thức để thêm phần tử vào danh sách
    public void addItemToList(String item) {
        blackList.add(item);
    }

    // Phương thức để lấy danh sách
    public List<String> getList() {
        return blackList;
    }
}
