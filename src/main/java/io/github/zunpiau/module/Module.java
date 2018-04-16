package io.github.zunpiau.module;

import io.github.zunpiau.util.Encoder;

import java.util.TreeMap;

@SuppressWarnings("unchecked")
public abstract class Module {

    abstract public String getRequestUrl();

    //机器翻译 API 要求公共参数和接口参数分别排序再拼接，CVM API 不区分
    abstract public String buildParameter(TreeMap<String, String>... parameters);

    String buildParameterMixed(TreeMap<String, String>... parameters) {
        TreeMap<String, String> merge = new TreeMap<>();
        for (TreeMap<String, String> parameter : parameters) {
            merge.putAll(parameter);
        }
        StringBuilder builder = new StringBuilder();
        merge.forEach((k, v) -> builder.append(Encoder.replaceUnderscores(k)).append('=').append(v).append('&'));
        return deleteLastChar(builder).toString();
    }

    String buildParameterSeparately(TreeMap<String, String>... parameters) {
        StringBuilder builder = new StringBuilder();
        for (TreeMap<String, String> parameter : parameters) {
            parameter.forEach((k, v) -> builder.append(Encoder.replaceUnderscores(k)).append('=').append(v).append('&'));
        }
        return deleteLastChar(builder).toString();
    }

    private StringBuilder deleteLastChar(StringBuilder builder) {
        if (builder.length() > 0)
            builder.deleteCharAt(builder.length() - 1);
        return builder;
    }

}
