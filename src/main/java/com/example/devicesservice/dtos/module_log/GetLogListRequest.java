package com.example.devicesservice.dtos.module_log;

import com.example.devicesservice.dtos.GetListRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetLogListRequest extends GetListRequest {

    private String moduleId;

}
