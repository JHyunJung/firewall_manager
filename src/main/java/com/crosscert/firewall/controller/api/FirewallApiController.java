package com.crosscert.firewall.controller.api;

import com.crosscert.firewall.Utill.RtnValue;
import com.crosscert.firewall.dto.FireWallDTO;
import com.crosscert.firewall.entity.FireWall;
import com.crosscert.firewall.service.FireWallService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/firewall")
public class FirewallApiController {

    private final FireWallService fireWallService;


    @ApiOperation(value = "firewall 등록")
    @PostMapping("")
    public ResponseEntity<FireWallDTO.Response.Create> save(@RequestBody FireWallDTO.Request.Create fireWall) {


        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @GetMapping("/list")
//    public RtnValue<Page<FireWall>> getFirewall(Pageable pageable) {
//        return new RtnValue(firewallService.searchPage( search,  pageable), true, "firewall 조회");
//    }
//
//    /**
//     * firewall 상세 조회
//     */
//    @ApiOperation(value = "firewall 상세 조회")
//    @GetMapping("/{id}")
//    public RtnValue<FireWall> detailFireWall(@PathVariable("id") String id) {
//        return  new RtnValue(firewallService.detailFireWall(Long.valueOf(id)), true, "FireWall 상세 조회");
//    }

    /**
     * firewall 등록
     */


    /**
     * firewall 수
     */
//    @ApiOperation(value = "firewall 수정")
//    @PutMapping("")
//    public RtnValue<FireWall> updateFireWall(@RequestBody FireWall fireWall) {
//        return  new RtnValue(firewallService.updateFireWall(fireWall), true, "FireWall 등록");
//    }
//
//    @ApiOperation(value = "firewall 삭제")
//    @DeleteMapping("/{id}")
//    public RtnValue<FireWall> deleteFireWall(@PathVariable("id") String id) {
//
//        return  new RtnValue(firewallService.deleteFireWall(id), true, "FireWall 삭제");
//    }


}