package utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tanglei
 * @date 18/10/11
 */
public class JSONUtil {

    public static String toJSONString(Object object) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("内部错误");
        }

    }

    public static String toJSONString(Object[] array) {
        if (array == null || array.length < 1)
            return null;

        return toJSONString(array);
    }

    public static Object toJSONObject(String jsonString, Class<?> classzz) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonString, classzz);
        } catch (Exception e) {
            throw new RuntimeException("内部错误");
        }
    }

    public static Object jsonToBean(Class<?> classZZ, JSONObject jsonObject) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonObject.toString(), classZZ);
        } catch (IOException e) {
            throw new RuntimeException("Json parse error.");
        }
    }

    /**
     * 将一个json字串转为list
     */
    public static <T> List<T> converListFormString(String jsonStr, Class<T> clazz) {
        if (StringUtils.isBlank(jsonStr)) {
            return new ArrayList<>();
        }
        JSONArray jsonArray = JSONArray.fromObject(jsonStr);

        List<T> list = (List<T>) JSONArray.toCollection(jsonArray, clazz);
        return list;
    }
}
