package com.main.repositories;

public interface TaskRepository {
    Long findMachineIdForTaskScan(Long vendingPointId);

    boolean createTaskScan(Long orderId, Long machineId, Long scanTaskNumberPages);
}
