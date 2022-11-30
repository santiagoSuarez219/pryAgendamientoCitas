package com.misiontic.ciclo4.pryAgendamientoCitas.Utility;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

@Service
public class ConvertEntity {
    ModelMapper modelMapper = new ModelMapper();
    public Object convert(Object obj,Object objDto){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
                objDto = modelMapper.map(obj, objDto.getClass());
        return objDto;
    }
}
