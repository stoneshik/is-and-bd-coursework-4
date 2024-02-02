package com.main.repositories;

import com.main.dto.OrderPrintDto;

public interface TaskRepository {
    Long findMachineIdForTaskScan(Long vendingPointId);

    Long findMachineIdForTaskPrint(OrderPrintDto orderPrintDto);

    boolean createTaskScan(Long orderId, Long machineId, Long scanTaskNumberPages);

    boolean createTasksPrint(Long orderId, Long machineId, OrderPrintDto orderPrintDto, Long userId);
}
