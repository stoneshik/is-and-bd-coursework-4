package com.main.services;

import com.main.dto.FileDto;
import com.main.dto.OrderPrintDto;
import com.main.entities.task.PrintTaskColor;
import com.main.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TaskService implements TaskRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final Long WRONG_ID = -1L;

    private Long findMachineIdForTask(Long vendingPointId, String sqlString) {
        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("vending_point_id", vendingPointId);
            return jdbcTemplate.queryForObject(
                    sqlString,
                    mapSqlParameterSource,
                    (rs, rowNum) -> {
                        return rs.getLong("machine_id");
                    }
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private Long getIdFromQueryResult(int queryResult, KeyHolder keyHolder) {
        if (queryResult <= 0) {
            return WRONG_ID;
        }
        Map<String, Object> mapResults = keyHolder.getKeys();
        if (mapResults == null || mapResults.isEmpty()) {
            return WRONG_ID;
        }
        Long id = ((Number) mapResults.get("order_id")).longValue();
        if (id < 0L) {
            return WRONG_ID;
        }
        return id;
    }

    private Long createTaskPrint(Long orderId, Long machineId, String printTaskColor, Long printTaskNumberCopies) {
        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("order_id", orderId);
            mapSqlParameterSource.addValue("machine_id", machineId);
            mapSqlParameterSource.addValue("print_task_color", printTaskColor);
            mapSqlParameterSource.addValue("print_task_number_copies", printTaskNumberCopies);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            int queryResult = jdbcTemplate.update(
                    """
                    INSERT INTO print_tasks(
                        print_task_id,
                        order_id,
                        machine_id,
                        print_task_color,
                        print_task_number_copies)
                    VALUES
                        (default, :order_id, :machine_id, :print_task_color, :print_task_number_copies);
                    """,
                    mapSqlParameterSource,
                    keyHolder
            );
            return getIdFromQueryResult(queryResult, keyHolder);
        } catch (EmptyResultDataAccessException e) {
            return WRONG_ID;
        }
    }

    private Long uploadFile(Long userId, MultipartFile file) {
        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("user_id", userId);
            mapSqlParameterSource.addValue("file_name", file.getName());
            mapSqlParameterSource.addValue("file", file.getBytes());
            KeyHolder keyHolder = new GeneratedKeyHolder();
            int queryResult = jdbcTemplate.update(
                    """
                    INSERT INTO files(
                        file_id,
                        user_id,
                        file_name,
                        file_load_datetime,
                        file_oid)
                    VALUES
                        (default,
                        :user_id,
                        :file_name,
                        default,
                        lo_from_bytea(0, :file));
                    """,
                    mapSqlParameterSource,
                    keyHolder
            );
            return getIdFromQueryResult(queryResult, keyHolder);
        } catch (EmptyResultDataAccessException | IOException e) {
            return WRONG_ID;
        }
    }

    private boolean createConnectionsForTaskPrint(Long fileId, Long machineId, Long printTaskId) {
        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("print_task_id", printTaskId);
            mapSqlParameterSource.addValue("file_id", fileId);
            int queryResult = jdbcTemplate.update(
                    """
                    INSERT INTO print_task_files(
                        print_task_file_id,
                        print_task_id,
                        file_id)
                    VALUES
                        (default, :print_task_id, :file_id);
                    """,
                    mapSqlParameterSource
            );
            if (queryResult <= 0) {
                return false;
            }
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("machine_id", machineId);
            mapSqlParameterSource.addValue("file_id", fileId);
            int queryResult = jdbcTemplate.update(
                    """
                    INSERT INTO machine_files(
                        machine_file_id,
                        machine_id,
                        file_id)
                    VALUES
                        (default, :machine_id, :file_id);
                    """,
                    mapSqlParameterSource
            );
            return queryResult > 0;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public Long findMachineIdForTaskScan(Long vendingPointId) {
        return findMachineIdForTask(
                vendingPointId,
                """
                SELECT machine_id FROM function_variants
                    WHERE vending_point_id = :vending_point_id AND function_variant = 'scan' LIMIT 1;"""
        );
    }

    @Override
    public Long findMachineIdForTaskPrint(OrderPrintDto orderPrintDto) {
        final Long vendingPointId = orderPrintDto.getVendingPointId();
        boolean isHavingBlackWhitePrintInOrder = false;
        boolean isHavingColorPrintInOrder = false;
        for (FileDto fileDto : orderPrintDto.getFiles()) {
            if (isHavingBlackWhitePrintInOrder && isHavingColorPrintInOrder) {
                break;
            }
            if (fileDto.getTypePrint().equals(PrintTaskColor.BLACK_WHITE.getName())) {
                isHavingBlackWhitePrintInOrder = true;
            } else if (fileDto.getTypePrint().equals(PrintTaskColor.COLOR.getName())) {
                isHavingColorPrintInOrder = true;
            }
        }
        String sqlString;
        if (!isHavingBlackWhitePrintInOrder && !isHavingColorPrintInOrder) {
            return null;
        } else if (isHavingBlackWhitePrintInOrder && isHavingColorPrintInOrder) {
            sqlString = """
                SELECT machine_id FROM function_variants
                    WHERE vending_point_id = :vending_point_id
                    AND function_variant = 'black_white'
                    AND function_variant = 'color'
                    LIMIT 1;""";
        } else if (isHavingBlackWhitePrintInOrder) {
            sqlString = """
                SELECT machine_id FROM function_variants
                    WHERE vending_point_id = :vending_point_id
                    AND function_variant = 'black_white'
                    LIMIT 1;""";
        } else {
            sqlString = """
                SELECT machine_id FROM function_variants
                    WHERE vending_point_id = :vending_point_id
                    AND function_variant = 'color'
                    LIMIT 1;""";
        }
        return findMachineIdForTask(vendingPointId, sqlString);
    }

    @Override
    public boolean createTaskScan(Long orderId, Long machineId, Long scanTaskNumberPages) {
        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("order_id", orderId);
            mapSqlParameterSource.addValue("machine_id", machineId);
            mapSqlParameterSource.addValue("scan_task_number_pages", scanTaskNumberPages);
            int queryResult = jdbcTemplate.update(
                    """
                    INSERT INTO scan_tasks(
                        scan_task_id,
                        order_id,
                        machine_id,
                        scan_task_number_pages)
                    VALUES
                        (default, :order_id, :machine_id, :scan_task_number_pages);
                    """,
                    mapSqlParameterSource
            );
            return queryResult > 0;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public boolean createTasksPrint(Long orderId, Long machineId, OrderPrintDto orderPrintDto) {
        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("order_id", orderId);
            mapSqlParameterSource.addValue("machine_id", machineId);
            mapSqlParameterSource.addValue("scan_task_number_pages", scanTaskNumberPages);
            int queryResult = jdbcTemplate.update(
                    """
                    INSERT INTO scan_tasks(
                        scan_task_id,
                        order_id,
                        machine_id,
                        scan_task_number_pages)
                    VALUES
                        (default, :order_id, :machine_id, :scan_task_number_pages);
                    """,
                    mapSqlParameterSource
            );
            return queryResult > 0;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
