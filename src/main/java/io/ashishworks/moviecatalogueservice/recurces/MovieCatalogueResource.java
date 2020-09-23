package io.ashishworks.moviecatalogueservice.recurces;

import io.ashishworks.moviecatalogueservice.models.CatologueItem;
import io.ashishworks.moviecatalogueservice.models.Movie;
import io.ashishworks.moviecatalogueservice.models.Rating;
import io.ashishworks.moviecatalogueservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import javax.management.modelmbean.ModelMBeanNotificationInfo;
import javax.management.monitor.MonitorNotification;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogueResource {

    @Autowired
    private RestTemplate restTemplate;

    /*@Autowired
    private WebClient.Builder webClientbuilder;*/

    @RequestMapping("/{userId}")
    public List<CatologueItem> getCatalogue(@PathVariable("userId") String userId){
        //RestTemplate restTemplate = new RestTemplate();
        UserRating ratings = restTemplate.getForObject("http://movie-ratings-service/ratings/users/"+ userId, UserRating.class);

        /*List<Rating> ratings = Arrays.asList(
           new Rating("1234",4),
                new Rating("jabTakhaiJaan",3)
        );*/

        return ratings.getUserRating().stream().map(rating -> {
            // For each movie id, call movie info Service and get details
           Movie movie =  restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
            /*Movie  movie = webClientbuilder.build()
                    .get()
                    .uri("http://localhost:8081/movies/" + rating.getMovieId())
                    .retrieve()
                    .bodyToMono(Movie.class)
                    .block();*/

            return new CatologueItem(movie.getName(),"Sci-Fi",rating.getRating());
        })
                //put them all together
                .collect(Collectors.toList());
        //return Collections.singletonList(new CatologueItem("Transformers","Sci-fi",4));
    }
}
