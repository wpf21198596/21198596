package com.fh.shop.api.timmer;

import com.fh.shop.api.product.mapper.IProductMapper;
import com.fh.shop.api.product.po.Shop;
import com.fh.shop.api.utils.emailUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SendEmailTimmer {

    @Autowired
    private IProductMapper productMapper;

    @Autowired
    Configuration configuration;

    @Value("${stock-attention-title}")
    private String title;

    @Value("${stock-attention-people}")
    private String people;

    /*@Scheduled(fixedRate = 10000)
    public void sendEmain() throws MessagingException {
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
        emailUtil.sendEmail(title,content,people);
        System.out.println("发送成功");
    }*/

    @Scheduled(cron = "30 19 1 * * ?")
    public void sendEmain() throws MessagingException, Exception {
        List<Shop> shopList = productMapper.selectList(null);
        // 创建一个模板使用的数据集，可以是pojo也可以是map。一般是Map。
        Map data=new HashMap();
        //向数据集中添加数据
        data.put("shopList",shopList);
        configuration.setClassForTemplateLoading(this.getClass(), "/ftl");
        Template template = configuration.getTemplate("stock.ftl");
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, data);
        emailUtil.sendEmail(title, content, people);
        System.out.println("发送成功");
    }

    /*private Template getTemplate() {
        try {
            //通过Freemarker的Configuration读取相应的ftl
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
            // 第三步：设置模板文件使用的字符集。一般就是utf-8.
            configuration.setDefaultEncoding("utf-8");
            //第二个参数 为对应存放.ftl文件的包名
            configuration.setClassForTemplateLoading(this.getClass(), "/ftl");
            // 第四步：加载一个模板，创建一个模板对象。
            Template template = configuration.getTemplate("aaa.ftl");
            return template;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    @Scheduled(fixedRate = 10000)
    public void test1(){
        System.out.println(new Date());
    }
}
