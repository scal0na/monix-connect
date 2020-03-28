import sbt._

object Dependencies {

  object DependencyVersions {
    val AWS = "1.11.749"
    val AwsSdk2Version = "2.10.60"
    val PureConfig = "0.12.3"
    val Monix = "3.1.0"
    val Circe = "0.11.1"
    val TypesafeConfig = "1.3.2"

    val Log4jScala = "11.0"
    val Log4j = "2.10.0"
    val ScalaLogging = "3.9.2"

    val Scalatest = "3.1.1"
    val Scalacheck = "1.14.3"
    val Mockito = "2.18.3"
    val Cats = "2.0.0"
  }

  private val S3Main = Seq(
    "io.monix" %% "monix-reactive"          % DependencyVersions.Monix,
    "com.amazonaws"                         % "aws-java-sdk-core" % DependencyVersions.AWS,
    "com.amazonaws"                         % "aws-java-sdk-s3" % DependencyVersions.AWS,
    "org.typelevel" %% "cats-core"          % DependencyVersions.Cats,
    "com.github.pureconfig" %% "pureconfig" % DependencyVersions.PureConfig
  )

  private val TestDependencies = Seq(
    "org.scalatest" %% "scalatest"   % DependencyVersions.Scalatest,
    "org.scalacheck" %% "scalacheck" % DependencyVersions.Scalacheck,
    "org.mockito"                    % "mockito-core" % DependencyVersions.Mockito
  )

  val S3 = S3Main ++ TestDependencies.map(_ % Test) ++ TestDependencies.map(_ % IntegrationTest)

  private val DynamoDbMain = Seq(
    "io.monix" %% "monix-reactive"          % DependencyVersions.Monix,
    "com.amazonaws"                         % "aws-java-sdk-core" % DependencyVersions.AWS,
    "com.amazonaws"                         % "aws-java-sdk-dynamodb" % DependencyVersions.AWS,
    "software.amazon.awssdk" % "dynamodb" % DependencyVersions.AwsSdk2Version,
    "org.typelevel" %% "cats-core"          % DependencyVersions.Cats,
    "com.github.pureconfig" %% "pureconfig" % DependencyVersions.PureConfig
  )

  val DynamoDb = DynamoDbMain ++ TestDependencies.map(_ % Test) ++ TestDependencies.map(_ % IntegrationTest)
}
