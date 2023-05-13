package com.dock.bank.digitalaccount.infra.storage

import com.amazonaws.AmazonServiceException
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.*
import com.dock.bank.digitalaccount.config.S3Configuration
import com.dock.bank.digitalaccount.core.port.storage.FileStorage
import org.joda.time.DateTime
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ResourceLoader
import org.springframework.core.io.support.ResourcePatternResolver
import org.springframework.stereotype.Component
import software.amazon.awssdk.core.exception.SdkClientException
import java.io.*


@Component
class S3Connection(
    @Value("\${cloud.aws.s3.bucket-name}") private val bucketName: String,
    @Value("\${cloud.aws.s3.file-name}") private val fileName: String,
    private val s3Client: AmazonS3
) : FileStorage {

    companion object {
        private val logger = LoggerFactory.getLogger(S3Connection::class.java)
    }

    override suspend fun save(fileContent: String) {
        try {
            s3Client.putObject(bucketName, fileName + " - " + DateTime.now() , fileContent)
        } catch (ex: Exception) {
            logger.error("Error trying to save file into s3 storage", ex)
        }
    }

    override suspend fun read(fileName: String): String? {
        try {
            val fileObject = s3Client.getObject(GetObjectRequest(bucketName, fileName))
            val objectContent = fileObject.objectContent;
            val reader = BufferedReader(InputStreamReader(objectContent))
            val result = StringBuilder()
            var line: String?
            try {
                while (reader.readLine().also { line = it } != null) {
                    result.append(line + "\r\n")
                }
                return result.toString()
            } catch (e: IOException) {
                throw Exception("Error reading S3 content", e)
            }
        } catch (e: AmazonS3Exception) {
            throw Exception("Error connection to s3 storage.", e)
        }
    }


    override suspend fun remove(fileName: String) {
        try {
            logger.info(String.format("Delete file from AWS S3. Object: [%s]", fileName))
            s3Client.deleteObject(DeleteObjectRequest (bucketName, fileName))
        } catch (e: AmazonServiceException) {
            e.printStackTrace()
        } catch (e: SdkClientException) {
            e.printStackTrace()
        }
    }
}