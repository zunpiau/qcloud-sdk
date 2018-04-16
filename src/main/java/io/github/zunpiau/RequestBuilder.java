package io.github.zunpiau;

import io.github.zunpiau.constant.CommonParameter;
import io.github.zunpiau.constant.RequestMethod;
import io.github.zunpiau.constant.SignatureMethod;
import io.github.zunpiau.module.Module;
import io.github.zunpiau.util.Encoder;
import io.github.zunpiau.util.Signer;

import java.time.Instant;
import java.util.Random;
import java.util.TreeMap;

@SuppressWarnings("WeakerAccess")
public class RequestBuilder {

    private final TreeMap<String, String> moduleParameter;
    private final TreeMap<String, String> commonParameter;
    private final String secretKey;
    private final Module module;
    private SignatureMethod signatureMethod = SignatureMethod.HmacSHA1;
    private RequestMethod requestMethod = RequestMethod.POST;

    public RequestBuilder(String secretId, String secretKey, String region, String action, Module module) {
        this.module = module;
        this.secretKey = secretKey;
        moduleParameter = new TreeMap<>(String::compareToIgnoreCase);
        commonParameter = new TreeMap<>(String::compareToIgnoreCase);
        commonParameter.put(CommonParameter.SECRET_ID, secretId);
        commonParameter.put(CommonParameter.REGION, region);
        commonParameter.put(CommonParameter.ACTION, action);
    }

    public RequestBuilder addModuleParameter(String key, String value) {
        moduleParameter.put(key, value);
        return this;
    }

    public RequestBuilder POST() {
        this.requestMethod = RequestMethod.POST;
        return this;
    }

    public RequestBuilder GET() {
        this.requestMethod = RequestMethod.GET;
        return this;
    }

    //为 HmacSHA1 时不需要添加到请求参数中，默认为HmacSHA1
    public RequestBuilder signatureMethod(SignatureMethod method) {
        if (method.equals(SignatureMethod.HmacSHA256))
            commonParameter.put(CommonParameter.SIGNATURE_METHOD, method.toString());
        this.signatureMethod = method;
        return this;
    }

    public RequestBuilder action(String action){
        commonParameter.put(CommonParameter.ACTION, action);
        return this;
    }

    public String getRequestParameter() {
        build();
        StringBuilder builder = new StringBuilder();
        commonParameter.forEach((k, v) -> builder.append(Encoder.urlEncode(k)).append('=')
                .append(Encoder.urlEncode(v)).append('&'));
        moduleParameter.forEach((k, v) -> builder.append(Encoder.urlEncode(k)).append('=')
                .append(Encoder.urlEncode(v)).append('&'));
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    public String getSignature() {
        return sign(completeAndBuildSignature());
    }

    protected String sign(String plainText) {
        byte[] hash = Signer.getInstance().hash(signatureMethod, secretKey, plainText);
        return Encoder.Base64Encode(hash);
    }

    protected String completeAndBuildSignature() {
        commonParameter.put(CommonParameter.TIMESTAMP, getTimeStamp());
        commonParameter.put(CommonParameter.NONCE, getRandomNumber());
        StringBuilder builder = new StringBuilder();
        builder.append(requestMethod)
                .append(module.getRequestUrl())
                .append('?');
        builder.append(module.buildParameter(commonParameter, moduleParameter));
        System.out.println(builder.toString());
        return builder.toString();
    }

    protected String getTimeStamp() {
        return String.valueOf(Instant.now().getEpochSecond());
    }

    protected String getRandomNumber() {
        return String.valueOf(new Random().nextInt(8999) + 1000);
    }

    private void build() {
        commonParameter.put(CommonParameter.SIGNATURE, getSignature());
    }

}
