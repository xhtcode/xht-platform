package com.xht.auth.security.jackson2;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.xht.auth.security.web.authentication.form.XhtFormLoginToken;
import org.springframework.security.core.GrantedAuthority;

import java.io.IOException;
import java.util.List;

/**
 * XhtFormLoginToken的自定义Jackson反序列化器
 * 用于将JSON数据反序列化为XhtFormLoginToken对象，处理认证令牌的各种属性
 * @author xht
 */
class XhtFormLoginTokenDeserializer extends JsonDeserializer<XhtFormLoginToken> {

    private static final TypeReference<List<GrantedAuthority>> GRANTED_AUTHORITY_LIST = new TypeReference<>() {
    };

    private static final TypeReference<Object> OBJECT = new TypeReference<>() {
    };

    /**
     * 从JSON数据反序列化为XhtFormLoginToken对象
     *
     * @param jp JSON解析器，用于读取JSON数据
     * @param context 反序列化上下文，提供反序列化过程中的相关信息
     * @return 反序列化后的XhtFormLoginToken对象
     * @throws IOException 当JSON解析或读取发生错误时抛出
     */
    @Override
    public XhtFormLoginToken deserialize(JsonParser jp, DeserializationContext context)
            throws IOException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode jsonNode = mapper.readTree(jp);

        // 读取认证状态标识
        boolean authenticated = readJsonNode(jsonNode, "authenticated").asBoolean();

        // 读取并解析主体信息（principal）
        JsonNode principalNode = readJsonNode(jsonNode, "principal");
        Object principal = getPrincipal(mapper, principalNode);

        // 读取并解析凭证信息（credentials）
        JsonNode credentialsNode = readJsonNode(jsonNode, "credentials");
        Object credentials = getCredentials(credentialsNode);

        // 解析权限列表
        List<GrantedAuthority> authorities = mapper.readValue(readJsonNode(jsonNode, "authorities").traverse(mapper),
                GRANTED_AUTHORITY_LIST);

        // 根据认证状态创建相应的token对象
        XhtFormLoginToken token = (!authenticated)
                ? XhtFormLoginToken.unauthenticated(principal, credentials)
                : XhtFormLoginToken.authenticated(principal, credentials, authorities);

        // 设置详细信息（details），如果不存在则设置为null
        JsonNode detailsNode = readJsonNode(jsonNode, "details");
        if (detailsNode.isNull() || detailsNode.isMissingNode()) {
            token.setDetails(null);
        } else {
            Object details = mapper.readValue(detailsNode.toString(), OBJECT);
            token.setDetails(details);
        }
        return token;
    }

    /**
     * 从JSON节点获取凭证信息
     *
     * @param credentialsNode 凭证对应的JSON节点
     * @return 凭证对象，如果节点为空或不存在则返回null，否则返回文本内容
     */
    private Object getCredentials(JsonNode credentialsNode) {
        if (credentialsNode.isNull() || credentialsNode.isMissingNode()) {
            return null;
        }
        return credentialsNode.asText();
    }

    /**
     * 从JSON节点获取主体信息
     *
     * @param mapper ObjectMapper实例，用于对象映射
     * @param principalNode 主体对应的JSON节点
     * @return 主体对象，如果节点是对象类型则反序列化为Object，否则作为文本返回
     * @throws IOException 当JSON解析发生错误时抛出
     */
    private Object getPrincipal(ObjectMapper mapper, JsonNode principalNode)
            throws IOException {
        if (principalNode.isObject()) {
            return mapper.readValue(principalNode.traverse(mapper), Object.class);
        }
        return principalNode.asText();
    }

    /**
     * 安全地读取JSON节点中的指定字段
     *
     * @param jsonNode 源JSON节点
     * @param field 要读取的字段名
     * @return 对应字段的JSON节点，如果字段不存在则返回MissingNode实例
     */
    private JsonNode readJsonNode(JsonNode jsonNode, String field) {
        return jsonNode.has(field) ? jsonNode.get(field) : MissingNode.getInstance();
    }

}
