package com.xht.demo;

import cn.hutool.core.io.FileUtil;
import com.xht.boot.swagger.EnableCustomSwagger;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

/**
 * 启动类
 *
 * @author xht
 **/
@EnableCustomSwagger
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) throws Exception {
        // SpringApplication.run(DemoApplication.class, args);
        //
        // GridFsTemplate gridFsTemplate = SpringContextUtils.getBean(GridFsTemplate.class);
        // List<String> strings = FileUtil.readUtf8Lines("E:\\MyProject\\xht-platform\\xht-demo\\src\\main\\java\\com\\xht\\demo\\1.txt");
        // for (String objectId : strings) {
        //     try {
        //         String[] split = objectId.split("@@@@@@@@");
        //         System.out.println(split[0]);
        //         GridFSFile gridFSFile = gridFsTemplate.findOne(new Query().addCriteria(Criteria.where("filename").is(split[0])));
        //         GridFsResource resource = gridFsTemplate.getResource(gridFSFile);
        //         InputStream inputStream = resource.getInputStream();
        //         String localFilePath = "C:\\Users\\admin\\Desktop\\mongodb\\" + split[1];
        //         IoUtil.write(new FileOutputStream(localFilePath), true, IoUtil.readBytes(inputStream));
        //         System.out.println(split[0] + "成功");
        //     } catch (Exception e) {
        //         e.printStackTrace();
        //     }
        // }
        for (File file : FileUtil.loopFiles("C:\\Users\\admin\\Desktop\\files")) {
            System.out.println(String.format("./mongorestore  -u root -p 'rootPwd' --authenticationDatabase admin --port 27017 --db moa --collection fs.files /data/mongodb/import229roll/files/%s", file.getName()));
        }


        System.out.println("------");
        for (File file : FileUtil.loopFiles("C:\\Users\\admin\\Desktop\\chunks")) {
            System.out.println(String.format(
                    "./mongorestore  -u root -p 'rootPwd' --authenticationDatabase admin --port 27017 --db moa --collection fs.chunks /data/mongodb/import229roll/chunks/%s", file.getName()));
        }
    }
}