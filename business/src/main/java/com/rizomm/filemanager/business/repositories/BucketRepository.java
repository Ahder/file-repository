package com.rizomm.filemanager.business.repositories;

import com.rizomm.filemanager.business.entites.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BucketRepository extends JpaRepository<Bucket, Long> {

    Bucket findByBucketName(String bucketName);
}
