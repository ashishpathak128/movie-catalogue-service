package io.ashishworks.moviecatalogueservice.recurces;

import io.ashishworks.moviecatalogueservice.models.CatologueItem;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogueResource {

    @RequestMapping("/{userId}")
    public List<CatologueItem> getCatalogue(@PathVariable String userId){
        return Collections.singletonList(new CatologueItem("Transformers","Sci-fi",4));
    }
}
