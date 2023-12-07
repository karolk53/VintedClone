package com.example.VintedClone.service;

import com.example.VintedClone.config.LocalDateTypeAdapter;
import com.example.VintedClone.dto.ProductResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class JsonExporterImpl implements  JsonExporter{

    @Override
    public String export(List<ProductResponse> products) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class,new LocalDateTypeAdapter())
                .create();
        return gson.toJson(products);
    }
}
