package com.jelena.ishrana.converter;

import com.jelena.ishrana.model.Namirnica;
import com.jelena.ishrana.service.NamirnicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;


public class StringToNamirnicaConverter implements Converter<String, Namirnica> {

    @Autowired
    private NamirnicaService namirnicaService;

    @Override
    public Namirnica convert(String namirnica_id) throws IllegalArgumentException {
        System.out.println("inside StringToNamirnicaConverter");
        if (namirnica_id == null) { // warning: condition namirnica_id == null always false
            throw new IllegalArgumentException("cannot convert namirnica_id=null to Namirnica");
        }
        return namirnicaService.findOne(Long.parseLong(namirnica_id));
    }
}
