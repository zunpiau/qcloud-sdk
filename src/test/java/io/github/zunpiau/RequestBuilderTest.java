package io.github.zunpiau;

import io.github.zunpiau.constant.SignatureMethod;
import io.github.zunpiau.module.cvm;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/*
  @see https://cloud.tencent.com/document/api/377/4214
 */
public class RequestBuilderTest {

    private static RequestBuilder builder;
    private static String signatureText;

    @BeforeClass
    public static void init() {
        builder = new RequestBuilder("AKIDz8krbsJ5yKBZQpn74WFkmLPx3gnPhESA",
                "Gu5t9xGARNpq86cd98joQYCN3Cozk1qA",
                "ap-guangzhou",
                "DescribeInstances",
                new cvm()) {
            @Override
            protected String getRandomNumber() {
                return "11886";
            }

            @Override
            protected String getTimeStamp() {
                return "1465185768";
            }
        };
        builder.GET()
                .signatureMethod(SignatureMethod.HmacSHA256)
                .addModuleParameter("InstanceIds.0", "ins-09dx96dg");

        signatureText = ("GETcvm.api.qcloud.com/v2/index.php?Action=DescribeInstances\n" +
                         "&InstanceIds.0=ins-09dx96dg\n" +
                         "&Nonce=11886\n" +
                         "&Region=ap-guangzhou\n" +
                         "&SecretId=AKIDz8krbsJ5yKBZQpn74WFkmLPx3gnPhESA\n" +
                         "&SignatureMethod=HmacSHA256\n" +
                         "&Timestamp=1465185768\n").replace("\n", "");
    }

    @Test
    public void sign() {
        assertEquals("0EEm/HtGRr/VJXTAD9tYMth1Bzm3lLHz5RCDv1GdM8s=", builder.sign(signatureText));
    }

    @Test
    public void completeAndBuildSignature() {
        assertEquals(signatureText, builder.completeAndBuildSignature());
    }
}