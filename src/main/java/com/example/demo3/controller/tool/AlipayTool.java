package com.example.demo3.controller.tool;

import com.alibaba.fastjson.JSONObject;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.AlipayApiException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//依赖有导入的话，注意target下WEB-INF/lib文件下有没有对应的包
public class AlipayTool {


    private static final String appid = "9021000124648758";
    private static final String url = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";
    private static final String privateKey="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDFIy5JoMtBT8yr87jFIfNsn864kYBndrJro8BgjPu6jK3Vxpoiwg4RD1WiBvHlGtd6P+yUU69qu9xg2VW6kbvmKTAXZ80DDt7j6oGaMrulNyhQKeJUUHsjL0HsjzYMCdhHuCdkRXemoADxzIDSgVmNIHIRBYDvauwwpfWoEOaBZFzeo/ht0/U4GWSM/iDW1kZNPTCBuUajAs2sKnKVa6tEQS8FgwNxBLcsD7hHIxp21TraeK2/w0Hlo7B8oZe5EyPbHDdEecj6T6qNEhTmi4typd5MiUulMzBt/y37Jo7hihzmpUKPvuoa+vvqRrHrGUDu8kwt6Iv4R1JC90V7+03nAgMBAAECggEAK1Yzi74MUJxBSx+146g2AhATeqGJz7TZPi5uUQtNH1JjuJYDNlbgjsGHOqo6LPo8mkRJNsY6zsTjimrcnLoCy3MMfmi0OdLgDsxzlzlF5Oj+NLkwZwaWO0LdRCqrX/QeFo4WIsXeqsOL0Zv6ScxjqlGSoclK8dbYe01QdpTYh2Ls/UVj2gJ+ONCxKBqaYylYokXx1vCUI9dcujsbvoRoxVZiYN/9p71VoGZMpZwH9SdKHlglA6Uma3ueEA88Qhb9xzoBuTt26VwGena0bzZw3hl9HCuntJV5OMo3cUjWmaUBZoy2jBwIleXyMiJA6SqRyAXSEomRcCGPwv3KyV47cQKBgQDinjOQ3RnMFWVy79US1E0ErGatb7Bd932ULuNlVl4rbCJSRm+X23/tTEwCIQl1ZFgZRmpK45hJNcLlXf8wDUMGfof6RWhGyFIGrHcjmU3oxUL7Q91dzz55vVrEBpLZkNeogHW8+RxYvhDf9h0+B2X1FMCrxxZL2gKwjhNESUCo3wKBgQDesnmEl8+ampM2qx0IuAjuZ4WtnKBsxq41Ntf1o0SYrBX7lslidJxF2qX3FDHRi4upV1EMs4JXVP0ASmmJvx+iSehSnf4DZded+CVmyvEev+U4Qr7NVO5WhMxIoRw/lgoORSINFM2p1Q9aBRsPE+G6XagyxwehIBSfESNflcGT+QKBgQCyNhrzJwzscz8OfhCf+BiBPlqYtaaKnuBAsgbdYGg52ZW8AewsDc+yezeizPw1Ny4Y4kkgj47os8CKw7q0vRkieErSw9ZXI51t5h7i/eJI1SfxlNL2vM2yyvggA9NkCQWvWyY77+Mc3otCtLcWWpcRfrpt2+hqyg0nbvMz1i9lHQKBgGJQvs8nynKBzX40GvRPczn1IBIedMCxEhXSKZN7Sfxw9J+G2U+LFbbNKbokAz50cesH7nNCCMgPcEahk2hJpWibYmKe95Z+bquB8tknvT2LEGI/LUcvOK2foSRrZDI6RiQ4pyjJR2az9jwSxvhoGZ6SXIBWV+rtY8053rkDP1vZAoGBAJVqJDlJhlWvDsxKZZoZkvIw3YOCjxdUtVV8DOJafuJ0/WqUEjyeM7F8zvkmZ2UiijHFLOgf13YS8qCmum+HIxB98NgDr3VJOqAb6GSsoqglXue0LVrfcVk7B5f6IyM3Fa1jwkZaKgEcSBHGcY5mu+nyvkQHc/uv6PsBu30NBZwT";
    private static final String publicKey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkYfyZ5C/RIspIiNb7GUNEJiXQ2gLosIOEI9H74Lf9XeeHaj8AwlTUb0NjRjzeZ62DpC1S7BvlY1aN5L7Ld+qHhf0usEDfa32cNAJISHfhNGKeElA6YdkYVg6kHdjMD6gCzHlXIhK8C9mN/vZBuJTLHLgwYLQY+9vw9Db3MYkWQ83Q8dJADMdQ0TqWSndevcgXzeVv2lGasT4Rm8HvMmvOMopCgG8gBl9JbuJv8vmg574JHz8K/b3kAXrE5Ozc3uNhuzzdJ4+S/TRl+aZWkKc+hMLqHqY45M3gXFPSpW6zq4iZeN+gyzzG9LvcXpKVZNZliaEQx2A/Fb+ytf26Bu9FwIDAQAB";

    public static String pay(String id, double price, String title) throws IOException {
        AlipayClient alipayClient = new DefaultAlipayClient(url,appid,privateKey,"json","UTF-8",publicKey,"RSA2");

        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
////异步接收地址，仅支持http/https，公网可访问
//        request.setNotifyUrl(notifyUrl);
////同步跳转地址，仅支持http/https
//        request.setReturnUrl(returnUrl);
/******必传参数******/
        JSONObject bizContent = new JSONObject();
//商户订单号，商家自定义，保持唯一性
        bizContent.put("out_trade_no", id);
//支付金额，最小值0.01元
        bizContent.put("total_amount", price);
//订单标题，不可使用特殊符号
        bizContent.put("subject", title);
//电脑网站支付场景固定传值FAST_INSTANT_TRADE_PAY
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
        request.setBizContent(bizContent.toString());

        AlipayTradePagePayResponse response = null;
        try {
            response = alipayClient.pageExecute(request);
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
        String form="";
        form = response.getBody();
        if(response.isSuccess()){
            System.out.println("调用成功\n"+form);
        } else {
            System.out.println("调用失败");
        }
        return form;
    }


}
