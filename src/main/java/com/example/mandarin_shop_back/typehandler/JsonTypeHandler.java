package com.example.mandarin_shop_back.typehandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JsonTypeHandler extends BaseTypeHandler<List<String>> {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
        try {
            ps.setString(i, mapper.writeValueAsString(parameter));
        } catch (JsonProcessingException e) {
            throw new SQLException("Error converting List<String> to JSON string", e);
        }
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        try {
            return mapper.readValue(rs.getString(columnName), new TypeReference<List<String>>() {});
        } catch (JsonProcessingException e) {
            throw new SQLException("Error converting JSON string to List<String>", e);
        }
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        try {
            return mapper.readValue(rs.getString(columnIndex), new TypeReference<List<String>>() {});
        } catch (JsonProcessingException e) {
            throw new SQLException("Error converting JSON string to List<String>", e);
        }
    }

    @Override
    public List<String> getNullableResult(java.sql.CallableStatement cs, int columnIndex) throws SQLException {
        try {
            return mapper.readValue(cs.getString(columnIndex), new TypeReference<List<String>>() {});
        } catch (JsonProcessingException e) {
            throw new SQLException("Error converting JSON string to List<String>", e);
        }
    }
}
