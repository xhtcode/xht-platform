package com.xht.system;

import com.xht.modules.admin.AdminApplication;
import com.xht.modules.admin.notice.dao.SysNoticePermissionDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author xht
 **/
@SpringBootTest(classes = AdminApplication.class)
public class NoticeTest {

    @Autowired
    private SysNoticePermissionDao sysNoticePermissionDao;

    @Test
    public void test() {
        Set<String> objects = new HashSet<>();
        objects.add("aaaa");
        objects.add("bbbb");
        sysNoticePermissionDao.hashPermission(1L, 2L, 3L, objects);
    }
}
