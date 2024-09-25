package com.example.devicesservice.dtos.module_log;

import com.example.devicesservice.dtos.GetListRequest;
import lombok.Data;

@Data
public class GetLogListRequest extends GetListRequest {

    private String moduleId;

}
