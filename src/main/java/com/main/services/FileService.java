package com.main.services;

import com.main.entities.file.FileInfoEntity;
import com.main.repositories.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService implements FileRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<FileInfoEntity> getFilesByOrderId(Long orderId) {
        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("order_id", orderId);
            return jdbcTemplate.query(
                    """
                    SELECT files.file_id,
                           files.user_id,
                           files.file_name,
                           files.file_load_datetime,
                           files.file_oid FROM files
                        INNER JOIN print_task_files
                            INNER JOIN print_tasks
                                INNER JOIN orders
                                ON orders.order_id = :order_id
                            ON orders.order_id = print_tasks.order_id
                        ON print_task_files.print_task_id = print_tasks.print_task_id
                    WHERE files.file_id = print_task_files.file_id;""",
                    mapSqlParameterSource,
                    (rs, rowNum) -> {
                        return new FileInfoEntity(
                                rs.getLong("file_id"),
                                rs.getLong("user_id"),
                                rs.getString("file_name"),
                                rs.getDate("file_load_datetime")
                        );
                    }
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public boolean removeFilesByOrderId(Long orderId) {
        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("order_id", orderId);
            int queryResult = jdbcTemplate.update(
                    """
                    DELETE FROM files
                    WHERE files.file_id in
                        (SELECT files.file_id FROM files
                            INNER JOIN print_task_files
                                INNER JOIN print_tasks
                                    INNER JOIN orders
                                    ON orders.order_id = :order_id
                                ON orders.order_id = print_tasks.order_id
                            ON print_task_files.print_task_id = print_tasks.print_task_id
                        WHERE files.file_id = print_task_files.file_id);""",
                    mapSqlParameterSource
            );
            return queryResult > 0;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public List<FileInfoEntity> getFilesAttachedPrintOrder(Long userId) {
        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("user_id", userId);
            return jdbcTemplate.query(
                    """
                    SELECT files.file_id,
                           files.user_id,
                           files.file_name,
                           files.file_load_datetime,
                           files.file_oid FROM files
                        INNER JOIN print_task_files
                        ON files.file_id = print_task_files.file_id
                    WHERE user_id = :user_id;""",
                    mapSqlParameterSource,
                    (rs, rowNum) -> {
                        return new FileInfoEntity(
                                rs.getLong("file_id"),
                                rs.getLong("user_id"),
                                rs.getString("file_name"),
                                rs.getDate("file_load_datetime")
                        );
                    }
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<FileInfoEntity> getFilesAttachedScanOrder(Long userId) {
        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("user_id", userId);
            return jdbcTemplate.query(
                    """
                    SELECT files.file_id,
                           files.user_id,
                           files.file_name,
                           files.file_load_datetime,
                           files.file_oid FROM files
                        INNER JOIN scan_task_files
                        ON files.file_id = scan_task_files.file_id
                    WHERE user_id = :user_id;""",
                    mapSqlParameterSource,
                    (rs, rowNum) -> {
                        return new FileInfoEntity(
                                rs.getLong("file_id"),
                                rs.getLong("user_id"),
                                rs.getString("file_name"),
                                rs.getDate("file_load_datetime")
                        );
                    }
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
