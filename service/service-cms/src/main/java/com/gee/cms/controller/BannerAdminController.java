package com.gee.cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gee.cms.entity.CrmBanner;
import com.gee.cms.service.CrmBannerService;
import com.gee.commonutils.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author Gee
 * @since 2020-07-22
 */
@RestController
@RequestMapping("/educms/banneradmin")
@CrossOrigin
@Slf4j
public class BannerAdminController {
    @Resource
    private CrmBannerService crmBannerService;

    //分页查询banner
    @ApiOperation("分页查询banner")
    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@ApiParam("当前页数") @PathVariable Long page, @ApiParam("每页记录数") @PathVariable Long limit) {
        Page<CrmBanner> bannerPage = new Page<>(page, limit);
        crmBannerService.page(bannerPage, null);
        log.info("banner" + bannerPage.getRecords());
        return R.ok().data("items", bannerPage.getRecords()).data("total", bannerPage.getTotal());
    }

    //添加Banner
    @ApiOperation("添加Banner")
    @PostMapping("addBanner")
    public R addBanner(@ApiParam("Banner对象") @RequestBody CrmBanner crmBanner) {
        log.info("banner信息" + crmBanner);
        crmBannerService.save(crmBanner);
        return R.ok();
    }

    //获取Banner
    @ApiOperation(value = "获取Banner")
    @GetMapping("get/{id}")
    public R get(@ApiParam("bannerId") @PathVariable String id) {
        log.info("bannerId" + id);
        CrmBanner banner = crmBannerService.getById(id);
        return R.ok().data("item", banner);
    }

    //修改Banner
    @ApiOperation("修改Banner")
    @PutMapping("update")
    public R updateById(@ApiParam("Banner对象") @RequestBody CrmBanner banner) {
        log.info("banner信息" + banner);
        crmBannerService.updateById(banner);
        return R.ok();
    }

    //删除Banner
    @ApiOperation("删除Banner")
    @DeleteMapping("remove/{id}")
    public R remove(@ApiParam("bannerId") @PathVariable String id) {
        log.info("bannerId" + id);
        crmBannerService.removeById(id);
        return R.ok();
    }
}

