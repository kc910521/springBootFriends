package ind.vmcomp;

import ind.service.CiService;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author BJQXDN0626
 * @create 2018/3/15
 */
public class CiPipeLine implements Pipeline {

    public static List<String> missCiIds = new LinkedList<>();

    private final int orgNumber;

    private final String id;

    private final CiService ciService;

    public CiPipeLine(String id, int ogNum, CiService ciService){
        this.id = id;
        this.orgNumber = ogNum;
        this.ciService = ciService;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        String resT = resultItems.get(CiPageProcessor.CKEY);
        if (StringUtils.isBlank(resT)){
            return;
        }
        String[] res = resT.trim().split("。|，|,|\\.");
        int count = countChar(Arrays.asList(res));
        if (count - 4 > orgNumber){
            //missing pars
            CiPipeLine.missCiIds.add(id);
            if (this.ciService != null){
                String[] resSentSpd = resT.trim().split("。");
                List<String> pars = new LinkedList<>();
                for (String as : resSentSpd){
                    pars.add(as + "。");
                }
                ciService.putParasData(id, pars);
            }

        }
    }

    public final static int countChar(List<String> strs){
        if (strs != null){
            int totalCount = 0;
            for (String str : strs){
                totalCount += clearChar(str).length();
            }
            return totalCount;
        }
        return 0;
    }

    public final static String clearChar(String orgChar){
        return orgChar.trim().replaceAll("。|，|,|\\.|\\?|！|？", "");
    }

}
