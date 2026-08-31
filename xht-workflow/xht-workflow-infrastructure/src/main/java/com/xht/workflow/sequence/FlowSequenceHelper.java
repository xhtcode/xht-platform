package com.xht.workflow.sequence;

import com.xht.workflow.common.exception.WorkFlowException;
import com.xht.workflow.sequence.dao.FlowSequenceDao;
import com.xht.workflow.sequence.entity.FlowSequenceEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 描述： 序列工具类
 *
 * @author xht
 **/
@Slf4j
@Component
@SuppressWarnings({"all"})
@RequiredArgsConstructor
public class FlowSequenceHelper {

    private final FlowSequenceDao flowSequenceDao;

    // ==================== 正则字符串常量（split分割用） ====================
    /**
     * 分割符：{ } - 三种符号分割
     */
    @SuppressWarnings("all")
    private static final String SPLIT_REGEX_BRACKET_DASH = "\\{|\\}|\\-";

    // ==================== 预编译Pattern匹配器（业务片段判断） ====================
    /**
     * 匹配包含日期格式：yyyy / yyyyMM / yyyyMMdd / yyMMdd / MMdd 的片段
     */
    private static final Pattern PATTERN_DATE_FRAGMENT = Pattern.compile(".*(yyyy|yyyyMM|yyyyMMdd|yyMMdd|MMdd).*");
    /**
     * 匹配包含 yy 的片段
     */
    private static final Pattern PATTERN_YY_FRAGMENT = Pattern.compile(".*(yy).*");
    /**
     * 匹配包含 N 的数字占位片段
     */
    private static final Pattern PATTERN_NUM_N_FRAGMENT = Pattern.compile(".*N.*");
    /**
     * 匹配纯数字固定值片段
     */
    private static final Pattern PATTERN_FIXED_NUM_FRAGMENT = Pattern.compile("\\d*$");

    // ==================== 预编译Pattern匹配器（占位符替换） ====================
    /**
     * 匹配 {yyyy|yyyyMM|yyyyMMdd|yyMMdd|MMdd} 日期占位符（忽略大小写）
     */
    private static final Pattern PATTERN_DATE_PLACEHOLDER = Pattern.compile("\\{(yyyy|yyyyMM|yyyyMMdd|yyMMdd|MMdd)\\}", Pattern.CASE_INSENSITIVE);
    /**
     * 匹配 {yy} 年份简写占位符（忽略大小写）
     */
    private static final Pattern PATTERN_YY_PLACEHOLDER = Pattern.compile("\\{yy\\}", Pattern.CASE_INSENSITIVE);
    /**
     * 匹配 {N} / {N数字} 数字序列号占位符
     */
    private static final Pattern PATTERN_NUM_PLACEHOLDER = Pattern.compile("\\{N\\d*\\}");

    /**
     * 根据序列号代码生成序列号
     *
     * @param sequenceCode 序列号代码
     * @return 序列号
     */
    @Transactional(rollbackFor = Exception.class)
    public String sequenceFormat(final String sequenceCode) {
        // 根据code获取记录中的sequenceFormat（序列号格式）、newCurrentValue（当前值）、steppingValue（步进值）、maxValue（最大值）
        FlowSequenceEntity entity = flowSequenceDao.findBySequenceCode(sequenceCode);
        String sequenceFormat = entity.getSequenceFormat();
        int newCurrentValue = entity.getCurrentValue();
        int steppingValue = entity.getSteppingValue();
        int oldCurrentValue = entity.getCurrentValue();
        String currentValStr = "";
        String dataFormat = "";
        String numFormat = "";
        String dateStr = "";
        String resultStr;
        String[] numFormatArr;
        List<String> position = new ArrayList<>();

        // 使用抽取后的分割常量
        String[] arr = sequenceFormat.split(SPLIT_REGEX_BRACKET_DASH);

        for (String s : arr) {
            if (Objects.equals("", s)) {
                continue;
            }
            // 使用预编译Pattern匹配
            if (PATTERN_DATE_FRAGMENT.matcher(s).matches()) {
                dataFormat = s;
                position.add("dataFormat");
            } else if (PATTERN_YY_FRAGMENT.matcher(s).matches()) {
                dataFormat = "yyyy-MM-dd";
                position.add("yearFormat");
            } else if (PATTERN_NUM_N_FRAGMENT.matcher(s).matches()) {
                numFormat = s;
                position.add("numFormat");
            } else if (PATTERN_FIXED_NUM_FRAGMENT.matcher(s).matches()) {
                position.add("fixedValue");
            }
        }

        if (StringUtils.hasText(numFormat)) {
            numFormatArr = numFormat.split("N");
            if (numFormatArr.length > 1) {
                int bit = Integer.parseInt(numFormatArr[1]);
                newCurrentValue += steppingValue;
                currentValStr = String.format("%0" + bit + "d", newCurrentValue);
            } else {
                newCurrentValue += steppingValue;
                currentValStr = String.valueOf(newCurrentValue);
            }
            entity.setCurrentValue(newCurrentValue);

            log.info("{}==============>{}", Thread.currentThread().getName(), oldCurrentValue);
            boolean rs = flowSequenceDao.updateSequence(entity.getId(), oldCurrentValue, newCurrentValue);
            if (!rs) {
                throw new WorkFlowException("流程序列格式化失败!");
            }
        }
        if (StringUtils.hasText(dataFormat)) {
            Date getDate = Calendar.getInstance().getTime();
            dateStr = new SimpleDateFormat(dataFormat).format(getDate);
        }
        resultStr = sequenceFormat;
        for (String item : position) {
            Matcher matcher;
            switch (item) {
                case "dataFormat" -> {
                    matcher = PATTERN_DATE_PLACEHOLDER.matcher(resultStr);
                    resultStr = matcher.replaceAll(dateStr);
                }
                case "yearFormat" -> {
                    matcher = PATTERN_YY_PLACEHOLDER.matcher(resultStr);
                    resultStr = matcher.replaceAll(dateStr.substring(2, 4));
                }
                case "numFormat" -> {
                    matcher = PATTERN_NUM_PLACEHOLDER.matcher(resultStr);
                    resultStr = matcher.replaceAll(currentValStr);
                }
            }
        }
        return resultStr;
    }
}