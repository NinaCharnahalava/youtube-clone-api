package com.programming.techie.youtubeclone1.repositories;

import com.programming.techie.youtubeclone1.models.Video;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoRepository extends MongoRepository<Video, String> {
}
