package com.squadbuilder.app.model

import com.squadbuilder.app.services.SquadBuilderProperties
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.{BSONBoolean, BSONDocument, BSONDouble, BSONInteger, BSONLong, BSONString, Macros}

import scala.language.implicitConversions

case class BaseEntity(udid: String) {
  implicit val handler = Macros.handler[BaseEntity]
}

object DBConnection {

  import reactivemongo.api._

  import scala.concurrent.ExecutionContext.Implicits.global

  private lazy val HOST = SquadBuilderProperties.getProperty("mongodb.host")
  private lazy val PORT = SquadBuilderProperties.getProperty("mongodb.port")
  private lazy val USER = SquadBuilderProperties.getProperty("mongodb.user")
  private lazy val PASSWORD = SquadBuilderProperties.getProperty("mongodb.password")
  private val DEFAULT_DB = SquadBuilderProperties.getProperty("mongodb.db")
  private val driver = new MongoDriver
  private val connection = driver.connection(List(s"$HOST:$PORT"))

  val db = connection(DEFAULT_DB)

}

trait BaseEntityCollection[T <: BaseEntity] {

  import com.squadbuilder.app.model.DBConnection._

  implicit def mapToBSONDocument(data: Map[String, Any]*): BSONDocument = BSONDocument(data.flatten.map { i =>
    i._1 -> (i._2 match {
      case s: String => BSONString(s)
      case in: Int => BSONInteger(in)
      case d: Double => BSONDouble(d)
      case l: Long => BSONLong(l)
      case b: Boolean => BSONBoolean(b)
    })
  })

  def findOne(query: BSONDocument)(implicit reader: T) = collection.find(query).one[T]

  def collection = db[BSONCollection](collectionName)

  protected def collectionName: String

}
