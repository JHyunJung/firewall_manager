package com.crosscert.firewall.controller.api;

import com.crosscert.firewall.dto.FireWallDTO;
import com.crosscert.firewall.entity.FireWall;
import com.crosscert.firewall.entity.Member;
import com.crosscert.firewall.service.FireWallService;
import com.crosscert.firewall.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class FireWallApiController {

    private final FireWallService fireWallService;
    private final MemberService memberService;

    @PostMapping("/firewall")
    public ResponseEntity<FireWallDTO.Response.Create> save(@RequestBody FireWallDTO.Request.Create requestDTO) {

        Member newMember = memberService.findById(requestDTO.getMemberId());
        FireWall newFireWall = FireWall.toEntity(requestDTO, newMember);
        fireWallService.save(newFireWall);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/firewallPage")
    public ResponseEntity<Page<FireWall>> fireWallPage(@RequestParam(value = "page") int page, @RequestParam(value = "size") int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<FireWall> result = fireWallService.findAll(pageRequest);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }




}