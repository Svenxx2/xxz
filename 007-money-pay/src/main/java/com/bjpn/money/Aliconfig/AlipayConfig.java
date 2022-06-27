package com.bjpn.money.Aliconfig;


import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2088621959263092";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCeJltCzoEyXnMK/bDEIk7U8AISRH43rpVn9x4LAYq8IhsEr9YVx2arK8bj0O090OXUs/aSL6V51Q5vIpfXNagYJFdgUlT5wyJTgwHaq1iTXIWe5m5DHEVd4+er9XiA3+vSrylUbKdSaBt8Ji0KhGMRSycg0epW3A9sB4S7PVhmDK1uGLNoIe3IeKzCsHl1VitaMQUc4HaLjj3WBGCnmrlQ/4JI9k0K7sqYMTOpLE+psPbKR2bZFkjZ6pdTFfCFrEt9x7BSLYaF+RxUhPHMjahZ8000+Iuni3Z8PmoyuOmbLxHE7hqd+Yal1aXEPWNvgKl4rKgikHaHpEMHhDrkhmQzAgMBAAECggEAKiwIQfRXN1tASjoEHQkZBGdL5hslnSuM48Bd3ol2uVxO4B0TdZNKqXJvAuj/mnBOGT40Dh9ufOJ3iuMqolTIkkd+lUzfJAsI6EQTBoCSihkt+IVx+atcRAE2EgDPNW6AsBnNZ5nhtHPWgHCdqiRLVp03veS8HJVp92C54EQngm6LQZumayYdQD+uMOg1iCTu9iRaCZeOr7pGE0bbgIzMqS8OkZwDD+qCMEfIzE+d6sORmllQzUxkN8VvkaWgUNb9li1hS/iudzOED83ep6g9TFfrojDNgAOwjSftMHO4IOQxy4AgRIa5JAPSSGzy5cnsKv+YFuMg7T81ByQA/L8zoQKBgQDcKtWEk/NUmFudME50Z52/rvJ9wKRkFdCuIgc4OkRM0SdO2o8FjTCqe3UJDcj1TB2eN2Lv/33CT9bB9QOYWK7GlNU51EpDlFVQ711Aa3Bj48+Q5BPDN/H5T/SqUq/3w35SNFbaV9NlAVk7B3+OkiW3+dPnZNjGXzSGaOnndwAyzQKBgQC345fpD2nU63xMy2s98Z4Q61tazMWH8+F0FREKFXDzD+7ScoaeGFnW0/170VkB+E1g7Lve4DMTdQSMyDpG+Kzqfvbse0fVMqaeK4c5h+uQr0Bjg6FYVRjsjI1VWDMB1HaGQ4K/EDK0cMqbbQFmqqirdh6cb0VQaKd8Towk5Iby/wKBgQC3FENjMKhaQyuDhaTntTX/LFpm6aeJtn5GHvPD9OfQvOej8aiWpDbAvVKwCa9/EyFZ7JKwIdOQnlVmCflIWQ5VAXRh1z89/P4JBFKV/AGEfu6efJpWbpIfkDZS39mF/SFw+Cb0/61Aw6NQq8aNZshIuU1VkI2WP5rs3hcLK/CVTQKBgG7N40F6GilEZDfZ8xr2Nmq3huqlrPkIyXcrIoNS/CZaGWwRunAU+D1sq7cqJFn9hpK601GvAtfckQtLs2IEgh0iuCMy/cvHo4J3dBoEfrtF/qR4MoaJcGVJI/ijFskpfunUUDpUxljYBh21hzyX5N68ZCGbIsca6KEMq4GnenF9AoGBAK0S7hb/trDR1iqjFdID+vXWrYF7WoBmgMbCV0sxoKosaZmAecC1JAjwcHMmFeJq+ktHt6OMwrK2iHQr6XoEkzXJHm9G/4lvAeEdZ/MYnPPBX8+6exWnPJRiw0A0FGuauYiDnYD6Lg+KnC7DWbBhGcfjRV1/3h1s+GSC1voqZdcU";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAniZbQs6BMl5zCv2wxCJO1PACEkR+N66VZ/ceCwGKvCIbBK/WFcdmqyvG49DtPdDl1LP2ki+ledUObyKX1zWoGCRXYFJU+cMiU4MB2qtYk1yFnuZuQxxFXePnq/V4gN/r0q8pVGynUmgbfCYtCoRjEUsnINHqVtwPbAeEuz1YZgytbhizaCHtyHiswrB5dVYrWjEFHOB2i4491gRgp5q5UP+CSPZNCu7KmDEzqSxPqbD2ykdm2RZI2eqXUxXwhaxLfcewUi2GhfkcVITxzI2oWfNNNPiLp4t2fD5qMrjpmy8RxO4anfmGpdWlxD1jb4CpeKyoIpB2h6RDB4Q65IZkMwIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://localhost:8080/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://localhost:9005/005-money-web/loan/page/payBack";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydevz.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

