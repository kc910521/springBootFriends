package ind.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ind.domains.Ci;
import ind.repository.CiRepository;
import ind.util.JsonFormatTool;
import ind.vmcomp.CiPageProcessor;
import ind.vmcomp.CiPipeLine;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QPageRequest;
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

    public void outputJSONFromES(String fromFileIndex, int page, int pageSize) {
//        QPageRequest qPageRequest = new QPageRequest(page ,pageSize);
//        qPageRequest.getSortOr(Sort.by(Sort.Order.asc("id.keyword")));
//        Page<Ci> pag = ciRepository.findAll(qPageRequest);
        Page<Ci> cis = ciRepository.findByFromFileIndexEqualsOrderByOCodeAsc(fromFileIndex, new QPageRequest(page ,pageSize));
//        Iterable<Ci> ciIterable = ciRepository.findAll();
        JSONArray jsonArray = new JSONArray();
        //FIX:keyouhua
        cis.getContent().stream().forEachOrdered(ci -> {
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(ci);
            jsonObject.remove("id");
            jsonObject.remove("fromFileIndex");
            jsonObject.remove("oCode");
            jsonArray.add(jsonObject);
        });
        WriteConfigJson(JsonFormatTool.formatJson(jsonArray.toJSONString()), fromFileIndex );
    }

    // justcopy
    private void WriteConfigJson(String args, String namePart) {
        String src = "D:\\repo\\chinese-poetry\\ci\\cix\\ci.song." + namePart + ".json";

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

    public synchronized String verifyCi(CiService ciService, String fromIndex, int page, int pageSize) {
        CiPipeLine.missCiIds.clear();
//        QPageRequest qPageRequest = new QPageRequest(page ,pageSize);
//        qPageRequest.getSortOr(Sort.by(Sort.Order.asc("id.keyword")));
        Page<Ci> cis = ciRepository.findByFromFileIndexEqualsOrderByOCodeAsc(fromIndex, new QPageRequest(page ,pageSize));
//        Page<Ci> pag = ciRepository.findAll(qPageRequest);
        cis.getContent().forEach(ci -> {
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

    public List<Ci> loadFromFiles(String absDirPath) throws IOException {
        final List<Ci> cisOuter = new LinkedList<>();
        Collection<File> files = FileUtils.listFiles(new File(absDirPath), FileFilterUtils.suffixFileFilter("json"), null);
        if (files != null && !files.isEmpty()) {
            files.stream().forEachOrdered(file -> {
                if (file.isFile() && file.exists() && file.getName().startsWith("ci.song")) {
                    String fileName = file.getName();
                    System.out.println(fileName);
                    ObjectMapper mapper = new ObjectMapper();
                    List<Ci> cis = null;
                    try {
                        cis = mapper.readValue(file, new TypeReference<List<Ci>>() {
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String fromFileIndex = fileName.replaceAll("ci\\.song\\.|\\.json", "");
                    cis.stream().forEach(ci -> {
                        ci.setId("song_ci_" + String.format("%06d", a++));
                        ci.setoCode(ci.getId());
                        ci.setFromFileIndex(fromFileIndex);
                    });
                    cisOuter.addAll(cis);
                }
            });
            return cisOuter;
        }
        return cisOuter;
    }

    public void saveAll(List<Ci> cis) {
        ciRepository.saveAll(cis);
    }

}
