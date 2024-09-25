package com.example.devicesservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PresenceSensorModuleTrigger {

    private Type type;
    private Set<Action> actions;

    public enum Type {
        ON_DETECT_PRESENCE,
        ON_DETECT_ABSENCE
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @SuperBuilder
    public static class Action {

        private Type type;

        public enum Type {
            DEVICE_CONTROL,
            NOTIFICATION
        }

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @SuperBuilder
    public static class DeviceControlAction extends Action {

        private Command command;

    }

}
