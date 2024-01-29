package com.main.entities.task;

import lombok.Data;

@Data
public class ScanTask {
    private Long scanTaskId;
    private Long orderId;
    private Long machineId;
    private Long scanTaskNumberPages;

    public ScanTask(Long scanTaskId, Long orderId, Long machineId, Long scanTaskNumberPages) {
        this.scanTaskId = scanTaskId;
        this.orderId = orderId;
        this.machineId = machineId;
        this.scanTaskNumberPages = scanTaskNumberPages;
    }
}
