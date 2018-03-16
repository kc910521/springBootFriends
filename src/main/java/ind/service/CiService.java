package ind.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ind.domains.Ci;
import ind.repository.CiRepository;
import ind.vmcomp.CiPageProcessor;
import ind.vmcomp.CiPipeLine;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author BJQXDN0626
 * @create 2018/3/13
 */
@Service
public class CiService {

    @Autowired
    private CiRepository ciRepository;

    int a = 1;

    public List<Ci> findByParas(@NotNull String str){
//        List<String> strings = new LinkedList<>();
//        strings.add(str);
        return ciRepository.findAllByParagraphsMatches(str);
    }

    public String verifyCi(){
        Iterable<Ci> iterator = ciRepository.findAll();
        iterator.forEach(ci -> {
            // TODO:可优化少去一次异常字符
            String url = assembleUrl(ci.getParagraphs());
            int ctc = CiPipeLine.countChar(ci.getParagraphs());
            Spider.create(new CiPageProcessor())
                    .addUrl(url)
                    .addPipeline(new CiPipeLine(ci.getId() , ctc))
                    .thread(5)
                    .run();

        });
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(CiPipeLine.missCiIds);
        System.out.println("丢失的数量：" + CiPipeLine.missCiIds.size());
        return CiPipeLine.missCiIds.toString();
    }

    private static String assembleUrl(List<String> pas){
        StringBuilder stringBuilder = new StringBuilder();
        if (pas != null){
            pas.stream().limit(3).forEach(s -> {
                stringBuilder.append(CiPipeLine.clearChar(s) + "%20");
            });
        }

        return "https://www.baidu.com/s?wd=" + stringBuilder;
    }

    public int loadFromFiles(String absDirPath) throws IOException {
        Collection<File> files = FileUtils.listFiles(new File(absDirPath),
                FileFilterUtils.suffixFileFilter("json"), null);
        if (files != null && !files.isEmpty()){
            files.stream().forEach(file -> {
                if (file.isFile() && file.exists() && file.getName().startsWith("ci.song")){
                    System.out.println(file.getName());
                    ObjectMapper mapper = new ObjectMapper();
                    List<Ci> cis = null;
                    try {
                        cis = mapper.readValue(file, new TypeReference<List<Ci>> (){});

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    cis.stream().forEach(ci -> {
                        ci.setId("ci_"+(a++));
                        System.out.println(a);
                    });
                    ciRepository.saveAll(cis);
                }
            });
            return 0;
        }
        return -1;
    }

//    public static void main(String[] args) {
//        List<String> sts = new LinkedList<>();
//        sts.add("罗绮满城春欲暮。");
//        sts.add("百花洲上寻芳去。");
//        sts.add("海霞红，");
//        sts.add("山烟翠！");
//        System.out.println(assembleUrl(sts));
//    }

}
