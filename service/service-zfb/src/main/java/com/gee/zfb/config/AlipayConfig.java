package com.gee.zfb.config;

public class AlipayConfig {
    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016102400750135";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC4RKaR8GPaCmsZwefw/5Uw4kBMFolwSNeaud3VBxYnh9M0qmd/OOIC7uBZHDRPxHcF1i+Z4/Nf2k2inV2cBZ/lX/7YqkVpFA4TYjH84Z5mlks1dPMW9Auq65RvufTvTPRwJG1MPA98cHZRYfZ1JYZi+fRNSWqFdzq0BPfyqiqkbVdlxkbt02GBkLrHLaRwL6jBNQhTUBXU6v6mrB5AR4KhWCxQExHE+RphXfN30pthhb+2oA0dRUjQlf1QWMoMRvziZdDUE0KyF1puJqjODcnyYHTpiPWZB8ncPRq3XSeaPUgwSKMttGIiseH1V8LH7xyNNcVRdQMPttE2La1VsoR3AgMBAAECggEBAJzBihJMtiUfB815r57NTlWvo5MW+QSUcxYlWBmJVcd7PKlo7XLFKT/lhuVHoRjawYYtl4EK8ldwlurnhGLeRhATgfE/qJyjevXyayP9q4sKACERxFqacfKNRlFlmICarrdXxcM8tM/fvEevVsKBkblRegrlkcDIlR/fui6tpGkLSZg3sAK9rORxaPsHBkQBAjwFG2SG7FmejGuc3PimTe9NrvqzbV/R67SiosoMiQ98/xQdnEHqnTuUneveEAKAB0AspNwWmISpxMBm5erVJ+VbWqLp4m2k1bD1HupJgR3a+0BAKiHm9mVpG63751trgZOJeX6AsgCQ6NPlCDnwCYECgYEA4pnKKlUPvfOwnUhfDPqda0M2hAqjjz9Lmipywoxgp99nNk1IntIKucNxxG+iRwMa5tUGzj+9drW9fZ46h6ch1a2OWEPtWXIlm0PyqPArQ/GwIL4InHNMlm669lLRUQYlasje1boqSj0h5bAcO4VRf9Vu4y29jFfUlJG0EOD6BykCgYEA0CzYxb8D2g7R6TPLwdYmB5SD5qZ7ceaSQC6ZV31fkb28qNStr5UBWk4kyyrLoIlxIqNi4JuZFvPvj5lzicRSsgYPsBCgzWPu6WYsVGuzfcNBInoEo9R6kf3S4mJgg1eJXkCbPFeUag0EZAxoIsJA6Lmi5ca7ifYPq6rp0J+Dwp8CgYA2UoXrlg67C9N4MSRQOThJwYgtKVvCeHsIT0rJptt5Aw58LxTMti/KDreUTurJOU5WrhT+lrs13SW7GpaPdJqg3gV01Xvt6HZpfWWL6F+G30bI4qWv09g1bAA4EuyJDv8jVVpORF3lBDi/FnP+NNyv15vIrGt3XIrivvc64KQv2QKBgQCAqYccyM8foZM5F6syVSHSA4d2MaUQI5p2kpZxjWpY0DTe//uNDoH0h2GUNBIAWZIT/3loKqL2nbONRCE1n6Igp0Bg6BYKe1444M47KwmXi+sg3NsrSnKq8n65LmZKJvsexr1rERIIbZ0uHMDr4rVtYPZ+H1LflLXgutSh/UTZLwKBgGS+faL6kcYc/FxfVQztajAALcom7T6Cswe10WgbBRrcLs5+SkmxoZxUu1Ukr7rcdpbru3v0nl+GrRoAdtkJaTDPX4Pp4Yhe0gu9JS2bXZ3jWsP/7cU4iifTq6aKmst3z0mzBDvkGagzkLPbpQZrv+rmUCOOBpbmytyGgFFZXbhr";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA43NeJv+NZlVcla5BTOvtiBLMEMdrkOdeU7YG/dyoxmduXoIWZn7cW5Bl8W4BaE3dK6VdfdDxgLhznYBFjuXLOoEfqFyiuFMFrSF4SwOVBXsbY0X/0STtQTE6eDxfQ0zp3CKWQyQl22uR6DhvKTBgwod609byi1gVYFqL+5Xxj3BJ188tnUnhFqdGh4Es3QlTa0Erf5XgURKNmJv0K+TkYSBxTAl0AYyrzoV+m9OOtf4kapBTi3quAVIUi2p00LMaSJrwLCDPOZNyEnOWeMgDQLNnKi2XpaFX/USM2dQJfzo8mKd/FZoFbBdPcDV8YckAZqsU46ukFWCllHUaQ1ejtQIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";

    // 签名方式
    public static String sign_type = "RSA2";

    //格式
    public static String format = "json";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";
}
