package org.superbiz.moviefun;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;
import org.superbiz.moviefun.albumsapi.AlbumsClient;
import org.superbiz.moviefun.moviesapi.MoviesClient;

@Configuration
public class ClientConfiguration {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${albums.url}") String albumsUrl;
    @Value("${movies.url}") String moviesUrl;

    @Bean
    public AlbumsClient albumsClient(RestOperations restOperations) {
        logger.error("albumsUrl {}",albumsUrl);
        return new AlbumsClient(albumsUrl, restOperations);
    }

    @Bean
    public MoviesClient moviesClient(RestOperations restOperations) {
        logger.error("moviesUrl {}",moviesUrl);
        return new MoviesClient(moviesUrl, restOperations);
    }
}
