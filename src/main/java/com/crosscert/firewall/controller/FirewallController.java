package com.crosscert.firewall.controller;

import com.crosscert.firewall.entity.FireWall;
import com.crosscert.firewall.service.FirewallService;
import com.crosscert.firewall.Utill.RtnValue;
import com.crosscert.firewall.entity.search.SearchFirewall;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Api(description = "firewall 관리")
@RestController
@RequestMapping(value = "/api/firewall")
public class FirewallController {

    @Autowired
    FirewallService firewallService;

    /**
     * firewall 조회
     */
    @ApiOperation(value = "firewall 조회")
    @GetMapping("/list")
    public RtnValue<Page<FireWall>> getFirewall(SearchFirewall search, Pageable pageable) {
        return  new RtnValue(firewallService.searchPage( search,  pageable), true, "firewall 조회");
    }

    /**
     * firewall 상세 조회
     */
    @ApiOperation(value = "firewall 상세 조회")
    @GetMapping("/{id}")
    public RtnValue<FireWall> detailFireWall(@PathVariable("id") String id) {
        return  new RtnValue(firewallService.detailFireWall(Long.valueOf(id)), true, "FireWall 상세 조회");
    }

    /**
     * firewall 등록
     */
    @ApiOperation(value = "firewall 등록")
    @PostMapping("")
    public RtnValue<FireWall> saveFireWall(@RequestBody FireWall fireWall) {
        return  new RtnValue(firewallService.saveFireWall(fireWall), true, "FireWall 등록");
    }

    /**
     * firewall 수
     */
    @ApiOperation(value = "firewall 수정")
    @PutMapping("")
    public RtnValue<FireWall> updateFireWall(@RequestBody FireWall fireWall) {
        return  new RtnValue(firewallService.updateFireWall(fireWall), true, "FireWall 등록");
    }

    @ApiOperation(value = "firewall 삭제")
    @DeleteMapping("/{id}")
    public RtnValue<FireWall> deleteFireWall(@PathVariable("id") String id) {

        return  new RtnValue(firewallService.deleteFireWall(id), true, "FireWall 삭제");
    }


}