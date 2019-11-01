package com.fh.shop.api.timmer;

import com.fh.shop.api.product.mapper.IProductMapper;
import com.fh.shop.api.product.po.Shop;
import com.fh.shop.api.utils.emailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.List;

@Component
public class SendEmailTimmer {

    @Autowired
    private IProductMapper productMapper;

    @Value("${}")
    private String people;

    @Scheduled(fixedRate = 30000)
    public void sendEmain() throws MessagingException {
        String title="库存预警提示";
        String content="<h1>以下商品库存告急</h1>\n" +
                "<table border=\"1\" cellspacing=\"0\" align=\"center\">\n" +
                "    <tr>\n" +
                "        <td>商品名称</td>\n" +
                "        <td>商品价格</td>\n" +
                "        <td>当前库存</td>\n" +
                "    </tr>";
        List<Shop> shopList = productMapper.selectList(null);
        for (Shop shop : shopList) {
            if (shop.getStock()<=10){
                content+="<tr>\n" +
                        "        <td>"+shop.getShopName()+"</td>\n" +
                        "        <td>"+shop.getPrice()+"</td>\n" +
                        "        <td>"+shop.getStock()+"</td>\n" +
                        "    </tr>";
            }
        }
        content+="</table>\n" +
                "<p><h2>发件人：王鹏飞</h2></p>";
        emailUtil.sendEmail(title,content);
        System.out.println("发送成功");
    }

    @Scheduled(fixedRate = 10000)
    public void test1(){
        System.out.println(new Date());
    }
}
