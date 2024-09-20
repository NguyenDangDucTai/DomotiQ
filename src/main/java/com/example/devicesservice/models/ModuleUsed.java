package com.example.devicesservice.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ModuleUsed {
    private String id;
    private String displayNameModule;
    private boolean visible;

    @Override
    public String toString() {
        return "ModuleUsed{" +
                "id='" + id + '\'' +
                ", displayNameModule='" + displayNameModule + '\'' +
                ", visible=" + visible +
                '}';
    }

}
