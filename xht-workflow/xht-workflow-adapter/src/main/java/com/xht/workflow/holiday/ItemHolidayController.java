package com.xht.workflow.holiday;

import com.xht.framework.common.domain.R;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.validation.Groups;
import com.xht.workflow.holiday.domain.form.ItemHolidayForm;
import com.xht.workflow.holiday.domain.query.ItemHolidayPageQuery;
import com.xht.workflow.holiday.domain.response.ItemHolidayResponse;
import com.xht.workflow.holiday.service.IItemHolidayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 描述： 流程事项-请假单控制器
 *
 * @author xht
 **/
@Tag(name = "请假单管理", description = "流程事项-请假单管理")
@Slf4j
@RestController
@RequestMapping("/workflow/item/holiday")
@RequiredArgsConstructor
public class ItemHolidayController {

    private final IItemHolidayService itemHolidayService;

    /**
     * 创建请假单
     *
     * @param form 请假单信息
     */
    @Operation(summary = "创建请假单")
    @PostMapping("/create")
    public R<Void> create(@Validated(value = {Groups.Create.class}) @RequestBody ItemHolidayForm form) {
        itemHolidayService.create(form);
        return R.ok().build();
    }

    /**
     * 删除请假单
     *
     * @param id 请假单ID
     */
    @Operation(summary = "删除请假单")
    @PostMapping("/remove/{id}")
    public R<Void> remove(@PathVariable Long id) {
        itemHolidayService.removeById(id);
        return R.ok().build();
    }

    /**
     * 修改请假单
     *
     * @param id   请假单ID
     * @param form 请假单信息
     */
    @Operation(summary = "修改请假单")
    @PostMapping("/update/{id}")
    public R<Void> updateById(@PathVariable Long id, @Validated(value = {Groups.Update.class}) @RequestBody ItemHolidayForm form) {
        itemHolidayService.updateById(id, form);
        return R.ok().build();
    }

    /**
     * 获取请假单详情
     *
     * @param id 请假单ID
     * @return 请假单详情
     */
    @Operation(summary = "获取请假单详情")
    @GetMapping("/get/{id}")
    public R<ItemHolidayResponse> findById(@PathVariable Long id) {
        return R.ok().build(itemHolidayService.findById(id));
    }

    /**
     * 分页查询请假单
     *
     * @param query 请假单查询参数
     * @return 请假单分页信息
     */
    @Operation(summary = "分页查询请假单")
    @GetMapping("/page")
    public R<PageResponse<ItemHolidayResponse>> findPageList(ItemHolidayPageQuery query) {
        return R.ok().build(itemHolidayService.findPageList(query));
    }

}
