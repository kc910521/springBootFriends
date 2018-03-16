package ind.vmcomp;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

/**
 * @author BJQXDN0626
 * @create 2018/3/15
 */
public class CiPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    public static final String  CKEY = "yoga";

    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        String ci = html.xpath("div[@id='content_left']/div[@srcid='28239']//div[@class='op_exactqa_detail_s_answer']/span//allText()").toString();
        System.out.println("orgci：" + ci);
        page.putField(CiPageProcessor.CKEY, ci);
    }

    @Override
    public Site getSite() {
        site.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36");
        site.addCookie("BAIDUID", "21D1233F6A84F0150638E5CFA8CD7ED0:FG=1");
        site.addHeader("Referer", "https://www.baidu.com/s?");
        return site;
    }

//    public static void main(String[] args) {
//        Spider.create(new CiPageProcessor())
//                .addUrl("https://www.baidu.com/s?wd=%E4%BC%91%E7%AC%91%E5%B1%B1%E7%BF%81%E4%B8%8D%E4%BD%8F%E5%B1%B1%20%E4%BA%8C%E5%B9%B4%E5%81%B7%E5%90%91%E6%AD%A4%E4%B8%AD%E9%97%B2")
//                .addPipeline(new CiPipeLine())
//                .thread(1)
//                .run();
//    }
}
