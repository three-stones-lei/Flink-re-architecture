package org.lei.function;

import org.apache.flink.connector.jdbc.JdbcConnectionOptions;
import org.apache.flink.connector.jdbc.JdbcExecutionOptions;
import org.apache.flink.connector.jdbc.JdbcSink;
import org.apache.flink.connector.jdbc.JdbcStatementBuilder;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

/**
 * ClassName: MysqlSinkFunction
 * Package: org.lei.function
 * Description:
 *
 * @Author Lei
 * @Create 17/4/2024 4:49 pm
 * @Version 1.0
 */
public class MysqlSinkFunction {
    public static SinkFunction getSinkFunction(){

        //write into mysql
        SinkFunction<Map<String, Object>> jdbcSink = JdbcSink.sink(
                "insert into audience.clamResult values(?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?,?,?,?,?)",

                new JdbcStatementBuilder<Map<String, Object>>() {

                    @Override
                    public void accept(PreparedStatement preparedStatement, Map<String, Object> result) throws SQLException {
                        preparedStatement.setString(1, (String) result.get("windowStart"));
                        preparedStatement.setString(2, (String)result.get("windowEnd"));
                        preparedStatement.setString(3, (String)result.get("event_name"));
                        preparedStatement.setString(4, (String)result.get("reauid"));
                        preparedStatement.setString(5, (String)result.get("my_rea_id"));
                        preparedStatement.setString(6, (String)result.get("rcauid"));
                        preparedStatement.setString(7, (String)result.get("my_rca_id"));
                        preparedStatement.setString(8, (String)result.get("locke_id"));
                        preparedStatement.setString(9, (String)result.get("user_country_code"));
                        preparedStatement.setString(10, (String)result.get("site"));
                        preparedStatement.setString(11, (String)result.get("site_section"));
                        preparedStatement.setString(12, (String)result.get("site_sub_section"));
                        preparedStatement.setString(13, (String)result.get("page_name"));
                        preparedStatement.setString(14, (String)result.get("page_type"));
                        preparedStatement.setString(15, (String)result.get("platform"));
                        preparedStatement.setString(16, (String)result.get("rendered_on"));
                        preparedStatement.setString(17, (String)result.get("state"));
                        preparedStatement.setString(18, (String)result.get("suburb"));
                        preparedStatement.setString(19, (String)result.get("postcode"));
                        preparedStatement.setString(20, (String)result.get("listing_id"));
                        preparedStatement.setString(21, (String)result.get("parent_listing_id"));
                        ObjectMapper objectMapper = new ObjectMapper();
                        String jsonData;
                        try {
                            jsonData = objectMapper.writeValueAsString(result.get("property_view"));
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                        preparedStatement.setString(22, jsonData);
                        preparedStatement.setInt(23, result.get("_3d_tour_played") == null ? 0:(int)result.get("_3d_tour_played"));
                    }
                },
                JdbcExecutionOptions.builder()
                        .withBatchIntervalMs(3000)
                        .withBatchSize(100)
                        .withMaxRetries(3)
                        .build(),
                new JdbcConnectionOptions.JdbcConnectionOptionsBuilder()
                        .withUrl("jdbc:mysql://localhost:3306")
                        .withUsername("root")
                        .withPassword("19820824")
                        .withConnectionCheckTimeoutSeconds(60)
                        .build()
        );
        return jdbcSink;
    }
}
