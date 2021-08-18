package com.gee.cms.controller;


import com.gee.cms.entity.CrmBanner;
import com.gee.cms.service.CrmBannerService;
import com.gee.commonutils.R;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author Gee
 * @since 2020-07-22
 */
@RestController
@RequestMapping("educms/bannerfront")
@CrossOrigin
@Slf4j
public class BannerFrontController {
    @Resource
    private CrmBannerService bannerService;

    //查询banner
    @ApiOperation("查询banner")
    @RequestMapping("getAllBanner")
    public R getAllBanner() {
        List<CrmBanner> list = bannerService.selectAllBanner();
        log.info("banner列表" + list);
        return R.ok().data("list", list);
    }
}

