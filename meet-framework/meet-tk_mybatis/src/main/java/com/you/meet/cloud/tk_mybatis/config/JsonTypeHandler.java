package com.you.meet.cloud.tk_mybatis.config;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.Alias;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author zhoujunlin
 * @date 2023年04月21日 9:37
 * @desc
 */
@Slf4j
@Alias(value = "JsonTypeHandler")
@MappedTypes({JSON.class})
@MappedJdbcTypes({JdbcType.VARCHAR, JdbcType.JAVA_OBJECT, JdbcType.ARRAY, JdbcType.OTHER})
public class JsonTypeHandler extends BaseTypeHandler<JSON> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, JSON parameter, JdbcType jdbcType) throws SQLException {
        try {
            ps.setString(i, JSONObject.toJSONString(parameter));
        } catch (Exception e) {
            log.error("JsonTypeHandler.setNonNullParameter error", e);
            if (jdbcType == JdbcType.ARRAY) {
                ps.setString(i, "[]");
            } else {
                ps.setString(i, "{}");
            }
        }
    }

    @Override
    public JSON getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String json = rs.getString(columnName);
        return convertStr2JSON(json);
    }

    @Override
    public JSON getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String json = rs.getString(columnIndex);
        return convertStr2JSON(json);
    }

    @Override
    public JSON getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String json = cs.getString(columnIndex);
        return convertStr2JSON(json);
    }

    private JSON convertStr2JSON(String json) {
        try {
            if (JSONUtil.isTypeJSONArray(json)) {
                return JSONObject.parseArray(json);
            } else if (JSONUtil.isTypeJSONObject(json)) {
                return JSONObject.parseObject(json);
            }
        } catch (Exception e) {
            log.error("JsonTypeHandler.convertStr2JSON error", e);
        }
        return null;
    }

}
