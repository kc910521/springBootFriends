package ind.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ind.domains.Ci;
import ind.repository.CiRepository;
import ind.vmcomp.CiPageProcessor;
import ind.vmcomp.CiPipeLine;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * @author BJQXDN0626
 * @create 2018/3/13
 */
@Service
public class CiService {

    @Autowired
    private CiRepository ciRepository;

    int a = 1;

    public void putParasData(String id, List<String> parContent) {
        Optional<Ci> ciOptional = ciRepository.findById(id);
        if (ciOptional.isPresent()) {
            Ci ci = ciOptional.get();
            ciRepository.deleteById(id);
            ci.setParagraphs(parContent);
            ciRepository.save(ci);
        }
    }

    public List<Ci> findByParas(@NotNull String str) {
        //        List<String> strings = new LinkedList<>();
        //        strings.add(str);
        return ciRepository.findAllByParagraphsMatches(str);
    }

    public void outputJSONFromES() {
        Iterable<Ci> ciIterable = ciRepository.findAll(Sort.by(Sort.Order.asc("id.keyword")));
        JSONArray jsonArray = new JSONArray();
        //FIX:keyouhua
        ciIterable.forEach(ci -> {
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(ci);
//            jsonObject.remove("id");
            jsonArray.add(jsonObject);
        });
        WriteConfigJson(jsonArray.toJSONString());
    }

    // justcopy
    private void WriteConfigJson(String args) {
        String src = "C:\\Users\\BJQXDN0626\\Downloads\\chinese-poetry-master\\chinese-poetry-master\\ci\\cixx\\ci.0.json";

        File file = new File(src);

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            file.delete();
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileWriter fw = null;
        try {
            fw = new FileWriter(file, true);
            fw.write(args);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public String verifyCi(CiService ciService) {
        CiPipeLine.missCiIds.clear();
        Iterable<Ci> iterator = ciRepository.findAll();
        iterator.forEach(ci -> {
            // TODO:可优化少去一次异常字符
            String url = assembleUrl(ci.getParagraphs());
            int ctc = CiPipeLine.countChar(ci.getParagraphs());
            Spider.create(new CiPageProcessor()).addUrl(url).addPipeline(new CiPipeLine(ci.getId(), ctc, ciService)).thread(5).run();

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

    private static String assembleUrl(List<String> pas) {
        StringBuilder stringBuilder = new StringBuilder();
        if (pas != null) {
            pas.stream().limit(3).forEach(s -> {
                stringBuilder.append(CiPipeLine.clearChar(s) + "%20");
            });
        }

        return "https://www.baidu.com/s?wd=" + stringBuilder;
    }

    public int loadFromFiles(String absDirPath) throws IOException {
        Collection<File> files = FileUtils.listFiles(new File(absDirPath), FileFilterUtils.suffixFileFilter("json"), null);
        if (files != null && !files.isEmpty()) {
            files.stream().forEach(file -> {
                if (file.isFile() && file.exists() && file.getName().startsWith("ci.song")) {
                    System.out.println(file.getName());
                    ObjectMapper mapper = new ObjectMapper();
                    List<Ci> cis = null;
                    try {
                        cis = mapper.readValue(file, new TypeReference<List<Ci>>() {
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    cis.stream().forEach(ci -> {
                        ci.setId("ci_" + String.format("%06d", a++));
                        System.out.println(a);
                    });
                    ciRepository.saveAll(cis);
                }
            });
            return 0;
        }
        return -1;
    }

}
