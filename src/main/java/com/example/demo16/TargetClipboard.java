package com.example.demo16;

import java.util.ArrayList;
import java.util.List;

public class TargetClipboard {

    List<Blob> clipboard_items;

    public TargetClipboard() {
        clipboard_items = new ArrayList<>();
    }

    public void copyClipboard(List<Blob> blob_list) {
        clipboard_items.clear();
        blob_list.forEach(blob -> clipboard_items.add(blob.duplicate()));
    }

    public List<Blob> getClipboard() {
        List<Blob> copy = new ArrayList<Blob>();
        clipboard_items.forEach(item -> copy.add(item.duplicate()));
        return copy;
    }

}
