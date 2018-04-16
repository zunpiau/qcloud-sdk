package io.github.zunpiau.module;

import java.util.TreeMap;

public class tmt extends Module {

    @Override
    public String getRequestUrl() {
        return "tmt.api.qcloud.com/v2/index.php";
    }

    @Override
    @SuppressWarnings("unchecked")
    public final String buildParameter(TreeMap<String, String>... parameters) {
        return super.buildParameterSeparately(parameters);
    }

    public static class Parameter {

        public static final String PROJECT_ID = "projectId";
        public static final String SOURCE = "source";
        public static final String SOURCE_TEXT = "sourceText";
        public static final String TARGET = "target";
    }
}
