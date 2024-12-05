package com.example.devicesservice.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Document(collection = "modules")
public class GestureCameraModule extends Module{

    private List<GestureCameraModuleTrigger> gestureCameraModuleTriggers;
    public GestureCameraModule(){
        this.type = ModuleType.GESTURE_CAMERA;
    }

}
