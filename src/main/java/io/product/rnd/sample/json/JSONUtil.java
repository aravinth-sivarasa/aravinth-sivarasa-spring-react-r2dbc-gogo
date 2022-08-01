package io.product.rnd.sample.json;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSONUtil {
    private static final Logger LOG = LoggerFactory.getLogger(JSONUtil.class);
    private static ObjectMapper om = new ObjectMapper();

    private JSONUtil() {

    }

    public static synchronized String toString(Object obj) {
        try {
            return om.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOG.error(e.getMessage());
            throw new UnableToProcessJSONException();
        }

    }

    public static synchronized <T> List<T> getArrayOfMetaData(String version, Class<T> type) {
        return getArray("/data/meta/" + version + "-" + type.getSimpleName() + ".json", type);

    }

    public static synchronized <T> List<T> getArray(String fileName, Class<T> type) {
        InputStream inputStream = TypeReference.class.getResourceAsStream(fileName);
        try {
            if (inputStream == null)
                throw new UnableToProcessJSONException();
            CollectionType collectionType = om.getTypeFactory().constructCollectionType(List.class, type);
            return om.readValue(inputStream, collectionType);
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
        throw new UnableToProcessJSONException();
    }
}
