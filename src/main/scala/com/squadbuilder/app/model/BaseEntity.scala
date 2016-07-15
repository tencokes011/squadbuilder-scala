package com.squadbuilder.app.model

import akka.actor.ActorSystem
import akka.event.Logging
import akka.util.Timeout
import com.github.jeroenr.bson.BsonDocument
import com.github.jeroenr.tepkin.MongoClient
import com.squadbuilder.app.services.SquadBuilderProperties
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization.write
import org.json4s.{DefaultFormats, _}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.language.{implicitConversions, postfixOps}

abstract class BaseEntity {
  implicit val formats = DefaultFormats

  val _id: String

  implicit def mongoBsonDocument = BsonDocument.from(parse(mongoJsonString).extract[Map[String, Any]])

  def mongoJsonString = write(this)
}


object DBConnection {

  private lazy val HOST = SquadBuilderProperties.getProperty("mongodb.host")
  private lazy val PORT = SquadBuilderProperties.getProperty("mongodb.port")
  private lazy val USER = SquadBuilderProperties.getProperty("mongodb.user")
  private lazy val PASSWORD = SquadBuilderProperties.getProperty("mongodb.password")
  private val DEFAULT_DB = SquadBuilderProperties.getProperty("mongodb.db")
  val driver = MongoClient(s"mongodb://$HOST:$PORT")
  val db = driver(DEFAULT_DB)
}

trait BaseEntityCollection[T <: BaseEntity] {

  import com.squadbuilder.app.model.DBConnection._

  implicit val formats = DefaultFormats

  implicit val system = ActorSystem()
  val log = Logging(system, getClass)

  implicit val timeout = Timeout(10 minutes)

  implicit def mapToBSONDocument(data: Map[String, Any]): BsonDocument = BsonDocument.from(data)

  def findOne(query: Map[String, Any]) = collection.findOne(query)

  def collection = db(collectionName)

  protected def collectionName: String

}
