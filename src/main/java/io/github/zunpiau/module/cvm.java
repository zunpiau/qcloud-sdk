package io.github.zunpiau.module;

import java.util.TreeMap;

public class cvm extends Module {

    @Override
    public String getRequestUrl() {
        return "cvm.api.qcloud.com/v2/index.php";
    }

    @SafeVarargs
    @Override
    public final String buildParameter(TreeMap<String, String>... parameters) {
        return super.buildParameterMixed(parameters);
    }
}
