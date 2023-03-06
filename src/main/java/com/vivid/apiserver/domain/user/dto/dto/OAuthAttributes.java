package com.vivid.apiserver.domain.user.dto.dto;

import java.util.HashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String attributeKey;
    private String name;
    private String email;
    private String picture;

    public static OAuthAttributes of(Map<String, Object> attributes, String attributeKey) {

        return ofGoogle(attributes, attributeKey);
    }

    public static OAuthAttributes ofGoogle(Map<String, Object> attributes, String attributeKey) {

        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .attributeKey(attributeKey)
                .build();
    }

    public Map<String, Object> convertToMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", attributeKey);
        map.put("key", attributeKey);
        map.put("name", name);
        map.put("email", email);
        map.put("picture", picture);

        return map;
    }
}
