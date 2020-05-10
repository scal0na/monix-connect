package monix.connect.gcs

import java.io.FileInputStream
import java.nio.file.Path

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.storage.Storage._
import com.google.cloud.storage.{StorageOptions, Storage => GoogleStorage}
import monix.connect.gcs.configuration.BucketConfig
import monix.connect.gcs.utiltiies.Paging
import monix.eval.Task
import monix.reactive.Observable

final class Storage(underlying: GoogleStorage) extends Paging {

  /**
   * Creates a new [[Bucket]] from the give config and options.
   */
  def createBucket(config: BucketConfig, options: BucketTargetOption*): Task[Bucket] =
    Task(underlying.create(config.getBucketInfo, options: _*))
      .map(Bucket.apply)

  /**
   * Returns the specified bucket or None if it doesn't exist.
   */
  def getBucket(name: String, options: BucketGetOption*): Task[Option[Bucket]] = {
    Task(underlying.get(name, options: _*)).map { optBucket =>
      Option(optBucket).map(Bucket.apply)
    }
  }

  /**
   * Returns an [[Observable]] of all buckets attached to this storage instance.
   */
  def listBuckets(options: BucketListOption *): Observable[Bucket] =
    walk(Task(underlying.list(options: _*))).map(Bucket.apply)
}

object Storage {

  def create(): Task[Storage] = {
    Task(StorageOptions.getDefaultInstance.getService).map(new Storage(_))
  }

  def create(projectId: String, credentials: Path): Task[Storage] = {
    Task {
      StorageOptions
        .newBuilder()
        .setProjectId(projectId)
        .setCredentials(GoogleCredentials.fromStream(new FileInputStream(credentials.toFile)))
        .build()
        .getService
    }.map(new Storage(_))
  }
}