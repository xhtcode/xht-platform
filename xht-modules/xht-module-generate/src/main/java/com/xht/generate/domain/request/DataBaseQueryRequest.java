package com.xht.generate.domain.request;

import com.xht.framework.core.domain.request.PageQueryRequest;
import lombok.Data;

/**
 * 数据库查询
 *
 * @author xht
 **/
@Data
public class DataBaseQueryRequest extends PageQueryRequest {

    private String tableName;
}
