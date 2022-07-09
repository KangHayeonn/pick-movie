package com.pick.movie.back.movieparser;
import com.pick.movie.back.model.HashtagRelationMovie;
import com.pick.movie.back.model.MovieInfo;
import com.pick.movie.back.model.TagList;
import com.pick.movie.back.repository.HashtagRelationMovieRepository;
import com.pick.movie.back.repository.MovieInfoRepository;
import com.pick.movie.back.repository.TagRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;


@Controller
public class GetMovieInfo {


    private final TagRepository tagRepository;
    private final MovieInfoRepository movieInfoRepository;
    private final HashtagRelationMovieRepository hashtagRelationMovieRepository;

    @Autowired
    public GetMovieInfo(TagRepository tagRepository, MovieInfoRepository movieInfoRepository, HashtagRelationMovieRepository hashtagRelationMovieRepository) {
        this.tagRepository = tagRepository;
        this.movieInfoRepository = movieInfoRepository;
        this.hashtagRelationMovieRepository = hashtagRelationMovieRepository;
    }

    @GetMapping("/go")
    public String goAction() throws Exception{
        go();
        return "<h1>home</h1>";
    }


    public void go() throws Exception {
        for (int i = 1; i <= 1; i++) {
            getUpdate(20,i);
        }

    }

    public void getUpdate(int limit, int page) throws Exception{

        URL url = new URL("https://yts.mx/api/v2/list_movies.json?sort_by=download_count&limit="+limit+"&page="+page);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuffer stringBuffer = new StringBuffer();
        String inputLine;

        //api request 받기
        while ((inputLine = bufferedReader.readLine()) != null)  {
            stringBuffer.append(inputLine);
        }

        bufferedReader.close();

        //Json으로 변환
        String response = stringBuffer.toString();
        JSONObject jo = new JSONObject(response);
        JSONObject jodata = jo.getJSONObject("data");


        JSONArray movies = jodata.getJSONArray("movies");
        //System.out.println(movies.get(0).toString());
        String[] parseKeyset = {"title", "year", "genres", "rating", "runtime", "description_full"};

        for (Object movie : movies) {
            JSONObject moviedata = new JSONObject(movie.toString());

            Optional<MovieInfo> titleExist = Optional.ofNullable(movieInfoRepository.findByTitle((String) moviedata.get("title")));
            if(titleExist.isPresent()) continue;

            MovieInfo movieInfo = new MovieInfo();
            movieInfo.setTitle((String)moviedata.get("title"));     //제목 설정.
            movieInfo.setYear((int)moviedata.get("year"));   //연도 설정     int형이였음.
            double rating = Double.valueOf(String.valueOf(moviedata.get("rating")));        //int으로 들어오는 예외발생.
            movieInfo.setRating(rating);   //평점 설정
            movieInfo.setRuntime((int)moviedata.get("runtime")); //상영 시간 설정.  int형이였음.
            movieInfo.setDescription((String)moviedata.get("description_full"));   //줄거리 설정
            //medium 커버 이미지 파싱, 저장.
            String imagePath = saveImage((String)moviedata.get("medium_cover_image"),(String)moviedata.get("title"));
            movieInfo.setPosterPath(imagePath);

            //영화저장
            movieInfoRepository.save(movieInfo);



            JSONArray tags = moviedata.getJSONArray("genres");  //태그 배열 저장.

            for(Object tag:tags){
                Optional<TagList> byTag = Optional.ofNullable(tagRepository.findByTag((String) tag));

                byTag.ifPresentOrElse(t ->{     //영화와 태그 관계도 저장.
                    HashtagRelationMovie hashtagRelationMovie = new HashtagRelationMovie();
                    hashtagRelationMovie.setMovieInfo(movieInfo);
                    hashtagRelationMovie.setTag(t);
                    hashtagRelationMovieRepository.save(hashtagRelationMovie);
                },() ->{        //새로 등장한 태그는 새로 추가 후 저장.
                    TagList newTag = new TagList();
                    newTag.setTag((String)tag);
                    tagRepository.save(newTag);
                    System.out.println((String)tag+"저장 성공!");
                    HashtagRelationMovie hashtagRelationMovie = new HashtagRelationMovie();
                    hashtagRelationMovie.setMovieInfo(movieInfo);
                    hashtagRelationMovie.setTag(newTag);
                    hashtagRelationMovieRepository.save(hashtagRelationMovie);
                });

            }
        }
    }

    private String saveImage(String strUrl, String fileName) throws IOException {

        URL url = null;
        InputStream in = null;
        OutputStream out = null;
        String filePath="";

        File newfile = new File("images");

        if(!newfile.exists()){   //폴더 존재하면 생성 X
            if (newfile.mkdirs()){
                System.out.println("디렉토리 생성 성공");
            }else{
                System.out.println("디렉토리 생성 실패");
            }
        }

        try {
            url = new URL(strUrl);
            in = url.openStream();
            filePath = "images/"+fileName+".jpg";
            out = new FileOutputStream(filePath); //저장경로

            while(true){
                //이미지를 읽어온다.
                int data = in.read();
                if(data == -1){
                    break;
                }
                //이미지를 쓴다.
                out.write(data);
            }
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(in != null){in.close();}
            if(out != null){out.close();}
        }
        return filePath;
    }
}
