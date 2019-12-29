package com.rizomm.filemanager.business.repositories;

import com.rizomm.filemanager.business.entities.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BucketRepository extends JpaRepository<Bucket, Long> {

    Bucket findByBucketName(String bucketName);
}
