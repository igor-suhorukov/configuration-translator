package com.github.igorsuhorukov.format;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.igorsuhorukov.dom.transform.DomTransformer;
import com.github.igorsuhorukov.dom.transform.converter.TypeAutoDetect;
import org.w3c.dom.Node;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class Translate {

    private static final TypeReference<Map<String, Object>> MAP_TYPE = new TypeReference<Map<String, Object>>() {};

    public Map<String, Object> translate(Node node){
        if(node == null){
            return null;
        }
        return new DomTransformer(new TypeAutoDetect()).transform(node);
    }

    public Node translate(Map<String, Object> objectTree){
        if(objectTree==null){
            return null;
        }
        //detect cyclic dependency
        Map<String, Object> root = objectTree.size() == 1 ? objectTree : Collections.singletonMap("root", objectTree);
        return new DomTransformer(new TypeAutoDetect()).transform(root);
    }

    public Map<String, Object> yamlToMap(String yaml) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        return mapper.readValue(yaml, MAP_TYPE);
    }

    public Map<String, Object> properiesToMap(String properties) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new JavaPropsFactory());
        return mapper.readValue(properties, MAP_TYPE);
    }

    public Map<String, Object> jsonToMap(String json) throws IOException{
        return new ObjectMapper().readValue(json, MAP_TYPE);
    }
}
