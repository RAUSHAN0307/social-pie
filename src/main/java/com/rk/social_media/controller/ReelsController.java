package com.rk.social_media.controller;

import com.rk.social_media.Exception.ReelException;
import com.rk.social_media.Exception.UserException;
import com.rk.social_media.dto.ReelsDto;
import com.rk.social_media.entity.Reels;
import com.rk.social_media.entity.User;
import com.rk.social_media.service.ReelService;
import com.rk.social_media.service.UserService;
import com.rk.social_media.utility.ReelsMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reel")
public class ReelsController {

    @Autowired
    private ReelService reelService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Create a new reel with title and video")
    @PostMapping("/create")
    public ReelsDto createReels(@RequestParam("title") String title,
                                @RequestParam (value = "file" , required = true) MultipartFile file,
                                @RequestHeader("Authorization") String jwt) throws IOException {
        User user = userService.findUserByJwt(jwt);
        Reels createdReel = reelService.createReels(title,file , user);
        return ReelsMapper.toDto(createdReel);
    }

    @Operation(summary = "Get all reels")
    @GetMapping("/findAll")
    public List<ReelsDto> findAllReels(){
        List<Reels> reels = reelService.findAllReels();
        return reels.stream().map(ReelsMapper::toDto).collect(Collectors.toList());
    }

    @Operation(summary = "Get all reels created by the logged-in user")
    @GetMapping("/usersReel")
    public List<ReelsDto> findUsersReel(@RequestHeader ("Authorization") String jwt) throws ReelException, UserException {
        User user = userService.findUserByJwt(jwt);
        List<Reels> reels = reelService.findUsersReel(user.getId());
        List<ReelsDto> reelsDtos = new ArrayList<>();
        for (Reels reel : reels){
            reelsDtos.add(ReelsMapper.toDto(reel));
        }
        return reelsDtos;
    }
    // here in the video they have taken through the pathvariable

    @Operation(summary = "Like or unlike a reel")
    @PutMapping("/like/{reelId}")
    public ReelsDto likeReel(@PathVariable Integer reelId, @RequestHeader("Authorization") String jwt) throws ReelException, UserException {
        User user = userService.findUserByJwt(jwt);
        Reels liked = reelService.likeReel(reelId, user.getId());
        return ReelsMapper.toDto(liked);
    }

    @Operation(summary = "Delete a reel")
    @DeleteMapping("/delete/{reelId}")
    public String deleteReel(@PathVariable Integer reelId, @RequestHeader("Authorization") String jwt) throws ReelException {
        User user = userService.findUserByJwt(jwt);
        reelService.deleteReel(reelId, user.getId());
        return "Reel deleted successfully";
    }
}
