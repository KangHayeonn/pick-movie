package com.pick.movie.back.controller;


import com.pick.movie.back.dto.MovieInfoDto;
import com.pick.movie.back.model.MovieInfo;
import com.pick.movie.back.repository.MovieInfoRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class MovieController {

    @Autowired
    MovieInfoRepository movieInfoRepository;

    // 모든 사람이 접근 가능
    @ApiOperation(value = "movie/by/title", notes = "")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
            @ApiResponse(code = 404, message = "찾을 수 없음"),
            @ApiResponse(code = 409, message = "해당 제목의 영화가 없습니다.")
    })

    @GetMapping("movie/by/title")
    public MovieInfoDto findMovieByTitle(@RequestParam String title, HttpServletResponse response) {
        MovieInfo  movieInfo = movieInfoRepository.findByTitle(title);

        if(movieInfo==null) {
            response.setStatus(409);
            return null;
        }

        MovieInfoDto movieInfoDto =  MovieInfoDto.builder()
                .id(movieInfo.getId())
                .title(movieInfo.getTitle())
                .description(movieInfo.getDescription())
                .posterPath(movieInfo.getPosterPath())
                .rating(movieInfo.getRating())
                .runtime(movieInfo.getRuntime())
                .year(movieInfo.getYear())
                .build();

        return movieInfoDto;

    }

    // 모든 사람이 접근 가능
    @ApiOperation(value = "movie/title/contains", notes = "영화 제목을 포함하는 검색 결과를 모두 반환합니다. 결과가 없으면 빈 배열을 반환해줍니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
            @ApiResponse(code = 404, message = "찾을 수 없음"),
    })
    @GetMapping("movie/title/contains")
    public List<MovieInfoDto> findAllMovieByTitle(@RequestParam String title, HttpServletResponse response) {
        List<MovieInfo>  movieInfos = movieInfoRepository.findByTitleContains(title);
        List<MovieInfoDto> results = new ArrayList<>();

        for (MovieInfo movieInfo : movieInfos) {
            results.add(
            MovieInfoDto.builder()
                    .id(movieInfo.getId())
                    .title(movieInfo.getTitle())
                    .description(movieInfo.getDescription())
                    .posterPath(movieInfo.getPosterPath())
                    .rating(movieInfo.getRating())
                    .runtime(movieInfo.getRuntime())
                    .year(movieInfo.getYear())
                    .build());
        }

        return results;
    }


    @ApiOperation(value = "movie/image", notes = "포스터 이미지를 반환합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
            @ApiResponse(code = 404, message = "찾을 수 없음"),
            @ApiResponse(code = 409, message = "해당 이미지를 찾을 수 없음"),
    })
    @GetMapping("movie/image")
    public ResponseEntity<Resource> getImage(@RequestParam String filePath) throws IOException {



        File file = new File(".");

        File absoluteFile = file.getAbsoluteFile();
        String rootPath = absoluteFile.getPath();

        rootPath = rootPath.replace(".","");

        Resource resource = new FileSystemResource(rootPath+filePath);

        if(resource==null) return new ResponseEntity<>(HttpStatus.CONFLICT);
        Path filePathOrin = null;
        filePathOrin = Paths.get(rootPath+filePath);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", Files.probeContentType(filePathOrin));


        ResponseEntity<Resource> response = new ResponseEntity<Resource>(resource,httpHeaders, HttpStatus.OK);

        return response;

    }


}
