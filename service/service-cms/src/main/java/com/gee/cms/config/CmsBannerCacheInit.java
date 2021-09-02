package com.gee.cms.config;

import com.gee.cms.entity.CrmBanner;
import com.gee.cms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CmsBannerCacheInit implements CommandLineRunner {
    @Autowired
    private CrmBannerService crmBannerService;

    public static List<CrmBanner> list = new ArrayList<>();

    @Override
    public void run(String... args) {
        list = crmBannerService.selectAllBanner();
    }

    @Scheduled(fixedDelay = 1000 * 60 * 30)
    public void refreshCmsBannerCache() {
        list = crmBannerService.selectAllBanner();
    }
}
